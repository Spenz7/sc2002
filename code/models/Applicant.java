package models;

import java.util.regex.Pattern;

public class Applicant {
    // Attributes
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;

    // Constructor
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method: Validate NRIC Format
    public boolean isValidNric() {
        // Simple regex for NRIC validation
        String nricRegex = "^[STFG]\\d{7}[A-Z]$";
        return Pattern.matches(nricRegex, this.nric);
    }

    // Method: Check Password
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Method: Change Password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Method: Validate Age for Application Eligibility
    public boolean isEligibleForApplication() {
        return maritalStatus.equalsIgnoreCase("Single") && age >= 35;
    }

    // Override toString for Displaying Applicant Information
    @Override
    public String toString() {
        return "Applicant{" +
                "name='" + name + '\'' +
                ", nric='" + nric + '\'' +
                ", age=" + age +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}
