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

    public static int getLoyaltyPoints(int cID) {
        int currentBalance = 0;
        try {
            // Get current point balance
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT POINT_BALANCE FROM LOYALTY_MEMBER " +
                            "WHERE CID = ?");
            ps.setInt(1, cID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                currentBalance = rs.getInt(1);
            }
        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return currentBalance;
    }

    public static void updateLoyaltyPoints(int cID, int newBalance) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE LOYALTY_MEMBER SET POINT_BALANCE = ? " +
                            "WHERE CID = ?");
            ps.setInt(1, newBalance);
            ps.setInt(2, cID);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    // convert payment passed in from UI to a standardized paymentMethod
    public String getPaymentMethodFromPayment(String payment) {
        if (payment.equals("Cash")) {
            return "Cash";
        } else if (payment.startsWith("C")) {
            return "Credit";
        } else if (payment.startsWith("D")) {
            return "Debit";
        } else {
            return "Redeem";
        }
    }

    public String getCardInfoFromPayment(String payment) {
        // If credit: payment is "CXXXXX"
        // If debit: payment is "DXXXXX"
        // If cash: payment is "Cash"
        // If redeem: payment is "Redeem"
        String result = payment.startsWith("C") || payment.startsWith("D") ?
                payment.substring(1):
                null;
        return result;
    }

    public int getNextTicketNum() {
        int ticketNum = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(TICKET_NUM) FROM TICKET");
            if (rs.next()) {
                ticketNum = rs.getInt(1) + 1;
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return ticketNum;
    }
}
