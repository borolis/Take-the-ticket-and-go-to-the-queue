import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.MalformedURLException;
import java.util.Date;


public class Main {


    public static void main(String[] args) throws Exception {
        Logger L = new Logger();
        Integer globalID = 0;
        DBHelper myDB = new DBHelper();
        AccountService accountService = new AccountService(myDB);

        ListOfQueue queueList = new ListOfQueue();
        queueList.addQueue(new LinkedQueue<>("001"));
        queueList.addQueue(new LinkedQueue<>("002"));
        queueList.addQueue(new LinkedQueue<>("003"));


        DashboardServlet dashboardServlet = new DashboardServlet(accountService, queueList);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);
        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        ProfileServlet profileServlet = new ProfileServlet(accountService);
        GetQueueServlet getQueueServlet = new GetQueueServlet(accountService, queueList);
        NewTicketServlet newTicketServlet = new NewTicketServlet(globalID, queueList);
        ShowTicketServlet showTicketServlet = new ShowTicketServlet(globalID, queueList);
        SettingsServlet settingsServlet = new SettingsServlet();
        RootServlet rootServlet = new RootServlet();

        //TODO settings


        Server server = new Server(80);

        ResourceHandler resourceHandler = new ResourceHandler() {
            @Override
            public Resource getResource(String path)
                    throws MalformedURLException {
                Resource resource = Resource.newClassPathResource(path);
                if (resource == null || !resource.exists()) {
                    System.out.println("/htmltemplates" + path);
                    resource = Resource.newClassPathResource("/htmltemplates" +path);
                }
                return resource;
            }
        };

        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase("/htmltemplates");

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resourceHandler, servletHandler } );

        server.setHandler(handlers);

        servletHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        servletHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        servletHandler.addServlet(new ServletHolder(dashboardServlet), "/dashboard");
        servletHandler.addServlet(new ServletHolder(logOutServlet), "/logout");
        servletHandler.addServlet(new ServletHolder(profileServlet), "/profile");
        servletHandler.addServlet(new ServletHolder(getQueueServlet), "/getqueue");
        servletHandler.addServlet(new ServletHolder(newTicketServlet), "/getticket");
        servletHandler.addServlet(new ServletHolder(showTicketServlet), "/ticketInfo");

        servletHandler.addServlet(new ServletHolder(settingsServlet), "/settings");


        ServletHolder defHolder = new ServletHolder("default",new DefaultServlet());
        defHolder.setInitParameter("resourceBase", "/htmltemplates");
        defHolder.setInitParameter("dirAllowed","true");
        servletHandler.addServlet(defHolder,"/");


        L.log("Server started!");
        server.start();
        server.join();
    }
}
