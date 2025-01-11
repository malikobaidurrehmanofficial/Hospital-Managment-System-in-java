package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ControllerClasses.UserManager;

public class LoginPanel extends JPanel {

    public LoginPanel(MainFrame mainFrame, UserManager userManager) {
        // Set layout
        setLayout(new BorderLayout());
        setBackground(new Color(50, 115, 220));

        // Title
        JLabel titleLabel = new JLabel("Hospital Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for username and password
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(new Color(50, 115, 220));
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(50, 115, 220), 2));
        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(new Color(50, 115, 220));
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(50, 115, 220), 2));
        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(50, 115, 220));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(loginButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("Welcome to Hospital Management System Developed By Malik Obaid Ur Rehman (FA23-BCS-055)", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        footerLabel.setForeground(Color.WHITE);
        add(footerLabel, BorderLayout.SOUTH);

        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.validateUser(username, password)) {
                    String role = userManager.getUserRole(username);
                    if ("Manager".equals(role)) {
                        mainFrame.showCard("AdminPanel");
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Invalid role. Only Admins can log in.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
