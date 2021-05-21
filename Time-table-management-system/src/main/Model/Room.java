package main.model;

public class Room {

    int rid;
    int buildingId;
    String room;
    int capacity;
    String center;
    String building;
    String roomType;

    public Room(){}

    public Room(String room) {
        this.room = room;
    }

    public Room(int rid, int buildingId, String room, int capacity) {
        this.rid = rid;
        this.buildingId = buildingId;
        this.room = room;
        this.capacity = capacity;
    }

    public Room(int rid, int buildingId, String room, int capacity, String center, String building) {
        this.rid = rid;
        this.buildingId = buildingId;
        this.room = room;
        this.capacity = capacity;
        this.center = center;
        this.building = building;
    }

    public Room(String building, String room, int capacity) {
        this.building = building;
        this.room = room;
        this.capacity = capacity;
    }

    public Room(int buildingId, String room, int capacity) {
        this.buildingId = buildingId;
        this.room = room;
        this.capacity = capacity;
    }

    public Room(int rid, int buildingId, String room, int capacity, String center, String building, String roomType) {
        this.rid = rid;
        this.buildingId = buildingId;
        this.room = room;
        this.capacity = capacity;
        this.center = center;
        this.building = building;
        this.roomType = roomType;
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

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
