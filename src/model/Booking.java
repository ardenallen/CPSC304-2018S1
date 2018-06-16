package model;


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
        this.tickets = tickets;
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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public boolean equals (Booking x) {
        return false;
    }
}
