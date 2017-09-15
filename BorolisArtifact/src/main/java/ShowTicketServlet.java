import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowTicketServlet extends HttpServlet {

    Integer global_id;
    ListOfQueue listOfQueue;

    public ShowTicketServlet(Integer _id, ListOfQueue _listOfQueue) {
        this.global_id = _id;
        this.listOfQueue = _listOfQueue;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger L = new Logger();
        String category = request.getParameter("category");
        String session_id = request.getSession().getId();

        ClientQueue newPeople = new ClientQueue(global_id.toString(), category);
        global_id++;
        L.log("New client: id[" + newPeople.getId() + "] category[" + newPeople.getCategory() + "]");
        listOfQueue.getQueueByCategory(category).push(newPeople);

        PageGenerator pageGenerator = PageGenerator.instance();

        response.getWriter().println(pageGenerator.getTicketInfoPage(newPeople.getId(), newPeople.getCategory()));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}


