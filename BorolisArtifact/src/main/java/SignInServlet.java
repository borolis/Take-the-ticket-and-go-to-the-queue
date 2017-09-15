import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import org.apache.commons.codec.digest.DigestUtils;

public class SignInServlet extends HttpServlet {


    AccountService accountService;

    public SignInServlet(AccountService _accountService) {
        this.accountService = _accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PageGenerator pageGenerator = PageGenerator.instance();
        if (authentificate(request.getSession().getId())) {
            response.getWriter().println("You are already signed in!");
            response.getWriter().println(pageGenerator.getRedirectPage("dashboard"));
        } else {
            response.getWriter().println(pageGenerator.getPage("signin.html", new HashMap<String, Object>()));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    private Account authorize(String login, String pass, String session_id) {
        boolean isRegistered = false;

        Account userAccount = accountService.findAccountByLogin(login);
        if (userAccount != null) {
            isRegistered = true;
        }

        if (isRegistered && userAccount.getPassword().equals(pass)) {
            //welcome
            userAccount.setSession_id(session_id);
            accountService.myDB.execUpdate(accountService.myDB.makeSQLInsertAuth(userAccount.getLogin(), userAccount.getSession_id()));
            return userAccount;
        } else {
            return null;
        }


    }

    private boolean authentificate(String session_id) {
        boolean isAuthorized = false;

        Account userAccount = accountService.findAccountBySession(session_id);
        if (userAccount != null) {
            isAuthorized = true;
        }
        return isAuthorized;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger L = new Logger();
        String login = request.getParameter("login").toLowerCase();
        String pass = request.getParameter("password").toLowerCase();
        String session_id = request.getSession().getId();
        pass = DigestUtils.md5Hex(pass);
        L.log("Login: login["  +login+ "] passhash[" + pass + "]");

        if (login == null || login.isEmpty() || pass == null || pass.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Incorrect login/pass");
            L.log("Incorrect login/pass");
            return;
        }

        Account userAccount = authorize(login, pass, session_id);

        PageGenerator pageGenerator = PageGenerator.instance();

        if (userAccount == null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(pageGenerator.getRedirectPage("signin"));
            L.log("Wrong login/pass");
            return;
        }

        response.getWriter().println(pageGenerator.getRedirectPage("dashboard"));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");

    }

}


