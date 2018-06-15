package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee extends User {
    public Employee(int userId) {
        super("employee", userId);
    }

    public int ticketSoldPerMoviePerShowtime (String movieTitle, String showTime) {
        int ticketSold = -1;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Ticket " +
                    "WHERE TITLE = ? AND START_TIME = ?");
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return ticketSold;
    }



}
