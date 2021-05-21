package main.model;

public class RoomTimeTable {
    private  String day;
    private String romm;
    private String timeString;
    private String subName;
    private String tagName;
    private String subCode;
    private String mainGroupId;
    private String subGroupId;
    private String sessionId;

    public RoomTimeTable() {
    }

    public RoomTimeTable(String day, String romm, String timeString, String subName, String tagName, String subCode, String mainGroupId, String subGroupId, String sessionId) {
        this.day = day;
        this.romm = romm;
        this.timeString = timeString;
        this.subName = subName;
        this.tagName = tagName;
        this.subCode = subCode;
        this.mainGroupId = mainGroupId;
        this.subGroupId = subGroupId;
        this.sessionId = sessionId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRomm() {
        return romm;
    }

    public void setRomm(String romm) {
        this.romm = romm;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(String mainGroupId) {
        this.mainGroupId = mainGroupId;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
