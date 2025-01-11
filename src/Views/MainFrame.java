package Views;

import ControllerClasses.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserManager userManager; // Add UserManager as a field

    public MainFrame() {
        // Initialize the UserManager
        userManager = new UserManager(); // Ensure this is instantiated properly

        // Frame settings
        setTitle("Hospital Management System");
        setSize(1024, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon cuiogo=new ImageIcon(getClass().getResource("/Images/cui.png"));
        setIconImage(cuiogo.getImage());
        setLocationRelativeTo(null);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to the CardLayout
        addPanels();

        // Add main panel to the frame
        add(mainPanel);
    }

    private void addPanels() {

        mainPanel.add(new LoginPanel(this, userManager), "LoginPanel"); // Pass both MainFrame and UserManager
        PatientManager patientManager=new PatientManager();
        patientManager.loadFromFile();
        DoctorManager doctorManager=new DoctorManager();
        doctorManager.loadFromFile();
        WardManager wardManager=new WardManager();
        wardManager.loadFromFile();
        MedicineManager medicineManager=new MedicineManager();
        medicineManager.loadFromFile();
        AppointmentManager appointmentManager=new AppointmentManager();
        appointmentManager.loadFromFile();


        mainPanel.add(new AdminPanel(this), "AdminPanel");
        mainPanel.add(new PatientManagementPanel(patientManager,doctorManager,wardManager,mainPanel,cardLayout), "PatientManagementPanel");
        // Inside MainFrame.java
//        mainPanel.add(new AddPatientPanel(new DoctorManager(),new WardManager(),new PatientManager(),mainPanel,cardLayout), "AddPatientPanel");
//        mainPanel.add(new UpdatePatientPanel(this), "UpdatePatientPanel");
//        mainPanel.add(new DeletePatientPanel(this), "DeletePatientPanel");

        mainPanel.add(new DoctorPanel(doctorManager,mainPanel,cardLayout), "DoctorPanel");
        mainPanel.add(new WardPanel(wardManager,mainPanel,cardLayout),"WardPanel");
        mainPanel.add(new MedicinePanel(medicineManager,mainPanel,cardLayout),"MedicinePanel");
        mainPanel.add(new AppointmentPanel(appointmentManager,doctorManager,mainPanel,cardLayout),"AppointmentPanel");
        mainPanel.add(new ReportsPanel(patientManager,appointmentManager,medicineManager,doctorManager,mainPanel,cardLayout),"ReportsPanel");
        mainPanel.add(new BillingPanel(patientManager, mainPanel, cardLayout), "BillingPanel");


        // TODO: Add other panels as needed
    }

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }
}
