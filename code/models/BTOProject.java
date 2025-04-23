package models;

import models.enums.FlatType;

import java.util.HashMap;
import java.util.Map;

public class BTOProject {
    // Attributes
    private String name;
    private String neighborhood;
    private int flatType; //2 room or 3 room
    private int unitCount;
    private double price;
    private boolean visibility;
    private Map<Integer, Integer> availableUnits; // Map for flat type and available units

    // Constructor
    public BTOProject(String name, String neighborhood, int flatType, int unitCount, double price, boolean visibility) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.flatType = flatType; // This remains as an integer (2 or 3)
        this.unitCount = unitCount;
        this.price = price;
        this.visibility = visibility;
        this.availableUnits = new HashMap<>();
        // Populate the map with the flat type as key and unit count as value
        this.availableUnits.put(flatType, unitCount);
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

    public int getFlatType() {
        return flatType;
    }

    public void setFlatType(int flatType) {
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
        return false; // No units available
    }

    // Method: Get Available Flats
    public int getAvailableFlats(FlatType flatType) {
        return availableUnits.getOrDefault(flatType, 0); // Returns unit count for given flat type
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
