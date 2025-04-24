package controllers;

import models.Enquiry;
import models.HDBOfficer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnquiryController {
    private List<Enquiry> enquiries = new ArrayList<>();
    private int nextEnquiryId = 1;

    // Create a new enquiry
    public void createEnquiry(String applicantNric, String projectName, String message) {
        Enquiry enquiry = new Enquiry(nextEnquiryId++, applicantNric, projectName, message, "Pending");
        enquiries.add(enquiry);
    }

    // Get enquiries by applicant
    public List<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        return enquiries.stream()
                .filter(e -> e.getApplicantNric().equals(applicantNric))
                .collect(Collectors.toList());
    }

    // Get enquiries assigned to an officer
    public List<Enquiry> getEnquiriesForOfficer(HDBOfficer officer) {
        return enquiries.stream()
                .filter(e -> e.getOfficerNric() != null && e.getOfficerNric().equals(officer.getNric()))
                .collect(Collectors.toList());
    }

    // Get an enquiry by its ID
    public Enquiry getEnquiryById(int enquiryId) {
        return enquiries.stream()
                .filter(e -> e.getEnquiryId() == enquiryId)
                .findFirst()
                .orElse(null);
    }

    // Update the enquiry message
    public boolean updateEnquiryMessage(int enquiryId, String newMessage) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null && enquiry.isPending()) {
            enquiry.setMessage(newMessage);
            return true;
        }
        return false;
    }

    // Delete an enquiry
    public boolean deleteEnquiry(int enquiryId) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null && enquiry.isPending()) {
            enquiries.remove(enquiry);
            return true;
        }
        return false;
    }

    // Staff-only: Respond to enquiry
    public boolean replyToEnquiry(HDBOfficer officer, Enquiry enquiry, String response) {
        if (enquiry != null) {
            enquiry.setReply(response);
            enquiry.setStatus("Responded");
            System.out.println("Reply submitted successfully for enquiry ID: " + enquiry.getEnquiryId());
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
