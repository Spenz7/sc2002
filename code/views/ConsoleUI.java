package views;

import controllers.AuthController;
import controllers.ApplicationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import controllers.ManagerController;
import controllers.OfficerRegistrationController;
import models.Applicant;
import models.HDBOfficer;
import models.HDBManager;

import java.util.Scanner;

public class ConsoleUI {
    private AuthController authController;
    private ApplicationController applicationController;
    private EnquiryController enquiryController;
    private ProjectController projectController;
    private ManagerController managerController;
    private OfficerRegistrationController officerRegistrationController;

    public ConsoleUI(AuthController authController,
                     ApplicationController applicationController,
                     EnquiryController enquiryController,
                     ProjectController projectController,
                     ManagerController managerController,
                     OfficerRegistrationController officerRegistrationController) {
        this.authController = authController;
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
        this.projectController = projectController;
        this.managerController = managerController;
        this.officerRegistrationController = officerRegistrationController;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the HDB BTO Application System!");

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> {
                    System.out.println("Thank you for using the system. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private void login(Scanner scanner) {
        System.out.print("\nEnter your NRIC: ");
        String nric = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Object user = authController.login(nric, password);

        if (user instanceof Applicant applicant) {
            System.out.println("Welcome, Applicant!");
            new ApplicantUI(scanner, applicant, applicationController, enquiryController, projectController).showMenu();
        } else if (user instanceof HDBOfficer officer) {
            System.out.println("Welcome, HDB Officer!");
            new HDBOfficerUI(scanner, officer, applicationController, enquiryController, projectController).showMenu();
        } else if (user instanceof HDBManager manager) {
            System.out.println("Welcome, HDB Manager!");
            new HDBManagerUI(scanner, manager, managerController, projectController).showMenu();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
}
