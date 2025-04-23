import controllers.EnquiryController;
import models.Enquiry;
import java.util.Scanner;
import java.util.List;

//port over from consoleUI
public class ApplicantUI {
    private final EnquiryController enquiryController;
    private final Scanner scanner;

    public ApplicantUI(EnquiryController enquiryController) {
        this.enquiryController = enquiryController;
        this.scanner = new Scanner(System.in);
    }

    // Main menu for applicants
    public void showApplicantMenu(String applicantNric) {
        while (true) {
            System.out.println("\n=== ENQUIRY MANAGEMENT ===");
            System.out.println("1. Create Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createEnquiry(applicantNric);
                case 2 -> viewEnquiries(applicantNric);
                case 3 -> editEnquiry(applicantNric);
                case 4 -> deleteEnquiry(applicantNric);
                case 5 -> { return; }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void createEnquiry(String applicantNric) {
        System.out.print("\nEnter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();

        enquiryController.createEnquiry(applicantNric, projectName, message);
        System.out.println("Enquiry submitted!");
    }

    private void viewEnquiries(String applicantNric) {
        List<Enquiry> enquiries = enquiryController.getEnquiriesByApplicant(applicantNric);
        if (enquiries.isEmpty()) {
            System.out.println("\nNo enquiries found.");
        } else {
            System.out.println("\nYour Enquiries:");
            enquiries.forEach(System.out::println);
        }
    }

    private void editEnquiry(String applicantNric) {
        viewEnquiries(applicantNric);
        System.out.print("\nEnter enquiry ID to edit: ");
        int enquiryId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new message: ");
        String newMessage = scanner.nextLine();

        if (enquiryController.updateEnquiryMessage(enquiryId, newMessage)) {
            System.out.println("Enquiry updated!");
        } else {
            System.out.println("Failed to update (may be already responded to or invalid ID).");
        }
    }

    private void deleteEnquiry(String applicantNric) {
        viewEnquiries(applicantNric);
        System.out.print("\nEnter enquiry ID to delete: ");
        int enquiryId = scanner.nextInt();
        scanner.nextLine();

        if (enquiryController.deleteEnquiry(enquiryId)) {
            System.out.println("Enquiry deleted!");
        } else {
            System.out.println("Failed to delete (may be already responded to or invalid ID).");
        }
    }

    // Staff menu (optional)
    public void respondToEnquiry() {
        System.out.print("\nEnter enquiry ID to respond: ");
        int enquiryId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter your response: ");
        String response = scanner.nextLine();

        if (enquiryController.respondToEnquiry(enquiryId, response)) {
            System.out.println("Response submitted!");
        } else {
            System.out.println("Failed to respond (invalid ID).");
        }
    }
}