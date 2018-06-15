package model;

import utils.OracleConnection;

import java.sql.*;

public class User {
    private String userClass;
    private int userId;
    public static Connection conn = OracleConnection.buildConnection();

    //Constructor
    public User(String userClass, int userId) {
        this.userClass = userClass;
        this.userId = userId;
    }

    public String getUserClass() {
        return userClass;
    }

     public void setUserClass(String newUserClass) { this.userClass = newUserClass; }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int newUserId) { this.userId = newUserId; }

    public static Movie getMovieInfo(String movieTitle) {

        Movie result = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Title, Duration, Genre, Censor " +
                    "FROM Movie " + "WHERE Title = ?");

            ps.setString(1, movieTitle);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                System.out.println(title);
                result = new Movie(title, duration, genre, censor);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }
}
