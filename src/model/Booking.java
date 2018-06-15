package model;


import java.util.ArrayList;

// Booking is a list of tickets
public class Booking {
    public String transactionNum;
    public String paymentMethod;
    public String cardInfo;
    public int eId;
    public int cId;
    public ArrayList<Ticket> tickets;

    public Booking(String transactionNum, String paymentMethod, String cardInfo, int eId, int cId, ArrayList<Ticket> tickets) {
        this.transactionNum = transactionNum;
        this.paymentMethod = paymentMethod;
        this.cardInfo = cardInfo;
        this.eId = eId;
        this.cId = cId;
        this.tickets = tickets;
    }
}
