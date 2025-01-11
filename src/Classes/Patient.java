package Classes;

public class Patient extends User {
    private int age;
    private String gender;
    private String disease;
    private int bedNumber;
    private String ward;
    private int wardId;
    private String doctorName;
    private int doctorId;
    private String admissionDate;
    private String dischargeDate;
    private boolean isCurrentlyAdmitted;
    private double bill;

    // Constructor
    public Patient( String username, String password, int age, String gender, String disease,
                   int bedNumber, String ward, int wardId, String doctorName, int doctorId,
                   String admissionDate, String dischargeDate, boolean isCurrentlyAdmitted, double bill) {
        super(UniqueIdGenerator.generatePatientId(), username, password, "Patient"); // Role is set to "Patient"
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.bedNumber = bedNumber;
        this.ward = ward;
        this.wardId = wardId;
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.isCurrentlyAdmitted = isCurrentlyAdmitted;
        this.bill = bill;
    }
    public Patient(Patient original) {
        super(original.getId(), original.getUsername(), original.getPassword(), original.getRole());
        this.age = original.age;
        this.gender = original.gender;
        this.disease = original.disease;
        this.bedNumber = original.bedNumber;
        this.ward = original.ward;
        this.wardId = original.wardId;
        this.doctorName = original.doctorName;
        this.doctorId = original.doctorId;
        this.admissionDate = original.admissionDate;
        this.dischargeDate = original.dischargeDate;
        this.isCurrentlyAdmitted = original.isCurrentlyAdmitted;
        this.bill = original.bill;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public boolean isCurrentlyAdmitted() {
        return isCurrentlyAdmitted;
    }

    public void setCurrentlyAdmitted(boolean currentlyAdmitted) {
        isCurrentlyAdmitted = currentlyAdmitted;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Patient [ID=" + getId() + ", Name=" + getUsername() + ", Disease=" + disease + ", Admitted=" + isCurrentlyAdmitted + "]";
    }

    public String toFileString() {
        return getId() + "|" + getUsername() + "|" + getPassword() + "|" + age + "|" + gender + "|" +
                disease + "|" + bedNumber + "|" + ward + "|" + wardId + "|" + doctorName + "|" +
                doctorId + "|" + admissionDate + "|" + (dischargeDate == null ? "null" : dischargeDate) + "|" +
                isCurrentlyAdmitted + "|" + bill;
    }

    public static Patient fromFileString(String line) {
        String[] parts = line.split("\\|");
        return new Patient(
                parts[1],
                parts[2],
                Integer.parseInt(parts[3]),
                parts[4],
                parts[5],
                Integer.parseInt(parts[6]),
                parts[7],
                Integer.parseInt(parts[8]),
                parts[9],
                Integer.parseInt(parts[10]),
                parts[11],
                "null".equals(parts[12]) ? null : parts[12],
                Boolean.parseBoolean(parts[13]),
                Double.parseDouble(parts[14])
        );
    }
}
