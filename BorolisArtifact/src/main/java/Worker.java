import java.util.HashMap;
import java.util.Map;

public class Worker {
    private String fullName = "";
    private String email = "";
    private String password = "";
    private String position = "";
    private String phoneNumber = "";
    private String country = "";
    private String photo = "";
    private String workersLoad = "";
    private String workersFeedback = "";

    public Worker(String _fullName, String _email, String _password,
                  String _position, String _phoneNumber, String _country,
                  String _photo, String _workersLoad, String _workersFeedback) {
        this.fullName = _fullName;
        this.email = _email;
        this.password = _password;
        this.position = _position;
        this.phoneNumber = _phoneNumber;
        this.country = _country;
        this.photo = _photo;
        this.workersLoad = _workersLoad;
        this.workersFeedback = _workersFeedback;
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

    public Map<String, Object> getMap() {
        Map<String, Object> result = new HashMap<>();
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
