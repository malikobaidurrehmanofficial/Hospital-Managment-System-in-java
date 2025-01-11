package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ControllerClasses.AppointmentManager;
import ControllerClasses.DoctorManager;
import Classes.Appointment;

public class AppointmentPanel extends JPanel {

    public AppointmentPanel(AppointmentManager appointmentManager, DoctorManager doctorManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Manage Appointments", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);

        // Search bar panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Appointment: ");
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Table to display appointment details
        String[] columns = {"ID", "Patient Name", "Doctor Name", "Date"};
        JTable appointmentTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(appointmentTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addAppointmentButton = new JButton("Add Appointment");
        JButton updateAppointmentButton = new JButton("Update Appointment");
        JButton deleteAppointmentButton = new JButton("Delete Appointment");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addAppointmentButton);
        buttonPanel.add(updateAppointmentButton);
        buttonPanel.add(deleteAppointmentButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Appointment Button Action
        addAppointmentButton.addActionListener(e -> openAppointmentForm(null, appointmentManager, doctorManager, appointmentTable, columns));

        // Update Appointment Button Action
        updateAppointmentButton.addActionListener(e -> {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int appointmentId = (int) appointmentTable.getValueAt(selectedRow, 0);
                Appointment appointment = appointmentManager.searchAppointmentById(appointmentId);
                if (appointment != null) {
                    openAppointmentForm(appointment, appointmentManager, doctorManager, appointmentTable, columns);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete Appointment Button Action
        deleteAppointmentButton.addActionListener(e -> {
            int selectedRow = appointmentTable.getSelectedRow();
            if (selectedRow >= 0) {
                int appointmentId = (int) appointmentTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this appointment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    appointmentManager.deleteAppointment(appointmentId);
                    JOptionPane.showMessageDialog(this, "Appointment deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(appointmentTable, appointmentManager, columns, "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));

        // Search Functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                refreshTable(appointmentTable, appointmentManager, columns, searchText);
            }
        });

        // Initial Table Load
        refreshTable(appointmentTable, appointmentManager, columns, "");
    }

    // Opens a JFrame for adding or updating an appointment
    private void openAppointmentForm(Appointment existingAppointment, AppointmentManager appointmentManager, DoctorManager doctorManager, JTable appointmentTable, String[] columns) {
        JFrame appointmentForm = new JFrame(existingAppointment == null ? "Add Appointment" : "Update Appointment");
        appointmentForm.setSize(500, 400);
        appointmentForm.setLayout(new BorderLayout());
        appointmentForm.setResizable(false); // Disable resizing
        appointmentForm.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Adjusted for simplified fields
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField patientNameField = new JTextField(existingAppointment != null ? existingAppointment.getPatientName() : "");
        JComboBox<String> doctorCombo = new JComboBox<>();
        JTextField dateField = new JTextField(existingAppointment != null ? existingAppointment.getDate() : "");

        // Populate doctor dropdown
        for (var doctor : doctorManager.getAllDoctors()) {
            doctorCombo.addItem(doctor.getUsername());
        }

        if (existingAppointment != null) {
            doctorCombo.setSelectedItem(existingAppointment.getDoctorName());
        }

        formPanel.add(new JLabel("Patient Name:"));
        formPanel.add(patientNameField);
        formPanel.add(new JLabel("Doctor:"));
        formPanel.add(doctorCombo);
        formPanel.add(new JLabel("Date (DD/MM/YYYY):"));
        formPanel.add(dateField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton(existingAppointment == null ? "Add Appointment" : "Update Appointment");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String patientName = patientNameField.getText();
                String selectedDoctor = (String) doctorCombo.getSelectedItem();
                String date = dateField.getText();

                if (patientName.isEmpty() || selectedDoctor == null || date.isEmpty()) {
                    JOptionPane.showMessageDialog(appointmentForm, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (existingAppointment == null) {
                    Appointment newAppointment = new Appointment(
                            appointmentManager.getAllAppointments().size() + 1,
                            patientName,
                            selectedDoctor,
                            date
                    );
                    appointmentManager.addAppointment(newAppointment);
                } else {
                    existingAppointment.setPatientName(patientName);
                    existingAppointment.setDoctorName(selectedDoctor);
                    existingAppointment.setDate(date);
                }

                JOptionPane.showMessageDialog(appointmentForm, "Appointment saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                appointmentForm.dispose();
                refreshTable(appointmentTable, appointmentManager, columns, "");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(appointmentForm, "Invalid input. Please check the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> appointmentForm.dispose());

        appointmentForm.add(formPanel, BorderLayout.CENTER);
        appointmentForm.add(buttonPanel, BorderLayout.SOUTH);
        appointmentForm.setVisible(true);
    }

    // Refresh the table with updated data based on the search text
    private void refreshTable(JTable table, AppointmentManager appointmentManager, String[] columns, String searchText) {
        Object[][] updatedData = appointmentManager.getAllAppointments().stream()
                .filter(appointment -> appointment
                        .getPatientName().toLowerCase().contains(searchText.toLowerCase()) ||
                        appointment.getDoctorName().toLowerCase().contains(searchText.toLowerCase()) ||
                        appointment.getDate().contains(searchText) ||
                        String.valueOf(appointment.getId()).contains(searchText))
                .map(appointment -> new Object[]{
                        appointment.getId(),
                        appointment.getPatientName(),
                        appointment.getDoctorName(),
                        appointment.getDate()
                })
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(updatedData, columns));
    }
}
