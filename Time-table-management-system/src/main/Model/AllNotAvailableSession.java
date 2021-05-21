package main.model;

import java.sql.Time;

public class AllNotAvailableSession {

    private int sessionId;
    private String subjectId;
    private int tagId;
    private String  groupId;
    private String subGroupId;
    private int studentCount;
    private float duration;
    private String isConsecutive;
    private String consectiveAdded;
    private String tagName;
    private String category;
    private int notAvailableSessionId;
    private String day;
    private Time toTime;
    private Time fromTime;

    public AllNotAvailableSession() {
    }

    public AllNotAvailableSession(int sessionId, String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive, String consectiveAdded, String tagName, String category, int notAvailableSessionId, String day, Time toTime, Time fromTime) {
        this.sessionId = sessionId;
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.studentCount = studentCount;
        this.duration = duration;
        this.isConsecutive = isConsecutive;
        this.consectiveAdded = consectiveAdded;
        this.tagName = tagName;
        this.category = category;
        this.notAvailableSessionId = notAvailableSessionId;
        this.day = day;
        this.toTime = toTime;
        this.fromTime = fromTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        this.subGroupId = subGroupId;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getIsConsecutive() {
        return isConsecutive;
    }

    public void setIsConsecutive(String isConsecutive) {
        this.isConsecutive = isConsecutive;
    }

    public String getConsectiveAdded() {
        return consectiveAdded;
    }

    public void setConsectiveAdded(String consectiveAdded) {
        this.consectiveAdded = consectiveAdded;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNotAvailableSessionId() {
        return notAvailableSessionId;
    }

    public void setNotAvailableSessionId(int notAvailableSessionId) {
        this.notAvailableSessionId = notAvailableSessionId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getToTime() {
        return toTime;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }

    @Override
    public String toString() {
        return "AllNotAvailableSession{" +
                "sessionId=" + sessionId +
                ", subjectId='" + subjectId + '\'' +
                ", tagId=" + tagId +
                ", groupId='" + groupId + '\'' +
                ", subGroupId='" + subGroupId + '\'' +
                ", studentCount=" + studentCount +
                ", duration=" + duration +
                ", isConsecutive='" + isConsecutive + '\'' +
                ", consectiveAdded='" + consectiveAdded + '\'' +
                ", tagName='" + tagName + '\'' +
                ", category='" + category + '\'' +
                ", notAvailableSessionId=" + notAvailableSessionId +
                ", day='" + day + '\'' +
                ", toTime=" + toTime +
                ", fromTime=" + fromTime +
                '}';
    }
}
