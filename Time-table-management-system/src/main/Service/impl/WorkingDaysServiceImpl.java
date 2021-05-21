package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;
import main.model.WorkingdDaysAndHours;
import main.service.WorkingDaysService;

import java.sql.*;
import java.util.ArrayList;

public class WorkingDaysServiceImpl implements WorkingDaysService {

    private Connection connection;
    private static final String WORKING_ID = "workingId";
    public WorkingDaysServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int addWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException {
        String sql = "Insert into workingdaysmain Values(?,?,?)";
        PreparedStatement stm = null;
        Statement stm1 = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setObject(1, 0);
            stm.setObject(2, workingDaysMain.getType());
            stm.setObject(3, workingDaysMain.getNoOfDays());
            int res = stm.executeUpdate();
            int lastId = 0;
            if (res > 0){
                try{
                    String sql1 = "select workingId from workingdaysmain order by 1 desc limit 1";
                    stm1 = connection.createStatement();
                    try (ResultSet rst = stm1.executeQuery(sql1)) {
                        if (rst.next()) {
                            lastId = Integer.parseInt(rst.getString(WORKING_ID));
                        }
                    }
                }finally {
                    if (stm1 != null) {
                        stm1.close();
                    }
                }
            }
            return lastId;
        } finally {
            if(stm!=null){
                stm.close();
            }

        }


    }

    @Override
    public boolean addWorkingDaysSub(WorkingDaysSub workingDaysSub) throws SQLException {
        String sql = "Insert into workingdayssub Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, workingDaysSub.getWorkingId());
            stm.setObject(3, workingDaysSub.getWorkingday());
            stm.setObject(4, workingDaysSub.getWorkingTime());
            stm.setObject(5, workingDaysSub.getWorkingTimeSlotType());

            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<WorkingdDaysAndHours> getAllNoOfWorkingDays() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT * FROM workingdaysmain wdm , workingdayssub wds WHERE wdm.workingId = wds.workingId";
            stm = connection.createStatement();
            ArrayList<WorkingdDaysAndHours> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    WorkingdDaysAndHours workingdDaysAndHours = new WorkingdDaysAndHours(
                            Integer.parseInt(rst.getString(WORKING_ID)),
                            rst.getString("type"),
                            Integer.parseInt(rst.getString("noOfDays")),
                            rst.getInt("subId"),
                            rst.getString("workingDay"),
                            rst.getString("timeslot"),
                            rst.getString("timeslottype")
                    );
                    list.add(workingdDaysAndHours);
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
    public ArrayList<String> getWorkingDaysAccordingId(int workingId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select workingday from workingdayssub where workingId='" + workingId + "'";
            stm = connection.createStatement();
            ArrayList<String> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    String day = rst.getString("workingday");
                    list.add(day);
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
    public boolean deleteWorkingDay(int workingId) throws SQLException {
        String sql = "Delete From workingdaysmain where workingId = '" + workingId + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean deleteWorkingDaysfromSub(int updateId) throws SQLException {
        String sql = "Delete From workingdayssub where workingId = '" + updateId + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean updateNoOfWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException {
        String sql = "Update workingdaysmain set type='" + workingDaysMain.getType() + "'," +
                "noOfDays='" + workingDaysMain.getNoOfDays() + "' " +
                "where workingId='" + workingDaysMain.getWorkingId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<WorkingDaysSub> getAllSubDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from workingdayssub";
            stm = connection.createStatement();
            ArrayList<WorkingDaysSub> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    WorkingDaysSub m1 = new WorkingDaysSub();
                    m1.setSubId(Integer.parseInt(rst.getString("subId")));
                    m1.setWorkingday(rst.getString("workingday"));
                    m1.setWorkingId(Integer.parseInt(rst.getString(WORKING_ID)));
                    list.add(m1);
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
    public boolean deleteWorkingDaysSub(int id, int workingId) throws SQLException {
        String sql = "Delete From workingdayssub where subId = '" + id + "'";
        Statement stm = connection.createStatement();
        try {
            int delete = stm.executeUpdate(sql);
            int count = 0;
            int update = 0;
            if (delete > 0) {
                String sqlCount = "select count(workingday) from workingdayssub where workingId ='" + workingId + "' ";
                try (ResultSet rst = stm.executeQuery(sqlCount)) {
                    if (rst.next()) {
                        count = Integer.parseInt(rst.getString("count(workingday)"));
                    }
                }
                String sqlUpdate = "update workingdaysmain set noOfDays = '" + count + "' where workingId ='" + workingId + "'";
                update = stm.executeUpdate(sqlUpdate);
            }
            return update > 0;
        } finally {
            stm.close();
        }
    }


    @Override
    public boolean checkWeekDayOrWeekEndIsAdded(String selectedType) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select workingId from workingdaysmain where type = '" + selectedType + "' ";
            stm = connection.createStatement();
            ArrayList<WorkingDaysSub> list = new ArrayList<>();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString(WORKING_ID) != null) {
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

    @Override
    public int getCountOfWorkingDays() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select count(subId) from workingdayssub ";
            stm = connection.createStatement();
            ArrayList<WorkingDaysSub> list = new ArrayList<>();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("count(subId)") != null) {
                        result = Integer.parseInt(rst.getString("count(subId)"));
                    } else {
                        result = 0;
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

    @Override
    public double getWorkingTime() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select timeslot from workingdayssub";
            stm = connection.createStatement();
            double result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = Double.parseDouble(rst.getString("timeslot"));

                }
            }
            return result;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public String getWorkingTimeType() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select timeslottype from workingdayssub ORDER BY timeslottype DESC LIMIT 1";
            stm = connection.createStatement();
            String result = " ";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString("timeslottype");

                }
            }
            return result;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<WorkingdDaysAndHours> getAllWorkingDetailsByWorkID(int workingId) throws SQLException {

        Statement stm = null;
        try {
            String sql = "SELECT * FROM workingdaysmain wdm , workingdayssub wds WHERE wdm.workingId = wds.workingId && wds.workingId = '"+workingId+"'";
            stm = connection.createStatement();
            ArrayList<WorkingdDaysAndHours> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    WorkingdDaysAndHours workingdDaysAndHours = new WorkingdDaysAndHours(
                            Integer.parseInt(rst.getString(WORKING_ID)),
                            rst.getString("type"),
                            Integer.parseInt(rst.getString("noOfDays")),
                            rst.getInt("subId"),
                            rst.getString("workingDay"),
                            rst.getString("timeslot"),
                            rst.getString("timeslottype")
                    );
                    list.add(workingdDaysAndHours);
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
    public boolean updateNoOfWorkingDaySub(WorkingDaysSub workingDaysSub) throws SQLException {
        String sql = "Update workingdayssub set workingday ='" + workingDaysSub.getWorkingday() + "'," +
                "startTime='" + workingDaysSub.getWorkingTime() +"'" +
                "where workingId='" + workingDaysSub.getWorkingId() + "'";


        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean getWorkingDaysCanAddMore() throws Exception {
        Statement stm = null;
        try {
            String sql = "select count(DISTINCT(workingId)) AS workID from workingdaysmain";
            stm = connection.createStatement();
            ArrayList<WorkingDaysSub> list = new ArrayList<>();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getInt("workID") != 1) {
                        result = true;
                    }else {
                        result = false;
                    }
                    if(rst.getInt("workID") == 0){
                        result = false;
                    }
                    if(rst.getInt("workID") == 1){
                        result = true;
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
