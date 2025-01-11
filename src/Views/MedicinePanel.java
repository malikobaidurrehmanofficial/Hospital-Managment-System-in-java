package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ControllerClasses.MedicineManager;
import Classes.Medicine;

public class MedicinePanel extends JPanel {

    public MedicinePanel(MedicineManager medicineManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Manage Medicines", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);

        // Search bar panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Medicine: ");
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        // Table to display medicine details
        String[] columns = {"ID", "Name", "Price", "Available"};
        JTable medicineTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(medicineTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addMedicineButton = new JButton("Add Medicine");
        JButton updateMedicineButton = new JButton("Update Medicine");
        JButton deleteMedicineButton = new JButton("Delete Medicine");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addMedicineButton);
        buttonPanel.add(updateMedicineButton);
        buttonPanel.add(deleteMedicineButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Medicine Button Action
        addMedicineButton.addActionListener(e -> openMedicineForm(null, medicineManager, medicineTable, columns));

        // Update Medicine Button Action
        updateMedicineButton.addActionListener(e -> {
            int selectedRow = medicineTable.getSelectedRow();
            if (selectedRow >= 0) {
                int medicineId = (int) medicineTable.getValueAt(selectedRow, 0);
                Medicine medicine = medicineManager.searchMedicineById(medicineId);
                if (medicine != null) {
                    openMedicineForm(medicine, medicineManager, medicineTable, columns);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a medicine to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Delete Medicine Button Action
        deleteMedicineButton.addActionListener(e -> {
            int selectedRow = medicineTable.getSelectedRow();
            if (selectedRow >= 0) {
                int medicineId = (int) medicineTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this medicine?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    medicineManager.deleteMedicine(medicineId);
                    JOptionPane.showMessageDialog(this, "Medicine deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(medicineTable, medicineManager, columns, "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a medicine to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));

        // Search Functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                refreshTable(medicineTable, medicineManager, columns, searchText);
            }
        });

        // Initial Table Load
        refreshTable(medicineTable, medicineManager, columns, "");
    }

    // Opens a JFrame for adding or updating a medicine
    private void openMedicineForm(Medicine existingMedicine, MedicineManager medicineManager, JTable medicineTable, String[] columns) {
        JFrame medicineForm = new JFrame(existingMedicine == null ? "Add Medicine" : "Update Medicine");
        medicineForm.setSize(400, 300);
        medicineForm.setLayout(new BorderLayout());
        medicineForm.setResizable(false); // Disable resizing
        medicineForm.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(existingMedicine != null ? existingMedicine.getName() : "");
        JTextField priceField = new JTextField(existingMedicine != null ? String.valueOf(existingMedicine.getPrice()) : "");
        JCheckBox availabilityCheckBox = new JCheckBox("Available", existingMedicine != null && existingMedicine.isAvailable());

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Available:"));
        formPanel.add(availabilityCheckBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton(existingMedicine == null ? "Add Medicine" : "Update Medicine");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String priceText = priceField.getText();
                boolean isAvailable = availabilityCheckBox.isSelected();

                if (name.isEmpty() || priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(medicineForm, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double price = Double.parseDouble(priceText);

                if (existingMedicine == null) {
                    Medicine newMedicine = new Medicine(name, price, isAvailable);
                    medicineManager.addMedicine(newMedicine);
                } else {
                    existingMedicine.setName(name);
                    existingMedicine.setPrice(price);
                    existingMedicine.setAvailable(isAvailable);
                }

                JOptionPane.showMessageDialog(medicineForm, "Medicine saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                medicineForm.dispose();
                refreshTable(medicineTable, medicineManager, columns, "");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(medicineForm, "Invalid price. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> medicineForm.dispose());

        medicineForm.add(formPanel, BorderLayout.CENTER);
        medicineForm.add(buttonPanel, BorderLayout.SOUTH);
        medicineForm.setVisible(true);
    }

    // Refresh the table with updated data based on the search text
    private void refreshTable(JTable table, MedicineManager medicineManager, String[] columns, String searchText) {
        Object[][] updatedData = medicineManager.getAllMedicines().stream()
                .filter(medicine -> medicine.getName().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(medicine.getId()).contains(searchText))
                .map(medicine -> new Object[]{medicine.getId(), medicine.getName(), medicine.getPrice(), medicine.isAvailable() ? "Yes" : "No"})
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(updatedData, columns));
    }
}
