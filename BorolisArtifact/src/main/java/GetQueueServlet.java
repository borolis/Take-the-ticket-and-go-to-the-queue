import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetQueueServlet extends HttpServlet {


    AccountService accountService;
    ListOfQueue listOfQueue;

    public GetQueueServlet(AccountService _accountService, ListOfQueue _listOfQueue) {
        this.accountService = _accountService;
        this.listOfQueue = _listOfQueue;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String session_id = request.getSession().getId();
        PageGenerator pageGenerator = PageGenerator.instance();
        if (authentificate(session_id)) {
            Worker current = accountService.myDB.getWorker(accountService.myDB.makeSQLqueryGetWorkerByLogin(accountService.myDB.getAccBySession(session_id).getLogin()));
            response.getWriter().println(pageGenerator.getQueuePage(current, listOfQueue));

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
        String nextPeople = request.getParameter("nextPeople");

        String session_id = request.getSession().getId();
        PageGenerator pageGenerator = PageGenerator.instance();

        if (authentificate(session_id)) {
            Worker current = accountService.myDB.getWorker(accountService.myDB.makeSQLqueryGetWorkerByLogin(accountService.myDB.getAccBySession(session_id).getLogin()));

            if (nextPeople != null && nextPeople.equals("next")) {
                //это жёстко))
                listOfQueue.getQueueByCategory(current.getWorkersCategory()).pop();
            }

            response.getWriter().println(pageGenerator.getQueuePage(current, listOfQueue));

        } else {
            response.getWriter().println(pageGenerator.getRedirectPage("signin"));
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

}


