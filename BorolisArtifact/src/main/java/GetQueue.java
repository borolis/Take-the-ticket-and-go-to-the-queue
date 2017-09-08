import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

public class GetQueue extends HttpServlet {
    private String login = "";

    private LinkedList<People> queue;

    public GetQueue(LinkedList<People> queue) {
        this.queue = queue;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("List: <br>");
        for (int i = 0; i < queue.size(); i++) {
            response.getWriter().println(queue.get(i).getId() + " " + queue.get(i).getCat() + "<br>");
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("get");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("\n");
        System.out.println(request.getReader().readLine());
        System.out.println("post");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}