package controllers;

import models.Application;
import models.BTOProject;
import models.HDBOfficer;
import models.enums.ApplicationStatus;
import models.enums.FlatType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    private List<HDBOfficer> officers; // List of all registered HDB officers

    public ManagerController(List<HDBOfficer> officers) {
        this.officers = officers;
    }

    // Approves or denies officer registrations based on eligibility criteria
    public boolean handleOfficerRegistration(String officerNric, boolean approve) {
    // Define the file path
    Path filePath = Paths.get("code", "data", "officerregistrations.csv");
    
    try {
        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());
        
        // Read all lines from the CSV file
        List<String> lines = Files.exists(filePath) 
            ? Files.readAllLines(filePath) 
            : new ArrayList<>();
        
        boolean found = false;
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts[0].equalsIgnoreCase(officerNric)) {
                // Update the status in memory
                String newStatus = approve ? ApplicationStatus.APPROVED.name() : ApplicationStatus.DENIED.name();
                lines.set(i, parts[0] + "," + parts[1] + "," + newStatus);
                found = true;
                break;
            }
        }
        
        if (found) {
            // Write all lines back to the CSV file
            Files.write(filePath, lines);
            System.out.println("Registration for officer '" + officerNric + "' has been: " + 
                             (approve ? "Approved" : "Denied"));
            return true;
        } else {
            System.out.println("Officer '" + officerNric + "' not found.");
            return false;
        }
    } catch (IOException e) {
        System.out.println("Error updating officer registration: " + e.getMessage());
        return false;
    }
}

    // Approves an application for a project
    public boolean approveApplication(Application application, BTOProject project) {
        // Retrieve the flat type as an int and convert to FlatType
        int flatTypeInt = application.getFlatType(); // Assuming getFlatType() returns an int
        FlatType flatType = FlatType.values()[flatTypeInt]; // Convert int to FlatType enum

        // Check flat availability
        int availableFlats = project.getAvailableFlats(flatType);
        if (availableFlats <= 0) {
            return false; // No flats available
        }

        // Update project flat count
        if (flatType.getDisplayName().equalsIgnoreCase(project.getType1())) {
            project.setUnitsType1(project.getUnitsType1() - 1);
        } else if (flatType.getDisplayName().equalsIgnoreCase(project.getType2())) {
            project.setUnitsType2(project.getUnitsType2() - 1);
        }

        // Update application status
        application.setStatus(ApplicationStatus.SUCCESSFUL); // Ensure SUCCESSFUL exists in ApplicationStatus
        return true;
    }

    // Retrieves a list of all officers
    public List<HDBOfficer> getAllOfficers() {
        return officers; // Returns the entire list of officers for the manager to review
    }
}
