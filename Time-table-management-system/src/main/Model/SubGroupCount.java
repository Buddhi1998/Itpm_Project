package main.model;

public class SubGroupCount {

    private String groupId;
    private int subGroupCount;

    public SubGroupCount() {
    }

    public SubGroupCount(String groupId, int subGroupCount) {
        this.groupId = groupId;
        this.subGroupCount = subGroupCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSubGroupCount() {
        return subGroupCount;
    }

    public void setSubGroupCount(int subGroupCount) {
        this.subGroupCount = subGroupCount;
    }
}
