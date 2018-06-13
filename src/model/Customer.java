package model;

public class Customer extends User {
    private boolean isLoyaltyMember = false;
    private int pointBalance;

    public Customer(Integer userId) {
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
