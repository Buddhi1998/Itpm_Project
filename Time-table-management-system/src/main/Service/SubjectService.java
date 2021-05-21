package main.service;




import main.model.Subject;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SubjectService {
    public boolean saveSubject(Subject subject) throws SQLException;
    public ArrayList<Subject> getAllSubjectDetails() throws SQLException ;
    public void deleteSubjectDetails(String id) throws SQLException ;
    public boolean updateSubject(Subject subject) throws SQLException ;
    public ArrayList<Subject> searchSubjectDetails(String name) throws SQLException ;
    public Subject getCategory(String id) throws SQLException;
}
