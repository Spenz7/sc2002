package code;
import java.util.List;
import java.util.Map;
import java.util.Date;
//hello
//projects just a storage for projects
public class Project {
    private boolean visibility;
    private String projectName;
    private String neighborhood;
    private List<String> flatTypes;
    private Map<String, Integer> numberOfUnits;
    private Date applicationOpenDate;
    private Date applicationCloseDate;
    private HDBManager managerInCharge;
    private int availableOfficerSlots;

    //constructor for projects
    public Project(String projectName, String neighborhood, List<String> flatTypes, 
                   Map<String, Integer> numberOfUnits, Date applicationOpenDate, 
                   Date applicationCloseDate, HDBManager managerInCharge, int availableOfficerSlots) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.flatTypes = flatTypes;
        this.numberOfUnits = numberOfUnits;
        this.applicationOpenDate = applicationOpenDate;
        this.applicationCloseDate = applicationCloseDate;
        this.managerInCharge = managerInCharge;
        this.availableOfficerSlots = availableOfficerSlots;
        this.visibility = true; 
    }

    public void toggleVisibility(boolean status) {
        this.visibility = status;
    }

    //functions to return project data
    public String getprojectname(){
        return projectName;

    }
}
