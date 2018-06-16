package model;

import java.math.BigDecimal;

public class Ticket {
    private int ticketNum;
    private BigDecimal price;
    private String transactionNum;
    private String title;
    private String startTime; // String??
    private int aId;

    public Ticket(int ticketNum, BigDecimal price, String transactionNum, String title, String startTime, int aId) {
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

    public BigDecimal getPrice() {
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

    public boolean equals (Ticket x) {
        boolean equalPrice = x.price.compareTo(this.price) == 1 ? true : false;
        return this.ticketNum == x.ticketNum &&
                equalPrice &&
                this.transactionNum.equals(x.transactionNum)  &&
                this.title.equals(x.title) &&
                this.startTime.equals(x.startTime) &&
                this.aId == x.aId;
    }
}
