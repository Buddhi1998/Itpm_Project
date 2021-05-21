package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.WorkingHoursPerDay;
import main.service.WorkingHoursService;

import java.sql.*;
import java.util.ArrayList;

public class WorkingHoursImpl implements WorkingHoursService {

    private Connection connection;

    public WorkingHoursImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveWorkingHours(WorkingHoursPerDay workingHours) throws SQLException {
        String sql = "Insert into workinghoursperday Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, workingHours.getWorkingTime());
            stm.setObject(3, workingHours.getTimeSlot());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<WorkingHoursPerDay> getAllWorkingHours() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from workinghoursperday";
            stm = connection.createStatement();
            ArrayList<WorkingHoursPerDay> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    WorkingHoursPerDay workingHoursPerDay = new WorkingHoursPerDay(Integer.parseInt(rst.getString("whpId")),
                            rst.getString("workingTime"),
                            rst.getString("timeSlot")
                    );
                    list.add(workingHoursPerDay);
                }
            }
            return list;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean updateWorkingHours(WorkingHoursPerDay workingHours) throws SQLException {
        String sql = "Update workinghoursperday set workingTime='" + workingHours.getWorkingTime() + "'" +
                ",timeSlot='" + workingHours.getTimeSlot() + "' where " +
                " whpId='" + workingHours.getWhpId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean deleteWorkingHours(int key) throws SQLException {
        String sql = "Delete From workinghoursperday where whpId = '" + key + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean checkHoursAdded(String selectedType) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select workingId from workingdaysmain where type = '" + selectedType + "' ";
            stm = connection.createStatement();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("workingId") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            return result;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

}
