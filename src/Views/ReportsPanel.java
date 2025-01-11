package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import ControllerClasses.*;
import Classes.*;

public class ReportsPanel extends JPanel {

    public ReportsPanel(PatientManager patientManager, AppointmentManager appointmentManager, MedicineManager medicineManager, DoctorManager doctorManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Reports Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);


        JPanel reportButtonPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        reportButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton admittedByAdmissionDateButton = new JButton("Admitted Patients Reports by Admission Date");
        JButton admittedByDischargeDateButton = new JButton("Admitted Patients Reports by Discharge Date");
        JButton admittedByDoctorButton = new JButton("Admitted Patients Reports by Doctor");
        JButton appointmentsByDoctorButton = new JButton("Appointment Reports by Doctor");
        JButton appointmentsByDateButton = new JButton("Appointments by Date");
        JButton medicineByAvailabilityButton = new JButton("Medicine by Availability");
        JButton totalRecordsButton = new JButton("Total Number of Records");
        JButton backButton = new JButton("Back");

        reportButtonPanel.add(admittedByAdmissionDateButton);
        reportButtonPanel.add(admittedByDischargeDateButton);
        reportButtonPanel.add(admittedByDoctorButton);
        reportButtonPanel.add(appointmentsByDoctorButton);
        reportButtonPanel.add(appointmentsByDateButton);
        reportButtonPanel.add(medicineByAvailabilityButton);
        reportButtonPanel.add(totalRecordsButton);
        reportButtonPanel.add(backButton);

        add(reportButtonPanel, BorderLayout.CENTER);

        // Action Listeners for Buttons

        // 1. Admitted Patients by Admission Date
        admittedByAdmissionDateButton.addActionListener(e -> {
            String date = JOptionPane.showInputDialog(this, "Enter Admission Date (DD/MM/YYYY):", "Filter by Admission Date", JOptionPane.QUESTION_MESSAGE);
            if (date != null && !date.isEmpty()) {
                List<Patient> filteredPatients = patientManager.getAllPatients().stream()
                        .filter(patient -> date.equals(patient.getAdmissionDate()) && patient.isCurrentlyAdmitted())
                        .toList();
                showResultsInTable("Admitted Patients by Admission Date", new String[]{"ID", "Name", "Age", "Gender", "Ward"},
                        filteredPatients.stream().map(p -> new Object[]{p.getId(), p.getUsername(), p.getAge(), p.getGender(), p.getWard()}).toArray(Object[][]::new));
            }
        });

        // 2. Admitted Patients by Discharge Date
        admittedByDischargeDateButton.addActionListener(e -> {
            String date = JOptionPane.showInputDialog(this, "Enter Discharge Date (DD/MM/YYYY):", "Filter by Discharge Date", JOptionPane.QUESTION_MESSAGE);
            if (date != null && !date.isEmpty()) {
                List<Patient> filteredPatients = patientManager.getAllPatients().stream()
                        .filter(patient -> date.equals(patient.getDischargeDate()) && !patient.isCurrentlyAdmitted())
                        .toList();
                showResultsInTable("Admitted Patients by Discharge Date", new String[]{"ID", "Name", "Age", "Gender", "Ward"},
                        filteredPatients.stream().map(p -> new Object[]{p.getId(), p.getUsername(), p.getAge(), p.getGender(), p.getWard()}).toArray(Object[][]::new));
            }
        });

        // 3. Admitted Patients by Doctor
        admittedByDoctorButton.addActionListener(e -> {
            String doctorName = JOptionPane.showInputDialog(this, "Enter Doctor Name:", "Filter by Doctor", JOptionPane.QUESTION_MESSAGE);
            if (doctorName != null && !doctorName.isEmpty()) {
                List<Patient> filteredPatients = patientManager.getAllPatients().stream()
                        .filter(patient -> doctorName.equalsIgnoreCase(patient.getDoctorName()) && patient.isCurrentlyAdmitted())
                        .toList();
                showResultsInTable("Admitted Patients by Doctor", new String[]{"ID", "Name", "Age", "Gender", "Ward"},
                        filteredPatients.stream().map(p -> new Object[]{p.getId(), p.getUsername(), p.getAge(), p.getGender(), p.getWard()}).toArray(Object[][]::new));
            }
        });

        // 4. Appointment Reports by Doctor
        appointmentsByDoctorButton.addActionListener(e -> {
            String doctorName = JOptionPane.showInputDialog(this, "Enter Doctor Name:", "Filter by Doctor", JOptionPane.QUESTION_MESSAGE);
            if (doctorName != null && !doctorName.isEmpty()) {
                List<Appointment> filteredAppointments = appointmentManager.getAllAppointments().stream()
                        .filter(appointment -> doctorName.equalsIgnoreCase(appointment.getDoctorName()))
                        .toList();
                showResultsInTable("Appointments by Doctor", new String[]{"ID", "Patient Name", "Doctor Name", "Date"},
                        filteredAppointments.stream().map(a -> new Object[]{a.getId(), a.getPatientName(), a.getDoctorName(), a.getDate()}).toArray(Object[][]::new));
            }
        });

        // 5. Appointments by Date
        appointmentsByDateButton.addActionListener(e -> {
            String date = JOptionPane.showInputDialog(this, "Enter Date (DD/MM/YYYY):", "Filter by Date", JOptionPane.QUESTION_MESSAGE);
            if (date != null && !date.isEmpty()) {
                List<Appointment> filteredAppointments = appointmentManager.getAllAppointments().stream()
                        .filter(appointment -> date.equals(appointment.getDate()))
                        .toList();
                showResultsInTable("Appointments by Date", new String[]{"ID", "Patient Name", "Doctor Name", "Date"},
                        filteredAppointments.stream().map(a -> new Object[]{a.getId(), a.getPatientName(), a.getDoctorName(), a.getDate()}).toArray(Object[][]::new));
            }
        });

        // 6. Medicine by Availability
        medicineByAvailabilityButton.addActionListener(e -> {
            List<Medicine> availableMedicines = medicineManager.getAllMedicines().stream()
                    .filter(Medicine::isAvailable)
                    .toList();
            showResultsInTable("Available Medicines", new String[]{"ID", "Name", "Price", "Available"},
                    availableMedicines.stream().map(m -> new Object[]{m.getId(), m.getName(), m.getPrice(), m.isAvailable() ? "Yes" : "No"}).toArray(Object[][]::new));
        });

        // 7. Total Number of Records
        totalRecordsButton.addActionListener(e -> {
            int totalPatients = patientManager.getAllPatients().size();
            int totalAppointments = appointmentManager.getAllAppointments().size();
            int totalMedicines = medicineManager.getAllMedicines().size();
            JOptionPane.showMessageDialog(this, "Total Records:\n" +
                            "Patients: " + totalPatients + "\n" +
                            "Appointments: " + totalAppointments + "\n" +
                            "Medicines: " + totalMedicines,
                    "Total Records", JOptionPane.INFORMATION_MESSAGE);
        });

        // 8. Back Button
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));
    }

    // Utility method to display results in a JTable
    private void showResultsInTable(String title, String[] columns, Object[][] data) {
        JFrame resultFrame = new JFrame(title);
        resultFrame.setSize(800, 400);
        resultFrame.setLayout(new BorderLayout());
        JTable resultTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.setLocationRelativeTo(this);
        resultFrame.setVisible(true);
    }
}
