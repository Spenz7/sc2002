package controllers;

import models.BTOProject;
import models.HDBOfficer;
import models.enums.RegistrationStatus;
import utils.DataLoader;

import java.io.IOException;
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
                // Update registration status in memory
                officer.setRegistrationStatus(newStatus);

                // Log status update
                System.out.println("Registration status updated for officer '" + officer.getName() + "' to: " + newStatus);

                // Persist updated status to file
                try {
                    DataLoader.updateOfficerRegistrationStatus(officer.getNric(), newStatus.toString());
                } catch (IOException e) {
                    System.err.println("Error saving registration status to file: " + e.getMessage());
                    return false;
                }

                return true;
            }
        }
        System.out.println("Officer '" + officerNric + "' not found.");
        return false;
    }

    // Checks if the officer is eligible to register for the selected project
    public boolean checkEligibility(HDBOfficer officer, BTOProject project) {
        if (officer.getAssignedProject() != null &&
                officer.getAssignedProject().overlaps(project)) { // Ensure overlaps method exists in BTOProject
            System.out.println("Officer is already handling a project during the same period.");
            return false;
        }
        if (officer.getRegistrationStatus() == RegistrationStatus.PENDING) {
            System.out.println("Officer already has a pending registration request.");
            return false;
        }
        return true;
    }

    // Submits a registration request for the officer
    public boolean submitRegistrationRequest(HDBOfficer officer, BTOProject project) {
        if (!checkEligibility(officer, project)) {
            return false; // Officer is not eligible
        }

        try {
            // Save the registration request to file (e.g., OfficerRegistrations.csv)
            DataLoader.saveOfficerRegistration(officer.getNric(), project.getProjectName(), "Pending");

            // Update the officer's in-memory registration status
            officer.setRegistrationStatus(RegistrationStatus.PENDING);

            System.out.println("Registration request submitted for officer '" + officer.getName() + 
                               "' to project: " + project.getProjectName());
            return true;

        } catch (IOException e) {
            System.err.println("Error submitting registration request: " + e.getMessage());
            return false;
        }
    }

    // Retrieves the current registration status of the officer
    public String getRegistrationStatus(HDBOfficer officer) {
        try {
            // Fetch the registration status from file or data store
            return DataLoader.getOfficerRegistrationStatus(officer.getNric());
        } catch (IOException e) {
            System.err.println("Error retrieving registration status: " + e.getMessage());
            return "Error retrieving status.";
        }
    }
}
