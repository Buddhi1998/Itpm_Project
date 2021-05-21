package main.service;

import main.model.YearAndSemester;

import java.sql.SQLException;
import java.util.ArrayList;

public interface YearandSemesterService {
    boolean saveAcademiceYear(YearAndSemester yearAndSemester) throws SQLException;

    boolean searchYearAndSemester(String year, String semester) throws SQLException;

    ArrayList<YearAndSemester> getAllDetails() throws SQLException;

    boolean updateYearAndSemester(YearAndSemester semester) throws SQLException;

    public boolean deleteYearAndSemester(int key) throws SQLException;

    public String searchYearAndSemesterName(int id) throws SQLException;

}
