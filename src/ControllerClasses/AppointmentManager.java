package ControllerClasses;

import Classes.Appointment;
import Files.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private ArrayList<Appointment> appointments;

    // Constructor
    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    // Add an Appointment
    public boolean addAppointment(Appointment appointment) {
        for (Appointment existing : appointments) {
            if (existing.getId() == appointment.getId()) {
                return false; // Appointment with the same ID already exists
            }
        }
        appointments.add(appointment);
        return true; // Appointment added successfully
    }

    // Update an Appointment
    public boolean updateAppointment(int id, Appointment updatedAppointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == id) {
                appointments.set(i, updatedAppointment);
                return true; // Appointment updated successfully
            }
        }
        return false; // Appointment not found
    }

    // Delete an Appointment
    public boolean deleteAppointment(int id) {
        return appointments.removeIf(appointment -> appointment.getId() == id);
    }

    // Search Appointment by ID
    public Appointment searchAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment; // Appointment found
            }
        }
        return null; // Appointment not found
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments); // Return a copy to prevent external modification
    }

    // Get Appointments by Doctor Name
    public List<Appointment> getAppointmentsByDoctorName(String doctorName) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorName().equalsIgnoreCase(doctorName)) {
                result.add(appointment);
            }
        }
        return result; // Return appointments for the specified doctor
    }

    // Get Appointments by Patient Name
    public List<Appointment> getAppointmentsByPatientName(String patientName) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientName().equalsIgnoreCase(patientName)) {
                result.add(appointment);
            }
        }
        return result; // Return appointments for the specified patient
    }

    // Load Appointments from File
    public void loadFromFile() {
        appointments.clear();
        try {
            List<String> lines = FileHandler.readFile("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/Appointments.txt");
            for (String line : lines) {
                appointments.add(Appointment.fromFileString(line));
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments from file: " + e.getMessage());
        }
    }
    public void saveToFile() {
        ArrayList<String> lines = new ArrayList<>();
        for (Appointment appointment : appointments) {
            lines.add(appointment.toFileString());
        }
        try {
            FileHandler.writeFile("C:/Users/HP/Documents/oops projects/Semprj/HospitalMAnagmentSystem/src/Files/Appointments.txt", lines);
        } catch (IOException e) {
            System.err.println("Error saving appointments to file: " + e.getMessage());
        }
    }

}
