package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public static void addShowtime(Timestamp startTime, String title, int aID) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO SHOWTIME1 " +
                            "(START_TIME, TITLE, AID) " +
                            "(?,?,?)");
            ps.setTimestamp(1, startTime);
            ps.setString(2, title);
            ps.setInt(3, aID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Adding " + startTime + "for movie " + title +  " failed.");
        }
    }

    public static void removeShowtime (Timestamp startTime, String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE SHOWTIME1 " +
                            "WHERE (START_TIME = ? AND TITLE = ?)");
            ps.setTimestamp(1, startTime);
            ps.setString(2, title);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing " + startTime + " for Movie " + title + " table failed.");
        }
    }

    public static void addEmployee(int userId, String name, int SIN, String phone) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO EMPLOYEE " +
                            "(EID, NAME, SIN, PHONE) " +
                            "(?,?,?,?)");
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setInt(3, SIN);
            ps.setString(3, phone);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Adding employee " + name + " failed.");
        }
    }

    public static void removeEmployee (int userID) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE EMPLOYEE " +
                            "WHERE EID = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing employee " + userID + " failed.");
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

    // Returns all the Employee who generated the max (most sales) or min (least sales) amount of ticket sales
    // Will not take into account of employee who did not sell any ticket on the given date
    public static List<Employee> getLeastMostSalesEmployee(String minMax, String date) {
        List<Employee> result = new ArrayList<>();

        return result;
    }


    private static int getLeastMostSales(String minMax) {
        int result = -1;

        return result;
    }

    public List<Customer> getBestCustomer() {
        List<Customer> result = new ArrayList<>();
        return result;
    }

    private static List<Integer> getEmployeeIDFromSales(int sales) {
        List<Integer> result = new ArrayList();

        return result;
    }


}
