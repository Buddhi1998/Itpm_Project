package main.service;

import main.model.PrefTag;

import java.sql.SQLException;

public interface PrefTagService {

    int getRoomId(String center, String building, String room) throws SQLException;

    int getTagIdFromTags(String tag) throws SQLException;


    boolean savePrefTagRoom(PrefTag prefTag) throws SQLException;
}
