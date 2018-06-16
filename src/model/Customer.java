package model;

//import java.math.BigDecimal;
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
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE LOYALTY_MEMBER SET POINT_BALANCE = POINT_BALANCE - ? WHERE CID = ?");
            ps.setInt(1, numOfTickets * TICKET_POINT_REDEEM);
            ps.setInt(2, getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void addPoint(int numOfTickets) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE LOYALTY_MEMBER SET POINT_BALANCE = POINT_BALANCE + ? WHERE CID = ?");
            ps.setInt(1, numOfTickets * TICKET_POINT_ADD);
            ps.setInt(2, getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
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
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO LOYALTY_MEMBER VALUES (?,0)");

            ps.setInt(1, super.getUserId());

            ps.executeUpdate();
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
        String paymentMethod;
        String cardInfo;
        int price = 13;

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
            }
        } else {
            this.redeem(quantity);
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
            ex.printStackTrace();
            System.exit(-1);
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
            conn.commit();
            ps.close();

            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO TICKET " +
                    "(TRANSACTION, TICKET_NUM, TITLE, START_TIME, PRICE, AID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            ps2.setString(1, "43456441781");
            ps2.setString(3, title);
            ps2.setTimestamp(4, startTime);
            ps2.setInt(5, price);
            ps2.setInt(6, auditorum);

            for (int i=0; i < quantity; i++) {
                ps2.setInt(2, ticketNum);
                ps2.executeUpdate();
            }
            ps2.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
        return true;
    }
}
