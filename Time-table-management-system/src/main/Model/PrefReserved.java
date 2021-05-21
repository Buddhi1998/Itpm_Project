package main.model;

public class PrefReserved {

    int id;
    int roomId;
    String day;
    String toTime;
    String fromTime;

    public PrefReserved() { }

    public PrefReserved(int id, int roomId, String day, String toTime, String fromTime) {
        this.id = id;
        this.roomId = roomId;
        this.day = day;
        this.toTime = toTime;
        this.fromTime = fromTime;
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

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
}
