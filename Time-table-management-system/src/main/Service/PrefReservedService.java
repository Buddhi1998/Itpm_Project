package main.service;

import main.model.Lecturer;
import main.model.PrefReserved;
import main.model.PrefReservedRoom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrefReservedService {
    boolean savePrefReservedRoom(PrefReserved prefRes) throws SQLException;

     ArrayList<PrefReservedRoom> getAllPrefRoomDetails() throws SQLException ;

    boolean deletePrefRoom(int id) throws SQLException ;



}
