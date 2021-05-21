package main.model;

public class ConsectiveSession {

    private int id;
    private String  lecturer;
    private String groupId;
    private String subject;
    private String tag;

    public ConsectiveSession() {
    }

    public ConsectiveSession(int id, String lecturer, String groupId, String subject, String tag) {
        this.id = id;
        this.lecturer = lecturer;
        this.groupId = groupId;
        this.subject = subject;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
