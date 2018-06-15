package model;

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
                String startTime = rs.getString("Start)time");
                float price = rs.getFloat("Price");
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
}
