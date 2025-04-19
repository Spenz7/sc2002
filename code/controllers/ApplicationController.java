package controllers;

import models.Application;
import enums.ApplicationStatus;

import java.util.ArrayList;
import java.util.List;

public class ApplicationController {
    private List<Application> applications; // List to hold all application records

    public ApplicationController() {
        applications = new ArrayList<>();
    }

    // Adds a new application to the list
    public void createApplication(Application application) {
        applications.add(application);
        System.out.println("Application successfully created for project: " + application.getProjectName());
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
}
