import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws Exception {

        LinkedList<People> globalQueue = new LinkedList<People>();


        Frontend frontend = new Frontend(globalQueue);
        GetQueue getQueue = new GetQueue(globalQueue);

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        server.setHandler(context);

        context.addServlet(new ServletHolder(frontend), "/makequeue");
        context.addServlet(new ServletHolder(getQueue), "/getqueue");

        server.start();
        server.join();
    }
}
