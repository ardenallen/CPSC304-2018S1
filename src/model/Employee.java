package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee extends User {
    private int eID;
    private String name;
    private int SIN;
    private String phone;

    public Employee(int userId, String name, int SIN, String phone) {
        super("employee", userId);
        this.name = name;
        this.SIN = SIN;
        this.phone = phone;
    }

    public static int ticketSoldPerMoviePerShowtime (String movieTitle, String showTime) {
        int ticketSold = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Ticket " +
                    "WHERE TITLE = ? AND START_TIME = {ts ?}");
            ps.setString(1, movieTitle);
            ps.setString(2, showTime);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ticketSold = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return ticketSold;
    }
}
