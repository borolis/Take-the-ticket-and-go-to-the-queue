import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
            accountService.myDB.execUpdate(accountService.myDB.makeSQLupdateUpdateSession(userAccount));
            return userAccount;
        } else {
            return null;
        }


    }

    boolean authentificate(String session_id) {
        boolean isAuthorizated = false;

        Account userAccount = accountService.findAccountBySession(session_id);
        if (userAccount != null) {
            isAuthorizated = true;
        }
        return isAuthorizated;
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String session_id = request.getSession().getId();

        if (login == null || login.isEmpty() || pass == null || pass.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Incorrect login/pass");
            System.out.println("Incorrect login/pass");
            return;
        }

        Account userAccount = authorize(login, pass, session_id);

        if (userAccount == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Wrong login/pass");
            System.out.println("Wrong login/pass");
            return;
        }

        PageGenerator pageGenerator = PageGenerator.instance();

        response.getWriter().println(pageGenerator.getRedirectPage("dashboard"));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");

    }

}


