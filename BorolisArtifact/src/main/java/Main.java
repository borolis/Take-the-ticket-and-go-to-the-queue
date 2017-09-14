import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) throws Exception {

        DBHelper myDB = new DBHelper();
        AccountService accountService = new AccountService(myDB);

        DashboardServlet dashboardServlet = new DashboardServlet(accountService);
        SignUpServlet signUpServlet = new SignUpServlet(accountService);
        SignInServlet signInServlet = new SignInServlet(accountService);
        LogOutServlet logOutServlet = new LogOutServlet(accountService);
        ProfileServlet profileServlet = new ProfileServlet(accountService);
        Server server = new Server(8080);

        ResourceHandler resourceHandler = new ResourceHandler() {
            @Override
            public Resource getResource(String path)
                    throws MalformedURLException {
                Resource resource = Resource.newClassPathResource(path);
                if (resource == null || !resource.exists()) {
                    resource = Resource.newClassPathResource("/htmltemplates" + path);
                }
                return resource;
            }
        };
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setResourceBase("/");

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resourceHandler, servletHandler } );
        server.setHandler(handlers);

        servletHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        servletHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        servletHandler.addServlet(new ServletHolder(dashboardServlet), "/dashboard");
        servletHandler.addServlet(new ServletHolder(logOutServlet), "/logout");
        servletHandler.addServlet(new ServletHolder(profileServlet), "/profile");

        System.out.println("Server started");
        server.start();
        server.join();
    }
}
