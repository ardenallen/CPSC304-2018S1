package model;

public class Ticket {
    private int ticketNum;
    private int price;
    private String transactionNum;
    private String title;
    private String startTime; // String??
    private int aId;

    public Ticket(int ticketNum, int price, String transactionNum, String title, String startTime, int aId) {
        this.ticketNum = ticketNum;
        this.price = price;
        this.transactionNum = transactionNum;
        this.title = title;
        this.startTime = startTime;
        this.aId = aId;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public int getPrice() {
        return price;
    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getaId() {
        return aId;
    }
}
