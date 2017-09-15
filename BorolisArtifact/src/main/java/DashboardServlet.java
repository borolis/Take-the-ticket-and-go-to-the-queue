import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardServlet extends HttpServlet {


    AccountService accountService;
    ListOfQueue queueList;

    public DashboardServlet(AccountService _accountService, ListOfQueue _queueList) {
        this.accountService = _accountService;
        this.queueList = _queueList;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String session_id = request.getSession().getId();
        PageGenerator pageGenerator = PageGenerator.instance();
        if (authentificate(session_id)) {

            Worker currentWorker = accountService.myDB.getWorker(accountService.myDB.makeSQLqueryGetWorkerByLogin(accountService.myDB.getAccBySession(session_id).getLogin()));

            Integer cuttentQueueSize = queueList.getQueueByCategory(currentWorker.getWorkersCategory()).size();

            Double currentWorkerLoadD = (cuttentQueueSize * 1.0) / (queueList.queueMaxSize * 1.0) * 100;
            Integer currentWorkerLoad = currentWorkerLoadD.intValue();
            currentWorker.setWorkersLoad(currentWorkerLoad.toString());
            response.getWriter().println(pageGenerator.getDashboardPage(currentWorker));

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
            accountService.myDB.execUpdate(accountService.myDB.makeSQLInsertAuth(userAccount.getLogin(), userAccount.getSession_id()));
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


