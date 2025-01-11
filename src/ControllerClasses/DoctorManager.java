package ControllerClasses;

import Classes.*;
import Classes.Appointment;
import Files.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorManager {
    private ArrayList<Doctor> doctors;
    private final String doctorFilePath = "C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/doctors.txt";


    public DoctorManager() {
        doctors = new ArrayList<>();
    }


    public boolean addDoctor(Doctor doctor) {
        for (Doctor existingDoctor : doctors) {
            if (existingDoctor.getUsername().equalsIgnoreCase(doctor.getUsername())) {
                return false;
            }
        }
        doctors.add(doctor);
        saveToFile();
        return true;
    }


    public void updateDoctor(Doctor updatedDoctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId() == updatedDoctor.getId()) {
                doctors.set(i, updatedDoctor);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Doctor with ID " + updatedDoctor.getId() + " not found.");
    }




    public boolean deleteDoctor(int id) {
        if (doctors.removeIf(doctor -> doctor.getId() == id)) {
            saveToFile();
            return true;
        }
        return false;
    }







    public ArrayList<Doctor> getAllDoctors() {
        return doctors;
    }








    public Doctor getDoctorById(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }



    public void loadFromFile() {
        try {
            ArrayList<String> doctorLines = FileHandler.readFile(doctorFilePath);
            for (String line : doctorLines) {
                doctors.add(Doctor.fromFileString(line));
            }
            System.out.println("Doctors loaded successfully from file.");
        } catch (IOException e) {
            System.out.println("Error loading doctors from file: " + e.getMessage());
        }
    }
    public void saveToFile() {
        try {
            ArrayList<String> doctorLines = new ArrayList<>();
            for (Doctor doctor : doctors) {
                doctorLines.add(doctor.toFileString());
            }
            FileHandler.writeFile(doctorFilePath, doctorLines);
            System.out.println("Doctors saved successfully to file.");
        } catch (IOException e) {
            System.out.println("Error saving doctors to file: " + e.getMessage());
        }
    }

}
