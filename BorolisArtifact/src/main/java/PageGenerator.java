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

            Template template = cfg.getTemplate(HTML_DIR + File.separator + filename);
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
            Template template = cfg.getTemplate(HTML_DIR + File.separator + "pages-profile.html");
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }

    public String getDashboardPage(Worker worker) {
        Map<String, Object> data = new HashMap<>();
        data.putAll(worker.getMap());

        Writer stream = new StringWriter();
        try {
            Template template = cfg.getTemplate(HTML_DIR + File.separator + "pages-dashboard.html");
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