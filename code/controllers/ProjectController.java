package controllers;

import models.BTOProject;
import models.HDBOfficer;
import models.Applicant;
import models.enums.FlatType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectController {
    private List<BTOProject> projects; // Holds all BTO project records

    public ProjectController() {
        projects = new ArrayList<>();
    }

    // Adds a new BTO project to the list
    public void createProject(BTOProject project) {
        projects.add(project);
        System.out.println("Project added: " + project.getProjectName()); // Fixed to match getProjectName method
    }

    // Toggles the visibility of a project
    public boolean toggleProjectVisibility(String projectName, boolean visibility) {
        for (BTOProject project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                project.setVisibility(visibility);
                System.out.println("Visibility of project '" + projectName + "' set to: " + visibility);
                return true;
            }
        }
        System.out.println("Project '" + projectName + "' not found.");
        return false;
    }

    // Retrieve available projects for officer registration
    public List<BTOProject> getAvailableProjectsForRegistration(HDBOfficer officer) {
        return projects.stream()
                .filter(BTOProject::isVisible) // Filter visible projects
                .filter(project -> officer.getAssignedProject() == null || // Not already assigned
                        !officer.getAssignedProject().overlaps(project)) // No overlap with current project
                .collect(Collectors.toList());
    }

    // Retrieve visible projects for applicants based on eligibility
    public List<BTOProject> getVisibleProjects(Applicant applicant) {
        return projects.stream()
                .filter(project -> project.isVisible() // Visibility is toggled "on"
                        && isEligibleForFlatType(applicant, project)) // Check applicant eligibility
                .collect(Collectors.toList());
    }

    // Helper method to check applicant eligibility for a flat type
    private boolean isEligibleForFlatType(Applicant applicant, BTOProject project) {
        if ("Single".equalsIgnoreCase(applicant.getMaritalStatus())) {
            return applicant.getAge() >= 35 && "2-Room".equalsIgnoreCase(project.getType1());
        } else if ("Married".equalsIgnoreCase(applicant.getMaritalStatus())) {
            return applicant.getAge() >= 21; // Married applicants must be >= 21
        }
        return false; // Default: Not eligible
    }

    // Retrieve projects managed by a specific manager based on NRIC
    public List<BTOProject> getProjectsByManager(String managerNric) {
        return projects.stream()
                .filter(project -> project.getManager().equalsIgnoreCase(managerNric))
                .collect(Collectors.toList());
    }

    // Delete a project by its name
    public boolean deleteProject(String projectName) {
        return projects.removeIf(project -> project.getProjectName().equalsIgnoreCase(projectName));
    }

    public boolean updateFlatAvailability(BTOProject project, String flatType, int newCount) {
        // Update the flat availability in the project
        if (project.getFlatAvailability().containsKey(flatType)) {
            project.getFlatAvailability().put(flatType, newCount);
            return true; // Indicate success
        } else {
            System.out.println("Invalid flat type: " + flatType);
            return false; // Indicate failure
        }
    }

}
