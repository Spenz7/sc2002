package controllers;

import models.HDBOfficer;

import java.util.List;

public class ManagerController {
    private List<HDBOfficer> officers; // List of all registered HDB officers

    public ManagerController(List<HDBOfficer> officers) {
        this.officers = officers;
    }

    // Approves or denies officer registrations based on eligibility criteria
    public boolean handleOfficerRegistration(String officerNric, boolean approve) {
        for (HDBOfficer officer : officers) {
            if (officer.getNric().equalsIgnoreCase(officerNric)) {
                String status = approve ? "Approved" : "Denied";
                System.out.println("Registration for officer '" + officer.getName() + "' has been: " + status);
                return true;
            }
        }
        System.out.println("Officer '" + officerNric + "' not found.");
        return false;
    }

    // Retrieves a list of all officers
    public List<HDBOfficer> getAllOfficers() {
        return officers; // Returns the entire list of officers for the manager to review
    }
}
