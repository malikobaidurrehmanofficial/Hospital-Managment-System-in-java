package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import ControllerClasses.WardManager;
import Classes.Ward;

public class WardPanel extends JPanel {
    public WardPanel(WardManager wardManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Manage Wards", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        add(titleLabel, BorderLayout.NORTH);


        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Ward: ");
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);


        String[] columns = {"ID", "Name", "Capacity", "Occupied Beds"};
        JTable wardTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(wardTable);
        add(tableScrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addWardButton = new JButton("Add Ward");
        JButton updateWardButton = new JButton("Update Ward");
        JButton deleteWardButton = new JButton("Delete Ward");
        JButton backButton = new JButton("Back");

        buttonPanel.add(addWardButton);
        buttonPanel.add(updateWardButton);
        buttonPanel.add(deleteWardButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);


        addWardButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Enter Ward Name:");
            if (name != null && !name.trim().isEmpty()) {
                try {
                    int capacity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Ward Capacity:"));
                    Ward newWard = new Ward(name, capacity);
                    wardManager.addWard(newWard);
                    JOptionPane.showMessageDialog(this, "Ward added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(wardTable, wardManager, columns, "");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid capacity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateWardButton.addActionListener(e -> {
            int selectedRow = wardTable.getSelectedRow();
            if (selectedRow >= 0) {
                int wardId = (int) wardTable.getValueAt(selectedRow, 0);
                Ward ward = wardManager.searchWardById(wardId);
                if (ward != null) {
                    String newName = JOptionPane.showInputDialog(this, "Enter New Name:", ward.getName());
                    try {
                        int newCapacity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter New Capacity:", ward.getCapacity()));

                        if (newName != null && !newName.trim().isEmpty()) {
                            ward.setName(newName);
                            ward.setCapacity(newCapacity);
                            JOptionPane.showMessageDialog(this, "Ward updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            refreshTable(wardTable, wardManager, columns, "");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid capacity. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a ward to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteWardButton.addActionListener(e -> {
            int selectedRow = wardTable.getSelectedRow();
            if (selectedRow >= 0) {
                int wardId = (int) wardTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this ward?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    wardManager.deleteWard(wardId);
                    JOptionPane.showMessageDialog(this, "Ward deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(wardTable, wardManager, columns, "");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a ward to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));

        // Search bar functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText();
                refreshTable(wardTable, wardManager, columns, searchText);
            }
        });

        // Initial table load
        refreshTable(wardTable, wardManager, columns, "");
    }

    // Refresh the table with updated data based on the search text
    private void refreshTable(JTable table, WardManager wardManager, String[] columns, String searchText) {
        Object[][] updatedData = wardManager.getAllWards().stream()
                .filter(ward -> ward.getName().toLowerCase().contains(searchText.toLowerCase()))
                .map(ward -> new Object[]{ward.getId(), ward.getName(), ward.getCapacity(), ward.getOccupiedBeds()})
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(updatedData, columns));
    }
}
