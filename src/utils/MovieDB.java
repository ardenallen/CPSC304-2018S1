package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDB {

    private OracleConnection oracle = new OracleConnection();

    // This returns the movie's info (duration, genre and censor) given a movieTitle
    public MovieInfo getMovieInfo (String movieTitle) {
        //String SQL = "SELECT Title, Duration, Genre, Censor FROM Movie WHERE Title = ?";
        //String SQL = "SELECT Title, Duration, Genre, Censor FROM Movie WHERE Title = '" + movieTitle + "'";
        String SQL = "SELECT Title, Duration, Genre, Censor FROM Movie WHERE Duration = ?";
        MovieInfo result = null;
        try {
            oracle.connect();
            PreparedStatement ps = oracle.conn.prepareStatement(SQL);
            ps.setInt(1, 140);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                String genre = rs.getString("Genre");
                String censor = rs.getString("Censor");
                System.out.println(title);
                result = new MovieInfo(duration, genre, censor);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } return result;
    }
}
