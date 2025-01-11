package Classes;

public class Appointment {
    private int id;
    private String patientName;
    private String doctorName;
    private String date;


    public Appointment(int id, String patientName, String doctorName, String date) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment [ID=" + id + ", Patient=" + patientName + ", Doctor=" + doctorName + ", Date=" + date + "]";
    }
    public String toFileString() {
        return id + "|" + patientName + "|" + doctorName + "|" + date;
    }
    public static Appointment fromFileString(String fileString) {
        String[] parts = fileString.split("\\|");
        int id = Integer.parseInt(parts[0]);
        String patientName = parts[1];
        String doctorName = parts[2];
        String date = parts[3];
        return new Appointment(id, patientName, doctorName, date);
    }



}
