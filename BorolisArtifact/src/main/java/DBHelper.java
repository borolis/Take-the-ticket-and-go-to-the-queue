import javafx.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;


public class DBHelper {
    private static final String url = "jdbc:mysql://localhost:3306/borolis";
    private static final String user = "root";
    private static final String password = "Foxconn/";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public DBHelper() {

        String query = "show tables";

    }

    private void DBConnect() {
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            try {
                con.close();
            } catch (SQLException e1) {
                //nothing
            }
        }
    }

    public void execUpdate(String query) {
        DBConnect();
        try {
            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            int count = stmt.executeUpdate(query);
            System.out.println("SQL Updated:" + count + " rows!");

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    public Account getAccount(String query) {
        DBConnect();
        try {

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);
            Account result = null;
            while (rs.next()) {
                result = new Account(rs.getString("_id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("session_id"));
            }
            return result;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
            sqlEx.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }


    public ResultSet execQuery(String query) {
        DBConnect();
        try {
            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query


            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу
            System.out.println("Выводим statement");

            rs = stmt.executeQuery(query);
            return rs; //не факт что сработает

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
            System.out.println(11);
            sqlEx.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public String makeSQLInsertNewAcc(Account acc) {
        String login = acc.getLogin();
        String password = acc.getPassword();
        String email = acc.getEmail();
        String session_id = acc.getSession_id();

        //myDB.execUpdate("INSERT INTO borolis (login, password, email, session_id) values('TestUser', '123456', 'boroliska@gmail.com', 'id001')");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append("borolis ");
        stringBuilder.append("(login, password, email, session_id)");
        stringBuilder.append("values");
        stringBuilder.append("(");
        stringBuilder.append("'" + login + "',");
        stringBuilder.append("'" + password + "',");
        stringBuilder.append("'" + email + "',");
        stringBuilder.append("'" + session_id);
        stringBuilder.append(");");
        return stringBuilder.toString();
    }






    public String makeSQLInsertAuth(String login, String session_id) {

        //myDB.execUpdate("INSERT INTO borolis (login, password, email, session_id) values('TestUser', '123456', 'boroliska@gmail.com', 'id001')");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append("sessionidtologin ");
        stringBuilder.append("(login, session_id)");
        stringBuilder.append("values");
        stringBuilder.append("(");
        stringBuilder.append("'" + login + "',");
        stringBuilder.append("'" + session_id +"'");
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    public String makeSQLDeleteAuth(String session_id) {
        System.out.println(session_id);
        //myDB.execUpdate("DELETE FROM `borolis`.`sessionidtologin` WHERE `session_id`='sessionId';");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM `borolis`.`sessionidtologin` WHERE `session_id`='");
        stringBuilder.append(session_id);
        stringBuilder.append("';");
        return stringBuilder.toString();
    }


    public String makeSQLqueryGetAccByLogin(String login) {
        //myDB.execUpdate("SELECT * FROM borolis.borolis WHERE login='TestUser'");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM borolis.borolis WHERE login='");
        stringBuilder.append(login);
        stringBuilder.append("';");

        return stringBuilder.toString();
    }



    public Account getAccBySession(String session_id) {
        DBConnect();
        try {

            stmt = con.createStatement();

            rs = stmt.executeQuery(makeSQLqueryGetLoginBySession(session_id));

            String login = null;
            while (rs.next()) {
                login = rs.getString("login");
            }
            if (login == null) {
                return null;
            }
            Account result = getAccount(makeSQLqueryGetAccByLogin(login));
            return result;

        } catch (SQLException sqlEx) {
            System.out.println(sqlEx.toString());
            sqlEx.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }


    public String makeSQLqueryGetLoginBySession(String session_id) {
        //myDB.execUpdate("SELECT * FROM borolis.borolis WHERE login='TestUser'");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT login FROM borolis.sessionidtologin WHERE session_id='");
        stringBuilder.append(session_id);
        stringBuilder.append("';");

        return stringBuilder.toString();
    }

    public String makeSQLupdateUpdateSession(Account acc) {
        //myDB.execUpdate("SELECT * FROM borolis.borolis WHERE login='TestUser'");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE `borolis`.`sessionidtologin` SET `session_id`='");
        stringBuilder.append(acc.getSession_id());
        stringBuilder.append("'");
        stringBuilder.append(" WHERE `login`='");
        stringBuilder.append(acc.getLogin());
        stringBuilder.append("';");

        return stringBuilder.toString();
    }


    //UPDATE `borolis`.`borolis` SET `session_id`='id9399' WHERE `_id`='2';


    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException se) {
        }
        try {
            stmt.close();
        } catch (SQLException se) {
        }
        try {
            rs.close();
        } catch (SQLException se) {
        }
    }
}
