package Classes;


import java.util.List;
public class Manager extends User {


    public Manager(String username, String password) {
        super(UniqueIdGenerator.generateManagerId(), username, password, "Manager"); // Generate unique ID for Manager
    }



    @Override
    public String toString() {
        return "Manager [ID=" + getId() + ", Username=" + getUsername() + "]";
    }
}

