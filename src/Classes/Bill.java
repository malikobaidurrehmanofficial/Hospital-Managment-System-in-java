package Classes;

public class Bill {
    private int billId;
    private int patientId;
    private double doctorCharges;
    private double wardCharges;
    private double medicineCharges;
    private double totalAmount;

    public Bill(int billId, int patientId, double doctorCharges, double wardCharges, double medicineCharges) {
        this.billId = billId;
        this.patientId = patientId;
        this.doctorCharges = doctorCharges;
        this.wardCharges = wardCharges;
        this.medicineCharges = medicineCharges;
        this.totalAmount = doctorCharges + wardCharges + medicineCharges;
    }

    public int getBillId() {
        return billId;
    }

    public int getPatientId() {
        return patientId;
    }

    public double getDoctorCharges() {
        return doctorCharges;
    }

    public double getWardCharges() {
        return wardCharges;
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String toFileString() {
        return billId + "|" + patientId + "|" + doctorCharges + "|" + wardCharges + "|" + medicineCharges + "|" + totalAmount;
    }

    // Create Bill object from file string
    public static Bill fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Bill(
                Integer.parseInt(parts[0]), // billId
                Integer.parseInt(parts[1]), // patientId
                Double.parseDouble(parts[2]), // doctorCharges
                Double.parseDouble(parts[3]), // wardCharges
                Double.parseDouble(parts[4]) // medicineCharges
        );
    }

    @Override
    public String toString() {
        return "Bill [ID=" + billId + ", Patient ID=" + patientId + ", Total=" + totalAmount + "]";
    }
}
