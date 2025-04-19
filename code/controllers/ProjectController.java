package controllers;

import models.BTOProject;

import java.util.ArrayList;
import java.util.List;

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

    // Retrieves all visible projects
    public List<BTOProject> getVisibleProjects() {
        List<BTOProject> visibleProjects = new ArrayList<>();
        for (BTOProject project : projects) {
            if (project.isVisible()) {
                visibleProjects.add(project);
            }
        }
        return visibleProjects; // Returns a list of projects with visibility set to true
    }
}
