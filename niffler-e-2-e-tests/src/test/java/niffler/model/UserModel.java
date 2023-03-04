package niffler.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    private String username;
    private String password;
    private boolean isAvailable;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
