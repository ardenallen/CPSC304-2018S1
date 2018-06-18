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



}
