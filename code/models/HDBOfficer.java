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
        Scanner scanner = new Scanner(System.in);

        // Check if the officer is assigned to a project
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        // Collect the applicant's NRIC
        System.out.print("Enter Applicant's NRIC for flat booking: ");
        String applicantNric = scanner.nextLine().trim();

        // Retrieve the applicant's booked flat type
        String bookedFlatType = DataLoader.getApplicantBookedFlatType(applicantNric);
        if (bookedFlatType == null) {
            System.out.println("No booked flat type found for applicant NRIC: " + applicantNric);
        }

        // Fetch the applicant's application status
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
        System.out.printf("1. %s (%d units available) - Price: $%.2f\n",
                assignedProject.getType1(), assignedProject.getUnitsType1(), assignedProject.getPriceType1());
        System.out.printf("2. %s (%d units available) - Price: $%.2f\n",
                assignedProject.getType2(), assignedProject.getUnitsType2(), assignedProject.getPriceType2());

        // Collect flat type choice
        System.out.print("Enter flat type choice (1 for " + assignedProject.getType1() +
                ", 2 for " + assignedProject.getType2() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String chosenFlatType;
        if (choice == 1) {
            chosenFlatType = assignedProject.getType1();
            if (assignedProject.getUnitsType1() <= 0) {
                System.out.println("No available units for " + chosenFlatType);
                return;
            }
            assignedProject.setUnitsType1(assignedProject.getUnitsType1() - 1);
        } else if (choice == 2) {
            chosenFlatType = assignedProject.getType2();
            if (assignedProject.getUnitsType2() <= 0) {
                System.out.println("No available units for " + chosenFlatType);
                return;
            }
            assignedProject.setUnitsType2(assignedProject.getUnitsType2() - 1);
        } else {
            System.out.println("Invalid choice. Booking aborted.");
            return;
        }

        // Update applicant's booking details
        boolean success = DataLoader.updateApplicantBooking(applicantNric, chosenFlatType, assignedProject.getProjectName());
        if (success) {
            System.out.println("Flat booking processed successfully. Applicant " + applicantNric + " has booked a " + chosenFlatType + " unit.");
        } else {
            System.err.println("Failed to update applicant's booking.");
        }
    }








    // Option 6: Update Flat Availability
    public void updateFlatAvailability() {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        System.out.println("\nCurrent Flat Availability:");
        System.out.println(assignedProject.getType1() + ": " + assignedProject.getUnitsType1() + " units");
        System.out.println(assignedProject.getType2() + ": " + assignedProject.getUnitsType2() + " units");

        System.out.print("Enter flat type to update (e.g., '2-Room' or '3-Room'): ");
        String flatType = scanner.nextLine().trim();

        System.out.print("Enter new available units for " + flatType + ": ");
        int newCount = scanner.nextInt();
        scanner.nextLine();

        if (newCount < 0) {
            System.out.println("Availability cannot be negative.");
            return;
        }

        if (flatType.equalsIgnoreCase(assignedProject.getType1())) {
            assignedProject.setUnitsType1(newCount);
        } else if (flatType.equalsIgnoreCase(assignedProject.getType2())) {
            assignedProject.setUnitsType2(newCount);
        } else {
            System.out.println("Invalid flat type provided.");
            return;
        }

        System.out.println("Flat availability updated: " + flatType + " now has " + newCount + " unit(s) available.");
    }

    // Option 7: Generate Receipt
    public String generateReceipt(String applicantNric) {
        if (assignedProject == null) {
            return "You are not assigned to any project.";
        }

        // Retrieve applicant details using DataLoader
        String applicantName = DataLoader.getApplicantName(applicantNric);
        int applicantAge = DataLoader.getApplicantAge(applicantNric);
        String maritalStatus = DataLoader.getApplicantMaritalStatus(applicantNric);
        String bookedFlatType = DataLoader.getApplicantBookedFlatType(applicantNric);

        // Verify that applicant details were successfully retrieved
        if (applicantName == null || maritalStatus == null || bookedFlatType == null || bookedFlatType.isEmpty()) {
            return "No valid booking found for applicant NRIC: " + applicantNric;
        }

        // Determine the price based on the booked flat type
        double unitPrice;
        if (bookedFlatType.equalsIgnoreCase(assignedProject.getType1())) {
            unitPrice = assignedProject.getPriceType1();
        } else if (bookedFlatType.equalsIgnoreCase(assignedProject.getType2())) {
            unitPrice = assignedProject.getPriceType2();
        } else {
            return "Booked flat type does not match project details.";
        }

        // Calculate the total cost (simple implementation for now)
        double totalCost = unitPrice;

        // Generate the receipt
        return String.format(
                "\n--- Booking Receipt ---\n" +
                "Applicant Name   : %s\n" +
                "NRIC             : %s\n" +
                "Age              : %s\n" +
                "Marital Status   : %s\n" +
                "Project          : %s\n" +
                "Neighborhood     : %s\n" +
                "Booked Flat Type : %s\n" +
                "Unit Price       : $%.2f\n" +
                "Total Cost       : $%.2f\n" +
                "-------------------------\n",
                applicantName.isEmpty() ? "Unknown" : applicantName,
                applicantNric,
                applicantAge == -1 ? "Unknown" : applicantAge,
                maritalStatus.isEmpty() ? "Unknown" : maritalStatus,
                assignedProject.getProjectName(),
                assignedProject.getNeighborhood(),
                bookedFlatType,
                unitPrice,
                totalCost
        );
    }

}
