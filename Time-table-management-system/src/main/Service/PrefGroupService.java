package main.service;

import main.model.PrefGroup;

import java.sql.SQLException;

public interface PrefGroupService {
    boolean savePrefGroupRoom(PrefGroup prefGroup) throws SQLException;

    int getGroupMainId(String group) throws SQLException;

    int getGroupSubId(String group) throws SQLException;
}
