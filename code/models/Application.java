package models;

import models.enums.ApplicationStatus;

public class Application {
    // Attributes
    private int applicationId;        // Unique identifier for the application
    private String applicantNric;     // NRIC of the applicant who submitted the application
    private String projectName;       // Name of the BTO project being applied for
    private ApplicationStatus status; // Current status of the application (e.g., "Pending", "Approved")

    // Constructor
    public Application(int applicationId, String applicantNric, String projectName, ApplicationStatus status) {
        this.applicationId = applicationId;
        this.applicantNric = applicantNric;
        this.projectName = projectName;
        this.status = status;
    }

    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    // Method: Update Application Status
    public void updateStatus(ApplicationStatus newStatus) {
        this.status = newStatus;
    }

    // Override toString for Displaying Application Details
    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", applicantNric='" + applicantNric + '\'' +
                ", projectName='" + projectName + '\'' +
                ", status=" + status +
                '}';
    }
}
