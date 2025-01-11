package Classes;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private boolean isAvailable;


    public Medicine(String name, double price, boolean isAvailable) {
        this.id = UniqueIdGenerator.generateMedicineId();
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Medicine [ID=" + id + ", Name=" + name + ", Price=" + price + ", Available=" + isAvailable + "]";
    }
    public String toFileString() {
        return id + "|" + name + "|" + price + "|" + isAvailable;
    }

    // Create a Medicine object from a file string
    public static Medicine fromFileString(String fileString) {
        try {
            String[] parts = fileString.split("\\|");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double price = Double.parseDouble(parts[2]);
            boolean isAvailable = Boolean.parseBoolean(parts[3]);
            return new Medicine(name, price, isAvailable);
        } catch (Exception e) {
            System.err.println("Error parsing medicine from file string: " + e.getMessage());
            return null;
        }
    }
}
