package code;

public class Applicant{
    private String userID,password;
    private int age;
    private boolean userGroup; //true if married, false if not

    public void viewOpenProject() {
        //if age >35, single, only 2 rooms
        //if married, 21 and above, return all
        //elif return none

        if (userGroup || age >= 21 ) {
            System.out.println("Project: " + Project.getprojectname());
    }
    public void applyProject(){
        //apply to either
    }

    public void viewApplicationStatus(){
        //return pending/successful/unsuccessful, after hdb manager
    }

    public void requestWithdrawal(){



    }

    public void submitEnquiry(String enquiry){


    }



}


