package views;

import controllers.ApplicationController;
import controllers.EnquiryController;
import models.Applicant;
import models.Application;
import models.Enquiry;

import java.util.List;
import java.util.Scanner;

public class ApplicantUI {
    private final Scanner scanner;
    private final Applicant applicant;
    private final ApplicationController applicationController;
    private final EnquiryController enquiryController;

    public ApplicantUI(Scanner scanner, Applicant applicant, 
                      ApplicationController applicationController, 
                      EnquiryController enquiryController) {
        this.scanner = scanner;
        this.applicant = applicant;
        this.applicationController = applicationController;
        this.enquiryController = enquiryController;
    }

    public void showMenu() {
        boolean shouldContinue = true;
        
        while (shouldContinue) {
            System.out.println("\nApplicant Dashboard:");
            System.out.println("1. View Applications");
            System.out.println("2. Manage Enquiries");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1 -> viewApplications();
                case 2 -> manageEnquiries();
                case 4 -> {
                    System.out.println("Logging out...");
                    shouldContinue = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewApplications() {
        List<Application> applications = applicationController.getApplicationsByApplicant(applicant.getNric());
        System.out.println("\nYour Applications:");
        applications.forEach(System.out::println);
    }

    private void manageEnquiries() {
        boolean backToDashboard = false;
        
        while (!backToDashboard) {
            System.out.println("\nEnquiry Management:");
            System.out.println("1. Create Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back to Dashboard");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createEnquiry();
                case 2 -> viewEnquiries();
                case 3 -> editEnquiry();
                case 4 -> deleteEnquiry();
                case 5 -> backToDashboard = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createEnquiry() {
        System.out.print("\nEnter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        Enquiry enquiry = new Enquiry(0, applicant.getNric(), projectName, message, "Pending");
        enquiryController.createEnquiry(
            applicant.getNric(), 
            projectName, 
            message
        );
        System.out.println("Enquiry submitted successfully!");
    }

    private void viewEnquiries() {
        List<Enquiry> enquiries = enquiryController.getEnquiriesByApplicant(applicant.getNric());
        if (enquiries.isEmpty()) {
            System.out.println("\nNo enquiries found.");
        } else {
            System.out.println("\nYour Enquiries:");
            enquiries.forEach(e -> System.out.printf(
                "ID: %d | Project: %s | Status: %s\nMessage: %s\n\n",
                e.getEnquiryId(), e.getProjectName(), e.getStatus(), e.getMessage()
            ));
        }
    }

    private void editEnquiry() {
        viewEnquiries();
        System.out.print("\nEnter enquiry ID to edit: ");
        int enquiryId = scanner.nextInt();
        scanner.nextLine();

        Enquiry enquiry = enquiryController.getEnquiryById(enquiryId);
        if (enquiry == null || !enquiry.getApplicantNric().equals(applicant.getNric())) {
            System.out.println("Enquiry not found or you don't have permission to edit it.");
            return;
        }

        System.out.print("Enter new message: ");
        String newMessage = scanner.nextLine();
        enquiry.setMessage(newMessage);
        enquiryController.updateEnquiryMessage(enquiryId,newMessage);
        System.out.println("Enquiry updated successfully!");
    }

    private void deleteEnquiry() {
        viewEnquiries();
        System.out.print("\nEnter enquiry ID to delete: ");
        int enquiryId = scanner.nextInt();
        scanner.nextLine();

        Enquiry enquiry = enquiryController.getEnquiryById(enquiryId);
        if (enquiry == null || !enquiry.getApplicantNric().equals(applicant.getNric())) {
            System.out.println("Enquiry not found or you don't have permission to delete it.");
            return;
        }

        enquiryController.deleteEnquiry(enquiryId);
        System.out.println("Enquiry deleted successfully!");
    }
}