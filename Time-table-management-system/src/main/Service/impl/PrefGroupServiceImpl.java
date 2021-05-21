package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefGroup;
import main.service.PrefGroupService;

import java.sql.*;


public class PrefGroupServiceImpl implements PrefGroupService {

    private Connection connection;

    public PrefGroupServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean savePrefGroupRoom(PrefGroup prefGroup) throws SQLException {

        String sql = "Insert into prefroomgroup Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            if (prefGroup.getGroupId() == 0) {
                stm.setObject(2, null);
            } else {
                stm.setObject(2, prefGroup.getGroupId());
            }
            if (prefGroup.getSubGroupId() == 0) {
                stm.setObject(3, null);
            } else {
                stm.setObject(3, prefGroup.getSubGroupId());
            }

            stm.setObject(1, 0);
            stm.setObject(4, prefGroup.getRoomId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public int getGroupMainId(String group) throws SQLException {

        Statement stm = null;
        try {
            String sql = "Select id from maingroup where groupid LIKE '%" + group + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result = 0;
                if (rst.next()) {
                    result = rst.getInt("id");
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
    public int getGroupSubId(String group) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select id from subgroup where subgroupid LIKE '%" + group + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result = 0;
                if (rst.next()) {
                    result = rst.getInt("id");
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
