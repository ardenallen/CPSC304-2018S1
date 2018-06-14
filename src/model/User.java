package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class User {
    private String userClass;
    private int userId;
    private Connection con;

    //Constructor
    public User(String userClass, int userId) {
        this.userClass = userClass;
        this.userId = userId;

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String newUserClass) { this.userClass = newUserClass; }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int newUserId) { this.userId = newUserId; }

    private boolean connect(String username, String password) {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";

        try {
            this.con = DriverManager.getConnection(connectURL, username, password);
            System.out.println("Connected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public MovieInfo getMovieInfo(String movieTitle) {
        return null;
    }

    public ShowTimeInfo getShowTimeInfo (String movieTitle) {
        return null;
    }
}
