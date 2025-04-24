package views;

import controllers.ApplicationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import models.HDBOfficer;

import java.util.List;
import java.util.Scanner;

import utils.DataLoader;

public class HDBOfficerUI {
    private final Scanner scanner;
    private final HDBOfficer officer;
    private final ApplicationController applicationController;
    private final EnquiryController enquiryController;
    private final ProjectController projectController;

    public HDBOfficerUI(Scanner scanner, HDBOfficer officer,
                        ApplicationController applicationController,
                        EnquiryController enquiryController,
                        ProjectController projectController) {
        this.scanner = scanner;
        this.officer = officer;
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
        this.projectController = projectController;
    }

    public void showMenu() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("\n=== HDB Officer Dashboard (NRIC: " + officer.getNric() + ") ===");
            System.out.println("1. Register for a Project");
            System.out.println("2. View Registration Status");
            System.out.println("3. View/Reply to Enquiries");
            System.out.println("4. View Assigned Project Details");
            System.out.println("5. Process Flat Booking");
            System.out.println("6. Update Flat Availability");
            System.out.println("7. Generate Receipt");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerForProject();
                case 2 -> viewRegistrationStatus();
                case 3 -> viewReplyEnquiries();
                case 4 -> viewAssignedProjectDetails();
                case 5 -> processFlatBooking();
                case 6 -> updateFlatAvailability();
                case 7 -> generateReceipt();
                case 8 -> changePassword(scanner);
                case 9 -> {
                    System.out.println("Logging out...");
                    shouldContinue = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void registerForProject() {
        // Implementation for registering for a project
    }

    private void viewRegistrationStatus() {
        // Implementation for viewing registration status
    }

    private void viewReplyEnquiries() {
        // Implementation for viewing/replying to enquiries
    }

    private void viewAssignedProjectDetails() {
        // Implementation for viewing assigned project details
    }

    private void processFlatBooking() {
        // Implementation for processing flat booking
    }

    private void updateFlatAvailability() {
        // Implementation for updating flat availability
    }

    private void generateReceipt() {
        // Implementation for generating a receipt
    }

    private void changePassword(Scanner scanner) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        officer.setPassword(newPassword); // Update in-memory password

        // Persist password update to a CSV file using a utility class (DataLoader or FileUtils)
        DataLoader.updateOfficerPasswordInCsv(officer, "data/OfficerList.csv"); 
        System.out.println("Password changed successfully.");
    }
}
