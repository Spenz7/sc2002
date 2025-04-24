package models;

import models.enums.FlatType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTOProject {
    private String name;
    private String neighborhood;
    private int twoRoomFlats;   // Number of 2-room flats
    private int threeRoomFlats; // Number of 3-room flats
    private Date openingDate;   // Application opening date
    private Date closingDate;   // Application closing date
    private String manager;     // Manager for the project
    private int officerSlot;    // Number of officer slots available
    private List<String> officers; // List of officers assigned to the project
    private boolean visibility = true; // Default to visible

    public BTOProject(String name, String neighborhood, int twoRoomFlats, int threeRoomFlats, 
                     Date openingDate, Date closingDate, String manager, int officerSlot, 
                     String[] officers) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.twoRoomFlats = twoRoomFlats;
        this.threeRoomFlats = threeRoomFlats;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.manager = manager;
        this.officerSlot = officerSlot;
        this.officers = List.of(officers); // Converts an array to a List
    }

    // Getters and Setters
    public String getProjectName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public int getTwoRoomFlats() {
        return twoRoomFlats;
    }

    public void setTwoRoomFlats(int twoRoomFlats) {
        this.twoRoomFlats = twoRoomFlats;
    }

    public int getThreeRoomFlats() {
        return threeRoomFlats;
    }

    public void setThreeRoomFlats(int threeRoomFlats) {
        this.threeRoomFlats = threeRoomFlats;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getOfficerSlot() {
        return officerSlot;
    }

    public void setOfficerSlot(int officerSlot) {
        this.officerSlot = officerSlot;
    }

    public List<String> getOfficers() {
        return officers;
    }

    public void setOfficers(List<String> officers) {
        this.officers = officers;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public int getAvailableFlats(FlatType flatType) {
        switch (flatType) {
            case TWO_ROOM:
                return twoRoomFlats;
            case THREE_ROOM:
                return threeRoomFlats;
            default:
                return 0;
        }
    }

    public boolean overlaps(BTOProject otherProject) {
        return !(this.closingDate.before(otherProject.openingDate) || 
                 this.openingDate.after(otherProject.closingDate));
    }

    public Map<String, Integer> getFlatAvailability() {
        Map<String, Integer> availability = new HashMap<>();
        availability.put("2-Room", twoRoomFlats);
        availability.put("3-Room", threeRoomFlats);
        return availability;
    }

    @Override
    public String toString() {
        return "BTOProject{" +
                "name='" + name + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", twoRoomFlats=" + twoRoomFlats +
                ", threeRoomFlats=" + threeRoomFlats +
                ", openingDate=" + openingDate +
                ", closingDate=" + closingDate +
                ", manager='" + manager + '\'' +
                ", officerSlot=" + officerSlot +
                ", officers=" + officers +
                ", visibility=" + visibility +
                '}';
    }
}