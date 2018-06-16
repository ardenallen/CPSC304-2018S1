package model;

//import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private static int ticketPoint = 1000;
    private boolean isLoyaltyMember;
    private int pointBalance;
    private String name;


    public Customer(int userId) {
        super("customer", userId);
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT NAME " +
                    "FROM CUSTOMER " +
                    "WHERE CID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.name = rs.getString("NAME");

            PreparedStatement ps2 = conn.prepareStatement("SELECT * " +
                    "FROM LOYALTY_MEMBER " +
                    "WHERE CID = ?");
            ps2.setInt(1, userId);
            ResultSet rs2 = ps2.executeQuery();
            this.isLoyaltyMember = rs2.next();
            ps.close();


        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public String getName() { return this.name; }

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

    public List<Ticket> viewBooking (int cId) {
        List<Ticket> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT T.TICKET_NUM, T.PRICE, T.TRANSACTION, T.TITLE, T.START_TIME, T.AID " +
                            "FROM BOOKING B, TICKET T " +
                            "WHERE T.TRANSACTION = B.TRANSACTION " +
                            "AND B.CID = ?");
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketNum = rs.getInt("TICKET_NUM");
                //BigDecimal price = rs.getBigDecimal("PRICE");
                String transaction = rs.getString("TRANSACTION");
                String title = rs.getString("TITLE");
                Timestamp start_time = rs.getTimestamp("START_TIME");
                String startTime = start_time.toString();
                int auditorium = rs.getInt("AID");
                //result.add(new Ticket(ticketNum, price, transaction, title, startTime, auditorium));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }

        return result;
    }

    public void signUpForLoyaltyMember() {
        this.isLoyaltyMember = true;
        this.pointBalance = 0;
    }
}
