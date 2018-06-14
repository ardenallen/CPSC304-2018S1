package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class OracleConnection {
    public Connection conn;

    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    public void connect()throws SQLException {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(connectURL,"ora_p9n0b","a10804152");
            System.out.println("\nConnected to Oracle!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            throw ex;
        }
    }
}