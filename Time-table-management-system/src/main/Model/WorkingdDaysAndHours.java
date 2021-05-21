package main.model;


public class WorkingdDaysAndHours {

    private int workingId;
    private String type;
    private int noOfDays;
    private int subId;
    private String workingday;
    private String timeSlot;
    private String timeSlotType;

    public WorkingdDaysAndHours() {
    }

    public WorkingdDaysAndHours(int workingId, String type, int noOfDays, int subId, String workingday, String timeSlot, String timeSlotType) {
        this.workingId = workingId;
        this.type = type;
        this.noOfDays = noOfDays;
        this.subId = subId;
        this.workingday = workingday;
        this.timeSlot = timeSlot;
        this.timeSlotType = timeSlotType;
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

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getWorkingday() {
        return workingday;
    }

    public void setWorkingday(String workingday) {
        this.workingday = workingday;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTimeSlotType() {
        return timeSlotType;
    }

    public void setTimeSlotType(String timeSlotType) {
        this.timeSlotType = timeSlotType;
    }

    @Override
    public String toString() {
        return "WorkingdDaysAndHours{" +
                "workingId=" + workingId +
                ", type='" + type + '\'' +
                ", noOfDays=" + noOfDays +
                ", subId=" + subId +
                ", workingday='" + workingday + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                '}';
    }
}
