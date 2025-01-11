package Views;

import ControllerClasses.*;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    public AdminPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 115, 220));

        JLabel titleLabel = new JLabel("Admin Panel", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonPanel.setBackground(new Color(50, 115, 220));

        JButton patientButton = new JButton("Manage Patients");
        JButton doctorButton = new JButton("Manage Doctors");
        JButton wardButton = new JButton("Manage Wards");
        JButton medicineButton = new JButton("Manage Medicines");
        JButton appointmentButton = new JButton("Manage Appointments");
        JButton reportsButton = new JButton("Generate Reports");
        JButton billingButton = new JButton("Billing");
        JButton logoutButton = new JButton("Logout");

        configureButton(patientButton);
        configureButton(doctorButton);
        configureButton(wardButton);
        configureButton(medicineButton);
        configureButton(appointmentButton);
        configureButton(reportsButton);
        configureButton(billingButton);
        configureButton(logoutButton);

        buttonPanel.add(patientButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(wardButton);
        buttonPanel.add(medicineButton);
        buttonPanel.add(appointmentButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(billingButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        patientButton.addActionListener(e -> mainFrame.showCard("PatientManagementPanel"));
        doctorButton.addActionListener(e -> mainFrame.showCard("DoctorPanel"));
        wardButton.addActionListener(e -> mainFrame.showCard("WardPanel"));
        medicineButton.addActionListener(e -> mainFrame.showCard("MedicinePanel"));
        appointmentButton.addActionListener(e -> mainFrame.showCard("AppointmentPanel"));
        reportsButton.addActionListener(e -> mainFrame.showCard("ReportsPanel"));
        billingButton.addActionListener(e -> mainFrame.showCard("BillingPanel"));
        logoutButton.addActionListener(e -> mainFrame.showCard("LoginPanel"));
    }

    private void configureButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(50, 115, 220));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 115, 220), 2));
    }
}
