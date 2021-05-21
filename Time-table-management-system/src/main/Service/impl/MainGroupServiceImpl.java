package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.AllGroupDetail;
import main.model.MainGroup;
import main.model.MainGroupCount;
import main.model.NotAvailableGroup;
import main.service.MainGroupService;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class MainGroupServiceImpl implements MainGroupService {

    private Connection connection;

    public MainGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public boolean saveMainGroupId(MainGroup mainGroup) throws SQLException {
        String sql = "Insert into maingroup Values(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, mainGroup.getMgroupName());
            stm.setObject(3, mainGroup.getGroupNumber());
            stm.setObject(4, mainGroup.getGroupid());
            stm.setObject(5, mainGroup.getProgrammeid());
            stm.setObject(6, mainGroup.getSemid());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public int getCountAccordingToName(String s) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select count(id) from maingroup where mgroupName ='" + s + "' group by mgroupName";
            stm = connection.createStatement();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("count(id)") != null) {
                        result = Integer.parseInt(rst.getString("count(id)"));
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
    public ArrayList<MainGroupCount> getAllGroupCount(int yearAndSem, int programme) throws SQLException {

        Statement stm = null;
        try {
            String yearSemSql = "";
            String programmeSql = "";
            if (yearAndSem != 0) {
                yearSemSql = " and m.semid LIKE '%" + yearAndSem + "%'";
            }
            if (programme != 0) {
                programmeSql = " and m.programmeid LIKE '%" + programme + "%'";
            }
            String sql = "select count(m.id),ANY_VALUE(a.fullName) AS fullName,ANY_VALUE(p.programmeName) AS programmeName " +
                    "from maingroup m,programme p,academicyearandsemester a " +
                    "where m.programmeid=p.programmeid and m.semid=a.id " + yearSemSql + " " + programmeSql + " " +
                    "group by mgroupName";
            stm = connection.createStatement();
            ArrayList<MainGroupCount> main = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    MainGroupCount mainGroupCount = new MainGroupCount
                            (rst.getString("fullName"),
                            rst.getString("programmeName"),
                            rst.getString("count(m.id)"));
                    main.add(mainGroupCount);
                }
            }
            return main;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<MainGroup> getAllGroupDetails(int id) throws SQLException {
        Statement stm = null;
        try {
            String idSql = "";
            if (id != 0) {
                idSql = " where m.groupNumber LIKE '%" + id + "%'";
            }
            String sql = "Select id,groupNumber,groupid from maingroup m " + idSql;
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<MainGroup> main = new ArrayList<>();
                while (rst.next()) {
                    MainGroup mainGroup = new MainGroup();
                    mainGroup.setId(Integer.parseInt(rst.getString("id")));
                    mainGroup.setGroupNumber(Integer.parseInt(rst.getString("groupNumber")));
                    mainGroup.setGroupid(rst.getString("groupid"));
                    main.add(mainGroup);
                }
                return main;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean searchMainGroup(String newGroupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select id from maingroup where groupid = '" + newGroupId + "'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                boolean result = false;
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
                return result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean updateGroupNumber(MainGroup m) throws SQLException {
        String sql = "Update maingroup set groupNumber='" + m.getGroupNumber() + "',groupid='" + m.getGroupid() + "' " +
                "where id='" + m.getId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public ArrayList<MainGroup> getAllMainGroupDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select * from maingroup ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<MainGroup> main = new ArrayList<>();
                while (rst.next()) {
                    MainGroup mainGroup = new MainGroup();
                    mainGroup.setId(Integer.parseInt(rst.getString("id")));
                    mainGroup.setGroupid(rst.getString("groupid"));
                    main.add(mainGroup);
                }
                return main;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean deleteMainGroup(int id) throws SQLException {

        String sql = "Delete From maingroup where id = '" + id + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean addNotAvailableGroup(NotAvailableGroup nag) throws SQLException {
        String sql = "Insert into notavailablegroup Values(?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            if (nag.getSubGroupId() != 0) {
                stm.setObject(6, nag.getSubGroupId());
            } else {
                stm.setObject(6, null);
            }

            if (nag.getMainGroupId() != 0) {
                stm.setObject(7, nag.getMainGroupId());
            } else {
                stm.setObject(7, null);
            }
            stm.setObject(1, 0);
            stm.setObject(2, nag.getDay());
            stm.setObject(3, LocalTime.parse(nag.getToTime()));
            stm.setObject(4, LocalTime.parse(nag.getFromTime()));
            stm.setObject(5, nag.getGroupId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<NotAvailableGroup> getAllNotAvailableGroupDetails(String groupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "";
            if (groupId.isEmpty()) {
                sql = "select * from notavailablegroup ";
            } else {
                sql = "select * from notavailablegroup where groupId LIKE '%" + groupId + "%' ";
            }
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<NotAvailableGroup> main = new ArrayList<>();
                while (rst.next()) {
                    NotAvailableGroup nag = new NotAvailableGroup();
                    nag.setId(Integer.parseInt(rst.getString("id")));
                    nag.setGroupId(rst.getString("groupId"));
                    nag.setToTime(rst.getString("toTime"));
                    nag.setFromTime(rst.getString("fromTime"));
                    nag.setDay(rst.getString("day"));
                    main.add(nag);
                }
                return main;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean deleteNotAvailableGroupId(int id) throws SQLException {
        String sql = "Delete From notavailablegroup where id = '" + id + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<AllGroupDetail> getAllGroupDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT \n" +
                    "ANY_VALUE(ayas.fullName) AS fullName,\n" +
                    "ANY_VALUE(p.programmeName) AS programmeName,\n" +
                    "ANY_VALUE(mg.mgroupName) AS mgroupName,\n" +
                    "ANY_VALUE(mg.groupNumber) AS groupNumber,\n" +
                    "ANY_VALUE(mg.groupid) AS groupid,\n" +
                    "ANY_VALUE(sg.subgroupnumber) AS subgroupnumber,\n" +
                    "ANY_VALUE(sg.subgroupid) AS subgroupid\n" +
                    "  FROM academicyearandsemester ayas ,maingroup mg ,\n" +
                    "subgroup sg , programme p  WHERE \n" +
                    "(ayas.id = mg.semid AND p.programmeid = mg.programmeid ) GROUP BY sg.id";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<AllGroupDetail> main = new ArrayList<>();
                while (rst.next()) {
                    AllGroupDetail details = new AllGroupDetail();

                    details.setAccdamicYearAndSemester(rst.getString("fullName"));
                    details.setProgramme(rst.getString("programmeName"));
                    details.setGroupeName(rst.getString("mgroupName"));
                    details.setGroupeNumber(rst.getString("groupNumber"));
                    details.setGroupeId(rst.getString("groupid"));
                    details.setSubGroupeNo(rst.getString("subgroupnumber"));
                    details.setSubGroupeId(rst.getString("subgroupid"));
                    main.add(details);
                }
                return main;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}
