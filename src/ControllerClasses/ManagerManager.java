package ControllerClasses;

import Classes.*;

import java.util.ArrayList;
import java.util.List;

public class ManagerManager {
    private List<Manager> managers;

    // Constructor
    public ManagerManager() {
        this.managers = new ArrayList<>();
    }

    // Add a new manager
    public boolean addManager(Manager manager) {
        for (Manager existingManager : managers) {
            if (existingManager.getUsername().equals(manager.getUsername())) {
                return false; // Username must be unique
            }
        }
        managers.add(manager);
        return true; // Manager added successfully
    }

    // Remove a manager by ID
    public boolean removeManager(int id) {
        return managers.removeIf(manager -> manager.getId() == id);
    }

    // Validate manager credentials
    public Manager validateCredentials(String username, String password) {
        for (Manager manager : managers) {
            if (manager.validate(username, password)) {
                return manager; // Return the manager if credentials match
            }
        }
        return null; // No matching manager found
    }

    // Search for a manager by ID
    public Manager searchManagerById(int id) {
        for (Manager manager : managers) {
            if (manager.getId() == id) {
                return manager; // Manager found
            }
        }
        return null; // Manager not found
    }

    // Search for a manager by username
    public Manager searchManagerByUsername(String username) {
        for (Manager manager : managers) {
            if (manager.getUsername().equalsIgnoreCase(username)) {
                return manager; // Manager found
            }
        }
        return null; // Manager not found
    }

    // Get all managers
    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers); // Return a copy to prevent external modification
    }

    // Update manager password
    public boolean updatePassword(int managerId, String newPassword) {
        for (Manager manager : managers) {
            if (manager.getId() == managerId) {
                manager.setPassword(newPassword);
                return true; // Password updated successfully
            }
        }
        return false; // Manager not found
    }

    // Load managers from file (to be implemented)
    public void loadFromFile() {
        // TODO: Add logic to load managers from a text file
    }

    // Save managers to file (to be implemented)
    public void saveToFile() {
        // TODO: Add logic to save managers to a text file
    }
}
