package model;

public class Customer extends User {
    private static int ticketPoint = 1000;
    private boolean isLoyaltyMember = false;
    private int pointBalance;
    private String name;


    public Customer(int userId, String name) {
        super("customer", userId);
        this.name = name;
    }

    public boolean isLoyaltyMember() {
        return isLoyaltyMember;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public boolean redeem(int numOfTickets) {
        if (this.pointBalance - numOfTickets * ticketPoint >= 0) {
            this.pointBalance = this.pointBalance - numOfTickets * ticketPoint;
            return true;
        } else {
            return false;
        }
    }

    public void updatePoint(int point) {
        this.pointBalance += point;
    }

    public void signupForLoyaltyMember() {
        this.isLoyaltyMember = true;
        this.pointBalance = 0;
    }
}
