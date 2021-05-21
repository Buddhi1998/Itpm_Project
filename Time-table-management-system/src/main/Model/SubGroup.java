package main.model;

public class SubGroup {
    private int id;
    private String subgroupid;
    private int subgroupnumber;
    private int maingroupid;

    public SubGroup() {
    }

    public SubGroup(int id, String subgroupid, int subgroupnumber, int maingroupid) {
        this.id = id;
        this.subgroupid = subgroupid;
        this.subgroupnumber = subgroupnumber;
        this.maingroupid = maingroupid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubgroupid() {
        return subgroupid;
    }

    public void setSubgroupid(String subgroupid) {
        this.subgroupid = subgroupid;
    }

    public int getSubgroupnumber() {
        return subgroupnumber;
    }

    public void setSubgroupnumber(int subgroupnumber) {
        this.subgroupnumber = subgroupnumber;
    }

    public int getMaingroupid() {
        return maingroupid;
    }

    public void setMaingroupid(int maingroupid) {
        this.maingroupid = maingroupid;
    }
}
