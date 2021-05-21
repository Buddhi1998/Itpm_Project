package main.model;


public class AllGroupDetail {

    private String accdamicYearAndSemester;
    private String programme;
    private String groupeName;
    private String groupeNumber;
    private String groupeId;
    private String subGroupeNo;
    private String subGroupeId;

    public String getAccdamicYearAndSemester() {
        return accdamicYearAndSemester;
    }

    public void setAccdamicYearAndSemester(String accdamicYearAndSemester) {
        this.accdamicYearAndSemester = accdamicYearAndSemester;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }


    public String getSubGroupeNo() {
        return subGroupeNo;
    }

    public void setSubGroupeNo(String subGroupeNo) {
        this.subGroupeNo = subGroupeNo;
    }

    public String getSubGroupeId() {
        return subGroupeId;
    }

    public void setSubGroupeId(String subGroupeId) {
        this.subGroupeId = subGroupeId;
    }

    public String getGroupeNumber() {
        return groupeNumber;
    }

    public void setGroupeNumber(String groupeNumber) {
        this.groupeNumber = groupeNumber;
    }

    public String getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(String groupeId) {
        this.groupeId = groupeId;
    }

    @Override
    public String toString() {
        return "AllGroupDetail{" +
                "accdamicYearAndSemester='" + accdamicYearAndSemester + '\'' +
                ", programme='" + programme + '\'' +
                ", groupeName='" + groupeName + '\'' +
                ", groupeId='" + groupeId + '\'' +
                ", subGroupeNo='" + subGroupeNo + '\'' +
                ", subGroupeId='" + subGroupeId + '\'' +
                '}';
    }
}
