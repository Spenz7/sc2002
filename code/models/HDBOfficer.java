package models;

import models.enums.RegistrationStatus;

public class HDBOfficer {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;
    private RegistrationStatus registrationStatus;
    private BTOProject assignedProject;

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
}
