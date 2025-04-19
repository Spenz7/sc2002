import utils.DataLoader;
import models.*;
import controllers.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Load data using the DataLoader
        DataLoader dataLoader = new DataLoader();
        List<Applicant> applicants = dataLoader.loadApplicants("data/ApplicantList.csv");
        List<HDBOfficer> officers = dataLoader.loadOfficers("data/OfficerList.csv");
        List<HDBManager> managers = dataLoader.loadManagers("data/ManagerList.csv");
        List<BTOProject> projects = dataLoader.loadProjects("data/BTOProjectList.csv");

        // Initialize controllers
        AuthController authController = new AuthController(applicants, officers, managers);
        ApplicationController applicationController = new ApplicationController();
        EnquiryController enquiryController = new EnquiryController();
        ProjectController projectController = new ProjectController();
        ManagerController managerController = new ManagerController(officers);
        OfficerRegistrationController officerRegistrationController = new OfficerRegistrationController(officers);

        // Populate the project controller
        for (BTOProject project : projects) {
            projectController.createProject(project);
        }

        // Start the UI
        ConsoleUI consoleUI = new ConsoleUI(
            authController,
            applicationController,
            enquiryController,
            projectController,
            managerController,
            officerRegistrationController
        );
        consoleUI.start();
    }
}
