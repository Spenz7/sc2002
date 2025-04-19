import utils.DataLoader;
import models.enums.FlatType;
import models.enums.ApplicationStatus;
import models.enums.RegistrationStatus;
import models.enums.MaritalStatus;
import models.Applicant;
import models.HDBOfficer;
import models.HDBManager;
import models.BTOProject;

import controllers.*;
import views.ConsoleUI;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize the DataLoader to load data from CSV files
        DataLoader dataLoader = new DataLoader();

        // Load data for Applicants, Officers, Managers, and Projects
        List<Applicant> applicants = dataLoader.loadApplicants("data/ApplicantList.csv");
        List<HDBOfficer> officers = dataLoader.loadOfficers("data/OfficerList.csv");
        List<HDBManager> managers = dataLoader.loadManagers("data/ManagerList.csv");
        List<BTOProject> projects = dataLoader.loadProjects("data/BTOProjectList.csv");

        // Initialize the Controllers
        AuthController authController = new AuthController(applicants, officers, managers);
        ApplicationController applicationController = new ApplicationController();
        EnquiryController enquiryController = new EnquiryController();
        ProjectController projectController = new ProjectController();
        ManagerController managerController = new ManagerController(officers);
        OfficerRegistrationController officerRegistrationController = new OfficerRegistrationController(officers);
        FilterController filterController = new FilterController();
        BookingController bookingController = new BookingController();

        // Populate the ProjectController with loaded projects
        for (BTOProject project : projects) {
            projectController.createProject(project);
        }

        // Start the Console UI
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
