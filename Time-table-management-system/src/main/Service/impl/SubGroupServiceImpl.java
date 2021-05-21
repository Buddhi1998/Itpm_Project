package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.SubGroup;
import main.model.SubGroupCount;
import main.service.SubGroupService;

import java.sql.*;
import java.util.ArrayList;

public class SubGroupServiceImpl implements SubGroupService {

    private Connection connection;
    private static final String MAIN_DROUPID = "maingroupid";
    public SubGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveDetails(SubGroup sub) throws SQLException {
        String sql = "Insert into subgroup Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, sub.getSubgroupid());
            stm.setObject(3, sub.getSubgroupnumber());
            stm.setObject(4, sub.getMaingroupid());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public int subGroupCountAccordingToId(int mainid) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select count(id) from subgroup where maingroupid ='" + mainid + "' group by maingroupid";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result = 0;
                if (rst.next()) {
                    if (rst.getString("count(id)") != null) {
                        result = Integer.parseInt(rst.getString("count(id)"));
                    } else {
                        result = 0;
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
    public ArrayList<SubGroupCount> getAllSubGroupCount(int mainGroupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "";
            if (mainGroupId != 0) {
                sql = " and s.maingroupid ='" + mainGroupId + "' ";
            }
            String sql1 = "select count(s.id),m.groupid from subgroup s,maingroup m " +
                    " where s.maingroupid=m.id " + sql +
                    " group by maingroupid";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql1)) {
                ArrayList<SubGroupCount> main = new ArrayList<>();
                while (rst.next()) {
                    SubGroupCount subGroup = new SubGroupCount();
                    subGroup.setGroupId(rst.getString("groupid"));
                    subGroup.setSubGroupCount(Integer.parseInt(rst.getString("count(s.id)")));
                    main.add(subGroup);
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
    public ArrayList<SubGroup> getAllSubGroupDetails(int id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "";
            if (id != 0) {
                sql = " where subgroupnumber Like '%" + id + "%'";
            }
            String sql1 = "Select * from subgroup " + sql;
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql1)) {
                ArrayList<SubGroup> subGroupList = new ArrayList<>();
                while (rst.next()) {
                    SubGroup s = new SubGroup(Integer.parseInt(rst.getString("id")),
                            rst.getString("subgroupid"),
                            Integer.parseInt(rst.getString("subgroupnumber")),
                            Integer.parseInt(rst.getString(MAIN_DROUPID)));
                    subGroupList.add(s);
                }
                return subGroupList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean searchSubGroup(String updateGroupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select id from subgroup where subgroupid = '" + updateGroupId + "'";
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
    public boolean updateGroupNumber(SubGroup m) throws SQLException {
        String sql = "Update subgroup set subgroupnumber='" + m.getSubgroupnumber() + "',subgroupid='" + m.getSubgroupid() + "' where id='" + m.getId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean deleteSubGroup(int id) throws SQLException {
        String sql = "Delete From subgroup where id = '" + id + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public int getMainGroup(int id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select maingroupid from subgroup where id='\"+id+\"'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result = 0;
                if (rst.getString(MAIN_DROUPID) != null) {
                    result = Integer.parseInt(rst.getString(MAIN_DROUPID));
                } else {
                    result = 0;
                }
                return result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }
}
