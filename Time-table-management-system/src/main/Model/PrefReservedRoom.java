package main.model;


import java.sql.Time;
import java.util.Date;

public class PrefReservedRoom {

    int id;
    int roomId;
    String day;
    Time  toTime;
    Time fromTime;
    String room;
    String roomType;
    String center;
    String building;

    public PrefReservedRoom() {
    }

    public PrefReservedRoom(int id, int roomId, String day, Time toTime, Time fromTime, String room, String roomType, String center, String building) {
        this.id = id;
        this.roomId = roomId;
        this.day = day;
        this.toTime = toTime;
        this.fromTime = fromTime;
        this.room = room;
        this.roomType = roomType;
        this.center = center;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Date getToTime() {
        return toTime;
    }



    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setToTime(Time toTime) {
        this.toTime = toTime;
    }

    public Time getFromTime() {
        return fromTime;
    }

    public void setFromTime(Time fromTime) {
        this.fromTime = fromTime;
    }
}
