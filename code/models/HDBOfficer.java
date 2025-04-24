package models;

import models.enums.RegistrationStatus;
import utils.DataLoader;
import java.util.Scanner;

import java.io.IOException;
import java.util.InputMismatchException;

public class HDBOfficer {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;
    private RegistrationStatus registrationStatus;
    private BTOProject assignedProject;

    private final Scanner scanner = new Scanner(System.in);

    public HDBOfficer(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
        this.registrationStatus = null; // Default to null
        this.assignedProject = null; // Default to no project assigned
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public BTOProject getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(BTOProject assignedProject) {
        this.assignedProject = assignedProject;
    }

    // Add this method to change the officer's password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Add this method to check password
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void processFlatBooking() {
        // Check if the officer is assigned to a project
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        // Collect the applicant's NRIC
        System.out.print("Enter Applicant's NRIC for flat booking: ");
        String applicantNric = scanner.nextLine().trim();

        // Retrieve the applicant's application status
        String applicationStatus = DataLoader.getApplicantApplicationStatus(applicantNric);
        if (!"Successful".equalsIgnoreCase(applicationStatus)) {
            System.out.println("Applicant's application is not successful. Cannot process booking.");
            return;
        }

        if ("Booked".equalsIgnoreCase(applicationStatus)) {
            System.out.println("Applicant has already booked a flat.");
            return;
        }

        // Display available flat types
        System.out.println("\nAvailable Flat Types:");
        System.out.println("1. 2-Room (" + assignedProject.getTwoRoomFlats() + " units available)");
        System.out.println("2. 3-Room (" + assignedProject.getThreeRoomFlats() + " units available)");

        // Collect flat type choice
        System.out.print("Enter flat type choice (1 for 2-Room, 2 for 3-Room): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String chosenFlatType;
        if (choice == 1) {
            chosenFlatType = "2-Room";
            if (assignedProject.getTwoRoomFlats() <= 0) {
                System.out.println("No available 2-Room units");
                return;
            }
            assignedProject.setTwoRoomFlats(assignedProject.getTwoRoomFlats() - 1);
        } else if (choice == 2) {
            chosenFlatType = "3-Room";
            if (assignedProject.getThreeRoomFlats() <= 0) {
                System.out.println("No available 3-Room units");
                return;
            }
            assignedProject.setThreeRoomFlats(assignedProject.getThreeRoomFlats() - 1);
        } else {
            System.out.println("Invalid choice. Booking aborted.");
            return;
        }

        // Update applicant's booking details
        boolean success = DataLoader.updateApplicantBooking(applicantNric, chosenFlatType, assignedProject.getProjectName());
        if (success) {
            System.out.println("Flat booking processed successfully. Applicant " + 
                               applicantNric + " has booked a " + chosenFlatType + " unit.");
        } else {
            System.err.println("Failed to update applicant's booking.");
        }
    }

    public void updateFlatAvailability() {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\nCurrent Flat Availability:");
        System.out.println("2-Room: " + assignedProject.getTwoRoomFlats() + " units");
        System.out.println("3-Room: " + assignedProject.getThreeRoomFlats() + " units");

        System.out.print("Enter flat type to update (2 or 3): ");
        int flatTypeChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new available units: ");
        int newCount = scanner.nextInt();
        scanner.nextLine();

        if (newCount < 0) {
            System.out.println("Availability cannot be negative.");
            return;
        }

        if (flatTypeChoice == 2) {
            assignedProject.setTwoRoomFlats(newCount);
            System.out.println("2-Room availability updated to " + newCount + " units.");
        } else if (flatTypeChoice == 3) {
            assignedProject.setThreeRoomFlats(newCount);
            System.out.println("3-Room availability updated to " + newCount + " units.");
        } else {
            System.out.println("Invalid choice. Please enter 2 or 3.");
        }
    }

    public String generateReceipt(String applicantNric) {
        if (assignedProject == null) {
            return "You are not assigned to any project.";
        }

        // Retrieve applicant details
        String applicantName = DataLoader.getApplicantName(applicantNric);
        int applicantAge = DataLoader.getApplicantAge(applicantNric);
        String maritalStatus = DataLoader.getApplicantMaritalStatus(applicantNric);
        String bookedFlatType = DataLoader.getApplicantBookedFlatType(applicantNric);

        if (applicantName == null || maritalStatus == null || bookedFlatType == null) {
            return "No valid booking found for applicant NRIC: " + applicantNric;
        }

        // Generate the receipt (without price information)
        return String.format(
                "\n--- Booking Receipt ---\n" +
                "Applicant Name   : %s\n" +
                "NRIC             : %s\n" +
                "Age              : %s\n" +
                "Marital Status   : %s\n" +
                "Project          : %s\n" +
                "Neighborhood     : %s\n" +
                "Booked Flat Type : %s\n" +
                "-------------------------\n",
                applicantName.isEmpty() ? "Unknown" : applicantName,
                applicantNric,
                applicantAge == -1 ? "Unknown" : applicantAge,
                maritalStatus.isEmpty() ? "Unknown" : maritalStatus,
                assignedProject.getProjectName(),
                assignedProject.getNeighborhood(),
                bookedFlatType
        );
    }
}