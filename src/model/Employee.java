package model;

import layout.dialog.CustomerDoesNotExistDialog;

import java.math.BigDecimal;
import java.sql.*;
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

            this.name = rs.getString("NAME");
            this.SIN = rs.getInt("SIN");
            this.phone = rs.getString("PHONE");

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
        // If credit: payment is "CXXXXX"
        // If debit: payment is "DXXXXX"
        // If cash: payment is "Cash"
        // If redeem: payment is "Redeem"
        String paymentMethod;
        String cardInfo;
        double price = 13;

        if (payment.equals("Cash")) {
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
        String timeStampString = showTime.toString();
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
                int ticketNum = rs.getInt("Ticket_num");
                String title = rs.getString("Title");
                Timestamp startTime = rs.getTimestamp("Start_time");
                BigDecimal price = rs.getBigDecimal("Price");
                int aId = rs.getInt("aID");
                Ticket x = new Ticket(ticketNum, price, transactionNumber, title, startTime, aId);
                tickets.add(x);
                paymentMethod = rs.getString("Payment_method");
                cardInfo = rs.getString("Card_info");
                eId = rs.getInt("eID");
                cId = rs.getInt("cId");
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

        }
        result = new Booking(transactionNumber, paymentMethod, cardInfo, eId, cId, tickets);
        return result;
    }

    // Employee will have to enter the cardNum if the ticket was bought using a card,
    // else please leave blank if cash
    public static void refund(String customerCardNum, List<Integer> ticketNums) {
        String paymentMethod = "";
        String cardInfo = "";
        String sqlTicketNums = "";
        for (int ticketNum : ticketNums) {
            sqlTicketNums += ticketNum + ", ";
        }
        sqlTicketNums = sqlTicketNums.substring(0, sqlTicketNums.length() - 2);
        String SQL = "SELECT CARD_INFO, PAYMENT_METHOD " +
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
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        // Check if payment method was cash OR customerCardNum == cardInfo
        if (paymentMethod == "Cash" || cardInfo.equals(customerCardNum)) {
            // Delete ticket and refund customer if condition is met
            try {
                for (int ticketNum : ticketNums) {
                    // For UPDATING the Ticket table
                    PreparedStatement psU = conn.prepareStatement(
                            "DELETE TICKET WHERE TICKET_NUM = ?");
                    psU.setInt(1, ticketNum);
                    psU.executeUpdate();
                    psU.close();
                }
                // No need to delete ticket from other tables; it is handled in the DB
            } catch (SQLException ex) {
                System.out.println("Message: " + ex.getMessage());
            }
        } else {
            System.out.println("Please enter the same card number you bought the ticket with.");
        }
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
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // Return all movies with the max (most popular) or min (least popular) # of ticket sold
    // Will not take into account of movies with no tickets sold
    public static List<Movie> getLeastMostPopularMovie(String minMax) {
        List<Movie> result = new ArrayList<>();
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

            while(rs.next()) {
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                Movie x = new Movie(title, duration, genre, censor);
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
                "SELECT TITLE, COUNT(*) AS TICKET_SOLD " +
                "FROM TICKET  " +
                "GROUP BY TITLE)";
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
                    "SELECT TITLE, COUNT(*) FROM TICKET " +
                    "GROUP BY TITLE");
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
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }
}
