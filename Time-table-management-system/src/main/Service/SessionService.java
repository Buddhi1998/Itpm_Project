package main.service;

import main.model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SessionService {

    int searchSession(int lecId, String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException;

    int searchSessionByDetails( String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException;

    boolean saveDetails(NotAvailableSession nas) throws SQLException;

    ArrayList<ConsectiveSession> getAllConsectiveSessions(String lecturer,String subject) throws SQLException;

    int getSessionIdForConsectiveSession(String groupId, String subject, String tagName) throws SQLException;

    boolean updateRowForConsectiveSession(int id) throws SQLException;

    boolean saveCosectiveSession(int id, int id1) throws SQLException;

    boolean addSession(Session s1) throws SQLException;

    boolean addLectureSession(int lecturerId,int sessionId) throws SQLException;

    ArrayList<SessionDTO> getAllSessions() throws SQLException;

    ArrayList<SessionDTO> searchSessions(String id) throws SQLException;

    ArrayList<SessionTagGroup> getSessionsAccordingToMainGroupId(String groupId) throws SQLException;

    ArrayList<SessionTagGroup> getParallelSessionsAccordingToMainGroupId(String trim) throws SQLException;

    ArrayList<SessionTagGroup> getParallelSessionsAccordingOrderId(String orderId) throws SQLException;

    ArrayList<AllNotAvailableSession> getAllNotAvailableSessions() throws SQLException;
}
