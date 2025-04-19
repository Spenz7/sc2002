package controllers;

import models.Applicant;
import models.HDBManager;
import models.HDBOfficer;

import java.util.List;

public class AuthController {
    // Lists to hold each type of user loaded from CSV files via your DataLoader.
    private List<Applicant> applicants;
    private List<HDBOfficer> officers;
    private List<HDBManager> managers;

    /**
     * Constructor initializes the controller with the provided user lists.
     */
    public AuthController(List<Applicant> applicants, List<HDBOfficer> officers, List<HDBManager> managers) {
        this.applicants = applicants;
        this.officers = officers;
        this.managers = managers;
    }

    /**
     * Attempts to log in a user by checking their NRIC format and password.
     * Returns the corresponding user object if successful; otherwise, returns null.
     */
    public Object login(String nric, String password) {
        // Validate NRIC format first (simple regex check).
        if (!nric.matches("^[STFG]\\d{7}[A-Z]$")) {
            System.out.println("Invalid NRIC format.");
            return null;
        }

        // Search for the user in the Applicants list.
        for (Applicant applicant : applicants) {
            if (applicant.getNric().equalsIgnoreCase(nric)) {
                if (applicant.checkPassword(password)) {
                    return applicant; // Successful login for an Applicant.
                } else {
                    System.out.println("Incorrect password for Applicant.");
                    return null;
                }
            }
        }

        // Search in the Officers list.
        for (HDBOfficer officer : officers) {
            if (officer.getNric().equalsIgnoreCase(nric)) {
                if (officer.checkPassword(password)) {
                    return officer; // Successful login for an HDB Officer.
                } else {
                    System.out.println("Incorrect password for Officer.");
                    return null;
                }
            }
        }

        // Search in the Managers list.
        for (HDBManager manager : managers) {
            if (manager.getNric().equalsIgnoreCase(nric)) {
                if (manager.checkPassword(password)) {
                    return manager; // Successful login for an HDB Manager.
                } else {
                    System.out.println("Incorrect password for Manager.");
                    return null;
                }
            }
        }

        // If no matching user found.
        System.out.println("User not found.");
        return null;
    }

    /**
     * Changes the password for the specified user if the provided current password matches.
     * Returns true if the password was successfully updated, false otherwise.
     */
    public boolean changePassword(Object user, String currentPassword, String newPassword) {
        if (user instanceof Applicant) {
            Applicant applicant = (Applicant) user;
            if (applicant.checkPassword(currentPassword)) {
                applicant.changePassword(newPassword);
                return true;
            }
        } else if (user instanceof HDBOfficer) {
            HDBOfficer officer = (HDBOfficer) user;
            if (officer.checkPassword(currentPassword)) {
                officer.changePassword(newPassword);
                return true;
            }
        } else if (user instanceof HDBManager) {
            HDBManager manager = (HDBManager) user;
            if (manager.checkPassword(currentPassword)) {
                manager.changePassword(newPassword);
                return true;
            }
        }
        System.out.println("Password change unsuccessful. Please check your current password.");
        return false;
    }
}
