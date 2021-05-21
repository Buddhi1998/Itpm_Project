package main.service;

import main.model.Department;
import main.model.Tag;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DepartmentService {
    boolean searchDepartment(String departmentName) throws SQLException;

    boolean saveDepartment(Department department) throws SQLException;

    boolean updateDepartment(Department department) throws SQLException;

    ArrayList<Department> getAllDetails() throws SQLException;

    String searchDepartmentName(int id) throws SQLException;

}
