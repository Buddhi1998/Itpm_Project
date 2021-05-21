package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefTag;
import main.model.Tag;
import main.service.PrefTagService;

import java.sql.*;
import java.util.ArrayList;

public class PrefTagServiceImpl implements PrefTagService {

    private Connection connection;
    public PrefTagServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public int getRoomId(String center, String building, String room) throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select r.rid from building b ,room r where b.bid = r.buildingid and b.center LIKE '%" + center + "%' " +
                    " and b.building LIKE '%" + building + "%' and r.room LIKE '%" + room + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Tag> tagList = new ArrayList<>();
                int result=0;
                if(rst.next()){
                    result = rst.getInt("r.rid");
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
    public int getTagIdFromTags(String tag) throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select tagid from tag where tagName LIKE '%" + tag + "'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Tag> tagList = new ArrayList<>();
                int result=0;
                if(rst.next()){
                    result = rst.getInt("tagid");
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
    public boolean savePrefTagRoom(PrefTag prefTag) throws SQLException {
        String sql = "Insert into prefroomtag Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefTag.getTagId());
            stm.setObject(3, prefTag.getRoomId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

}
