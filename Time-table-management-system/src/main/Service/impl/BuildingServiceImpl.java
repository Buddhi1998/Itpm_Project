package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Building;
import main.service.BuildingService;

import java.sql.*;
import java.util.ArrayList;

public class BuildingServiceImpl implements BuildingService {

    public static final String BUILDING = "building";
    public static final String CENTER = "center";
    private Connection connection;
    String buldingName;

    public BuildingServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public ArrayList<Building> searchBuildingDetailsByCenter(String center) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from building where center LIKE '%" + center + "%'";
            stm = connection.createStatement();
            ArrayList<Building> buildingsList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    while (rst.next()) {
                        Building building = new Building(Integer.parseInt(rst.getString("bid")),
                                rst.getString(BUILDING), rst.getString(CENTER));
                        buildingsList.add(building);
                    }
                }
            }
            return buildingsList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public String searchBuildingName(int id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select building  from building where bId = '" + id + "' ";

            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {

                while (rst.next()) {

                    buldingName = rst.getString(BUILDING);
                }
            }

            return buldingName;

        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }


    @Override
    public boolean saveBuildings(Building building) throws SQLException {

        String sql = "Insert into building Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, building.getCenter());
            stm.setObject(3, building.getBuilding());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean searchBuilding(String center, String building) throws SQLException {

        Statement stm = null;
        try {
            String sql = "select bid from building where center = '" + center + "' " +
                    "&& building='" + building + "'";
            stm = connection.createStatement();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("bid") != null) {
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
    public ArrayList<Building> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from building";
            stm = connection.createStatement();
            ArrayList<Building> buildingList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    Building buildingRows = new Building(Integer.parseInt(rst.getString("bid")),
                            rst.getString(BUILDING),
                            rst.getString(CENTER));
                    buildingList.add(buildingRows);
                }
            }
            return buildingList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean deleteBuilding(int key) throws SQLException {
        String sql = "Delete From building where bid = '" + key + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean updateBuildingDetails(Building building12) throws SQLException {
        String sql = "Update building set center='" + building12.getCenter() + "'," +
                "building='" + building12.getBuilding() + "'  " +
                "where bid='" + building12.getBid() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public ArrayList<Building> searchBuildingDetailsByUsingCenter(String center) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from building where center LIKE '%" + center + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Building> buildingsList = new ArrayList<>();
                while (rst.next()) {
                    Building building = new Building(Integer.parseInt(rst.getString("bid")),
                            rst.getString(BUILDING),
                            rst.getString(CENTER));
                    buildingsList.add(building);
                }
                return buildingsList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<Building> getAllAllRoomDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select  * from building";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Building> buildings = new ArrayList<>();
                while (rst.next()) {
                    Building r1 = new Building(Integer.parseInt(rst.getString("bid")),
                            rst.getString(BUILDING),
                            rst.getString(CENTER));
                    buildings.add(r1);
                }
                return buildings;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Building> getAllDetailsForSearch(String bcenter, String bbuilding) throws SQLException {


        String buildingsql = "";
        String centerSql = "";
        Statement stm = null;
        String sql = "";
        try {
            stm = connection.createStatement();
            if (bbuilding == null && bcenter == null) {
                sql = "select * " +
                        "from building ";
            }
            if (bbuilding != null) {
                buildingsql = " building LIKE '%" + bbuilding + "%'";
                sql = "select * " +
                        "from building " +
                        "where " + buildingsql;

            }
            if (bcenter != null) {
                centerSql = " center LIKE '%" + bcenter + "%'";
                sql = "select * from building where " + centerSql;
            }
            if (bbuilding != null && bcenter != null) {
                sql = "select * from building where building LIKE '%" + bbuilding + "%' and center LIKE '%" + bcenter + "%'";
            }
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Building> buildingA = new ArrayList<>();
                while (rst.next()) {
                    Building building = new Building(Integer.parseInt(rst.getString("bid")),
                            rst.getString(BUILDING),
                            rst.getString(CENTER));
                    buildingA.add(building);
                }
                return buildingA;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public Building searchBuilding(int buildingID) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from building WHERE bid ='" + buildingID + "'";
            stm = connection.createStatement();
            Building building = null;
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    building = new Building(rst.getInt("bid"),
                            rst.getString(BUILDING),
                            rst.getString(CENTER));
                }
            }
            return building;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}
