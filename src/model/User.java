package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User {
    public Connection conn;
    private String userClass;
    private int userId;

    //Constructor
    public User(String userClass, int userId) {
        this.userClass = userClass;
        this.userId = userId;
    }

    public String getUserClass() {
        return userClass;
    }

    // Connects to Oracle database with hardcoded username and password
    public void connect() {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection(connectURL,"ora_p9n0b","a10804152");
            System.out.println("\nConnected to Oracle!");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public void setUserClass(String newUserClass) { this.userClass = newUserClass; }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int newUserId) { this.userId = newUserId; }

    //public MovieInfo getMovieInfo(String ) {

    //}
}
