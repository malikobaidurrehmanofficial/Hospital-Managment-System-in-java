# Hospital Management System

## Project Overview

The **Hospital Management System** is a Java-based application designed to streamline hospital operations using Object-Oriented Programming (OOP) principles. The application incorporates a user-friendly graphical interface built with **Swing** and organizes functionality across four main packages:

1. **Main**: Entry point of the application.
2. **Controllers**: Handles business logic and controls the application's core functionality.
3. **Views**: Contains Swing-based GUI components, including panels for various screens.
4. **Files**: Manages persistent data storage using text files.

This project aims to provide basic management functionality for hospitals. However, further extensions for handling doctor and patient modules are encouraged.

---

## Features

- **Object-Oriented Design**: The project leverages OOP concepts such as inheritance, encapsulation, and polymorphism.
- **GUI with Swing**: Provides an intuitive interface for users.
- **File Handling**: Stores and retrieves data using plain text files.
- **Modular Architecture**: Packages and classes are organized for scalability and easy maintenance.

---

## Package Structure

### 1. `Classes`
Contains the application's core entites classe.

- `Patients`: .

### 2. `controllers`
Includes classes responsible for handling application logic and communication between views and data.

- Example classes:
  - `PatientController`: Manages overall patient operations.
  - `AppointmentController`: Handles appointment scheduling.

### 3. `views`
Contains all Swing-based GUI components, organized into panels for different screens.

- Example classes:
  - `AdminPanel`: Main dashboard for navigating the system.
  - `PatientPanel`: GUI for patient-related operations.

### 4. `files`
Manages data storage and retrieval using text files.

- Example classes:
  - `FileManager`: Reads and writes data to text files.
  - `DataParser`: Processes and formats data for storage.

---

## Future Development

This project currently provides the foundation for a hospital management system. Due to time constraints, certain modules, such as detailed doctor and patient management, have not been implemented. 

### Suggested Extensions

1. **Doctor Management Module**:
   - Add functionality to manage doctor profiles, specialties, and schedules.
   - Enable doctor-patient assignments.

2. **Patient Management Module**:
   - Develop a comprehensive patient database.
   - Implement features for medical history and treatment tracking.

3. **Database Integration**:
   - Replace text file storage with a robust database system like MySQL or PostgreSQL.

4. **Authentication**:
   - Add user roles such as admin, doctor, and receptionist.

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/malikobaidurrehmanofficial/Hospital-Managment-System-in-java
   ```
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans).
3. Compile and run the `Main` class to start the application.

---

## Contributing

Contributions are welcome! If you are a developer interested in expanding this project, feel free to:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with detailed notes about your changes.

---

## Acknowledgments

This project was developed with guidance and assistance from online resources and the developer community. Special thanks to those who supported the creation of this system.

---

## License

This project is licensed under the MIT License. You are free to use, modify, and distribute this project as per the terms of the license.
