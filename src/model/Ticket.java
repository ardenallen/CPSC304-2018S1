package model;

public class Ticket {
    public int ticketNum;
    public int price;
    public String transactionNum;
    public String title;
    public String timestamp; // String??
    public int aId;

    public Ticket(int ticketNum, int price, String transactionNum, String title, String timestamp, int aId) {
        this.ticketNum = ticketNum;
        this.price = price;
        this.transactionNum = transactionNum;
        this.title = title;
        this.timestamp = timestamp;
        this.aId = aId;
    }


}
