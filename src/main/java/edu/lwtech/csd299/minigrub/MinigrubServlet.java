//Owned by Beni Ungur
package edu.lwtech.csd299.minigrub;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.log4j.*;
import freemarker.core.*;
import freemarker.template.*;

@WebServlet(name = "MinigrubServlet", urlPatterns = {"/hub"}, loadOnStartup = 0)
public class MinigrubServlet extends HttpServlet {

    private static final long serialVersionUID = 2020111122223333L;
    private static final Logger logger = Logger.getLogger(MinigrubServlet.class);

    private static final String TEMPLATE_DIR = "/WEB-INF/classes/templates";
    private static final Configuration freemarker = new Configuration(Configuration.getVersion());

    private DAO<RestaurantPojo> restaurantMemoryDao = null;
    private DAO<UserPojo> userMemoryDao = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.warn("=========================================");
        logger.warn("  MinigrubServlet init() started");
        logger.warn("    http://localhost:8080/minigrub/hub");
        logger.warn("=========================================");

        logger.info("Getting real path for templateDir");
        String templateDir = config.getServletContext().getRealPath(TEMPLATE_DIR);
        logger.info("...real path is: " + templateDir);
        
        logger.info("Initializing Freemarker.templateDir = " + templateDir);
        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            logger.error("Template directory not found in directory: " + templateDir, e);
        }
        logger.info("Successfully Loaded Freemarker");
        
        restaurantMemoryDao = new RestaurantPojoMemoryDAO();
        userMemoryDao = new UserPojoMemoryDAO();
        addDemoData();
        

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "index";

        int owner = 0;
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if(session != null) {
            try {
                owner = (Integer)session.getAttribute("owner");
                loggedIn = true;
            } catch (NumberFormatException e) {
                owner = 0;
                loggedIn = false;
            }
        }

        HashMap<String, Integer> rNameID = new HashMap<>();
        for (RestaurantPojo rp : restaurantMemoryDao.getAll()) {
            rNameID.put(rp.getName(), rp.getID());
        }

        String template = "";
        RestaurantPojo restaurant = null;
        Map<String, Object> model = new HashMap<>();
        model.put("owner", owner);
        model.put("loggedIn", loggedIn);

        switch (command) {

            case "index":
                template = "index.ftl";
                model.put("restaurants", rNameID);
                break;

            case "menu":
                restaurant = restaurantMemoryDao.getByID(parseInt(request.getParameter("id")));

                model.put("restaurantMenu", restaurant.getMenu());
                model.put("restaurantName", restaurant.getName());
                model.put("restaurantDesc", restaurant.getDescription());

                template = "menu.ftl";
                
                break;

            case "register":
                template = "register.ftl";
                break;
            
            case "checkout":
                template = "checkout.ftl";
                break;

            case "signin":
                template = "signin.ftl";
                break;

            default:
                logger.debug("Unknown GET command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e)  {
                    logger.error("IO Error: ", e);
                }
                return;
        }
        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - POST " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "";

        int owner = 0;
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                owner = Integer.parseInt((String)session.getAttribute("owner"));
                loggedIn = true;
            } catch (NumberFormatException e) {
                owner = 0;
                loggedIn = false;
            }
        }

        String message = "";
        String template = "";
        Map<String, Object> model = new HashMap<>();
        String email = "";
        String password = "";
        Map<String, Integer> cart = new HashMap<>();
        UserPojo user;

        switch (command) {
            case "index":
                break;

            case "signin":
                email = request.getParameter("email");
                password = request.getParameter("password");

                user = userMemoryDao.search(email);
                if (user == null) {
                    message = "We do not have that email on file. Please try again.<br /><a href='?cmd=login'>Log In</a>";
                    model.put("loggedIn", loggedIn);
                    model.put("message", message);
                    break;
                }

                if (user.getPassword().equals(password)) {
                    owner = user.getID();
                    loggedIn = true;

                    session = request.getSession(true);
                    session.setAttribute("owner", owner);

                    message = "You have successfully logged in!<br /><a href='?cmd=index'>View Restaurants</a>";
                } else {
                    message = "Incorrect password. Please try again.<br /><a href='?cmd=login'>Log In</a>";
                } 

                model.put("loggedIn", loggedIn);
                model.put("message", message);
                break;
            case "register":
                email = request.getParameter("email");
                password = request.getParameter("password");

                user = userMemoryDao.search(email);
                if (user != null) {
                    message = "That username is already registered here. Please use a different username. <br /> <a href='?cmd=login'>Log In</a>";
                    model.put("message", message);
                    break;
                }

                user = new UserPojo(email, password);
                userMemoryDao.insert(user);

                message = "Welcome to Minigrub.com! You are now registered. Please <a href='?cmd=login'> log in</a>.";
                model.put("message", message);
                break;

            case "menu":
                user = userMemoryDao.getByID(owner);
                user.loadCart(request.getParameter("${restaurantMenu[item]}"), parseInt(request.getParameter("quantity")));

                message = "Successfully added items to cart. <a href='?cmd=checkout'>Go to cart</a>.";
                model.put("message", message);
                break;

            default:
                logger.info("Unknown POST command received: " + command);

                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    logger.error("IO Error: ", e);
                }
                return;
        }

        model.put("message", message);

        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- POST " + request.getRequestURI() + " " + time + "ms");
    }
    
    @Override
    public void destroy() {
        logger.warn("-----------------------------------------");
        logger.warn("  MinigrubServlet destroy() completed");
        logger.warn("-----------------------------------------");
    }

    @Override
    public String getServletInfo() {
        return "minigrub Servlet";
    }

    // ========================================================================

    private void processTemplate(HttpServletResponse response, String template, Map<String, Object> model) {
        logger.debug("Processing Template: " + template);
        
        try (PrintWriter out = response.getWriter()) {
            Template view = freemarker.getTemplate(template);
            view.process(model, out);
        } catch (TemplateException | MalformedTemplateNameException | ParseException e) {
            logger.error("Template Error: ", e);
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        } 
    }
    
    private int parseInt(String s) {
        int i = -1;
        if (s != null) {
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                i = -2;
            }
        }
        return i;
    }

    private void addDemoData() {
        logger.debug("Creating sample RestaurantPojos...");
        
        String pizzaPlaceDesc = "Best local pizza restaurant!";
        String burgerPlaceDesc = "Come get your burg on!";
        String tacoPlaceDesc = "Best tacos around! Me gusta!";
        String ricePlaceDesc = "General Tso's anyone?";

        HashMap<String, Double> pizzaMenu = new HashMap<>();
        pizzaMenu.put("Pepperoni Pizza", 10.99);
        pizzaMenu.put("Cheese pizza", 8.99);

        HashMap<String, Double> burgerMenu = new HashMap<>();
        burgerMenu.put("Single Cheeseburger", 2.99);
        burgerMenu.put("Big Mick", 5.99);

        HashMap<String, Double> tacoMenu = new HashMap<>();
        tacoMenu.put("Chalupy", 3.99);
        tacoMenu.put("Burrito", 5.99);

        HashMap<String, Double> riceMenu = new HashMap<>();
        riceMenu.put("General Kim's", 9.99);
        riceMenu.put("White Rice", 1.99);


        restaurantMemoryDao.insert(new RestaurantPojo(-1, "Pizzeria", pizzaPlaceDesc, pizzaMenu));
        restaurantMemoryDao.insert(new RestaurantPojo(-1, "Burger Joint", burgerPlaceDesc, burgerMenu));
        restaurantMemoryDao.insert(new RestaurantPojo(-1, "Taco Del Goodness", tacoPlaceDesc, tacoMenu));
        restaurantMemoryDao.insert(new RestaurantPojo(-1, "Rice Is Nice", ricePlaceDesc, riceMenu));
        
        logger.info("...menu items inserted");
    }

}
