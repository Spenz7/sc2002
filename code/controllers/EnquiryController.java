package controllers;

import models.Enquiry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnquiryController {
    private List<Enquiry> enquiries = new ArrayList<>();
    private int nextEnquiryId = 1;

    // Create
    public void createEnquiry(String applicantNric, String projectName, String message) {
        Enquiry enquiry = new Enquiry(nextEnquiryId++, applicantNric, projectName, message, "Pending");
        enquiries.add(enquiry);
    }

    // Read
    public List<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        return enquiries.stream()
                .filter(e -> e.getApplicantNric().equals(applicantNric))
                .collect(Collectors.toList());
    }

    public Enquiry getEnquiryById(int enquiryId) {
        return enquiries.stream()
                .filter(e -> e.getEnquiryId() == enquiryId)
                .findFirst()
                .orElse(null);
    }

    // status Update
    public boolean updateEnquiryMessage(int enquiryId, String newMessage) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null && enquiry.isPending()) {
            enquiry.setMessage(newMessage);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteEnquiry(int enquiryId) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null && enquiry.isPending()) {
            enquiries.remove(enquiry);
            return true;
        }
        return false;
    }

    // Staff-only: Respond to enquiry
    public boolean respondToEnquiry(int enquiryId, String response) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setStatus("Responded: " + response);
            return true;
        }
        return false;
    }

    // Retrieve all enquiries related to a specific project
    public List<Enquiry> getEnquiriesByProject(String projectName) {
        return enquiries.stream()
                .filter(e -> e.getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }
}
