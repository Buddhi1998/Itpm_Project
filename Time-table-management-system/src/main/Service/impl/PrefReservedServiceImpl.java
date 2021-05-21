package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Lecturer;
import main.model.PrefReserved;
import main.model.PrefReservedRoom;
import main.service.PrefReservedService;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class PrefReservedServiceImpl implements PrefReservedService {

    private Connection connection;
    public PrefReservedServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean savePrefReservedRoom(PrefReserved prefRes) throws SQLException {
        String sql = "Insert into prefroomreserved Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefRes.getRoomId());
            stm.setObject(3, prefRes.getDay());
            stm.setObject(4, LocalTime.parse(prefRes.getToTime()));
            stm.setObject(5, LocalTime.parse(prefRes.getFromTime()));

            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<PrefReservedRoom> getAllPrefRoomDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "SELECT * FROM prefroomreserved p , room r , building b WHERE b.bid = r.buildingid\n" +
                    "&& r.rid = p.roomId";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<PrefReservedRoom> prefReservedArrayList = new ArrayList<>();
                while (rst.next()) {
                    PrefReservedRoom prefReserved = new PrefReservedRoom();

                    prefReserved.setId(rst.getInt("id"));
                    prefReserved.setDay(rst.getString("day"));
                    prefReserved.setRoomId(rst.getInt("roomId"));
                    prefReserved.setFromTime(rst.getTime("fromTime"));
                    prefReserved.setToTime(rst.getTime("toTime"));
                    prefReserved.setRoom(rst.getString("room"));
                    prefReserved.setRoomType(rst.getString("roomType"));
                    prefReserved.setCenter(rst.getString("center"));
                    prefReserved.setBuilding(rst.getString("building"));
                    prefReservedArrayList.add(prefReserved);

                }
                return prefReservedArrayList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean deletePrefRoom(int id) throws SQLException {
        String sql = "delete from prefroomreserved where id = '"+id+"'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }


}
