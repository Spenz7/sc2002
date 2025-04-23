package utils;

import models.Applicant;
import models.HDBOfficer;
import models.HDBManager;
import models.BTOProject;
import models.enums.FlatType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                int flatType = Integer.parseInt(data[2].trim()); // Convert string to FlatType
                int unitCount = Integer.parseInt(data[3].trim());
                double price = Double.parseDouble(data[4].trim());
                boolean visibility = Boolean.parseBoolean(data[5].trim());
                projects.add(new BTOProject(name, neighborhood, flatType, unitCount, price, visibility));
            }
        } catch (IOException e) {
            System.err.println("Error reading projects file: " + e.getMessage());
        }
        return projects;
    }
}
