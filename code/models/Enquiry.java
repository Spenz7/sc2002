package models;

public class Enquiry {
    // Attributes
    private int enquiryId;         // Unique ID for each enquiry
    private String applicantNric;  // NRIC of the applicant who submitted the enquiry
    private String projectName;    // Name of the BTO project related to the enquiry
    private String message;        // Enquiry message content
    private String status;         // Status of the enquiry (e.g., "Pending", "Responded")

    // Constructor
    public Enquiry(int enquiryId, String applicantNric, String projectName, String message, String status) {
        this.enquiryId = enquiryId;
        this.applicantNric = applicantNric;
        this.projectName = projectName;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public int getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(int enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getApplicantNric() {
        return applicantNric;
    }

    public void setApplicantNric(String applicantNric) {
        this.applicantNric = applicantNric;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method: Update Status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Override toString for Displaying Enquiry Details
    @Override
    public String toString() {
        return "Enquiry{" +
                "enquiryId=" + enquiryId +
                ", applicantNric='" + applicantNric + '\'' +
                ", projectName='" + projectName + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
