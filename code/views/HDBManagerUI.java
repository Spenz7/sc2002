package views;

import controllers.ManagerController;
import controllers.ProjectController;
import models.HDBManager;
import models.BTOProject;

import java.util.List;
import java.util.Scanner;

public class HDBManagerUI {
    private final Scanner scanner;
    private final HDBManager manager;
    private final ManagerController managerController;
    private final ProjectController projectController;

    public HDBManagerUI(Scanner scanner, HDBManager manager, ManagerController managerController, ProjectController projectController) {
        this.scanner = scanner;
        this.manager = manager;
        this.managerController = managerController;
        this.projectController = projectController;
    }

    public void showMenu() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("\n=== HDB Manager Dashboard (NRIC: " + manager.getNric() + ") ===");
            System.out.println("1. Create New BTO Project");
            System.out.println("2. Edit Existing Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. Approve Officer Registrations");
            System.out.println("6. Approve Applications");
            System.out.println("7. Approve Withdrawal Requests");
            System.out.println("8. Generate Reports");
            System.out.println("9. Filter My Projects");
            System.out.println("10. Change Password");
            System.out.println("11. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createNewProject();
                case 2 -> editProject();
                case 3 -> deleteProject();
                case 4 -> toggleProjectVisibility();
                case 5 -> approveOfficerRegistrations();
                case 6 -> approveApplications();
                case 7 -> approveWithdrawalRequests();
                case 8 -> generateReports();
                case 9 -> filterMyProjects();
                case 10 -> changePassword(scanner);
                case 11 -> {
                    System.out.println("Logging out...");
                    shouldContinue = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createNewProject() {
        // Implementation for creating a new project
    }

    private void editProject() {
        // Implementation for editing an existing project
    }

    private void deleteProject() {
        // Implementation for deleting a project
    }

    private void toggleProjectVisibility() {
        // Implementation for toggling project visibility
    }

    private void approveOfficerRegistrations() {
        // Implementation for approving officer registrations
    }

    private void approveApplications() {
        // Implementation for approving applications
    }

    private void approveWithdrawalRequests() {
        // Implementation for approving withdrawal requests
    }

    private void generateReports() {
        // Implementation for generating reports
    }

    private void filterMyProjects() {
        // Implementation for filtering manager's own projects
    }

    private void changePassword(Scanner scanner) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        manager.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }
}
