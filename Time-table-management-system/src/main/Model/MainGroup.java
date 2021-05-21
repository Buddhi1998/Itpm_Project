package main.model;

public class MainGroup {
    private int id;
    private String groupid;
    private String mgroupName;
    private int programmeid;
    private int semid;
    private int groupNumber;

    public MainGroup() {
    }

    public MainGroup(int id, String groupid, int programmeid, int semid) {
        this.id = id;
        this.groupid = groupid;
        this.programmeid = programmeid;
        this.semid = semid;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public int getProgrammeid() {
        return programmeid;
    }

    public void setProgrammeid(int programmeid) {
        this.programmeid = programmeid;
    }

    public int getSemid() {
        return semid;
    }

    public void setSemid(int semid) {
        this.semid = semid;
    }

    public String getMgroupName() {
        return mgroupName;
    }

    public void setMgroupName(String mgroupName) {
        this.mgroupName = mgroupName;
    }
}
