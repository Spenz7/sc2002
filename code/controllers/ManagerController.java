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
    private List<HDBOfficer> officers;

    public ManagerController(List<HDBOfficer> officers) {
        this.officers = officers;
    }

    // [Keep handleOfficerRegistration method exactly as is - no changes needed]
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

    // Updated approveApplication method
    public boolean approveApplication(Application application, BTOProject project) {
        // Get the flat type from application
        FlatType flatType = application.getFlatType(); // Assuming this now returns FlatType enum
        
        // Check flat availability based on type
        int availableFlats;
        if (flatType == FlatType.TWO_ROOM) {
            availableFlats = project.getTwoRoomFlats();
            if (availableFlats <= 0) {
                return false;
            }
            project.setTwoRoomFlats(availableFlats - 1);
        } 
        else if (flatType == FlatType.THREE_ROOM) {
            availableFlats = project.getThreeRoomFlats();
            if (availableFlats <= 0) {
                return false;
            }
            project.setThreeRoomFlats(availableFlats - 1);
        }
        else {
            return false; // Invalid flat type
        }

        // Update application status
        application.setStatus(ApplicationStatus.SUCCESSFUL);
        return true;
    }

    // Alternative version if Application uses String flat types
    public boolean approveApplicationAlternative(Application application, BTOProject project) {
        String flatType = application.getFlatType(); // Assuming this returns "2-Room" or "3-Room"
        
        if ("2-Room".equalsIgnoreCase(flatType)) {
            if (project.getTwoRoomFlats() <= 0) {
                return false;
            }
            project.setTwoRoomFlats(project.getTwoRoomFlats() - 1);
        }
        else if ("3-Room".equalsIgnoreCase(flatType)) {
            if (project.getThreeRoomFlats() <= 0) {
                return false;
            }
            project.setThreeRoomFlats(project.getThreeRoomFlats() - 1);
        }
        else {
            return false;
        }

        application.setStatus(ApplicationStatus.SUCCESSFUL);
        return true;
    }

    // [Keep getAllOfficers method exactly as is - no changes needed]
    public List<HDBOfficer> getAllOfficers() {
        return officers;
    }
}