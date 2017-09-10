import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;

public class SignUpServlet extends HttpServlet {


    AccountService accountService;

    public SignUpServlet(AccountService _accountService) {
        this.accountService = _accountService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("");

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        boolean isRegistered = false;

        if (accountService.findAccountByLogin(login) != null) {
            isRegistered = true;
        }
        if (!isRegistered) {
            accountService.createNewAccount(new Account(null, login, password, email, null));
        }
        response.getWriter().println("blyad, zaregan))))");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
