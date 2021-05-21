package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Room;
import main.service.RoomService;

import java.sql.*;
import java.util.ArrayList;

public class RoomServiceImpl implements RoomService {

    private Connection connection;
    private static final String R_ROOM= "r.room";
    private static final String R_CAPACITY= "r.capacity";
    private static final String B_CENTER= "b.center";
    private static final String B_BUILDING= "b.building";

    public RoomServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveRooms(Room room) throws SQLException {
        String sql = "Insert into room Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, room.getBuildingId());
            stm.setObject(3, room.getRoom());
            stm.setObject(4, room.getCapacity());
            stm.setObject(5, room.getRoomType());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<Room> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = " Select r.rid, b.bid, r.room, r.capacity, r.roomType, b.center, b.building from building b ,room r where b.bid = r.buildingid";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Room> roomList = new ArrayList<>();
                while (rst.next()) {
                    Room roomRows = new Room(Integer.parseInt(rst.getString("r.rid")),
                            Integer.parseInt(rst.getString("b.bid")),
                            rst.getString(R_ROOM),
                            Integer.parseInt(rst.getString(R_CAPACITY)),
                            rst.getString(B_CENTER),
                            rst.getString(B_BUILDING),
                            rst.getString("roomType"));
                    roomList.add(roomRows);
                }
                return roomList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean deleteRoom(int key) throws SQLException {
        String sql = "Delete From room where rid = '" + key + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean updateRoomDetails(Room room12) throws SQLException {
        String sql = "Update room set " +
                "buildingid='" + room12.getBuildingId() + "', " +
                "room='" + room12.getRoom() + "',  " +
                "capacity='" + room12.getCapacity() + "',roomType='"+room12.getRoomType()+"' " +
                "where rid='" + room12.getRid() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean searchRoom(String building, String room) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select rid from room where building = '" + building + "' " +
                    "&& room='" + room + "'";
            stm = connection.createStatement();
            ArrayList<Room> roomsList = new ArrayList<>();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("rid") != null) {
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
    public ArrayList<Room> getAllRoomDetails() throws SQLException {


        Statement stm = null;
        try {
            String sql = "Select * from room r, building b";
            stm = connection.createStatement();
            ArrayList<Room> rooms = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    Room r1 = new Room(Integer.parseInt(rst.getString("r.rid")),
                            Integer.parseInt(rst.getString("b.bid")),
                            rst.getString(R_ROOM),
                            Integer.parseInt(rst.getString(R_CAPACITY)),
                            rst.getString(B_CENTER),
                            rst.getString(B_BUILDING));
                    rooms.add(r1);
                }
                return rooms;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public ArrayList<Room> getAllDetailsForSearch(String rbuilding, String rroom) throws SQLException {
        String buildingsql = "";
        String roomSql = "";
        Statement stm = null;
        try {
            if (rbuilding != null) {
                buildingsql = " and b.building LIKE '%" + rbuilding + "%'";
            }
            if (rroom != null) {
                roomSql = " and r.room LIKE '%" + rroom + "%'";
            }
            String sql = "select b.building, r.room, r.capacity " +
                    "from building b, room r " +
                    "where b.bid = r.buildingid " + buildingsql + " " + roomSql + " ";
            stm = connection.createStatement();
            ArrayList<Room> roomA = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    Room room = new Room(rst.getString(B_BUILDING),
                            rst.getString(R_ROOM),
                            Integer.parseInt(rst.getString(R_CAPACITY)));
                    roomA.add(room);
                }
                return roomA;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public ArrayList<Room> searchRoomDetailsByUsingbuilding(String building) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select r.rid, r.buildingid, r.room,r.capacity from room r, building b where b.bid = r.buildingid and b.building LIKE '%" + building + "%'";
            stm = connection.createStatement();
            ArrayList<Room> roomsList = new ArrayList<>();

            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    Room room = new Room(Integer.parseInt(rst.getString("r.rid")),
                            Integer.parseInt(rst.getString("r.buildingid")),
                            rst.getString(R_ROOM),
                            Integer.parseInt(rst.getString(R_CAPACITY)));
                    roomsList.add(room);
                }
                return roomsList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}
