package ControllerClasses;

import Classes.*;
import Files.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatientManager {
    private ArrayList<Patient> patients;
    private ArrayList<Bill> bills;

    private final String patientFilePath = "C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/patients.txt";
    private final String billFilePath = "C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/bills.txt";


    // Constructor
    public PatientManager() {
        patients = new ArrayList<>();
        bills=new ArrayList<>();
    }

    // Add a new patient
    public boolean addPatient(Patient patient) {
        // Check if a patient with the same ID already exists
        for (Patient existingPatient : patients) {
            if (existingPatient.getId() == patient.getId()) {
                return false; // Patient already exists
            }
        }
        patients.add(patient);
        saveToFile();
        return true; // Patient added successfully
    }

    // Update an existing patient
    public boolean updatePatient(int id, Patient updatedPatient) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                patient.setAge(updatedPatient.getAge());
                patient.setGender(updatedPatient.getGender());
                patient.setDisease(updatedPatient.getDisease());
                patient.setBedNumber(updatedPatient.getBedNumber());
                patient.setWard(updatedPatient.getWard());
                patient.setWardId(updatedPatient.getWardId());
                patient.setDoctorName(updatedPatient.getDoctorName());
                patient.setDoctorId(updatedPatient.getDoctorId());
                patient.setAdmissionDate(updatedPatient.getAdmissionDate());
                patient.setDischargeDate(updatedPatient.getDischargeDate());
                patient.setCurrentlyAdmitted(updatedPatient.isCurrentlyAdmitted());
                patient.setBill(updatedPatient.getBill());
                saveToFile();
                return true; // Patient updated successfully
            }
        }
        return false; // Patient not found
    }

    // Delete a patient by ID
    public boolean deletePatient(int id) {
        if (patients.removeIf(patient -> patient.getId() == id)) {
            saveToFile();
            return true;
        }
        return false;
    }

    // Search for a patient by ID
    public Patient searchPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient; // Return the patient if found
            }
        }
        return null; // Patient not found
    }

    // Search for patients by Name
    public List<Patient> searchPatientByName(String name) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getUsername().equalsIgnoreCase(name)) {
                result.add(patient);
            }
        }
        return result;
    }

    // Get a list of all patients
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients); // Return a copy to prevent modification
    }
    private List<Patient> patientHistory = new ArrayList<>();

    public void addPatientToHistory(Patient patient) {
        patientHistory.add(new Patient(patient)); // Add a deep copy to maintain the record
    }

    public List<Patient> getPatientHistory(int patientId) {
        List<Patient> history = new ArrayList<>();
        for (Patient record : patientHistory) {
            if (record.getId() == patientId) {
                history.add(record);
            }
        }
        return history;
    }

    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient; // Return the patient if the ID matches
            }
        }
        return null; // Return null if no patient is found with the given ID
    }

    public void generateBill(int patientId, double doctorCharges, double wardCharges, double medicineCharges) {
        int billId = bills.size() + 1;
        Bill bill = new Bill(billId, patientId, doctorCharges, wardCharges, medicineCharges);
        bills.add(bill);
        saveToFile();

    }

    public ArrayList<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }





    public void loadFromFile() {
        try {
            List<String> patientLines = FileHandler.readFile(patientFilePath);
            for (String line : patientLines) {
                Patient patient = Patient.fromFileString(line);
                patients.add(patient);
            }
            List<String> billLines = FileHandler.readFile(billFilePath);
            for (String line : billLines) {
                Bill bill = Bill.fromFileString(line);
                bills.add(bill);
            }
        } catch (IOException e) {
            System.out.println("Error loading from files: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            ArrayList<String> patientLines = new ArrayList<>();
            for (Patient patient : patients) {
                patientLines.add(patient.toFileString());
            }
            FileHandler.writeFile(patientFilePath, patientLines);

            ArrayList<String> billLines = new ArrayList<>();
            for (Bill bill : bills) {
                billLines.add(bill.toFileString());
            }
            FileHandler.writeFile(billFilePath, billLines);
        } catch (IOException e) {
            System.out.println("Error saving to files: " + e.getMessage());
        }
    }
}

