package main.model;

public class YearAndSemester {

    int id;
    String yearName;
    String semesterName;
    String fullName;

    public YearAndSemester() {

    }

    public YearAndSemester(int id, String yearName, String semesterName, String fullName) {
        this.id = id;
        this.yearName = yearName;
        this.semesterName = semesterName;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
