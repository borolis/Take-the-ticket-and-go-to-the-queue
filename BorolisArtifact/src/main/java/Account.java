public class Account {
    private String id;
    private String login;
    private String password;
    private String email;
    private String session_id;

    public Account(String _id, String _login, String _password, String _email, String _session_id) {
        this.id = _id;
        this.login = _login;
        this.password = _password;
        this.email = _email;
        this.session_id = _session_id;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

}
