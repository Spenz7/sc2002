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
import models.BTOProject;
import models.enums.ApplicationStatus;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    // Controllers for managing operations
    private AuthController authController;
    private ApplicationController applicationController;
    private EnquiryController enquiryController;
    private ProjectController projectController;
    private ManagerController managerController;
    private OfficerRegistrationController officerRegistrationController;

    // Constructor to initialize controllers
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

    // Main method for application entry
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the HDB BTO Application System!");

        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. View Projects");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> login(scanner); // Handle login functionality
                case 2 -> viewProjects(); // Display BTO projects
                case 3 -> {
                    System.out.println("Thank you for using the system. Goodbye!");
                    running = false; // Exit the application
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // Login functionality
    private void login(Scanner scanner) {
        System.out.print("\nEnter your NRIC: ");
        String nric = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Object user = authController.login(nric, password);

        if (user instanceof Applicant) {
            System.out.println("Welcome, Applicant!");
            applicantMenu(scanner, (Applicant) user);
        } else if (user instanceof HDBOfficer) {
            System.out.println("Welcome, HDB Officer!");
            officerMenu(scanner, (HDBOfficer) user);
        } else if (user instanceof HDBManager) {
            System.out.println("Welcome, HDB Manager!");
            managerMenu(scanner, (HDBManager) user);
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    // Applicant-specific menu
    private void applicantMenu(Scanner scanner, Applicant applicant) {
        System.out.println("\nApplicant Dashboard:");
        System.out.println("1. View Applications");
        System.out.println("2. Submit Enquiry");
        System.out.println("3. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        switch (choice) {
            case 1 -> {
                List<Application> applications = applicationController.getApplicationsByApplicant(applicant.getNric());
                System.out.println("\nYour Applications:");
                applications.forEach(System.out::println);
            }
            case 2 -> {
                System.out.print("Enter project name for enquiry: ");
                String projectName = scanner.nextLine();
                System.out.print("Enter your enquiry message: ");
                String message = scanner.nextLine();
                Enquiry enquiry = new Enquiry(0, applicant.getNric(), projectName, message, "Pending");
                enquiryController.createEnquiry(enquiry);
                System.out.println("Enquiry submitted successfully!");
            }
            case 3 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid option. Returning to dashboard.");
        }
    }

    // Officer-specific menu
    private void officerMenu(Scanner scanner, HDBOfficer officer) {
        System.out.println("\nOfficer Dashboard:");
        System.out.println("1. View Enquiries");
        System.out.println("2. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        switch (choice) {
            case 1 -> {
                System.out.print("Enter project name to view enquiries: ");
                String projectName = scanner.nextLine();
                List<Enquiry> enquiries = enquiryController.getEnquiriesByProject(projectName);
                System.out.println("\nEnquiries:");
                enquiries.forEach(System.out::println);
            }
            case 2 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid option. Returning to dashboard.");
        }
    }

    // Manager-specific menu
    private void managerMenu(Scanner scanner, HDBManager manager) {
        System.out.println("\nManager Dashboard:");
        System.out.println("1. Manage Officer Registrations");
        System.out.println("2. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        switch (choice) {
            case 1 -> {
                System.out.print("Enter officer NRIC to handle registration: ");
                String officerNric = scanner.nextLine();
                System.out.print("Approve registration? (true/false): ");
                boolean approve = scanner.nextBoolean();
                scanner.nextLine(); // Consume the newline
                managerController.handleOfficerRegistration(officerNric, approve);
            }
            case 2 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid option. Returning to dashboard.");
        }
    }

    // Displays all BTO projects
    private void viewProjects() {
        List<BTOProject> projects = projectController.getVisibleProjects();
        System.out.println("\nAvailable BTO Projects:");
        projects.forEach(System.out::println);
    }
}
