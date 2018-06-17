package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {

    public static Connection buildConnection() {
        Connection conn = null;
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(connectURL,"ora_a2l0b","a30183157");
            System.out.println("\nConnected to Oracle!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
        return conn;
    }
}
