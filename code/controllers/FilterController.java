package controllers;

import models.BTOProject;
import enums.FlatType;

import java.util.ArrayList;
import java.util.List;

public class FilterController {
    // Filters projects by flat type
    public List<BTOProject> filterByFlatType(List<BTOProject> projects, FlatType flatType) {
        List<BTOProject> filteredProjects = new ArrayList<>();
        for (BTOProject project : projects) {
            if (project.getAvailableFlats(flatType) > 0) { // Checks if the project has flats of the given type
                filteredProjects.add(project);
            }
        }
        return filteredProjects; // Returns the filtered list
    }

    // Filters projects by neighborhood
    public List<BTOProject> filterByNeighborhood(List<BTOProject> projects, String neighborhood) {
        List<BTOProject> filteredProjects = new ArrayList<>();
        for (BTOProject project : projects) {
            if (project.getNeighborhood().equalsIgnoreCase(neighborhood)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects; // Returns projects located in the specified neighborhood
    }

    // Allows saving filter settings (stub method for future use)
    public void saveFilterSettings(String settings) {
        System.out.println("Filter settings saved: " + settings);
        // This is just a placeholder. Actual implementation could involve saving to a file or database.
    }
}
