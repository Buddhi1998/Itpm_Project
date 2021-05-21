package main.service;

import main.model.AllGroupDetail;
import main.model.MainGroup;
import main.model.MainGroupCount;
import main.model.NotAvailableGroup;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface MainGroupService {

    public boolean saveMainGroupId(MainGroup mainGroup) throws SQLException;

    int getCountAccordingToName(String s)throws SQLException;

    ArrayList<MainGroupCount> getAllGroupCount(int yearAndSem, int programme) throws SQLException;

    ArrayList<MainGroup> getAllGroupDetails(int id)throws  SQLException;

    boolean searchMainGroup(String newGroupId) throws SQLException;

    boolean updateGroupNumber(MainGroup m) throws SQLException;

    ArrayList<MainGroup> getAllMainGroupDetails() throws SQLException;

    boolean deleteMainGroup(int id) throws SQLException;

    boolean addNotAvailableGroup(NotAvailableGroup nag) throws SQLException, ParseException;

    ArrayList<NotAvailableGroup> getAllNotAvailableGroupDetails(String groupId) throws SQLException;

    boolean deleteNotAvailableGroupId(int id) throws SQLException;

    ArrayList<AllGroupDetail> getAllGroupDetails() throws SQLException;
}
