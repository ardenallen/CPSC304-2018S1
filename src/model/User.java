package model;

import java.sql.*;

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

    public MovieInfo getMovieInfo(String movieTitle) {
        MovieInfo result = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT Title, Duration, Genre, Censor " +
                    "FROM Movie " + "WHERE Title = ?");

            ps.setString(1, movieTitle);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                System.out.println(title);
                result = new MovieInfo(title, duration, genre, censor);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }
}
