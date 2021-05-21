package main.service;

import main.model.SubGroup;
import main.model.SubGroupCount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SubGroupService {
    boolean saveDetails(SubGroup sub) throws SQLException;

    int subGroupCountAccordingToId(int mainid) throws SQLException;

    ArrayList<SubGroupCount> getAllSubGroupCount(int yearId) throws SQLException;

    ArrayList<SubGroup> getAllSubGroupDetails(int id) throws SQLException;

    boolean searchSubGroup(String updateGroupId) throws SQLException;

    boolean updateGroupNumber(SubGroup m) throws SQLException;

    boolean deleteSubGroup(int id) throws SQLException;

    int getMainGroup(int id) throws SQLException;


}
