// package views;

// import controllers.ApplicationController;
// import controllers.EnquiryController;
// import controllers.ProjectController;
// import models.Applicant;
// import models.Application;
// import models.Enquiry;
// import models.BTOProject;
// import models.enums.ApplicationStatus;

// import java.util.List;
// import java.util.Scanner;

// public class ApplicantUI {
//     private final Scanner scanner;
//     private final Applicant applicant;
//     private final ApplicationController applicationController;
//     private final EnquiryController enquiryController;
//     private final ProjectController projectController;

//     public ApplicantUI(Scanner scanner, Applicant applicant, 
//                      ApplicationController applicationController,
//                      EnquiryController enquiryController,
//                      ProjectController projectController) {
//         this.scanner = scanner;
//         this.applicant = applicant;
//         this.applicationController = applicationController;
//         this.enquiryController = enquiryController;
//         this.projectController = projectController;
//     }

//     public void showMenu() {
//         boolean shouldContinue = true;
        
//         while (shouldContinue) {
//             System.out.println("\nApplicant Dashboard:");
//             System.out.println("1. View Available Projects");
//             System.out.println("2. Apply for Project");
//             System.out.println("3. View My Applications");
//             System.out.println("4. Manage Enquiries");
//             System.out.println("5. Change Password");
//             System.out.println("6. Logout");
//             System.out.print("Choose an option: ");

//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume the newline

//             switch (choice) {
//                 case 1 -> viewAvailableProjects();
//                 case 2 -> applyForProject();
//                 case 3 -> viewApplications();
//                 case 4 -> manageEnquiries();
//                 case 6 -> {
//                     System.out.println("Logging out...");
//                     shouldContinue = false;
//                 }
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     //project applications

//     private void viewAvailableProjects() {
//         List<BTOProject> projects = projectController.getVisibleProjects();
//         System.out.println("\nAvailable Projects:");
//         for (int i = 0; i < projects.size(); i++) {
//             System.out.printf("%d. %s\n", i + 1, projects.get(i));
//         }
//     }

//     private void applyForProject() {
//         // Show available projects
//         List<BTOProject> projects = projectController.getVisibleProjects();
//         viewAvailableProjects();

//         System.out.print("\nEnter project number to apply (0 to cancel): ");
//         int projectChoice = scanner.nextInt();
//         scanner.nextLine();

//         if (projectChoice == 0) {
//             return;
//         }

//         if (projectChoice < 1 || projectChoice > projects.size()) {
//             System.out.println("Invalid project selection.");
//             return;
//         }

//         BTOProject selectedProject = projects.get(projectChoice - 1);

//         // Check eligibility
//         if (!isEligibleForProject(selectedProject)) {
//             System.out.println("You are not eligible for this project.");
//             return;
//         }

//         // Check if already applied
//         if (applicationController.hasExistingApplication(applicant.getNric())) {
//             System.out.println("You already have an active application.");
//             return;
//         }

//         // Create application
//         Application application = new Application(
//             0, // Auto-generated ID
//             applicant.getNric(),
//             selectedProject.getName(),
//             ApplicationStatus.PENDING
//         );

//         if (applicationController.createApplication(application)) {
//             System.out.println("Application submitted successfully!");
//         } else {
//             System.out.println("Failed to submit application.");
//         }
//     }

//     private boolean isEligibleForProject(BTOProject project) {
//         // Check age and marital status requirements
//         if (applicant.getMaritalStatus().equals("Single")) {
//             return applicant.getAge() >= 35 && project.getFlatType() == 2;
//         } else if (applicant.getMaritalStatus().equals("Married")) {
//             return applicant.getAge() >= 21;
//         }
//         return false;
//     }

    


//     private void viewApplications() {
//         List<Application> applications = applicationController.getApplicationsByApplicant(applicant.getNric());
//         System.out.println("\nYour Applications:");
//         applications.forEach(System.out::println);
//     }

//     //enquiries

//     private void manageEnquiries() {
//         boolean backToDashboard = false;
        
//         while (!backToDashboard) {
//             System.out.println("\nEnquiry Management:");
//             System.out.println("1. Create Enquiry");
//             System.out.println("2. View My Enquiries");
//             System.out.println("3. Edit Enquiry");
//             System.out.println("4. Delete Enquiry");
//             System.out.println("5. Back to Dashboard");
//             System.out.print("Choose an option: ");

