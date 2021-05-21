package main.model;

public class Dashboard2 {

    String designation;
    int noOfDesig;
    String yearSem;
    int noOfSubjects;

    public Dashboard2() {
    }

    public Dashboard2(int noOfSubjects, String yearSem) {
        this.yearSem = yearSem;
        this.noOfSubjects = noOfSubjects;
    }

    public Dashboard2(String designation, int noOfDesig) {
        this.designation = designation;
        this.noOfDesig = noOfDesig;
    }

    public String getYearSem() {
        return yearSem;
    }

    public void setYearSem(String yearSem) {
        this.yearSem = yearSem;
    }

    public int getNoOfSubjects() {
        return noOfSubjects;
    }

    public void setNoOfSubjects(int noOfSubjects) {
        this.noOfSubjects = noOfSubjects;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getNoOfDesig() {
        return noOfDesig;
    }

    public void setNoOfDesig(int noOfDesig) {
        this.noOfDesig = noOfDesig;
    }
}
