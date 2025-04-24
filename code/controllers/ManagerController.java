package controllers;

import models.Application;
import models.BTOProject;
import models.HDBOfficer;
import models.enums.ApplicationStatus;

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

    public boolean approveApplication(Application application, BTOProject project) {
    // Check flat availability
        int flatType = application.getFlatType(); // Assuming this exists
        int availableFlats = project.getAvailableFlats(flatType);
    
        if (availableFlats <= 0) {
            return false;
        }
    
    // Update project flat count
        if (flatType.equalsIgnoreCase(project.getType1())) {
            project.setUnitsType1(project.getUnitsType1() - 1);
        } else if (flatType.equalsIgnoreCase(project.getType2())) {
            project.setUnitsType2(project.getUnitsType2() - 1);
        }
    
    // Update application status
        application.setStatus(ApplicationStatus.APPROVED);
        return true;
    }   

    // Retrieves a list of all officers
    public List<HDBOfficer> getAllOfficers() {
        return officers; // Returns the entire list of officers for the manager to review
    }
}
