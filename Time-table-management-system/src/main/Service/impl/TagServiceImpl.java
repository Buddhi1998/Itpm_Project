package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Tag;
import main.service.TagService;

import java.sql.*;
import java.util.ArrayList;


public class TagServiceImpl implements TagService {

    private Connection connection;

    public TagServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveTag(Tag tag) throws SQLException {
        String sql = "Insert into tag Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, tag.getTagName());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean searchTag(String name) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select tagid from tag where tagName = '" + name + "' ";
            stm = connection.createStatement();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("tagid") != null) {
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
    public ArrayList<Tag> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from tag";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Tag> tagList = new ArrayList<>();
                while (rst.next()) {
                    Tag t = new Tag(Integer.parseInt(rst.getString("tagid")), rst.getString("tagName"));
                    tagList.add(t);

                }
                return tagList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean updateTag(Tag tag) throws SQLException {
        String sql = "Update tag set tagName='" + tag.getTagName() + "' where tagid='" + tag.getTagId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean deleteTag(int key) throws SQLException {
        String sql = "Delete From tag where tagid = '" + key + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }
}
