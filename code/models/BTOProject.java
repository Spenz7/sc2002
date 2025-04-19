package models;

public class BTOProject {
    // Attributes
    private String name;
    private String neighborhood;
    private String flatType;  // Example: "3-room", "4-room", etc.
    private int unitCount;
    private double price;
    private boolean visibility;  // Indicates if the project is visible to applicants

    // Constructor
    public BTOProject(String name, String neighborhood, String flatType, int unitCount, double price, boolean visibility) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.flatType = flatType;
        this.unitCount = unitCount;
        this.price = price;
        this.visibility = visibility;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    // Method: Decrement Unit Count
    public boolean decrementUnits() {
        if (unitCount > 0) {
            unitCount--;
            return true;
        }
        return false;  // No units available
    }

    // Override toString for Displaying Project Information
    @Override
    public String toString() {
        return "BTOProject{" +
                "name='" + name + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", flatType='" + flatType + '\'' +
                ", unitCount=" + unitCount +
                ", price=" + price +
                ", visibility=" + visibility +
                '}';
    }
}
