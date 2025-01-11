package ControllerClasses;

import Classes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Files.*;

public class MedicineManager {
    private List<Medicine> medicines;

    // Constructor
    public MedicineManager() {
        medicines = new ArrayList<>();
    }

    // Add a new medicine
    public boolean addMedicine(Medicine medicine) {
        for (Medicine existingMedicine : medicines) {
            if (existingMedicine.getId() == medicine.getId()) {
                return false; // Medicine with the same ID already exists
            }
        }
        medicines.add(medicine);
        saveToFile();
        return true; // Medicine added successfully
    }

    // Update an existing medicine
    public boolean updateMedicine(int id, String name, double price, boolean isAvailable) {
        for (Medicine medicine : medicines) {
            if (medicine.getId() == id) {
                medicine.setName(name);
                medicine.setPrice(price);
                medicine.setAvailable(isAvailable);
                return true; // Medicine updated successfully
            }
        }
        return false; // Medicine not found
    }

    // Delete a medicine by ID
    public boolean deleteMedicine(int id) {
        return medicines.removeIf(medicine -> medicine.getId() == id);
    }

    // Search for a medicine by ID
    public Medicine searchMedicineById(int id) {
        for (Medicine medicine : medicines) {
            if (medicine.getId() == id) {
                return medicine; // Medicine found
            }
        }
        return null; // Medicine not found
    }

    // Search for medicines by Name
    public List<Medicine> searchMedicineByName(String name) {
        List<Medicine> result = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                result.add(medicine);
            }
        }
        return result; // Return all medicines that match the name
    }

    // Get all medicines
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines); // Return a copy to avoid external modification
    }

    // Get all available medicines
    public List<Medicine> getAvailableMedicines() {
        List<Medicine> availableMedicines = new ArrayList<>();
        for (Medicine medicine : medicines) {
            if (medicine.isAvailable()) {
                availableMedicines.add(medicine);
            }
        }
        return availableMedicines;
    }

    public void loadFromFile() {
        List<String> lines = null;
        try {
            lines = FileHandler.readFile("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/medicines.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String line : lines) {
            Medicine medicine = Medicine.fromFileString(line);
            if (medicine != null) {
                medicines.add(medicine);
            }
        }
    }

    public void saveToFile() {
        ArrayList<String> lines = new ArrayList<>();
        for (Medicine medicine : medicines) {
            lines.add(medicine.toFileString());
        }
        try {
            FileHandler.writeFile("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/medicines.txt", lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
