package model;

import utils.OracleConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int ticketNum;
    private BigDecimal price;
    private String transactionNum;
    private String title;
    private Timestamp startTime;
    private int aId;

    public Ticket(int ticketNum, BigDecimal price, String transactionNum, String title, Timestamp startTime, int aId) {
        this.ticketNum = ticketNum;
        this.price = price;
        this.transactionNum = transactionNum;
        this.title = title;
        this.startTime = startTime;
        this.aId = aId;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public int getaId() {
        return aId;
    }

    public static List<Ticket> getTicketsOfShowtime(Showtime showtime) {
        List<Ticket> result = new ArrayList<>();

        Connection conn = OracleConnection.buildConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM TICKET WHERE TITLE = ? AND START_TIME = ?");

            ps.setString(1, showtime.getMovieTitle());
            ps.setTimestamp(2, showtime.getStartTime());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketNum = rs.getInt("TICKET_NUM");
                BigDecimal price = rs.getBigDecimal("PRICE");
                String transactionNum = rs.getString("TRANSACTION");
                String title = rs.getString("TITLE");
                Timestamp timestamp = rs.getTimestamp("START_TIME");
                int aId = rs.getInt("AID");

                result.add(new Ticket(ticketNum, price, transactionNum, title, timestamp, aId));
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return result;
    }
}
