import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LogOutServlet extends HttpServlet {


    AccountService accountService;

    public LogOutServlet(AccountService _accountService) {
        this.accountService = _accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        PageGenerator pageGenerator = PageGenerator.instance();
        if (authentificate(request.getSession().getId())) {
            response.getWriter().println("You will logout in 2 seconds");
            logout(request.getSession().getId());
            response.getWriter().println(pageGenerator.getRedirectPage("signin"));
        } else {
            response.getWriter().println(pageGenerator.getRedirectPage("signin"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    private void logout(String session_id) {
        accountService.myDB.execUpdate(accountService.myDB.makeSQLDeleteAuth(session_id));
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
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
    }

}


