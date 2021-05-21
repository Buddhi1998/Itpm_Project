package main.model;

public class SessionDTO {
    int sessionId;
    String subjectName;
    String tagName;
    String  groupName;
    int studentCount;
    float duration;
    String lecturer;

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public SessionDTO(int sessionId, String subjectName, String tagName, int studentCount , float duration, String groupName, String lecturer) {
        this.sessionId = sessionId;
        this.subjectName = subjectName;
        this.tagName = tagName;
        this.groupName = groupName;
        this.studentCount = studentCount;
        this.duration = duration;
        this.lecturer = lecturer;
    }

    public SessionDTO(int sessionId, String subjectName, String tagName, int studentCount , float duration,String groupName) {
        this.sessionId = sessionId;
        this.subjectName = subjectName;
        this.tagName = tagName;
        this.groupName = groupName;
        this.studentCount = studentCount;
        this.duration = duration;
    }

    public SessionDTO() {
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
}
