import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class DashboardServlet extends HttpServlet {


    AccountService accountService;

    public DashboardServlet(AccountService _accountService) {
        this.accountService = _accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PageGenerator pageGenerator = PageGenerator.instance();
        if (authentificate(request.getSession().getId())) {
            response.getWriter().println("This is your dashboard");
        } else {
            response.getWriter().println(pageGenerator.getRedirectPage("signin"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    private Account authorizate(String login, String pass, String session_id) {
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

    }

}


