import java.util.Date;

public class Logger {

    public void log(String message) {
        Date date = new Date();
        System.out.println("(" + date.toString() + ") (" + message + ")");
    }
}
