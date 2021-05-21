package main.service;

import main.model.Building;
import main.model.Dashboard;
import main.model.Dashboard2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DashboardService {

    ArrayList<Dashboard> getBuildingCount() throws SQLException;

    ArrayList<Dashboard> getEmployeeCount() throws SQLException;

    ArrayList<Dashboard2> getDesignationCount() throws SQLException;

    ArrayList<Dashboard2> getSubjects() throws SQLException;

    int getCountOfLectures() throws SQLException;

    int getCountOfSubjects() throws SQLException;

    int getCountOfBuildings() throws SQLException;

    int getCountOfRooms() throws SQLException;
}
