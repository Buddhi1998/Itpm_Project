package main.service;

import main.model.WorkingHoursPerDay;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkingHoursService {

    public boolean saveWorkingHours(WorkingHoursPerDay workingHours) throws SQLException;

    public ArrayList<WorkingHoursPerDay> getAllWorkingHours() throws SQLException;

    public boolean updateWorkingHours(WorkingHoursPerDay workingHours) throws SQLException;

    public boolean deleteWorkingHours(int key) throws SQLException;

    boolean checkHoursAdded(String selectedType) throws SQLException;

}
