package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Manager extends Employee {

    public Manager(int userId) {
        super(userId);
    }

    public void addMovie(String title, int duration, String genre, String censor) {
         try {
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO MOVIE " +
                             "(TITLE, GENRE, DURATION, CENSOR) " +
                             "(?,?,?,?)");
             ps.setString(1, title);
             ps.setInt(2, duration);
             ps.setString(3, genre);
             ps.setString(4, censor);
             ps.executeUpdate();
         } catch (SQLException ex) {
             System.out.println("Message: " + ex.getMessage());
         }
    }

    public void removeMovie (String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE MOVIE WHERE Title = ?");
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException ex) {

        }
    }
}
