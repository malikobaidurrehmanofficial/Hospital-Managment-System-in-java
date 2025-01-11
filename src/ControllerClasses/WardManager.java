package ControllerClasses;

import Classes.Ward;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WardManager {
    private List<Ward> wards;

    // Constructor
    public WardManager() {
        this.wards = new ArrayList<>();
    }

    // Add a new ward
    public boolean addWard(Ward ward) {
        for (Ward existingWard : wards) {
            if (existingWard.getName().equalsIgnoreCase(ward.getName())) {
                return false;
            }
        }
        wards.add(ward);
        saveToFile();
        return true;
    }

    // Update an existing ward
    public boolean updateWard(int id, String name, int capacity) {
        for (Ward ward : wards) {
            if (ward.getId() == id) {
                ward.setName(name);
                ward.setCapacity(capacity);
                return true; // Ward updated successfully
            }
        }
        return false; // Ward not found
    }


    public boolean deleteWard(int id) {
        for (int i = 0; i < wards.size(); i++) {
            if (wards.get(i).getId() == id) {
                wards.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // Search for a ward by ID
    public Ward searchWardById(int id) {
        for (Ward ward : wards) {
            if (ward.getId() == id) {
                return ward; // Ward found
            }
        }
        return null; // Ward not found
    }

    // Search for wards by name
    public List<Ward> searchWardByName(String name) {
        List<Ward> result = new ArrayList<>();
        for (Ward ward : wards) {
            if (ward.getName().equalsIgnoreCase(name)) {
                result.add(ward);
            }
        }
        return result; // Return all wards that match the name
    }

    // Get all wards
    public List<Ward> getAllWards() {
        return new ArrayList<>(wards); // Return a copy to prevent external modification
    }

    // Check if a ward has available beds
    public boolean hasAvailableBeds(int id) {
        Ward ward = searchWardById(id);
        if (ward != null) {
            return ward.hasAvailableBeds();
        }
        return false; // Ward not found
    }

    // Admit a patient to a ward
    public boolean admitPatientToWard(int id) {
        Ward ward = searchWardById(id);
        if (ward != null && ward.hasAvailableBeds()) {
            ward.admitPatient();
            return true; // Patient admitted successfully
        }
        return false; // Ward not found or no available beds
    }

    // Discharge a patient from a ward
    public boolean dischargePatientFromWard(int id) {
        Ward ward = searchWardById(id);
        if (ward != null && ward.getOccupiedBeds() > 0) {
            ward.dischargePatient();
            return true; // Patient discharged successfully
        }
        return false; // Ward not found or no patients to discharge
    }

    public Ward getWardById(int id) {
        for (Ward ward : wards) {
            if (ward.getId() == id) {
                return ward;
            }
        }
        return null; // Return null if no ward with the given ID is found
    }

    // Load wards from file
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/wards.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Ward ward = Ward.fromFileString(line);
                if (ward != null) {
                    wards.add(ward);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading wards from file: " + e.getMessage());
        }
    }

    // Save wards to file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/wards.txt"))) {
            for (Ward ward : wards) {
                writer.write(ward.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving wards to file: " + e.getMessage());
        }
    }
}
