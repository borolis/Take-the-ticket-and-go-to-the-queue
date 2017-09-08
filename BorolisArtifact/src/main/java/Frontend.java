import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.abs;

public class Frontend extends HttpServlet {

    private LinkedList<People> queue;

    public Frontend(LinkedList<People> queue) {
        this.queue = queue;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<html>\n" +
                "\t<head>\n" +
                "\t</head>\n" +
                "\n" +
                "\t<body>\n" +
                "\n" +
                "\t\t<form action=\"\\makequeue\" method=\"post\">\n" +
                "\t\t\t<button type=\"submit\" class=\"btn btn-success\" role=\"link\" name=\"item\" value=\"1\">Item 1</button>\n" +
                "\t\t\t<button type=\"submit\" class=\"btn btn-success\" role=\"link\" name=\"item\" value=\"2\">Item 2</button>\n" +
                "\t\t\t<button type=\"submit\" class=\"btn btn-success\" role=\"link\" name=\"item\" value=\"3\">Item 3</button>\n" +
                "\t\t</form>\n" +
                "\t\t\n" +
                "\t\t</form>\n" +
                "\t\n" +
                "\t</body>\n" +
                "\n" +
                "</html>\n");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("get");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getReader().readLine();
        Random random = new Random();
        Integer tmp_id = random.nextInt();
        tmp_id = abs(tmp_id);

        String id = tmp_id.toString();

        System.out.println("New client: id" + id);

        queue.add(new People(id, category));

        response.getWriter().println("Your id:" + id + "<br>" + "Your category:" + category);
        response.getWriter().println("<head>\n" +
                "<meta http-equiv=\"refresh\" content=\"4;URL=/makequeue\" />\n" +
                "</head>");
        response.setContentType("text/html;charset=windows-1251");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}