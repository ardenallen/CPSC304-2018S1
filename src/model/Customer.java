package model;

import java.sql.*;

public class Customer extends User {
    private boolean isLoyaltyMember = false;
    private int pointBalance;

    private Connection con;

    public Customer(int userId) {
        super("customer", userId);
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }

    private boolean connect(String username, String password) {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";

        try {
            con = DriverManager.getConnection(connectURL,username,password);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }

    public boolean isLoyaltyMember() {
        return isLoyaltyMember;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public void signupForLoyaltyMember() {
        isLoyaltyMember = true;
        pointBalance = 0;
    }
}
