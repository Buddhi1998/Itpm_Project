package main.model;

public class Subject {
    String subId;
    String subName;
    int offeredYearSem;
    int noLecHrs;
    int noTutHrs;
    int noEvalHrs;
    int noLabHrs;
    String yearSem;
    String subType;
    String category;

    public Subject(String subType, String category) {
        this.subType = subType;
        this.category = category;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Subject(String subId, String subName, int offeredYearSem, int noLecHrs, int noTutHrs, int noEvalHrs,  String subType, String category) {
        this.subId = subId;
        this.subName = subName;
        this.offeredYearSem = offeredYearSem;
        this.noLecHrs = noLecHrs;
        this.noTutHrs = noTutHrs;
        this.noEvalHrs = noEvalHrs;
        this.subType = subType;
        this.category = category;
    }

    public Subject(String subId, String subName, int offeredYearSem, int noLecHrs, int noTutHrs, int noEvalHrs) {
        this.subId = subId;
        this.subName = subName;
        this.offeredYearSem = offeredYearSem;
        this.noLecHrs = noLecHrs;
        this.noTutHrs = noTutHrs;
        this.noEvalHrs = noEvalHrs;
    }

    public Subject(String subId, String subName, int offeredYearSem, int noLecHrs, int noTutHrs, int noEvalHrs, int noLabHrs, String subType, String category) {
        this.subId = subId;
        this.subName = subName;
        this.offeredYearSem = offeredYearSem;
        this.noLecHrs = noLecHrs;
        this.noTutHrs = noTutHrs;
        this.noEvalHrs = noEvalHrs;
        this.noLabHrs = noLabHrs;
        this.subType = subType;
        this.category = category;
    }

    public Subject() {
    }

    public String getYearSem() {
        return yearSem;
    }

    public void setYearSem(String yearSem) {
        this.yearSem = yearSem;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getOfferedYearSem() {
        return offeredYearSem;
    }

    public void setOfferedYearSem(int offeredYearSem) {
        this.offeredYearSem = offeredYearSem;
    }

    public int getNoLecHrs() {
        return noLecHrs;
    }

    public void setNoLecHrs(int noLecHrs) {
        this.noLecHrs = noLecHrs;
    }

    public int getNoTutHrs() {
        return noTutHrs;
    }

    public void setNoTutHrs(int noTutHrs) {
        this.noTutHrs = noTutHrs;
    }

    public int getNoEvalHrs() {
        return noEvalHrs;
    }

    public void setNoEvalHrs(int noEvalHrs) {
        this.noEvalHrs = noEvalHrs;
    }

    public int getNoLabHrs() {
        return noLabHrs;
    }

    public void setNoLabHrs(int noLabHrs) {
        this.noLabHrs = noLabHrs;
    }
}
