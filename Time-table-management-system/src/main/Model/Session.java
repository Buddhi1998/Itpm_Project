package main.model;

public class Session {
    int sessionId;
    String subjectId;
    int tagId;
    String  groupId;
    String subGroupId;
    int studentCount;
    float duration;
    String isConsecutive;
    String consectiveAdded;
    String isParallel;
    String category;

    public String getIsParallel() {
        return isParallel;
    }

    public void setIsParallel(String isParallel) {
        this.isParallel = isParallel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Session(String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive, String isParallel, String category) {
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.studentCount = studentCount;
        this.duration = duration;
        this.isConsecutive = isConsecutive;
        this.isParallel = isParallel;
        this.category = category;
    }

    public Session(int sessionId, String subjectId, int tagId, int studentCount, float duration) {
        this.sessionId = sessionId;
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.studentCount = studentCount;
        this.duration = duration;

    }



    public Session(String subjectId, int tagId, String  groupId, String subGroupId, int studentCount, float duration, String isConsecutive) {
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.studentCount = studentCount;
        this.duration = duration;
        this.isConsecutive = isConsecutive;
    }

    public Session() {
    }

    public Session(int sessionId, String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive) {
        this.sessionId = sessionId;
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.studentCount = studentCount;
        this.duration = duration;
        this.isConsecutive = isConsecutive;
    }

    public Session(int sessionId, String subjectId, int tagId, String groupId, String subGroupId, int studentCount, float duration, String isConsecutive, String consectiveAdded) {
        this.sessionId = sessionId;
        this.subjectId = subjectId;
        this.tagId = tagId;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.studentCount = studentCount;
        this.duration = duration;
        this.isConsecutive = isConsecutive;
        this.consectiveAdded = consectiveAdded;
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
}
