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
    
                // Handle flat types
                int twoRoomFlats = Integer.parseInt(data[2].trim());
                int threeRoomFlats = Integer.parseInt(data[3].trim());
    
                // Parse application dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String openingDateString = data[4].trim();
                String closingDateString = data[5].trim();
                Date openingDate = dateFormat.parse(openingDateString);
                Date closingDate = dateFormat.parse(closingDateString);
    
                // Manager details
                String manager = data[6].trim();
    
                // Officer slots and officer list
                int officerSlot = Integer.parseInt(data[7].trim());
                String[] officers = data[8].split(";"); // Assuming officers are separated by semicolons
    
                // Create and add project
                BTOProject project = new BTOProject(name, neighborhood, twoRoomFlats, threeRoomFlats, 
                                                 openingDate, closingDate, manager, officerSlot, officers);
                projects.add(project);
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error reading projects file: " + e.getMessage());
        }
        return projects;
    }
    
    // Update applicant's booking
    public static boolean updateApplicantBooking(String applicantNric, String flatType, String projectName) {
        String filePath = "data/Applicants.csv"; // Adjust file path as needed
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) {
                    if (data.length < 6) {
                        data = Arrays.copyOf(data, 7); // Expand columns if necessary
                    }
                    data[5] = "Booked"; // Set application status
                    data[6] = flatType; // Set booked flat type
                    data = Arrays.copyOf(data, 8);
                    data[7] = projectName; // Store project name
                    updated = true; // Indicate successful update
                }
                lines.add(String.join(",", data));
            }
        } catch (IOException e) {
            System.err.println("Error updating applicant booking: " + e.getMessage());
            return false; // Return false if an error occurs
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing updated booking to file: " + e.getMessage());
            return false; // Return false if an error occurs
        }

        return updated; // Return true if booking was successfully updated
    }


    public static String getApplicantName(String applicantNric) {
        String filePath = "data/Applicants.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) {
                    return data[0]; // Name
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing applicant data: " + e.getMessage());
        }
        return null; // Return null if not found
    }

    public static int getApplicantAge(String applicantNric) {
        String filePath = "data/Applicants.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) {
                    return Integer.parseInt(data[2]); // Age
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing applicant data: " + e.getMessage());
        }
        return -1; // Return -1 if not found
    }

    public static String getApplicantApplicationStatus(String applicantNric) {
        String filePath = "data/Applicants.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) {
                    return data.length > 5 ? data[5] : "Unknown"; // Application Status
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing applicant data: " + e.getMessage());
        }
        return "Unknown"; // Return "Unknown" if not found
    }


    // Retrieve applicant's booked flat type
    public static String getApplicantBookedFlatType(String applicantNric) {
        String filePath = "data/Applicants.csv"; // Adjust file path as needed
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) {
                    return data.length > 6 ? data[6] : null; // Return booked flat type or null
                }
            }
        } catch (IOException e) {
            System.err.println("Error retrieving booked flat type: " + e.getMessage());
        }
        return null; // Return null if an error occurs or no booking is found
    }


    // Save officer registration to CSV
    public static void saveOfficerRegistration(String officerNric, String projectName, String status) throws IOException {
        String filePath = "data/OfficerRegistrations.csv"; // Adjust the file path as needed
        List<String> lines = new ArrayList<>();

        // Read existing entries to check for duplicates
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line); // Add existing lines to the list
                String[] data = line.split(",");
                // Check if the NRIC and project name already exist
                if (data[0].equals(officerNric) && data[1].equals(projectName)) {
                    System.out.println("Registration already exists for Officer " + officerNric + " in project " + projectName);
                    return; // Exit without saving duplicate entry
                }
            }
        } catch (FileNotFoundException e) {
            // File not found – this is likely the first entry, so we create the file
            System.out.println("OfficerRegistrations.csv does not exist yet. Creating new file.");
        }

        // Append the new entry if no duplicates are found
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(officerNric + "," + projectName + "," + status); // Write the new registration entry
            bw.newLine();
        }

        System.out.println("Successfully registered Officer " + officerNric + " for project " + projectName + ".");
    }

    public static void updateOfficerRegistrationStatus(String officerNric, String status) throws IOException {
        String filePath = "data/OfficerRegistrations.csv";
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

    public static String getOfficerRegistrationStatus(String officerNric) throws IOException {
        String filePath = "data/OfficerRegistrations.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(officerNric)) {
                    return data[2]; // Registration status is in the third column
                }
            }
        }
        return "No registration found.";
    }

    public static boolean updateOfficerPasswordInCsv(HDBOfficer officer, String filePath) {
        try {
            List<String> lines = new ArrayList<>();
            boolean updated = false;

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
                        updated = true;
                    }
                    lines.add(String.join(",", data));
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + filePath);
                return false; // Return false if file is not found
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String updatedLine : lines) {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                return false; // Return false if writing to the file fails
            }

            if (!updated) {
                System.out.println("No matching NRIC found for officer: " + officer.getNric());
            }

            return updated; // Return true if the update succeeded, false otherwise

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return false;
        }
    }


    public static String getApplicantMaritalStatus(String applicantNric) {
        String filePath = "data/Applicants.csv"; // Adjust file path as needed
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(applicantNric)) { // Assuming column 1 is NRIC
                    return data[3]; // Assuming column 3 stores marital status
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing applicant data: " + e.getMessage());
        }
        return null; // Return null if not found
    }

}
