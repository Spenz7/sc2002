package models;

public class HDBManager {
    // Attributes
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password;

    // Constructor
    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
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
        String nricRegex = "^[STFG]\\d{7}[A-Z]$"; // Ensuring compliance with NRIC format
        return nric.matches(nricRegex);
    }

    // Method: Check Password
    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Method: Change Password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Override toString for Displaying Manager Information
    @Override
    public String toString() {
        return "HDBManager{" +
                "name='" + name + '\'' +
                ", nric='" + nric + '\'' +
                ", age=" + age +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }
}
