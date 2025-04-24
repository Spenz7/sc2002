package utils;

import models.*;
import models.enums.FlatType;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataLoader {

    // Load applicants from CSV
    public List<Applicant> loadApplicants(String filePath) {
        List<Applicant> applicants = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String nric = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].trim();
                String password = data[4].trim();
                applicants.add(new Applicant(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.err.println("Error reading applicants file: " + e.getMessage());
        }
        return applicants;
    }

    // Load officers from CSV
    public List<HDBOfficer> loadOfficers(String filePath) {
        List<HDBOfficer> officers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String nric = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].trim();
                String password = data[4].trim();
                officers.add(new HDBOfficer(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.err.println("Error reading officers file: " + e.getMessage());
        }
        return officers;
    }

    // Load managers from CSV
    public List<HDBManager> loadManagers(String filePath) {
        List<HDBManager> managers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String nric = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                String maritalStatus = data[3].trim();
                String password = data[4].trim();
                managers.add(new HDBManager(name, nric, age, maritalStatus, password));
            }
        } catch (IOException e) {
            System.err.println("Error reading managers file: " + e.getMessage());
        }
        return managers;
    }

    // Load BTO projects from CSV
    public List<BTOProject> loadProjects(String filePath) {
        List<BTOProject> projects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String neighborhood = data[1].trim();

                // Handle multiple flat types
                String type1 = data[2].trim();
                int unitsType1 = Integer.parseInt(data[3].trim());
                double priceType1 = Double.parseDouble(data[4].trim());
                String type2 = data[5].trim();
                int unitsType2 = Integer.parseInt(data[6].trim());
                double priceType2 = Double.parseDouble(data[7].trim());

                // Parse application dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String openingDateString = data[8].trim();
                String closingDateString = data[9].trim();
                Date openingDate = dateFormat.parse(openingDateString);
                Date closingDate = dateFormat.parse(closingDateString);

                // Manager details
                String manager = data[10].trim();

                // Officer slots and officer list
                int officerSlot = Integer.parseInt(data[11].trim());
                String[] officers = data[12].split(";"); // Assuming officers are separated by semicolons

                // Create and add project
                BTOProject project = new BTOProject(name, neighborhood, type1, unitsType1, priceType1, type2, unitsType2, priceType2, openingDate, closingDate, manager, officerSlot, officers);
                projects.add(project);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error reading projects file: " + e.getMessage());
        }
        return projects;
    }

    // Save officer registration to CSV
    public static void saveOfficerRegistration(String officerNric, String projectName, String status) throws IOException {
        String filePath = "data/OfficerRegistrations.csv"; // Adjust the file path as needed
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(officerNric + "," + projectName + "," + status);
            bw.newLine();
        }
    }

    // Retrieve officer registration status from CSV
    public static String getOfficerRegistrationStatus(String officerNric) throws IOException {
        String filePath = "data/OfficerRegistrations.csv"; // Adjust the file path as needed
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(officerNric)) {
                    return data[2]; // Registration status is stored in the third column
                }
            }
        }
        return "No registration found.";
    }

    // Update officer registration status in CSV
    public static void updateOfficerRegistrationStatus(String officerNric, String status) throws IOException {
        String filePath = "data/OfficerRegistrations.csv"; // Adjust the file path as needed
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(officerNric)) {
                    data[2] = status; // Update the registration status
                }
                lines.add(String.join(",", data));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        }
    }

    // Update officer's password in CSV
    public static void updateOfficerPasswordInCsv(HDBOfficer officer, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        lines.add(line); // Add the header line as is
                        continue;
                    }
                    String[] data = line.split(",");
                    if (data[1].trim().equals(officer.getNric())) {
                        // Update password for matching NRIC
                        data[4] = officer.getPassword();
                        lines.add(String.join(",", data));
                    } else {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
            System.out.println("Password updated in CSV for officer: " + officer.getNric());
        } catch (IOException e) {
            System.err.println("Error updating officer password in file: " + e.getMessage());
        }
    }

    // Update officer's password in CSV
    public static void updateOfficerPasswordInCsv(HDBOfficer officer, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean isHeader = true;
                while ((line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        lines.add(line); // Add the header line as is
                        continue;
                    }
                    String[] data = line.split(",");
                    if (data[1].trim().equals(officer.getNric())) {
                        // Update password for matching NRIC
                        data[4] = officer.getPassword();
                        lines.add(String.join(",", data));
                    } else {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
            System.out.println("Password updated in CSV for officer: " + officer.getNric());
        } catch (IOException e) {
            System.err.println("Error updating officer password in file: " + e.getMessage());
        }
    }
}
