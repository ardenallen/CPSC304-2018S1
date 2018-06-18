package model;

import utils.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private static Connection conn = OracleConnection.buildConnection();
    private String title;
    private int duration; // in minutes
    private String genre;
    private String censor;

    public Movie(String title, int duration, String genre, String censor) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.censor = censor;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getCensor() {
        return censor;
    }

    public static List<Movie> getAllMovie() {
        List<Movie> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM MOVIE");
            ResultSet rs = ps.executeQuery();
            result = createMoviesFromResultSet(rs);
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public static Movie findMovieByTitle(String movieTitle) {
        List<Movie> result = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM MOVIE WHERE TITLE = ?");
            ps.setString(1, movieTitle);
            ResultSet rs = ps.executeQuery();
            result = createMoviesFromResultSet(rs);
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return result.get(0);
    }

    public static List<Movie> createMoviesFromResultSet(ResultSet rs){
        List<Movie> result = new ArrayList<>();
        try {
            while (rs.next()) {
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                Movie movie = new Movie(title, duration, genre, censor);
                result.add(movie);
            }
        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public boolean equals(Movie movie) {
        return  movie.title.equals(this.title) &&
                movie.duration == this.duration &&
                movie.genre.equals(this.genre) &&
                movie.censor.equals(this.censor);
    }
}
