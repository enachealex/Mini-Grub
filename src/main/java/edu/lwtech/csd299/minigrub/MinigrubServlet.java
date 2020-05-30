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

    private DAO<DemoPojo> demoMemoryDao = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.warn("=========================================");
        logger.warn("  MinigrubServlet init() started");
        logger.warn("    http://localhost:8080/minigrub/hub");
        logger.warn("=========================================");

        logger.info("Getting real path for templateDir");
        String templateDir = config.getServletContext().getRealPath(TEMPLATE_DIR);
        logger.info("...real path is: " + templateDir);
        
        logger.info("Initializing Freemarker. templateDir = " + templateDir);
        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            logger.error("Template directory not found in directory: " + templateDir, e);
        }
        logger.info("Successfully Loaded Freemarker");
        
        demoMemoryDao = new DemoPojoMemoryDAO();
        addDemoData();

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "show";

        String template = "";
        Map<String, Object> model = new HashMap<>();

        //TODO: Add more URL commands to the servlet
        switch (command) {

            case "show":
                String indexParam = request.getParameter("index");
                int index = (indexParam == null) ? 0 : Integer.parseInt(indexParam);
                int numItems = demoMemoryDao.getAllIDs().size();
                int nextIndex = (index + 1) % numItems;
                int prevIndex = index - 1;
                if (prevIndex < 0) prevIndex = numItems-1;

                template = "show.ftl";
                model.put("item", demoMemoryDao.getByIndex(index));
                model.put("prevIndex", prevIndex);
                model.put("nextIndex", nextIndex);
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

    //TODO: doPost() goes here - if needed.
    
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
        logger.debug("Creating sample DemoPojos...");
        
        demoMemoryDao.insert(new DemoPojo("Item I"));
        demoMemoryDao.insert(new DemoPojo("Item II"));
        demoMemoryDao.insert(new DemoPojo("Item III"));
        
        logger.info("...items inserted");
    }

}
