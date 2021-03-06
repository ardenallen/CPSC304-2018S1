package model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Employee extends User {
    private String name;
    private int SIN;
    private String phone;

    public Employee(int userId) {
        super("employee", userId);

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * " +
                    "FROM EMPLOYEE " +
                    "WHERE EID = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            rs.next();

            this.name = rs.getString("NAME").trim();
            this.SIN = rs.getInt("SIN");
            this.phone = rs.getString("PHONE").trim();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public String getName() {
        return name;
    }

    public int getSIN() {
        return SIN;
    }

    public String getPhone() {
        return phone;
    }

    public boolean sellTickets(Movie movie, Showtime showtime, int quantity, String payment, Customer customer) {
        String paymentMethod;
        String cardInfo;
        double price = payment.equals("Redeem") ? 0 : 13;
        // Helpers to get paymentMethod and cardInfo from payment
        paymentMethod = getPaymentMethodFromPayment(payment);
        cardInfo = getCardInfoFromPayment(payment);

        if (paymentMethod.equals("Redeem")) {
            if (!customer.canRedeem(quantity)) {
                return false;
            } else {
                customer.redeem(quantity);
            }
        }

        int cId = customer.getUserId();
        String title = movie.getTitle();
        int auditorium = showtime.getaId();
        Timestamp startTime = showtime.getStartTime();

        int ticketNum = getNextTicketNum();


        ThreadLocalRandom random = ThreadLocalRandom.current();
        Long randomNumber = random.nextLong(10_000_000_000L, 100_000_000_000L);
        String transactionNumber = randomNumber.toString();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO BOOKING " +
                    "(TRANSACTION, PAYMENT_METHOD, CARD_INFO, EID, CID) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, transactionNumber);
            ps.setString(2, paymentMethod);
            ps.setString(3, cardInfo);
            ps.setInt(4, super.getUserId());
            ps.setInt(5, cId);
            ps.executeUpdate();
            ps.close();

            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO TICKET " +
                    "(TRANSACTION, TICKET_NUM, TITLE, START_TIME, PRICE, AID) " +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            ps2.setString(1, transactionNumber);
            ps2.setString(3, title);
            ps2.setTimestamp(4, startTime);
            ps2.setDouble(5, price);
            ps2.setInt(6, auditorium);

            for (int i=0; i < quantity; i++) {
                ps2.setInt(2, ticketNum + i);
                ps2.executeUpdate();
            }
            ps2.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        if (!paymentMethod.equals("Redeem")) {
            customer.addPoint(quantity);
        }

        return true;
    }

    public static int ticketSoldPerMoviePerShowtime (String movieTitle, Timestamp showTime) {
        int ticketSold = -1;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM Ticket " +
                    "WHERE TITLE = ? AND START_TIME = ?");
            ps.setString(1, movieTitle);
            ps.setTimestamp(2, showTime);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ticketSold = rs.getInt(1);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return ticketSold;
    }

    public static Booking getBooking(String transactionNumber) {
        Booking result = null;
        String paymentMethod = "";
        String cardInfo = "";
        int eId = -1;
        int cId = -1;
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT T.TRANSACTION, TICKET_NUM, TITLE, START_TIME, PRICE, AID, " +
                            "PAYMENT_METHOD, CARD_INFO, EID, CID " +
                            "FROM BOOKING B, TICKET T " +
                            "WHERE T.TRANSACTION = ? " +
                            "AND T.TRANSACTION = B.TRANSACTION");
            ps.setString(1, transactionNumber);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int ticketNum = rs.getInt("TICKET_NUM");
                BigDecimal price = rs.getBigDecimal("PRICE");
                String transactionNum = rs.getString("TRANSACTION");
                String title = rs.getString("TITLE");
                Timestamp timestamp = rs.getTimestamp("START_TIME");
                int aId = rs.getInt("AID");
                tickets.add(new Ticket(ticketNum, price, transactionNum, title, timestamp, aId));

                paymentMethod = rs.getString("Payment_method");
                cardInfo = rs.getString("Card_info");
                eId = rs.getInt("eID");
                cId = rs.getInt("cId");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

        }
        result = new Booking(transactionNumber, paymentMethod, cardInfo, eId, cId, tickets);
        return result;
    }

    // Employee will have to enter the cardNum if the ticket was bought using a card,
    // else please leave blank if cash
    public static boolean refund(String customerCardNum, List<Integer> ticketNums) {
        String paymentMethod = "";
        String cardInfo = "";
        int cID = -1;
        String sqlTicketNums = "";
        for (int ticketNum : ticketNums) {
            sqlTicketNums += ticketNum + ", ";
        }
        sqlTicketNums = sqlTicketNums.substring(0, sqlTicketNums.length() - 2);
        String SQL = "SELECT CARD_INFO, PAYMENT_METHOD, CID " +
                "FROM TICKET T, BOOKING B " +
                "WHERE TICKET_NUM IN (%s) " +
                "AND T.TRANSACTION = B.TRANSACTION";
        SQL = String.format(SQL, sqlTicketNums);

        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                paymentMethod = rs.getString("Payment_method");
                cardInfo = rs.getString("Card_info");
                cID = rs.getInt("CID");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        // Refund points to customer if payment method is redeem
        if (paymentMethod.trim().equals("Redeem")) {
            int currentBalance = getLoyaltyPoints(cID);
            int newBalance = currentBalance + 1000 * ticketNums.size();
            updateLoyaltyPoints(cID, newBalance);
        }
        // Check if payment method was cash, redeem OR if the given cardNum is the same as the one in record
        if (paymentMethod.trim().equals("Cash") ||
                paymentMethod.trim().equals("Redeem") ||
                (cardInfo!= null && cardInfo.equals(customerCardNum))) {
            // Delete ticket and refund customer if condition is met
            try {
                for (int ticketNum : ticketNums) {
                    // For UPDATING the Ticket table
                    PreparedStatement psU = conn.prepareStatement(
                            "DELETE TICKET WHERE TICKET_NUM = ?");
                    // No need to delete ticket from other tables; it is handled in the DB
                    psU.setInt(1, ticketNum);
                    psU.executeUpdate();
                    psU.close();
                }
                return true;
            } catch (SQLException ex) {
                System.out.println("Message: " + ex.getMessage());
            }
        } else {
            System.out.println("Please enter the same card number you bought the ticket with.");
        }

        return false;
    }

    // Returns a list of MovieStat in descending order of the number of tickets sold
    public static List<MovieStat> getAllMovieStats() {
        List<MovieStat> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT T.TITLE, COUNT(*) " +
                            "FROM TICKET T " +
                            "GROUP BY T.TITLE " +
                            "ORDER BY COUNT(*) DESC");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String title = rs.getString("Title");
                int count = rs.getInt(2);
                MovieStat x = new MovieStat(title, count);
                result.add(x);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // Return all movies with the max (most popular) or min (least popular) # of ticket sold
    public static List<MovieStat> getLeastMostPopularMovie(String minMax) {
        List<MovieStat> result = new ArrayList<>();
        int minMaxNumTicketSold = Employee.getLeastMostPopularMovieTicketCount(minMax);
        List<String> movieTitles = Employee.getMovieFromNumTicketsSold(minMaxNumTicketSold);
        String sqlMovieTitles = "";
        for (String movieTitle : movieTitles) {
            sqlMovieTitles += "'" + movieTitle + "', ";
        }
        sqlMovieTitles = sqlMovieTitles.substring(0, sqlMovieTitles.length() - 2);
        String SQL = "SELECT * FROM MOVIE WHERE TITLE IN (%s)";
        SQL = String.format(SQL, sqlMovieTitles);

        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("Title");
                MovieStat x = new MovieStat(title, minMaxNumTicketSold);
                result.add(x);
            }

            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    private static int getLeastMostPopularMovieTicketCount(String minMax) {
        int result = -1;
        String SQL = "SELECT %s(TICKET_SOLD) FROM (" +
                "SELECT M.TITLE, COUNT(T.TICKET_NUM) AS TICKET_SOLD " +
                "FROM MOVIE M  " +
                "LEFT OUTER JOIN TICKET T " +
                "ON M.TITLE = T.TITLE " +
                "GROUP BY M.TITLE)";
        SQL = minMax.equalsIgnoreCase("Min") ?
                String.format(SQL, "MIN") :
                String.format(SQL, "MAX");
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result = rs.getInt(1);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // Extracts movie name given the number of ticket sold
    private static List<String> getMovieFromNumTicketsSold(int numTicketSold) {
        List<String> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT M.TITLE, COUNT(T.TICKET_NUM) AS TICKET_SOLD " +
                            "FROM MOVIE M  " +
                            "LEFT OUTER JOIN TICKET T " +
                            "ON M.TITLE = T.TITLE " +
                            "GROUP BY M.TITLE");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int count = rs.getInt(2);
                if (count == numTicketSold) {
                    String title = rs.getString("Title");
                    result.add(title);
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public static BigDecimal getTotalBookingPrice(String transactionNum) {
        BigDecimal result = new BigDecimal(0);
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT TRANSACTION, SUM(PRICE) AS TOTAL FROM TICKET " +
                            "WHERE TRANSACTION = ? " +
                            "GROUP BY TRANSACTION");
            ps.setString(1, transactionNum);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                BigDecimal price = rs.getBigDecimal(2);
                result = result.add(price);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }
}
