package main.model;

public class MainGroupCount {

    private String yearandsemester;
    private String programme;
    private String groupcount;

    public MainGroupCount() {
    }

    public MainGroupCount(String yearandsemester, String programme, String groupcount) {
        this.yearandsemester = yearandsemester;
        this.programme = programme;
        this.groupcount = groupcount;
    }

    public String getYearandsemester() {
        return yearandsemester;
    }

    public void setYearandsemester(String yearandsemester) {
        this.yearandsemester = yearandsemester;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(String groupcount) {
        this.groupcount = groupcount;
    }
}
