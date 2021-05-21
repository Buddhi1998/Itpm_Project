package main.model;

public class SessionTagGroup {

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
    private String categoty;

    public SessionTagGroup() {
    }

    public SessionTagGroup(int sessionId, String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive, String consectiveAdded, String tagName) {
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
    }

    public SessionTagGroup(int sessionId, String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive, String consectiveAdded, String tagName, String categoty) {
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
        this.categoty = categoty;
    }

    public String getCategoty() {
        return categoty;
    }

    public void setCategoty(String categoty) {
        this.categoty = categoty;
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

    @Override
    public String toString() {
        return "SessionTagGroup{" +
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
                ", categoty='" + categoty + '\'' +
                '}';
    }
}
