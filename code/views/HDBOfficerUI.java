package views;

import controllers.OfficerRegistrationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import models.BTOProject;
import models.Enquiry;
import models.HDBOfficer;

import java.util.List;
import java.util.Scanner;

import utils.DataLoader;

public class HDBOfficerUI {
    private final Scanner scanner;
    private final HDBOfficer officer;
    private final OfficerRegistrationController registrationController;
    private final EnquiryController enquiryController;
    private final ProjectController projectController;

    public HDBOfficerUI(Scanner scanner, HDBOfficer officer,
                        OfficerRegistrationController registrationController,
                        EnquiryController enquiryController,
                        ProjectController projectController) {
        this.scanner = scanner;
        this.officer = officer;
        this.registrationController = registrationController;
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
        List<BTOProject> availableProjects = projectController.getAvailableProjectsForRegistration(officer);
        if (availableProjects.isEmpty()) {
            System.out.println("No projects available for registration.");
            return;
        }

        System.out.println("\nAvailable Projects for Registration:");
        for (int i = 0; i < availableProjects.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, availableProjects.get(i).getProjectName(),
                    availableProjects.get(i).getNeighborhood());
        }

        System.out.print("Select a project to register (Enter number): ");
        int selectedProjectIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (selectedProjectIndex < 1 || selectedProjectIndex > availableProjects.size()) {
            System.out.println("Invalid selection. Returning to dashboard.");
            return;
        }

        BTOProject selectedProject = availableProjects.get(selectedProjectIndex - 1);

        boolean isSuccess = registrationController.submitRegistrationRequest(officer, selectedProject);
        if (isSuccess) {
            System.out.println("Registration request submitted successfully for project: " 
                    + selectedProject.getProjectName());
        } else {
            System.out.println("Failed to submit registration request. Please try again.");
        }
    }

    private void viewRegistrationStatus() {
        String status = registrationController.getRegistrationStatus(officer);
        System.out.println("Your registration status: " + status);
    }

    private void viewReplyEnquiries() {
        List<Enquiry> enquiries = enquiryController.getEnquiriesForOfficer(officer);

        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available to reply.");
            return;
        }

        System.out.println("\nEnquiries:");
        for (int i = 0; i < enquiries.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, enquiries.get(i).getMessage());
        }

        System.out.print("Select an enquiry to reply (Enter number): ");
        int selectedEnquiryIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (selectedEnquiryIndex < 1 || selectedEnquiryIndex > enquiries.size()) {
            System.out.println("Invalid selection. Returning to dashboard.");
            return;
        }

        Enquiry selectedEnquiry = enquiries.get(selectedEnquiryIndex - 1);

        System.out.print("Enter your reply: ");
        String reply = scanner.nextLine();

        enquiryController.replyToEnquiry(officer, selectedEnquiry, reply);
        System.out.println("Reply submitted successfully.");
    }

    private void viewAssignedProjectDetails() {
        BTOProject assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\nAssigned Project Details:");
        System.out.println("Project Name: " + assignedProject.getProjectName());
        System.out.println("Neighborhood: " + assignedProject.getNeighborhood());
        System.out.println("Flat Types and Availability:");
        assignedProject.getFlatAvailability().forEach((type, count) -> 
                System.out.printf("%s: %d units remaining\n", type, count));
    }

    private void processFlatBooking() {
        officer.processFlatBooking();
    }

    private void updateFlatAvailability() {
        BTOProject assignedProject = officer.getAssignedProject();

        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\nFlat Availability:");
        assignedProject.getFlatAvailability().forEach((type, count) -> 
                System.out.printf("%s: %d units remaining\n", type, count));

        System.out.print("Enter flat type to update availability: ");
        String flatType = scanner.nextLine().trim();

        System.out.print("Enter new availability count: ");
        int newCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        boolean success = projectController.updateFlatAvailability(assignedProject, flatType, newCount);
        if (success) {
            System.out.println("Flat availability updated successfully.");
        } else {
            System.out.println("Failed to update flat availability. Please try again.");
        }
    }


    private void generateReceipt() {
        System.out.print("Enter Applicant's NRIC for receipt generation: ");
        String applicantNric = scanner.nextLine().trim();

        String receipt = officer.generateReceipt(applicantNric); // Call the updated method
        if (receipt.contains("--- Booking Receipt ---")) {
            System.out.println(receipt);
        } else {
            System.out.println(receipt); // Display error message if receipt generation fails
        }
    }




    private void changePassword(Scanner scanner) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        officer.setPassword(newPassword);

        boolean success = DataLoader.updateOfficerPasswordInCsv(officer, "data/OfficerList.csv");
        if (success) {
            System.out.println("Password updated successfully.");
        } else {
            System.err.println("Failed to update password. Please try again.");
        }
    }
}
