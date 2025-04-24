package controllers;

import models.Application;
import models.enums.ApplicationStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationController {
    private List<Application> applications; // List to hold all application records

    public ApplicationController() {
        applications = new ArrayList<>();
    }

    // Adds a new application to the list
    public boolean createApplication(Application application) {
        applications.add(application);
        System.out.println("Application successfully created for project: " + application.getProjectName());
        return true;
    }

    public boolean hasExistingApplication(String applicantNric) {
        return applications.stream()
            .anyMatch(app -> app.getApplicantNric().equals(applicantNric) 
                && app.getStatus() != ApplicationStatus.WITHDRAWN);
    }

    // Updates the status of an existing application
    public boolean updateApplicationStatus(int applicationId, ApplicationStatus newStatus) {
        for (Application app : applications) {
            if (app.getApplicationId() == applicationId) {
                app.updateStatus(newStatus);
                System.out.println("Application status updated to: " + newStatus);
                return true;
            }
        }
        System.out.println("Application ID not found. No updates made.");
        return false;
    }

    // Gets all applications submitted by a specific applicant based on NRIC
    public List<Application> getApplicationsByApplicant(String applicantNric) {
        List<Application> result = new ArrayList<>();
        for (Application app : applications) {
            if (app.getApplicantNric().equalsIgnoreCase(applicantNric)) {
                result.add(app);
            }
        }
        return result; // Returns a list of applications for the given NRIC
    }

    // Helper method: Retrieve an application by its ID
    public Application getApplicationById(int applicationId) {
        return applications.stream()
                .filter(app -> app.getApplicationId() == applicationId)
                .findFirst()
                .orElse(null);
    }

    // Request withdrawal for an existing application
    public boolean requestWithdrawal(int applicationId) {
        Application application = getApplicationById(applicationId);
        if (application == null) {
            System.out.println("Application ID not found.");
            return false;
        }

        if (application.getStatus() == ApplicationStatus.BOOKED) {
            System.out.println("Cannot withdraw an application after booking.");
            return false;
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        System.out.println("Withdrawal request for application ID " + applicationId + " has been processed.");
        return true;
    }

    public List<Application> getApplicationsByProjectAndStatus(String projectName, ApplicationStatus status) {
        return applications.stream()
            .filter(app -> app.getProjectName().equalsIgnoreCase(projectName))
            .filter(app -> app.getStatus() == status)
            .collect(Collectors.toList());
    }

    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applications.stream()
            .filter(app -> app.getStatus() == status)
            .collect(Collectors.toList());
    }

    //getapplication by project and status?

    
}
