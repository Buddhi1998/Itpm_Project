package main.model;

public class WorkingDaysSub {

    private int subId;
    private int workingId;
    private String workingday;
    private String workingTime;
    private String workingTimeSlotType;

    public WorkingDaysSub() {
    }

    public WorkingDaysSub(int subId, int workingId, String workingday, String workingTime, String workingTimeSlotType) {
        this.subId = subId;
        this.workingId = workingId;
        this.workingday = workingday;
        this.workingTime = workingTime;
        this.workingTimeSlotType = workingTimeSlotType;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }

    public String getWorkingday() {
        return workingday;
    }

    public void setWorkingday(String workingday) {
        this.workingday = workingday;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.workingTime = workingTime;
    }

    public String getWorkingTimeSlotType() {
        return workingTimeSlotType;
    }

    public void setWorkingTimeSlotType(String workingTimeSlotType) {
        this.workingTimeSlotType = workingTimeSlotType;
    }

    @Override
    public String toString() {
        return "WorkingDaysSub{" +
                "subId=" + subId +
                ", workingId=" + workingId +
                ", workingday='" + workingday + '\'' +
                ", workingTime='" + workingTime + '\'' +
                ", workingTimeSlotType='" + workingTimeSlotType + '\'' +
                '}';
    }
}
