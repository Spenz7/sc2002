// package models;
// import models.enums.MaritalStatus;
// import java.util.regex.Pattern;

// public class Applicant {
//     // Attributes
//     private String name;
//     private String nric;
//     private int age;
//     private String maritalStatus;
//     private String password;

//     // Constructor
//     public Applicant(String name, String nric, int age, String maritalStatus, String password) {
//         this.name = name;
//         this.nric = nric;
//         this.age = age;
//         this.maritalStatus = maritalStatus;
//         this.password = password;
//     }

//     // Getters and Setters
//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getNric() {
//         return nric;
//     }

//     public void setNric(String nric) {
//         this.nric = nric;
//     }

//     public int getAge() {
//         return age;
//     }

//     public void setAge(int age) {
//         this.age = age;
//     }

//     public String getMaritalStatus() {
//         return maritalStatus;
//     }

//     public void setMaritalStatus(String maritalStatus) {
//         this.maritalStatus = maritalStatus;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     // Method: Validate NRIC Format
//     public boolean isValidNric() {
//         // Simple regex for NRIC validation
//         String nricRegex = "^[STFG]\\d{7}[A-Z]$";
//         return Pattern.matches(nricRegex, this.nric);
//     }

//     // Method: Check Password
//     public boolean checkPassword(String inputPassword) {
//         return this.password.equals(inputPassword);
//     }

//     // Method: Change Password
//     public void changePassword(String newPassword) {
//         this.password = newPassword;
//     }

//     // Method: Validate Age for Application Eligibility
//     public boolean isEligibleForApplication() {
//         return maritalStatus.equalsIgnoreCase("Single") && age >= 35;
//     }

//     // Override toString for Displaying Applicant Information
//     @Override
//     public String toString() {
//         return "Applicant{" +
//                 "name='" + name + '\'' +
//                 ", nric='" + nric + '\'' +
//                 ", age=" + age +
//                 ", maritalStatus='" + maritalStatus + '\'' +
//                 '}';
//     }
// }

package models;

import models.enums.RegistrationStatus;
import java.util.regex.Pattern;

public class Applicant extends HDBCustomer {
    // No Scanner needed since UI handles input
    // No duplicate fields - all inherited from HDBCustomer

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    // Implement required abstract methods (simplified for your UI)
    @Override
    public void displayDashboard() {
        // Empty or basic implementation since UI handles display
    }

    @Override
    public void performDuties() {
        // Empty implementation since UI handles workflow
    }

    // Applicant-specific business logic methods
    public boolean isValidNric() {
        String nricRegex = "^[STFG]\\d{7}[A-Z]$";
        return Pattern.matches(nricRegex, this.nric);
    }

    public boolean isEligibleForApplication() {
        return getMaritalStatus().equalsIgnoreCase("Single") && getAge() >= 35;
    }

    // Enhanced password change that could work with your CSV system
    public boolean changePassword(String currentPassword, String newPassword) {
        if (!checkPassword(currentPassword)) {
            return false;
        }
        
        if (newPassword.length() < 8) {
            return false;
        }
        
        this.password = newPassword;
        return true;
    }

    @Override
    public String toString() {
        return String.join(",",
            getName(),
            getNric(),
            String.valueOf(getAge()),
            getMaritalStatus(),
            getPassword(),
            getRegistrationStatus() != null ? getRegistrationStatus().name() : "null"
        );
    }
}