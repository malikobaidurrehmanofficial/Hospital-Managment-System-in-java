package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ControllerClasses.DoctorManager;
import Classes.Doctor;

public class DoctorPanel extends JPanel {
    public DoctorPanel(DoctorManager doctorManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Manage Doctors", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);

        // Search bar panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Doctor: ");
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Table to display doctor details
        String[] columns = {"ID", "Name", "Specialization"};
        JTable doctorTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(doctorTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addDoctorButton = new JButton("Add Doctor");
        JButton updateDoctorButton = new JButton("Update Doctor");
        JButton deleteDoctorButton = new JButton("Delete Doctor");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addDoctorButton);
        buttonPanel.add(updateDoctorButton);
        buttonPanel.add(deleteDoctorButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        addDoctorButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter Doctor's Name:");
            if (name != null && !name.trim().isEmpty()) {
                String specialization = JOptionPane.showInputDialog(this, "Enter Doctor's Specialization:");
                if (specialization != null && !specialization.trim().isEmpty()) {
                    Doctor newDoctor = new Doctor(name, "default_password", specialization); // Replace with proper password logic
                    doctorManager.addDoctor(newDoctor);
                    JOptionPane.showMessageDialog(this, "Doctor added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(doctorTable, doctorManager, columns, "");
                }
            }
        });

        updateDoctorButton.addActionListener(e -> {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int doctorId = (int) doctorTable.getValueAt(selectedRow, 0);
                Doctor doctor = doctorManager.getDoctorById(doctorId);
                if (doctor != null) {
                    String newName = JOptionPane.showInputDialog(this, "Enter New Name:", doctor.getUsername());
                    String newSpecialization = JOptionPane.showInputDialog(this, "Enter New Specialization:", doctor.getSpecialization());

                    if (newName != null && newSpecialization != null && !newName.trim().isEmpty() && !newSpecialization.trim().isEmpty()) {
                        doctor.setUsername(newName);
                        doctor.setSpecialization(newSpecialization);
                        doctorManager.updateDoctor(doctor);
                        JOptionPane.showMessageDialog(this, "Doctor updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        refreshTable(doctorTable, doctorManager, columns, "");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a doctor to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteDoctorButton.addActionListener(e -> {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow >= 0) {
                int doctorId = (int) doctorTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    doctorManager.deleteDoctor(doctorId);
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(doctorTable, doctorManager, columns, "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));

        // Search bar functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                refreshTable(doctorTable, doctorManager, columns, searchText);
            }
        });

        // Initial table load
        refreshTable(doctorTable, doctorManager, columns, "");
    }

    // Refresh the table with updated data based on the search text
    private void refreshTable(JTable table, DoctorManager doctorManager, String[] columns, String searchText) {
        Object[][] updatedData = doctorManager.getAllDoctors().stream()
                .filter(doctor -> doctor.getUsername().toLowerCase().contains(searchText.toLowerCase()) || doctor.getSpecialization().toLowerCase().contains(searchText.toLowerCase()))
                .map(doctor -> new Object[]{doctor.getId(), doctor.getUsername(), doctor.getSpecialization()})
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(updatedData, columns));
    }
}