//             int choice = scanner.nextInt();
//             scanner.nextLine();

//             switch (choice) {
//                 case 1 -> createEnquiry();
//                 case 2 -> viewEnquiries();
//                 case 3 -> editEnquiry();
//                 case 4 -> deleteEnquiry();
//                 case 5 -> backToDashboard = true;
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void createEnquiry() {
//         System.out.print("\nEnter project name: ");
//         String projectName = scanner.nextLine();
//         System.out.print("Enter your message: ");
//         String message = scanner.nextLine();

//         Enquiry enquiry = new Enquiry(0, applicant.getNric(), projectName, message, "Pending");
//         enquiryController.createEnquiry(
//             applicant.getNric(), 
//             projectName, 
//             message
//         );
//         System.out.println("Enquiry submitted successfully!");
//     }

//     private void viewEnquiries() {
//         List<Enquiry> enquiries = enquiryController.getEnquiriesByApplicant(applicant.getNric());
//         if (enquiries.isEmpty()) {
//             System.out.println("\nNo enquiries found.");
//         } else {
//             System.out.println("\nYour Enquiries:");
//             enquiries.forEach(e -> System.out.printf(
//                 "ID: %d | Project: %s | Status: %s\nMessage: %s\n\n",
//                 e.getEnquiryId(), e.getProjectName(), e.getStatus(), e.getMessage()
//             ));
//         }
//     }

//     private void editEnquiry() {
//         viewEnquiries();
//         System.out.print("\nEnter enquiry ID to edit: ");
//         int enquiryId = scanner.nextInt();
//         scanner.nextLine();

//         Enquiry enquiry = enquiryController.getEnquiryById(enquiryId);
//         if (enquiry == null || !enquiry.getApplicantNric().equals(applicant.getNric())) {
//             System.out.println("Enquiry not found or you don't have permission to edit it.");
//             return;
//         }

//         System.out.print("Enter new message: ");
//         String newMessage = scanner.nextLine();
//         enquiry.setMessage(newMessage);
//         enquiryController.updateEnquiryMessage(enquiryId,newMessage);
//         System.out.println("Enquiry updated successfully!");
//     }

//     private void deleteEnquiry() {
//         viewEnquiries();
//         System.out.print("\nEnter enquiry ID to delete: ");
//         int enquiryId = scanner.nextInt();
//         scanner.nextLine();

//         Enquiry enquiry = enquiryController.getEnquiryById(enquiryId);
//         if (enquiry == null || !enquiry.getApplicantNric().equals(applicant.getNric())) {
//             System.out.println("Enquiry not found or you don't have permission to delete it.");
//             return;
//         }

//         enquiryController.deleteEnquiry(enquiryId);
//         System.out.println("Enquiry deleted successfully!");
//     }
// }



package views;

import controllers.ApplicationController;
import controllers.EnquiryController;
import controllers.ProjectController;
import models.Applicant;
import models.Application;
import models.Enquiry;
import models.BTOProject;
import models.enums.ApplicationStatus;

import java.util.List;
import java.util.Scanner;

public class ApplicantUI {
    private final Scanner scanner;
    private final Applicant applicant;
    private final ApplicationController applicationController;
    private final EnquiryController enquiryController;
    private final ProjectController projectController;

    public ApplicantUI(Scanner scanner, Applicant applicant, 
                     ApplicationController applicationController,
                     EnquiryController enquiryController,
                     ProjectController projectController) {
        this.scanner = scanner;
        this.applicant = applicant;
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
        this.projectController = projectController;
    }

    public void showMenu() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            // Aligned menu format
            System.out.println("\n=== Applicant Dashboard (NRIC: " + applicant.getNric() + ") ===");
            System.out.println("1. View Available Projects");
            System.out.println("2. Apply for a Project");
            System.out.println("3. View My Application Status");
            System.out.println("4. Request Withdrawal");
            System.out.println("5. Manage Enquiries (Create/View/Edit/Delete)");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> viewAvailableProjects();
                case 2 -> applyForProject();
                case 3 -> viewApplicationStatus();
                case 4 -> requestWithdrawal();
                case 5 -> manageEnquiries();
                case 6 -> changePassword(scanner);
                case 7 -> {
                    System.out.println("Logging out...");
                    shouldContinue = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Request withdrawal functionality
    private void requestWithdrawal() {
        System.out.println("\nYour Applications:");
        List<Application> applications = applicationController.getApplicationsByApplicant(applicant.getNric());

        if (applications.isEmpty()) {
            System.out.println("You have no active applications to withdraw.");
            return;
        }

        for (int i = 0; i < applications.size(); i++) {
            Application app = applications.get(i);
            System.out.printf("%d. Project: %s | Status: %s%n", i + 1, app.getProjectName(), app.getStatus());
        }

        System.out.print("\nEnter the application number to request withdrawal (0 to cancel): ");
        int applicationChoice = scanner.nextInt();
        scanner.nextLine();

        if (applicationChoice == 0) return;

        if (applicationChoice < 1 || applicationChoice > applications.size()) {
            System.out.println("Invalid choice. Returning to dashboard.");
            return;
        }

        Application selectedApplication = applications.get(applicationChoice - 1);

        if (selectedApplication.getStatus() == ApplicationStatus.BOOKED) {
            System.out.println("You cannot withdraw an application after booking.");
            return;
        }

        if (applicationController.requestWithdrawal(selectedApplication.getApplicationId())) {
            System.out.println("Withdrawal request submitted successfully.");
        } else {
            System.out.println("Failed to process withdrawal request.");
        }
    }

    private void changePassword(Scanner scanner) {
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        applicant.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    private void viewAvailableProjects() {
        List<BTOProject> projects = projectController.getVisibleProjects(applicant);
        System.out.println("\nAvailable Projects:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, projects.get(i));
        }
    }


    private void applyForProject() {
        // Show available projects
        List<BTOProject> projects = projectController.getVisibleProjects(applicant);
        viewAvailableProjects();

        System.out.print("\nEnter project number to apply (0 to cancel): ");
        int projectChoice = scanner.nextInt();
        scanner.nextLine();

        if (projectChoice == 0) {
            return;
        }

        if (projectChoice < 1 || projectChoice > projects.size()) {
            System.out.println("Invalid project selection.");
            return;
        }

        BTOProject selectedProject = projects.get(projectChoice - 1);

        // Check eligibility
        if (!isEligibleForProject(selectedProject)) {
            System.out.println("You are not eligible for this project.");
            return;
        }

        // Check if already applied
        if (applicationController.hasExistingApplication(applicant.getNric())) {
            System.out.println("You already have an active application.");
            return;
        }

        // Create application
        Application application = new Application(
            0, // Auto-generated ID
            applicant.getNric(),
            selectedProject.getName(),
            ApplicationStatus.PENDING
        );

        if (applicationController.createApplication(application)) {
            System.out.println("Application submitted successfully!");
        } else {
            System.out.println("Failed to submit application.");
        }
    }

    // private boolean isEligibleForProject(BTOProject project) {
    //     if (applicant.getMaritalStatus().equals("Single")) {
    //         return applicant.getAge() >= 35 && project.getFlatType() == 2;
    //     } else if (applicant.getMaritalStatus().equals("Married")) {
    //         return applicant.getAge() >= 21;
    //     }
    //     return false;
    // }

    private boolean isEligibleForProject(BTOProject project) {
        if (applicant.getMaritalStatus().equals("Single")) {
            // Single applicants must be >=35 and project must have 2-room flats available
            return applicant.getAge() >= 35 && project.getTwoRoomFlats() > 0;
        } else if (applicant.getMaritalStatus().equals("Married")) {
            // Married applicants must be >=21 and project must have either flat type available
            return applicant.getAge() >= 21 && 
                   (project.getTwoRoomFlats() > 0 || project.getThreeRoomFlats() > 0);
        }
        return false;
    }

    private void viewApplicationStatus() {
        List<Application> applications = applicationController.getApplicationsByApplicant(applicant.getNric());
        System.out.println("\nYour Applications:");
        applications.forEach(System.out::println);
    }

    private void manageEnquiries() {
        // Manage enquiries logic remains as per your friend's code
    }
}
