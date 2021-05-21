package main.model;

public class WorkingHoursPerDay {

    private int whpId;
    private String workingTime;
    private String timeSlot;

    public WorkingHoursPerDay() {
    }

    public WorkingHoursPerDay(int whpId, String workingTime, String timeSlot) {
        this.whpId = whpId;
        this.workingTime = workingTime;
        this.timeSlot = timeSlot;
    }

    public WorkingHoursPerDay(String workingTime, String timeSlot) {
        this.workingTime = workingTime;
        this.timeSlot = timeSlot;
    }

    public int getWhpId() {
        return whpId;
    }

    public void setWhpId(int whpId) {
        this.whpId = whpId;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
