package model;


import utils.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Booking is a list of tickets
public class Booking {
    private String transactionNum;
    private String paymentMethod;
    private String cardInfo;
    private int eId;
    private int cId;
    private List<Ticket> tickets;

    public Booking(String transactionNum, String paymentMethod, String cardInfo, int eId, int cId, List<Ticket> tickets) {
        this.transactionNum = transactionNum;
        this.paymentMethod = paymentMethod;
        this.cardInfo = cardInfo;
        this.eId = eId;
        this.cId = cId;

        if (tickets == null) {
            this.tickets = new ArrayList<>();
        } else {
            this.tickets = tickets;
        }

    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public int geteId() {
        return eId;
    }

    public int getcId() {
        return cId;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public boolean equals (Booking x) {
        return false;
    }

    public static Booking getBooking(String transactionNum) {
        Connection conn = OracleConnection.buildConnection();

        Booking result;

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM BOOKING WHERE TRANSACTION = ?");
            ps.setString(1, transactionNum);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String paymentMethod = rs.getString("PAYMENT_METHOD");
                String cardInfo = rs.getString("CARD_INFO");
                int eId = rs.getInt("EID");
                int cId = rs.getInt("CID");

                result = new Booking(transactionNum, paymentMethod, cardInfo, eId, cId, null);

                result.setTickets(Ticket.viewTicketsInBooking(transactionNum));

                return result;
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return null;
    }
}
