package controllers;

import models.HDBOfficer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OfficerController {
    private List<HDBOfficer> officers; // List to hold all officers

    public OfficerController() {
        officers = loadOfficerListFromCsv(); // Initialize by loading officers from CSV
    }

    // Method to update officer password and persist the changes to CSV
    public boolean updateOfficerPassword(String officerNric, String newPassword) {
        for (HDBOfficer officer : officers) {
            if (officer.getNric().equalsIgnoreCase(officerNric)) {
                officer.setPassword(newPassword); // Update password in-memory
                saveOfficerListToCsv(officers); // Persist updated data back to CSV
                System.out.println("Password updated successfully for officer: " + officer.getName());
                return true;
            }
        }
        System.out.println("Officer with NRIC '" + officerNric + "' not found.");
        return false;
    }

    // Helper method: Load officer list from CSV file
    private List<HDBOfficer> loadOfficerListFromCsv() {
        List<HDBOfficer> officers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/OfficerList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String nric = fields[0].trim();
                String name = fields[1].trim();
                String password = fields[2].trim();
                officers.add(new HDBOfficer(nric, name, password));
            }
        } catch (IOException e) {
            System.err.println("Error loading officer list: " + e.getMessage());
        }
        return officers;
    }

    // Helper method: Save officer list back to CSV file
    private void saveOfficerListToCsv(List<HDBOfficer> officers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/OfficerList.csv"))) {
            for (HDBOfficer officer : officers) {
                bw.write(officer.getNric() + "," + officer.getName() + "," + officer.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving officer list: " + e.getMessage());
        }
    }

    // Method to get the full officer list
    public List<HDBOfficer> getOfficers() {
        return officers;
    }
}
