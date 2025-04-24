package controllers;

import models.BTOProject;
import models.Applicant;


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
        System.out.println("Project added: " + project.getName());
    }

    // Toggles the visibility of a project
    public boolean toggleProjectVisibility(String projectName, boolean visibility) {
        for (BTOProject project : projects) {
            if (project.getName().equalsIgnoreCase(projectName)) {
                project.setVisibility(visibility);
                System.out.println("Visibility of project '" + projectName + "' set to: " + visibility);
                return true;
            }
        }
        System.out.println("Project '" + projectName + "' not found.");
        return false;
    }

    public List<BTOProject> getVisibleProjects(Applicant applicant) {
        return projects.stream()
                .filter(project -> project.isVisible() // Visibility is toggled "on"
                        && isEligibleForFlatType(applicant, project)) // Check applicant eligibility
                .collect(Collectors.toList());
    }

    private boolean isEligibleForFlatType(Applicant applicant, BTOProject project) {
        if ("Single".equalsIgnoreCase(applicant.getMaritalStatus())) {
            return applicant.getAge() >= 35 && "2-Room".equalsIgnoreCase(project.getType1());
        } else if ("Married".equalsIgnoreCase(applicant.getMaritalStatus())) {
            return applicant.getAge() >= 21;
        }
        return false; // Default: Not eligible
    }

    public List<BTOProject> getProjectsByManager(String managerNric) {
        return projects.stream()
                .filter(p -> p.getManager().equalsIgnoreCase(managerNric))
                .collect(Collectors.toList());
    }
    
    public boolean deleteProject(String projectName) {
        return projects.removeIf(p -> p.getName().equalsIgnoreCase(projectName));
    }



}
