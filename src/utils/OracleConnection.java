package utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class OracleConnection {
    private Connection conn;

    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    private boolean connect(String username, String password)
    {
        String connectURL = "jdbc:oracle:thin@dbhost.ugrad.cs";

        try
        {
            conn = DriverManager.getConnection(connectURL,username,password);

            System.out.println("\nConnected to Oracle!");
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }

}
