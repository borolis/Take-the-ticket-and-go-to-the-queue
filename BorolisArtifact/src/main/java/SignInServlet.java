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
        response.getWriter().println(pageGenerator.getPage("signin.html", new HashMap<String, Object>()));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (login == null || login.isEmpty() || pass == null || pass.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        boolean isRegistered = false;
        Account userAccount = null;
        userAccount = accountService.findAccountByLogin(login);
        if (userAccount != null) {
            isRegistered = true;
        }

        if (isRegistered && userAccount.getPassword().equals(pass))
        {
            //welcome
            response.getWriter().println("Hello pidor, ti avtorizovan!" + "<br>");
            response.getWriter().println("Your email is: " + userAccount.getEmail());
        }
        else {
            response.getWriter().println("Idi nahoooy dolboyeb!");
        }

        /*
        if ((accountService.containsKey(login)) && (accountService.get(login).equals(pass))) {
            isRegistered = true;
        }

        if (isRegistered) {
            response.getWriter().println("Authorized: " + login);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {

            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
*/
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=utf-8");
    }
}
