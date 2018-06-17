package model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Customer extends User {
    private static final int TICKET_POINT_REDEEM = 1000;
    private static final int TICKET_POINT_ADD = 50;

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

    public void setPoint(int newBalance) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE LOYALTY_MEMBER " +
                    "SET POINT_BALANCE = ? " +
                    "WHERE CID = ?");
            ps.setInt(1, newBalance);
            ps.setInt(2, this.getUserId());
            ps.executeUpdate();
            ps.close();
            this.pointBalance = newBalance;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public int getPointBalance() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * " +
                    "FROM LOYALTY_MEMBER " +
                    "WHERE CID = ?");
            ps.setInt(1, getUserId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pointBalance = rs.getInt("POINT_BALANCE");
                return pointBalance;
            }
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }

        return -1;
    }

    public boolean canRedeem(int numOfTickets) {
        return pointBalance - numOfTickets * TICKET_POINT_REDEEM >= 0;
    }

    public void redeem(int numOfTickets) {
        try {
            if (this.canRedeem(numOfTickets)) {
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE LOYALTY_MEMBER SET POINT_BALANCE = POINT_BALANCE - ? WHERE CID = ?");
                ps.setInt(1, numOfTickets * TICKET_POINT_REDEEM);
                ps.setInt(2, getUserId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void addPoint(int numOfTickets) {
        try {
            if (this.isLoyaltyMember()) {
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE LOYALTY_MEMBER SET POINT_BALANCE = POINT_BALANCE + ? WHERE CID = ?");
                ps.setInt(1, numOfTickets * TICKET_POINT_ADD);
                ps.setInt(2, getUserId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
    }

    public List<Booking> getAllBookings() {
        List<Booking> result = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM BOOKING WHERE CID = ?");
            ps.setInt(1, super.getUserId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String transactionNum = rs.getString("TRANSACTION");
                String paymentMethod = rs.getString("PAYMENT_METHOD");
                String cardInfo = null;

                if (!paymentMethod.equals("Cash")) {
                    cardInfo = rs.getString("CARD_INFO");
                }

                int eId = rs.getInt("EID");
                int cId = rs.getInt("CID");

                result.add(new Booking(transactionNum, paymentMethod, cardInfo, eId, cId,
                                Ticket.viewTicketsInBooking(transactionNum)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public List<Ticket> getAllTickets(String transactionNum) {
        List<Ticket> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TICKET WHERE TRANSACTION = ?");

            ps.setString(1, transactionNum);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ticketNum = rs.getInt("TICKET_NUM");
                String title = rs.getString("TITLE");
                Timestamp startTime = rs.getTimestamp("START_TIME");
                BigDecimal price = rs.getBigDecimal("PRICE");
                int aId = rs.getInt("AID");

                result.add(new Ticket(ticketNum, price, transactionNum, title, startTime, aId));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public void signUpForLoyaltyMember() {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO LOYALTY_MEMBER VALUES (?,0)");

            ps.setInt(1, super.getUserId());
            ps.executeUpdate();
            this.isLoyaltyMember = true;
            this.pointBalance = 0;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public void cancelMembership() {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM LOYALTY_MEMBER " +
                    "WHERE CID = ?");
            ps.setInt(1, this.getUserId());
            ps.executeUpdate();
            ps.close();
            this.isLoyaltyMember = false;
            this.pointBalance = 0;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public static List<String> getRecommendations() {
        List<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    // Since Oracle does not support TOP, we are doing a nested query
                    // The inner query will get all movies in descending order of ticket count
                    // The out query will pick the top 3 using ROWNUM
                    "SELECT * FROM (" +
                            "SELECT TITLE, COUNT(*) FROM TICKET " +
                            "GROUP BY TITLE " +
                            "ORDER BY COUNT(*) DESC)" +
                            "WHERE ROWNUM = 1 OR ROWNUM = 2 OR ROWNUM = 3");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public boolean buyTickets(Movie movie, Showtime showtime, int quantity, String payment) {
        // If credit: payment is "CXXXXX"
        // If debit: payment is "DXXXXX"
        // If cash: payment is "Cash"
        // If redeem: payment is "Redeem"

        String paymentMethod;
        String cardInfo;
        double price = 13;

        if (payment == "Cash") {
            paymentMethod = "Cash";
            cardInfo = null;
        } else if (payment.startsWith("C")) {
            paymentMethod = "Credit";
            cardInfo = payment.substring(1);
        } else if (payment.startsWith("D")) {
            paymentMethod = "Debit";
            cardInfo = payment.substring(1);
        } else {
            paymentMethod = "Redeem";
            cardInfo = null;
            price = 0;
        }

        if (paymentMethod == "Redeem") {
            if (!this.canRedeem(quantity)) {
                return false;
            } else {
                this.redeem(quantity);
            }
        }

        int cId = this.getUserId();
        String title = movie.getTitle();
        int auditorum = showtime.getaId();
        Timestamp startTime = showtime.getStartTime();

        int ticketNum = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(TICKET_NUM) FROM TICKET");
            if (rs.next()) {
                ticketNum = rs.getInt(1) + 1;
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }


        ThreadLocalRandom random = ThreadLocalRandom.current();
        Long randomNumber = random.nextLong(10_000_000_000L, 100_000_000_000L);
        String transactionNumber = randomNumber.toString();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO BOOKING " +
                    "(TRANSACTION, PAYMENT_METHOD, CARD_INFO, EID, CID) " +
                    "VALUES (?, ?, ?, NULL, ?)");
            ps.setString(1, transactionNumber);
            ps.setString(2, paymentMethod);
            ps.setString(3, cardInfo);
            ps.setInt(4, cId);
            ps.executeUpdate();
            ps.close();

            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO TICKET " +
                    "(TRANSACTION, TICKET_NUM, TITLE, START_TIME, PRICE, AID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            ps2.setString(1, transactionNumber);
            ps2.setString(3, title);
            ps2.setTimestamp(4, startTime);
            ps2.setDouble(5, price);
            ps2.setInt(6, auditorum);

            for (int i=0; i < quantity; i++) {
                ps2.setInt(2, ticketNum + i);
                ps2.executeUpdate();
            }
            ps2.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        if (paymentMethod != "Redeem") {
            this.addPoint(quantity);
        }
        return true;
    }
}
