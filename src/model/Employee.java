package model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static Booking getBooking(String transactionNumber) {
        Booking result = null;
        String paymentMethod = "";
        String cardInfo = "";
        int eId = -1;
        int cId = -1;
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT T.TRANSACTION, TICKET_NUM, TITLE, START_TIME, PRICE, AID, " +
                            "PAYMENT_METHOD, CARD_INFO, EID, CID " +
                            "FROM BOOKING B, TICKET T " +
                            "WHERE T.TRANSACTION = ? " +
                            "AND T.TRANSACTION = B.TRANSACTION");
            ps.setString(1, transactionNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketNum = rs.getInt("Ticket_num");
                String title = rs.getString("Title");
                String startTime = rs.getString("Start_time");
                BigDecimal price = rs.getBigDecimal("Price");
                int aId = rs.getInt("aID");
                Ticket x = new Ticket(ticketNum, price, transactionNumber, title, startTime, aId);
                tickets.add(x);
                paymentMethod = rs.getString("Payment_method");
                cardInfo = rs.getString("Card_info");
                eId = rs.getInt("eID");
                cId = rs.getInt("cId");
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

        }
        result = new Booking(transactionNumber, paymentMethod, cardInfo, eId, cId, tickets);
        return result;
    }

    // Employee will have to enter the cardNum if the ticket was bought using a card,
    // else please leave blank if cash
    public static void refund(String customerCardNum, int ticketNum) {
        String paymentMethod = "";
        String cardInfo = "";
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT CARD_INFO, PAYMENT_METHOD " +
                            "FROM TICKET T, BOOKING B " +
                            "WHERE TICKET_NUM = ? " +
                            "AND T.TRANSACTION = B.TRANSACTION");
            ps.setInt(1, ticketNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                paymentMethod = rs.getString("Payment_method");
                cardInfo = rs.getString("Card_info");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        // Check if payment method was cash OR customerCardNum == cardInfo
        if (paymentMethod == "Cash" || cardInfo.equals(customerCardNum)) {
            // Delete ticket and refund customer if condition is met
            try {
                // For UPDATING the Ticket table
                PreparedStatement psU = conn.prepareStatement(
                        "DELETE TICKET WHERE TICKET_NUM = ?");
                psU.setInt(1, ticketNum);
                psU.executeUpdate();
                // No need to delete ticket from other tables; it is handled in the DB
                psU.close();
            } catch (SQLException ex) {
                System.out.println("Message: " + ex.getMessage());
                System.out.println("Refunding ticket# " + ticketNum + " failed.");
            }
        } else {
            System.out.println("Please enter the same card number you bought the ticket with.");
        }
    }

    public int getRevenuePerEmployeePerDay(String date) {
        //SUM AGGREGATE
        // USE THIS.EID
        int result = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT SUM(PRICE) FROM TICKET "
            );
        } catch (SQLException ex) {

        }
        return result;
    }

    // Returns a list of MovieStat in descending order of the number of tickets sold
    public static List<MovieStat> getAllMovieStats() {
        List<MovieStat> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT T.TITLE, COUNT(*) " +
                            "FROM TICKET T " +
                            "GROUP BY T.TITLE " +
                            "ORDER BY COUNT(*) DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                int count = rs.getInt(2);
                MovieStat x = new MovieStat(title, count);
                result.add(x);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }
}
