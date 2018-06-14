package model;

import java.sql.*;

public class Customer extends User {
    private boolean isLoyaltyMember = false;
    private int pointBalance;

    private Connection con;

    public Customer(int userId) {
        super("customer", userId);
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
