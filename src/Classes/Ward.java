package Classes;

public class Ward {
    private int id;
    private String name;
    private int capacity;
    private int occupiedBeds;


    public Ward(String name, int capacity) {
        this.id = UniqueIdGenerator.generateWardId();
        this.name = name;
        this.capacity = capacity;
        this.occupiedBeds = 0;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedBeds() {
        return occupiedBeds;
    }

    public void setOccupiedBeds(int occupiedBeds) {
        if (occupiedBeds <= capacity) {
            this.occupiedBeds = occupiedBeds;
        } else {
            throw new IllegalArgumentException("Occupied beds cannot exceed ward capacity.");
        }
    }


    public boolean hasAvailableBeds() {
        return occupiedBeds < capacity;
    }

    // Add a patient to the ward
    public void admitPatient() {
        if (hasAvailableBeds()) {
            occupiedBeds++;
        } else {
            throw new IllegalStateException("No available beds in the ward.");
        }
    }


    public void dischargePatient() {
        if (occupiedBeds > 0) {
            occupiedBeds--;
        } else {
            throw new IllegalStateException("No patients to discharge.");
        }
    }

    @Override
    public String toString() {
        return "Ward [ID=" + id + ", Name=" + name + ", Capacity=" + capacity + ", Occupied Beds=" + occupiedBeds + "]";
    }
    public String toFileString() {
        return id + "|" + name + "|" + capacity + "|" + occupiedBeds;
    }

    // Create a Ward object from a string read from a file
    public static Ward fromFileString(String fileString) {
        try {
            String[] parts = fileString.split("\\|");
            if (parts.length == 4) {
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int capacity = Integer.parseInt(parts[2]);
                int occupiedBeds = Integer.parseInt(parts[3]);

                Ward ward = new Ward(name, capacity); // Use the existing constructor
                ward.setOccupiedBeds(occupiedBeds);


                ward.id = id;

                return ward;
            }
        } catch (Exception e) {
            System.err.println("Error parsing Ward from file string: " + e.getMessage());
        }
        return null;
    }

}

