package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {

    public Manager(int userId, String name, int SIN, String phone) {
        super(userId, name, SIN, phone);
    }

    public static void addMovie(String title, int duration, String genre, String censor) {
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
             System.out.println("Adding " + title + " to Movie table failed.");
         }
    }

    public static void removeMovie (String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE MOVIE WHERE Title = ?");
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing " + title + " from Movie table failed.");
        }
    }

    public static List<Employee> getAllEmployee() {
        List<Employee> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM EMPLOYEE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int eId = rs.getInt("eID");
                String name = rs.getString("Name");
                int SIN = rs.getInt("SIN");
                String phone = rs.getString("Phone");
                Employee employee = new Employee(eId, name, SIN, phone);
                result.add(employee);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }


}
