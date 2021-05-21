package main.model;

public class PrefTag {

    int id;
    int tagId;
    int roomId;

    public PrefTag() { }

    public PrefTag(int id, int tagId, int roomId) {
        this.id = id;
        this.tagId = tagId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
