package main.model;

public class Building {

    int bid;
    String building;
    String center;

    public Building(){}

    public Building(int bid, String building, String center) {
        this.bid = bid;
        this.building = building;
        this.center = center;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
