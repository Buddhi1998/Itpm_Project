package main.model;

public class Lecturer {
    int empId;
    String empName;
    String Faculty;
    int  department;
    String center;
    String designation;
    int building;
    int level;
    String rank;
    String departmentName;
    String buildingName;

    public Lecturer(int empId, String empName) {
        this.empId = empId;
        this.empName = empName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Lecturer(int empId, String empName, String faculty, int department, String center, String designation, int building, int level, String rank, String departmentName, String buildingName) {
        this.empId = empId;
        this.empName = empName;
        Faculty = faculty;
        this.department = department;
        this.center = center;
        this.designation = designation;
        this.building = building;
        this.level = level;
        this.rank = rank;
        this.departmentName = departmentName;
        this.buildingName = buildingName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public Lecturer(int empId, String empName, String faculty, int department, String center, String designation, int building, int level, String rank) {
        this.empId = empId;
        this.empName = empName;
        Faculty = faculty;
        this.department = department;
        this.center = center;
        this.designation = designation;
        this.building = building;
        this.level = level;
        this.rank = rank;
    }

    public Lecturer() {
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", Faculty='" + Faculty + '\'' +
                ", department=" + department +
                ", center='" + center + '\'' +
                ", designation='" + designation + '\'' +
                ", building=" + building +
                ", level=" + level +
                ", rank='" + rank + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }
}
