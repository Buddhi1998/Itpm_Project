package main.model;

public class Programme {

    int programmeId;
    String programmeName;

    public Programme() {
    }

    public Programme(int programmeId, String programmeName) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
    }

    public int getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(int programmeId) {
        this.programmeId = programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }
}
