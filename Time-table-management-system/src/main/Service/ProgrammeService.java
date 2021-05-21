package main.service;

import main.model.Programme;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProgrammeService {

    public boolean saveProgramme(Programme programme) throws SQLException;

    public boolean searchProgramme(String name) throws SQLException;

    public ArrayList<Programme> getAllDetails() throws SQLException;

    public boolean updateProgramme(Programme programme) throws SQLException;

    public boolean deleteProgramme(int key) throws SQLException;

}
