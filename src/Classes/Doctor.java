package Classes;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String specialization;


    private ArrayList<Appointment> appointments;


    public Doctor( String username, String password, String specialization) {
        super(UniqueIdGenerator.generateDoctorId(), username, password, "Doctor"); // Role is set to "Doctor"
        this.specialization = specialization;
        this.appointments = new ArrayList<>();
    }

    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }


    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }


    public List<Appointment> getAppointmentsByDate(String date) {
        List<Appointment> filteredAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }


    public List<String> getAllPatients() {
        List<String> patientNames = new ArrayList<>();
        for (Appointment appointment : appointments) {
            patientNames.add(appointment.getPatientName());
        }
        return patientNames;
    }

    @Override
    public String toString() {
        return "Doctor [ID=" + getId() + ", Name=" + getUsername() + ", Specialization=" + specialization + "]";
    }

    public String toFileString() {
        return getId() + "|" + getUsername() + "|" + getPassword() + "|" + specialization;
    }


    public static Doctor fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Doctor(
                parts[1],
                parts[2],
                parts[3]
        );
    }
}
