package controllers;

import models.HDBOfficer;
import enums.RegistrationStatus;

import java.util.List;

public class OfficerRegistrationController {
    private List<HDBOfficer> officers; // List of all officer profiles

    public OfficerRegistrationController(List<HDBOfficer> officers) {
        this.officers = officers;
    }

    // Updates an officer's registration status
    public boolean updateRegistrationStatus(String officerNric, RegistrationStatus newStatus) {
        for (HDBOfficer officer : officers) {
            if (officer.getNric().equalsIgnoreCase(officerNric)) {
                System.out.println("Registration status updated for officer '" + officer.getName() + "' to: " + newStatus);
                return true;
            }
        }
        System.out.println("Officer '" + officerNric + "' not found.");
        return false;
    }
}
