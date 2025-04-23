// package controllers;

// import models.Enquiry;

// import java.util.ArrayList;
// import java.util.List;

// public class EnquiryController {
//     private List<Enquiry> enquiries; // List to hold all enquiries

//     public EnquiryController() {
//         enquiries = new ArrayList<>();
//     }

//     // Adds a new enquiry to the list
//     public void createEnquiry(Enquiry enquiry) {
//         enquiries.add(enquiry);
//         System.out.println("Enquiry created for project: " + enquiry.getProjectName());
//     }

//     // Updates the status of an enquiry
//     public boolean updateEnquiryStatus(int enquiryId, String newStatus) {
//         for (Enquiry enquiry : enquiries) {
//             if (enquiry.getEnquiryId() == enquiryId) {
//                 enquiry.updateStatus(newStatus);
//                 System.out.println("Enquiry status updated to: " + newStatus);
//                 return true;
//             }
//         }
//         System.out.println("Enquiry ID not found. No updates made.");
//         return false;
//     }

//     // Retrieves all enquiries for a specific project
//     public List<Enquiry> getEnquiriesByProject(String projectName) {
//         List<Enquiry> result = new ArrayList<>();
//         for (Enquiry enquiry : enquiries) {
//             if (enquiry.getProjectName().equalsIgnoreCase(projectName)) {
//                 result.add(enquiry);
//             }
//         }
//         return result; // Returns all enquiries related to the project
//     }
// }

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

    // Update
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
}