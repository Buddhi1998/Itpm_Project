package main.model;

public class PrefGroup {

    int id;
    int groupId;
    int subGroupId;
    int roomId;

    public PrefGroup() { }

    public PrefGroup(int id, int groupId, int subGroupId, int roomId) {
        this.id = id;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(int subGroupId) {
        this.subGroupId = subGroupId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
