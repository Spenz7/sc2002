package controllers;

import models.Enquiry;

import java.util.ArrayList;
import java.util.List;

public class EnquiryController {
    private List<Enquiry> enquiries; // List to hold all enquiries

    public EnquiryController() {
        enquiries = new ArrayList<>();
    }

    // Adds a new enquiry to the list
    public void createEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
        System.out.println("Enquiry created for project: " + enquiry.getProjectName());
    }

    // Updates the status of an enquiry
    public boolean updateEnquiryStatus(int enquiryId, String newStatus) {
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getEnquiryId() == enquiryId) {
                enquiry.updateStatus(newStatus);
                System.out.println("Enquiry status updated to: " + newStatus);
                return true;
            }
        }
        System.out.println("Enquiry ID not found. No updates made.");
        return false;
    }

    // Retrieves all enquiries for a specific project
    public List<Enquiry> getEnquiriesByProject(String projectName) {
        List<Enquiry> result = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            if (enquiry.getProjectName().equalsIgnoreCase(projectName)) {
                result.add(enquiry);
            }
        }
        return result; // Returns all enquiries related to the project
    }
}
