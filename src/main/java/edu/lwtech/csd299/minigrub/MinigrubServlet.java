//Owned by Beni Ungur
package edu.lwtech.csd299.minigrub;

import java.io.*;
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
        addDemoData();

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "index";

        String template = "";
        RestaurantPojo restaurant = null;
        Map<String, Object> model = new HashMap<>();

        //TODO: Add more URL commands to the servlet
        switch (command) {

            case "index":
                template = "index.ftl";
                
                break;

            case "menu":
                int id = Integer.parseInt(request.getParameter("id"));
                restaurant = restaurantMemoryDao.getByID(id);

                template = "menu.ftl";

                model.put("restaurant", restaurant);
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

        String template = "";
        Map<String, Object> model = new HashMap<>();

        switch (command) {
            case "register":
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                UserPojo user = new UserPojo(-1, email, email, email, password);
                break;
        }

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

    private void addDemoData() {
        logger.debug("Creating sample RestaurantPojos...");
        
        String pizzaPlaceDesc = "Best local pizza restaurant!";
        String tacoPlaceDesc = "Best tacos around! Me gusta!";
        String ricePlaceDesc = "General Tso's anyone?";

        HashMap<String, Double> pizzaMenu = new HashMap<>();
        pizzaMenu.put("Pepperoni Pizza", 10.99);
        pizzaMenu.put("Cheese pizza", 8.99);

        HashMap<String, Double> tacoMenu = new HashMap<>();
        tacoMenu.put("Chalupa", 3.99);
        tacoMenu.put("Burrito", 5.99);

        HashMap<String, Double> riceMenu = new HashMap<>();
        riceMenu.put("General Tso's", 9.99);
        riceMenu.put("White Rice", 1.99);


        restaurantMemoryDao.insert(new RestaurantPojo(1000, "PizzaPlace", pizzaPlaceDesc, pizzaMenu));
        restaurantMemoryDao.insert(new RestaurantPojo(1001, "TacosPlace", tacoPlaceDesc, tacoMenu));
        restaurantMemoryDao.insert(new RestaurantPojo(1002, "RicePlace", ricePlaceDesc, riceMenu));
        
        logger.info("...menu items inserted");
    }

}
