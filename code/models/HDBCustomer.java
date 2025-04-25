package models;

import models.enums.RegistrationStatus;
import java.util.Scanner;

public abstract class HDBCustomer {
    protected String name;
    protected String nric;
    protected int age;
    protected String maritalStatus;
    protected String password;
    protected RegistrationStatus registrationStatus;
    protected final Scanner scanner = new Scanner(System.in);

    public HDBCustomer(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    // Common getters and setters
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

    // Common methods
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Abstract methods for polymorphic behavior
    // public abstract void displayDashboard();
    // public abstract void performDuties();
}