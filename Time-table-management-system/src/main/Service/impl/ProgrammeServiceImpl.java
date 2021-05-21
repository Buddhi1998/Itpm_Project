package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Programme;
import main.service.ProgrammeService;

import java.sql.*;
import java.util.ArrayList;

public class ProgrammeServiceImpl implements ProgrammeService {

    private Connection connection;

    public ProgrammeServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }
    @Override
    public boolean saveProgramme(Programme programme) throws SQLException {
        String sql = "Insert into programme Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, programme.getProgrammeName());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean searchProgramme(String name) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select programmeid from programme where programmeName = '" + name + "' ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                boolean result = false;
                if (rst.next()) {
                    if (rst.getString("programmeid") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
                return result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Programme> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from programme";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Programme> programmes = new ArrayList<>();
                while(rst.next()){
                    Programme pr = new Programme(Integer.parseInt(rst.getString("programmeid")),rst.getString("programmeName"));
                    programmes.add(pr);
                }
                return programmes;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public boolean updateProgramme(Programme programme) throws SQLException {
        String sql="Update programme set programmeName='"+programme.getProgrammeName()+"' where programmeid='"+programme.getProgrammeId()+"'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean deleteProgramme(int key) throws SQLException {
        String sql = "Delete From programme where programmeid = '"+key+"'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }
}
