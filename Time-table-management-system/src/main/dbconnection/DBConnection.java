package main.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private Connection conn;
    private static DBConnection dbConnection;
    public static final Logger log = Logger.getLogger(DBConnection.class.getName());


    private static final String P_WORD ="timetable";
    private static final String USER ="timetable123";
    private static final String SERVER ="www.db4free.net";
    private static final String PORT ="3306";
    private static final String DATABASE ="timetablemng";

//    For Localhost
//    private static final String P_WORD ="1234";
//    private static final String USER ="root";
//    private static final String SERVER ="localhost";
//    private static final String PORT ="3306";
//    private static final String DATABASE ="timetablemanagementsystem";


    private DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String jdbcurl = "jdbc:mysql://"+SERVER+":"+ PORT +"/"+DATABASE;
            conn = (Connection) DriverManager.getConnection(jdbcurl, USER, P_WORD);
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE,e.getMessage());

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return this.conn;
    }

}
