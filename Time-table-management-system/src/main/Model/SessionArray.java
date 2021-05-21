package main.model;

public class SessionArray {


    private String subjectCode;
    private String subjectName;
    private String tagName;
    private String subgroup;

    public SessionArray() {
    }

    public SessionArray(String subjectCode, String subjectName, String tagName, String subgroup) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.tagName = tagName;
        this.subgroup = subgroup;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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
}
