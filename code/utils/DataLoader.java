package utils;

import models.Applicant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    // Method to load applicant data from a CSV file
    public List<Applicant> loadApplicants(String filePath) {
        List<Applicant> applicants = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // To skip the header row
            
            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Split the line by commas (CSV format)
                String[] data = line.split(",");

                // Parse the fields into variables
                String name = data[0].trim();
                String nric = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].trim();
                String password = data[4].trim();

                // Create an Applicant object and add it to the list
                applicants.add(new Applicant(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data: " + e.getMessage());
        }

        return applicants;
    }
}
