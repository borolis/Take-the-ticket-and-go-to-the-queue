import java.util.HashMap;
import java.util.Map;

public class Worker {
    private String login = "";
    private String fullName = "";
    private String email = "";
    private String password = "";
    private String position = "";
    private String phoneNumber = "";
    private String country = "";
    private String photo = "";
    private String workersCategory = "";
    private String workersLoad = "";
    private String workersFeedback = "";

    public Worker(String _login, String _fullName, String _email, String _password,
                  String _position, String _phoneNumber, String _country,
                  String _photo, String _workersCategory, String _workersLoad, String _workersFeedback) {
        this.login = _login;
        this.fullName = _fullName;
        this.email = _email;
        this.password = _password;
        this.position = _position;
        this.phoneNumber = _phoneNumber;
        this.country = _country;
        this.photo = _photo;
        this.workersCategory = _workersCategory;
        this.workersLoad = _workersLoad;
        this.workersFeedback = _workersFeedback;
    }

    public String getWorkersCategory() {
        return workersCategory;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getWorkersLoad() {
        return workersLoad;
    }

    public String getWorkersFeedback() {
        return workersFeedback;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setWorkersCategory(String workersCategory) {
        this.workersCategory = workersCategory;
    }

    public void setWorkersFeedback(String workersFeedback) {
        this.workersFeedback = workersFeedback;
    }

    public void setWorkersLoad(String workersLoad) {
        this.workersLoad = workersLoad;
    }


    public Map<String, Object> getMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("login", login);
        result.put("fullName", fullName);
        result.put("email", email);
        result.put("password", password);
        result.put("position", position);
        result.put("phoneNumber", phoneNumber);
        result.put("country", country);
        result.put("photo", photo);
        result.put("workersLoad", workersLoad);
        result.put("workersFeedback", workersFeedback);
        return result;
    }
}
