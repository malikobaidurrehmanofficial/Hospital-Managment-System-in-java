package Classes;

public class UniqueIdGenerator {

    private static int userIdCounter = 2200;
    private static int patientIdCounter = 1000;
    private static int appointmentIdCounter = 5000;
    private static int wardIdCounter = 2000;
    private static int medicineIdCounter = 3000;
    private static int managerIdCounter=550;
    private static int doctorIdCounter=77;

    public static int generatePatientId() {
        return patientIdCounter++;
    }

    public static int generateAppointmentId() {
        return appointmentIdCounter++;
    }

    public static int generateWardId() {
        return wardIdCounter++;
    }

    public static int generateMedicineId() {
        return medicineIdCounter++;
    }

    public static int generateManagerId() {
        return managerIdCounter++;
    }

    public static int generateDoctorId() {
        return doctorIdCounter++;
    }
    public static int generateUserId(){
        return userIdCounter++;
    }
}
