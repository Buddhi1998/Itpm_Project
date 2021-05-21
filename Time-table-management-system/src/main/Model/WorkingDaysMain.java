package main.model;

import java.util.Date;

public class WorkingDaysMain {
    private int workingId;
    private String type;
    private int noOfDays;

    public WorkingDaysMain() {
    }

    public WorkingDaysMain(int workingId, String type, int noOfDays) {
        this.workingId = workingId;
        this.type = type;
        this.noOfDays = noOfDays;
    }

    public WorkingDaysMain(String type, int noOfDays) {
        this.type = type;
        this.noOfDays = noOfDays;
    }


    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }


}
