package main.service;

import main.model.Building;
import main.model.Lecturer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BuildingService {


    public ArrayList<Building> searchBuildingDetailsByCenter(String name) throws SQLException;
    public String searchBuildingName(int id) throws SQLException;

    public boolean saveBuildings(Building building) throws SQLException;

    public boolean searchBuilding(String center, String building) throws SQLException;

    ArrayList<Building> getAllDetails() throws SQLException;

    public boolean deleteBuilding(int key) throws SQLException;

    boolean updateBuildingDetails(Building building12) throws SQLException;


    ArrayList<Building> searchBuildingDetailsByUsingCenter(String center) throws SQLException;

    ArrayList<Building> getAllAllRoomDetails() throws SQLException;

    public ArrayList<Building> getAllDetailsForSearch(String bcenter, String bbuilding) throws SQLException;

    Building searchBuilding(int buildingID)throws SQLException;

}


