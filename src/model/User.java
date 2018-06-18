package model;

import utils.OracleConnection;

import java.sql.*;

public class User {
    private String userClass;
    private int userId;
    public static Connection conn = OracleConnection.buildConnection();

    //Constructor
    public User(String userClass, int userId) {
        this.userClass = userClass;
        this.userId = userId;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String newUserClass) { this.userClass = newUserClass; }

    public Integer getUserId() {
        return userId;
    }

    public static Movie getMovieInfo(String movieTitle) {

        Movie result = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT Title, Duration, Genre, Censor " +
                    "FROM Movie " + "WHERE Title = ?");

            ps.setString(1, movieTitle);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                System.out.println(title);
                result = new Movie(title, duration, genre, censor);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // convert payment passed in from UI to a standardized paymentMethod
    public String getPaymentMethodFromPayment(String payment) {
        if (payment.equals("Cash")) {
            return "Cash";
        } else if (payment.startsWith("C")) {
            return "Credit";
        } else if (payment.startsWith("D")) {
            return "Debit";
        } else {
            return "Redeem";
        }
    }

    public String getCardInfoFromPayment(String payment) {
        // If credit: payment is "CXXXXX"
        // If debit: payment is "DXXXXX"
        // If cash: payment is "Cash"
        // If redeem: payment is "Redeem"
        String result = payment.startsWith("C") || payment.startsWith("D") ?
                payment.substring(1):
                null;
        return result;
    }

}
