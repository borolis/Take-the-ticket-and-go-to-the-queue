import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {
//IDEA      private static final String HTML_DIR = "src/main/resources/htmltemplates";
//JAR       private static final String HTML_DIR = "src/main/resources/htmltemplates";

    private static final String HTML_DIR = "htmltemplates";
    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {

            Template template = cfg.getTemplate(HTML_DIR + "/" + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }


    public String getProfilePage(Worker worker) {
        Map<String, Object> data = new HashMap<>();
        data.putAll(worker.getMap());

        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "pages-profile.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }


    public String getQueuePage(Worker worker, ListOfQueue queueList) {

        Map<String, Object> data = new HashMap<>();
        data.putAll(worker.getMap());


        LinkedQueue<ClientQueue> allClientsQueue = queueList.getQueueByCategory(worker.getWorkersCategory());
        if (queueList.getQueueByCategory(worker.getWorkersCategory()).isEmpty()) {
            data.put("allClientsLines", "You don't have work right now)");
            data.put("currentClientLine", "");
        } else {
            ClientQueue currentClient = queueList.getQueueByCategory(worker.getWorkersCategory()).top();
            LinkedQueue<ClientQueue> currentClientQueue = new LinkedQueue<>("table");
            currentClientQueue.push(currentClient);


            data.put("allClientsLines", getTable(allClientsQueue, false));
            data.put("currentClientLine", getTable(currentClientQueue, true));

        }
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "table-basic.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }


    public String getTable(LinkedQueue<ClientQueue> queue, boolean withButton) {
        int numberOfLines = queue.size();

        String result = "";

        for (int i = 0; i < numberOfLines; i++) {
            Map<String, Object> data = new HashMap<>();
            String buttonCode = "";
            data.put("line", ((Integer) (i + 1)).toString());
            data.put("ID", queue.get(i).getId());
            data.put("category", queue.get(i).getCategory());


            if (withButton) buttonCode = "<form class=\"login-form\" action=\"/getqueue\" method=\"POST\">\n" +
                    "\t<input class =\"btn pull-left hidden-sm-down btn-success\" type=\"submit\" name=\"nextPeople\" value=\"next\" class=\"button\" />\n" +
                    "</form>";
            data.put("buttonNextPeople", buttonCode);
            //data.putAll();

            Writer stream = new StringWriter();
            try {
                Template template = cfg.getTemplate(HTML_DIR + "/" + "table-element.html");
                template.process(data, stream);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }

            result = result + stream.toString();
        }
        return result;
    }

    public String getDashboardPage(Worker worker) {
        Map<String, Object> data = new HashMap<>();
        data.putAll(worker.getMap());

        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "index.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }

    public String getTicketInfoPage(String _id, String _category) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", _id);
        data.put("category", _category);


        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "ticketinfo.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }


    public String getNewTicketPage() {
        Map<String, Object> data = new HashMap<>();

        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "newticket.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }

    public String getNewTicketPage(String id, String category) {
        Map<String, Object> data = new HashMap<>();
        data.put("id",id);
        data.put("category",category);


        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + "/" + "newticket.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }

    public String getRedirectPage(String urlRedirect) {
        String filename = "redirect.html";
        Map<String, Object> data = new HashMap<>();
        data.put("URL", urlRedirect);
        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
}