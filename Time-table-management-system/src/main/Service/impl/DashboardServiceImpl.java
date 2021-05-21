package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Dashboard;
import main.model.Dashboard2;
import main.service.DashboardService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DashboardServiceImpl implements DashboardService {

    private Connection connection;

    public DashboardServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public ArrayList<Dashboard> getBuildingCount() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select center,count(building) AS NoOfBuildings from building group by center order by center ASC";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Dashboard> noBuildingList = new ArrayList<>();
                while (rst.next()) {
                    Dashboard dashboard = new Dashboard();
                    dashboard.setCenter(rst.getString("center"));
                    dashboard.setNoOfBuildings(Integer.parseInt(rst.getString("NoOfBuildings")));
                    noBuildingList.add(dashboard);
                }
                return noBuildingList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Dashboard> getEmployeeCount() throws SQLException {
        Statement stm = null;
        try {
            String sql= "select faculty,count(employeeId) AS NoOfEmployees from lecturer group by faculty order by faculty ASC";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Dashboard> noEmpList = new ArrayList<>();
                while (rst.next()) {
                    Dashboard dashboard = new Dashboard();
                    dashboard.setFaculty(rst.getString("faculty"));
                    dashboard.setNoOfEmployees(Integer.parseInt(rst.getString("NoOfEmployees")));
                    noEmpList.add(dashboard);
                }
                return noEmpList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Dashboard2> getDesignationCount() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select designation,count(designation) AS NoOfdesignation from lecturer group by designation order by designation ASC";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Dashboard2> noDesList = new ArrayList<>();
                while (rst.next()) {
                    Dashboard2 dashboard2 = new Dashboard2();
                    dashboard2.setDesignation(rst.getString("designation"));
                    dashboard2.setNoOfDesig(Integer.parseInt(rst.getString("NoOfdesignation")));
                    noDesList.add(dashboard2);
                }
                return noDesList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<Dashboard2> getSubjects() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select a.fullName,count(s.subId) AS NoOfSubjects from subject s, academicyearandsemester a where s.offeredYearSemId = a.id group by offeredYearSemId";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Dashboard2> noSubList = new ArrayList<>();
                while (rst.next()) {
                    Dashboard2 dashboard2 = new Dashboard2();
                    dashboard2.setYearSem(rst.getString("a.fullName"));
                    dashboard2.setNoOfSubjects(Integer.parseInt(rst.getString("NoOfSubjects")));

                    noSubList.add(dashboard2);
                }
                return noSubList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getCountOfLectures() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT COUNT(lecturer.employeeId) FROM lecturer";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
              int count = 0;
                while (rst.next()) {

                    int nooflectures = rst.getInt(1);
                    count =  nooflectures;
                }
                return count;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getCountOfSubjects() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT COUNT(subject.subId) FROM subject";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int count = 0;
                while (rst.next()) {

                    int noofsubjects = rst.getInt(1);
                    count =  noofsubjects;
                }
                return count;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getCountOfBuildings() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT COUNT(room.rid)  FROM room";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int count = 0;
                while (rst.next()) {

                    int noofrooms = rst.getInt(1);
                    count =  noofrooms;
                }
                return count;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getCountOfRooms() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT COUNT(building.bid)  FROM building";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int count = 0;
                while (rst.next()) {

                    int noofbuildings = rst.getInt(1);
                    count = noofbuildings;
                }
                return count;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}
