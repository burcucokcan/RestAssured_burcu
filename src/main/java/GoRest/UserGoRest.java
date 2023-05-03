package GoRest;

import java.util.HashMap;
import java.util.Map;

public class UserGoRest {

    int id;
    String name;
    String gender;
    String email;
    String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserGoRest{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
  //  Map<String, String> newUser= new HashMap<>();
  //      newUser.put("name", rndFullName);
  //              newUser.put("gender", "female");
  //              newUser.put("email", rndEmail);
  //              newUser.put("status", "active");//