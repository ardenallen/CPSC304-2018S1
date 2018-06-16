package model;

import utils.OracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Showtime {
    public static Connection conn = OracleConnection.buildConnection();
    private Timestamp startTime;
    private String movieTitle;
    private boolean cc;
    private int aId;

    public Showtime(Timestamp startTime, String movieTitle, boolean cc, int aId) {
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.cc = cc;
        this.aId = aId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public boolean isCc() {
        return cc;
    }

    public int getaId() {
        return aId;
    }

    public static List<Showtime> getAllShowtimes(Movie movie) {
        List<Showtime> result = new ArrayList<>();

        try {
            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT * FROM SHOWTIME1 WHERE TITLE = ?");
            ps1.setString(1, movie.getTitle());
            ResultSet rs1 = ps1.executeQuery();

            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM SHOWTIME2 WHERE TITLE = ?");
            ps2.setString(1, movie.getTitle());
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                while (rs1.next()) {
                    Timestamp timestamp = rs1.getTimestamp("START_TIME");
                    String title = rs1.getString("TITLE");
                    boolean cc = rs2.getBoolean("CC");
                    int aId = rs2.getInt("AID");

                    result.add(new Showtime(timestamp, title, cc, aId));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return result;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
