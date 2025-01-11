package ControllerClasses;

import Classes.User;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        // Add default admin user
        users.add(new User(1, "obaid", "obaid", "Manager"));
    }

    public boolean validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public String getUserRole(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return null; // Return null if no user is found
    }
}
