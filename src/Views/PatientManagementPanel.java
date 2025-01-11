package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import ControllerClasses.PatientManager;
import ControllerClasses.DoctorManager;
import ControllerClasses.WardManager;
import Classes.Patient;

public class PatientManagementPanel extends JPanel {

    public PatientManagementPanel(PatientManager patientManager, DoctorManager doctorManager, WardManager wardManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Manage Patients", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);

        // Search bar panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Patient: ");
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Table to display patient details
        String[] columns = {"ID", "Name", "Disease", "Ward", "Bed Number"};
        JTable patientTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(patientTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addPatientButton = new JButton("Add Patient");
        JButton updatePatientButton = new JButton("Update Patient");
        JButton dischargePatientButton = new JButton("Discharge Patient");
        JButton viewHistoryButton = new JButton("View History");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addPatientButton);
        buttonPanel.add(updatePatientButton);
        buttonPanel.add(dischargePatientButton);
        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Patient Button Action
        addPatientButton.addActionListener(e -> openPatientForm(null, patientManager, doctorManager, wardManager, patientTable, columns));

        // Update Patient Button Action
        updatePatientButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                int patientId = (int) patientTable.getValueAt(selectedRow, 0);
                Patient patient = patientManager.searchPatientById(patientId);
                if (patient != null) {
                    openPatientForm(patient, patientManager, doctorManager, wardManager, patientTable, columns);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Discharge Patient Button Action
        dischargePatientButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                int patientId = (int) patientTable.getValueAt(selectedRow, 0);
                Patient patient = patientManager.searchPatientById(patientId);
                if (patient != null && patient.isCurrentlyAdmitted()) {
                    // Mark patient as discharged and free the bed
                    patient.setCurrentlyAdmitted(false);
                    patient.setDischargeDate(java.time.LocalDate.now().toString());
                    wardManager.getWardById(patient.getWardId()).dischargePatient();

                    // Add patient record to history
                    patientManager.addPatientToHistory(patient);

                    JOptionPane.showMessageDialog(this, "Patient discharged successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(patientTable, patientManager, columns, "");
                } else {
                    JOptionPane.showMessageDialog(this, "Patient is not currently admitted.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to discharge.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // View History Button Action
        viewHistoryButton.addActionListener(e -> {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                int patientId = (int) patientTable.getValueAt(selectedRow, 0);
                List<Patient> history = patientManager.getPatientHistory(patientId);
                if (history != null && !history.isEmpty()) {
                    StringBuilder historyDetails = new StringBuilder("History for Patient ID: " + patientId + "\n\n");
                    for (Patient record : history) {
                        historyDetails.append("Admission Date: ").append(record.getAdmissionDate()).append("\n");
                        historyDetails.append("Discharge Date: ").append(record.getDischargeDate() != null ? record.getDischargeDate() : "Still Admitted").append("\n");
                        historyDetails.append("Ward: ").append(record.getWard()).append("\n");
                        historyDetails.append("Bed Number: ").append(record.getBedNumber()).append("\n\n");
                    }
                    JOptionPane.showMessageDialog(this, historyDetails.toString(), "Patient History", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No history found for the selected patient.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to view history.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));

        // Search Functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                refreshTable(patientTable, patientManager, columns, searchText);
            }
        });

        // Initial Table Load
        refreshTable(patientTable, patientManager, columns, "");
    }

    // Opens a JFrame for adding or updating a patient
    private void openPatientForm(Patient existingPatient, PatientManager patientManager, DoctorManager doctorManager, WardManager wardManager, JTable patientTable, String[] columns) {
        JFrame patientForm = new JFrame(existingPatient == null ? "Add Patient" : "Update Patient");
        patientForm.setSize(400, 500);
        patientForm.setLayout(new BorderLayout());
        patientForm.setResizable(false); // Disable resizing
        patientForm.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(14, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(existingPatient != null ? existingPatient.getUsername() : "");
        JTextField ageField = new JTextField(existingPatient != null ? String.valueOf(existingPatient.getAge()) : "");
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        if (existingPatient != null) {
            genderCombo.setSelectedItem(existingPatient.getGender());
        }
        JTextField diseaseField = new JTextField(existingPatient != null ? existingPatient.getDisease() : "");
        JTextField passwordField = new JTextField(existingPatient != null ? existingPatient.getPassword() : "");
        JTextField admissionDateField = new JTextField(existingPatient != null ? existingPatient.getAdmissionDate() : "");
        JTextField dischargeDateField = new JTextField(existingPatient != null ? existingPatient.getDischargeDate() : "");

        JCheckBox currentlyAdmittedCheck = new JCheckBox("Currently Admitted", existingPatient == null || existingPatient.isCurrentlyAdmitted());

        JComboBox<String> doctorCombo = new JComboBox<>();
        JComboBox<String> wardCombo = new JComboBox<>();
        JComboBox<Integer> bedCombo = new JComboBox<>();

        // Populate doctor and ward dropdowns dynamically
        doctorCombo.removeAllItems();
        for (var doctor : doctorManager.getAllDoctors()) {
            doctorCombo.addItem(doctor.getId() + " - " + doctor.getUsername());
        }

        wardCombo.removeAllItems();
        for (var ward : wardManager.getAllWards()) {
            wardCombo.addItem(ward.getId() + " - " + ward.getName());
        }

        if (existingPatient != null) {
            doctorCombo.setSelectedItem(existingPatient.getDoctorId() + " - " + existingPatient.getDoctorName());
            wardCombo.setSelectedItem(existingPatient.getWardId() + " - " + existingPatient.getWard());
            updateBedCombo(wardCombo, bedCombo, wardManager, existingPatient.getBedNumber());
        } else {
            updateBedCombo(wardCombo, bedCombo, wardManager, -1);
        }

        wardCombo.addActionListener(e -> updateBedCombo(wardCombo, bedCombo, wardManager, -1));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderCombo);
        formPanel.add(new JLabel("Disease:"));
        formPanel.add(diseaseField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Admission Date (DD/MM/YYYY):"));
        formPanel.add(admissionDateField);
        formPanel.add(new JLabel("Discharge Date (DD/MM/YYYY):"));
        formPanel.add(dischargeDateField);
        formPanel.add(new JLabel("Currently Admitted:"));
        formPanel.add(currentlyAdmittedCheck);
        formPanel.add(new JLabel("Doctor:"));
        formPanel.add(doctorCombo);
        formPanel.add(new JLabel("Ward:"));
        formPanel.add(wardCombo);
        formPanel.add(new JLabel("Bed Number:"));
        formPanel.add(bedCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton(existingPatient == null ? "Add Patient" : "Update Patient");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String ageText = ageField.getText();
                String gender = (String) genderCombo.getSelectedItem();
                String disease = diseaseField.getText();
                String password = passwordField.getText();
                String admissionDate = admissionDateField.getText();
                String dischargeDate = dischargeDateField.getText();
                boolean isCurrentlyAdmitted = currentlyAdmittedCheck.isSelected();
                String selectedDoctor = (String) doctorCombo.getSelectedItem();
                String selectedWard = (String) wardCombo.getSelectedItem();
                Integer selectedBed = (Integer) bedCombo.getSelectedItem();

                if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty() || password.isEmpty() || selectedDoctor == null || selectedWard == null || selectedBed == null) {
                    JOptionPane.showMessageDialog(patientForm, "All fields except Discharge Date are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age = Integer.parseInt(ageText);
                int doctorId = Integer.parseInt(selectedDoctor.split(" - ")[0]);
                int wardId = Integer.parseInt(selectedWard.split(" - ")[0]);

                if (existingPatient == null) {
                    Patient newPatient = new Patient(
                            name,
                            password,
                            age,
                            gender,
                            disease,
                            selectedBed,
                            wardManager.getWardById(wardId).getName(),
                            wardId,
                            doctorManager.getDoctorById(doctorId).getUsername(),
                            doctorId,
                            admissionDate,
                            isCurrentlyAdmitted ? null : dischargeDate,
                            isCurrentlyAdmitted,
                            0.0
                    );
                    patientManager.addPatient(newPatient);
                    if (isCurrentlyAdmitted) {
                        wardManager.getWardById(wardId).admitPatient();
                    }
                } else {
                    existingPatient.setUsername(name);
                    existingPatient.setAge(age);
                    existingPatient.setGender(gender);
                    existingPatient.setDisease(disease);
                    existingPatient.setPassword(password);
                    existingPatient.setAdmissionDate(admissionDate);
                    existingPatient.setDischargeDate(isCurrentlyAdmitted ? null : dischargeDate);
                    existingPatient.setCurrentlyAdmitted(isCurrentlyAdmitted);
                    existingPatient.setDoctorId(doctorId);
                    existingPatient.setDoctorName(doctorManager.getDoctorById(doctorId).getUsername());
                    existingPatient.setWardId(wardId);
                    existingPatient.setWard(wardManager.getWardById(wardId).getName());
                    existingPatient.setBedNumber(selectedBed);
                }

                JOptionPane.showMessageDialog(patientForm, "Patient saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                patientForm.dispose();
                refreshTable(patientTable, patientManager, columns, "");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(patientForm, "Invalid age. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(patientForm, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> patientForm.dispose());

        patientForm.add(buttonPanel, BorderLayout.NORTH);
        patientForm.add(formPanel, BorderLayout.CENTER);
        patientForm.setVisible(true);
    }

    private void updateBedCombo(JComboBox<String> wardCombo, JComboBox<Integer> bedCombo, WardManager wardManager, int currentBed) {
        bedCombo.removeAllItems();
        String selectedWard = (String) wardCombo.getSelectedItem();
        if (selectedWard != null) {
            int wardId = Integer.parseInt(selectedWard.split(" - ")[0]);
            int totalBeds = wardManager.getWardById(wardId).getCapacity();
            int occupiedBeds = wardManager.getWardById(wardId).getOccupiedBeds();
            for (int i = 1; i <= totalBeds; i++) {
                if (i != currentBed && i <= occupiedBeds) {
                    continue;
                }
                bedCombo.addItem(i);
            }
            if (currentBed != -1) {
                bedCombo.setSelectedItem(currentBed);
            }
        }
    }

    private void refreshTable(JTable table, PatientManager patientManager, String[] columns, String searchText) {
        Object[][] updatedData = patientManager.getAllPatients().stream()
                .filter(patient -> patient.getUsername().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(patient.getId()).contains(searchText))
                .map(patient -> new Object[]{patient.getId(), patient.getUsername(), patient.getDisease(), patient.getWard(), patient.getBedNumber()})
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(updatedData, columns));
    }
}
