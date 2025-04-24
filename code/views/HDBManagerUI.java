package views;

import controllers.ApplicationController;
import controllers.ManagerController;
import controllers.ProjectController;
import models.HDBManager;
import models.enums.ApplicationStatus;
import models.Application;
import models.BTOProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class HDBManagerUI {
    private final Scanner scanner;
    private final HDBManager manager;
    private final ManagerController managerController;
    private final ProjectController projectController;
    private final ApplicationController applicationController;

    public HDBManagerUI(
    Scanner scanner, 
    HDBManager manager, 
    ManagerController managerController, 
    ProjectController projectController,
    ApplicationController applicationController) {
        this.scanner = scanner;
        this.manager = manager;
        this.managerController = managerController;
        this.projectController = projectController;
        this.applicationController = applicationController;
    }

    public void showMenu() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("\n=== HDB Manager Dashboard (NRIC: " + manager.getNric() + ") ===");
            System.out.println("1. Create New BTO Project");
            System.out.println("2. Edit Existing Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. Manage Officer Registrations");
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
                case 5 -> manageOfficerRegistrations();
                case 6 -> approveApplications();
                //case 7 -> approveWithdrawalRequests();
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
        System.out.println("\n=== Create New BTO Project ===");
        
        System.out.print("Project Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Neighborhood: ");
        String neighborhood = scanner.nextLine();
        
        System.out.print("Number of 2-Room Flats: ");
        int twoRoomFlats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Number of 3-Room Flats: ");
        int threeRoomFlats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Application Opening Date (YYYY-MM-DD): ");
        String openingDateStr = scanner.nextLine();
        Date openingDate = parseDate(openingDateStr);
        
        System.out.print("Application Closing Date (YYYY-MM-DD): ");
        String closingDateStr = scanner.nextLine();
        Date closingDate = parseDate(closingDateStr);
        
        System.out.print("Officer Slots Available: ");
        int officerSlot = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        BTOProject newProject = new BTOProject(
            name,
            neighborhood,
            twoRoomFlats,
            threeRoomFlats,
            openingDate,
            closingDate,
            manager.getNric(), // Manager in charge
            officerSlot,
            new String[0] // Empty officers array
        );
        
        projectController.createProject(newProject);
        System.out.println("Project created successfully!");
    }

    private void editProject() {
        System.out.println("\n=== Edit Project ===");
        List<BTOProject> myProjects = projectController.getProjectsByManager(manager.getNric());
        
        if (myProjects.isEmpty()) {
            System.out.println("No projects found that you manage.");
            return;
        }
        
        System.out.println("\nYour Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, myProjects.get(i).getName());
        }
        
        System.out.print("Select project to edit (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice == 0) return;
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        BTOProject project = myProjects.get(choice - 1);
        
        System.out.println("\nEditing Project: " + project.getName());
        System.out.println("1. Edit 2-Room Flats (Current: " + project.getTwoRoomFlats() + ")");
        System.out.println("2. Edit 3-Room Flats (Current: " + project.getThreeRoomFlats() + ")");
        System.out.println("3. Edit Application Dates");
        System.out.println("4. Edit Officer Slots");
        System.out.println("5. Cancel");
        System.out.print("Choose what to edit: ");
        
        int editChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (editChoice) {
            case 1 -> {
                System.out.print("New number of 2-Room Flats: ");
                int newTwoRoom = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                project.setTwoRoomFlats(newTwoRoom);
                System.out.println("2-Room flats updated successfully!");
            }
            case 2 -> {
                System.out.print("New number of 3-Room Flats: ");
                int newThreeRoom = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                project.setThreeRoomFlats(newThreeRoom);
                System.out.println("3-Room flats updated successfully!");
            }
            case 3 -> {
                System.out.print("New Opening Date (YYYY-MM-DD): ");
                Date newOpening = parseDate(scanner.nextLine());
                System.out.print("New Closing Date (YYYY-MM-DD): ");
                Date newClosing = parseDate(scanner.nextLine());
                
                project.setOpeningDate(newOpening);
                project.setClosingDate(newClosing);
                System.out.println("Application dates updated successfully!");
            }
            case 4 -> {
                System.out.print("New Officer Slots: ");
                int newSlots = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                project.setOfficerSlot(newSlots);
                System.out.println("Officer slots updated successfully!");
            }
            case 5 -> { return; }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void deleteProject() {
        System.out.println("\n=== Delete Project ===");
        List<BTOProject> myProjects = projectController.getProjectsByManager(manager.getNric());
        
        if (myProjects.isEmpty()) {
            System.out.println("No projects found that you manage.");
            return;
        }
        
        System.out.println("\nYour Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, myProjects.get(i).getName());
        }
        
        System.out.print("Select project to delete (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice == 0) return;
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        String projectName = myProjects.get(choice - 1).getName();
        System.out.print("Are you sure you want to delete " + projectName + "? (yes/no): ");
        String confirmation = scanner.nextLine();
        
        if ("yes".equalsIgnoreCase(confirmation)) {
            if (projectController.deleteProject(projectName)) {
                System.out.println("Project deleted successfully!");
            } else {
                System.out.println("Failed to delete project.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void toggleProjectVisibility() {
        System.out.println("\n=== Toggle Project Visibility ===");
        List<BTOProject> myProjects = projectController.getProjectsByManager(manager.getNric());
        
        if (myProjects.isEmpty()) {
            System.out.println("No projects found that you manage.");
            return;
        }
        
        System.out.println("\nYour Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.printf("%d. %s (Visible: %s)\n", 
                i + 1, 
                myProjects.get(i).getName(), 
                myProjects.get(i).isVisible() ? "Yes" : "No");
        }
        
        System.out.print("Select project to toggle (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice == 0) return;
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        BTOProject project = myProjects.get(choice - 1);
        boolean newVisibility = !project.isVisible();
        
        if (projectController.toggleProjectVisibility(project.getName(), newVisibility)) {
            System.out.printf("Visibility for %s set to: %s\n", 
                project.getName(), 
                newVisibility ? "Visible" : "Hidden");
        } else {
            System.out.println("Failed to update visibility.");
        }
    }

    private void filterMyProjects() {
        System.out.println("\n=== My Projects ===");
        List<BTOProject> myProjects = projectController.getProjectsByManager(manager.getNric());
        
        if (myProjects.isEmpty()) {
            System.out.println("No projects found that you manage.");
            return;
        }
        
        System.out.println("\nProjects Under Your Management:");
        myProjects.forEach(project -> {
            System.out.printf("- %s (Visible: %s)\n", 
                project.getName(), 
                project.isVisible() ? "Yes" : "No");
            System.out.printf("  2-Room Flats: %d, 3-Room Flats: %d\n",
                project.getTwoRoomFlats(), project.getThreeRoomFlats());
            System.out.printf("  Application Period: %s to %s\n",
                project.getOpeningDate(), project.getClosingDate());
        });
    }

    // ... [rest of the methods remain unchanged]

    private void approveApplications() {
        System.out.println("\n=== Approve/Reject Applications ===");
        
        // Get projects managed by this manager
        List<BTOProject> myProjects = projectController.getProjectsByManager(manager.getNric());
        
        if (myProjects.isEmpty()) {
            System.out.println("No projects found that you manage.");
            return;
        }
        
        // Show projects to select from
        System.out.println("\nYour Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, myProjects.get(i).getName());
        }
        
        System.out.print("Select project to view applications (0 to cancel): ");
        int projectChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (projectChoice == 0) return;
        if (projectChoice < 1 || projectChoice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        BTOProject selectedProject = myProjects.get(projectChoice - 1);
        
        // Get pending applications for this project

        List<Application> pendingApplications = applicationController.getApplicationsByProjectAndStatus(
            selectedProject.getName(),
            ApplicationStatus.PENDING
            
            );
        
        if (pendingApplications.isEmpty()) {
            System.out.println("No pending applications for this project.");
            return;
        }
        
        System.out.println("\nPending Applications:");
        for (int i = 0; i < pendingApplications.size(); i++) {
            Application app = pendingApplications.get(i);
            System.out.printf("%d. Applicant: %s (NRIC: %s)\n", 
                i + 1, 
                //app.getApplicantName(), 
                app.getApplicantNric());
        }
    
        System.out.print("Select application to process (0 to cancel): ");
        int appChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (appChoice == 0) return;
        if (appChoice < 1 || appChoice > pendingApplications.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Application selectedApp = pendingApplications.get(appChoice - 1);
        
        System.out.println("\nApplication Details:");
        System.out.println("- Project: " + selectedApp.getProjectName());
        //System.out.println("- Applicant: " + selectedApp.getApplicantName());
        System.out.println("- NRIC: " + selectedApp.getApplicantNric());
        //System.out.println("- Applied On: " + selectedApp.getApplicationDate());
        
        System.out.print("\nApprove this application? (approve/reject/cancel): ");
        String decision = scanner.nextLine().toLowerCase();
        
        switch (decision) {
            case "approve" -> {
                // Check flat availability
                if (managerController.approveApplication(selectedApp, selectedProject)) {
                    System.out.println("Application approved successfully!");
                } else {
                    System.out.println("Failed to approve application (no available flats).");
                }
            }
            case "reject" -> {
                applicationController.updateApplicationStatus(
                    selectedApp.getApplicationId(),
                    ApplicationStatus.REJECTED
                );
                System.out.println("Application rejected.");
            }
            case "cancel" -> System.out.println("Operation cancelled.");
            default -> System.out.println("Invalid choice. Operation cancelled.");
        }
    }

    private void manageOfficerRegistrations() {
    System.out.println("\nManager Dashboard:");
    
    // Display all officer registrations from specified file path
    String filePath = "code/data/OfficerRegistrations.csv"; 
    displayAllOfficerRegistrations(filePath);
    
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

    private void displayAllOfficerRegistrations(String filePath) {
        System.out.println("\nAll Officer Registrations:");
        System.out.println("-------------------------");
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean hasRecords = false;
            
            // Print header
            System.out.printf("%-12s %-20s %-10s%n", "NRIC", "Name", "Status");
            System.out.println("----------------------------------------");
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    hasRecords = true;
                    System.out.printf("%-12s %-20s %-10s%n", 
                        values[0], values[1], values[2]);
                }
            }
            
            if (!hasRecords) {
                System.out.println("No officer registrations found.");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Registration file not found at " + filePath);
        } catch (IOException e) {
            System.out.println("Error reading officer registrations: " + e.getMessage());
        }
        
        System.out.println(); // Add empty line for better formatting
    }

    private void generateReports() {
        // Implementation for generating reports
    }

    private void changePassword(Scanner scanner) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        manager.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            return null;
        }
    }

    
}
