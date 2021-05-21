package main.model;

public class Dashboard {

    int noOfBuildings;
    String center;
    String faculty;
    int noOfEmployees;

    public Dashboard(){}

    public Dashboard(int noOfBuildings, String center) {
        this.noOfBuildings = noOfBuildings;
        this.center = center;
    }

    public Dashboard(String faculty, int noOfEmployees) {
        this.faculty = faculty;
        this.noOfEmployees = noOfEmployees;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getNoOfEmployees() {
        return noOfEmployees;
    }

    public void setNoOfEmployees(int noOfEmployees) {
        this.noOfEmployees = noOfEmployees;
    }

    public int getNoOfBuildings() {
        return noOfBuildings;
    }

    public void setNoOfBuildings(int noOfBuildings) {
        this.noOfBuildings = noOfBuildings;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
