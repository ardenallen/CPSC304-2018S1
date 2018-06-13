package model;

public class Customer extends User {
    private boolean isLoyaltyMember = false;

    public Customer(Integer userId) {
        super("customer", userId);
    }

    public boolean isLoyaltyMember() {
        return isLoyaltyMember;
    }

    public void signupForLoyaltyMember() {
        isLoyaltyMember = true;
    }
}
