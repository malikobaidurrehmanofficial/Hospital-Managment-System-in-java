package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import ControllerClasses.PatientManager;
import Classes.Bill;

public class BillingPanel extends JPanel {
    public BillingPanel(PatientManager patientManager, JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 115, 220));

        JLabel titleLabel = new JLabel("Billing Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Bill ID", "Patient ID", "Doctor Charges", "Ward Charges", "Medicine Charges", "Total"};
        JTable billTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(billTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(50, 115, 220));
        JButton generateBillButton = new JButton("Generate Bill");
        JButton printBillButton = new JButton("Print Bill");
        JButton backButton = new JButton("Back");
        configureButton(generateBillButton);
        configureButton(printBillButton);
        configureButton(backButton);
        buttonPanel.add(generateBillButton);
        buttonPanel.add(printBillButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable(billTable, patientManager, columns);

        generateBillButton.addActionListener(e -> {
            String patientIdStr = JOptionPane.showInputDialog(this, "Enter Patient ID:");
            if (patientIdStr != null && !patientIdStr.isEmpty()) {
                try {
                    int patientId = Integer.parseInt(patientIdStr);
                    double doctorCharges = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Doctor Charges:"));
                    double wardCharges = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Ward Charges:"));
                    double medicineCharges = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Medicine Charges:"));

                    patientManager.generateBill(patientId, doctorCharges, wardCharges, medicineCharges);
                    JOptionPane.showMessageDialog(this, "Bill generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable(billTable, patientManager, columns);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        printBillButton.addActionListener(e -> {
            int selectedRow = billTable.getSelectedRow();
            if (selectedRow >= 0) {
                StringBuilder billDetails = new StringBuilder();
                for (int i = 0; i < billTable.getColumnCount(); i++) {
                    billDetails.append(columns[i]).append(": ").append(billTable.getValueAt(selectedRow, i)).append("\n");
                }
                JTextArea printArea = new JTextArea(billDetails.toString());
                try {
                    printArea.print();
                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to print the bill.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a bill to print.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "AdminPanel"));
    }

    private void refreshTable(JTable table, PatientManager patientManager, String[] columns) {
        ArrayList<Bill> bills = patientManager.getAllBills();
        Object[][] data = bills.stream()
                .map(bill -> new Object[]{
                        bill.getBillId(),
                        bill.getPatientId(),
                        bill.getDoctorCharges(),
                        bill.getWardCharges(),
                        bill.getMedicineCharges(),
                        bill.getTotalAmount()
                })
                .toArray(Object[][]::new);
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private void configureButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(50, 115, 220));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
    }
}
