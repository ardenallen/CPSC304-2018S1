package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDB {

    private OracleConnection oracle = new OracleConnection();

    // This returns the movie's info (duration, genre and censor) given a movieTitle
    public MovieInfo getMovieInfo (String movieTitle) {
        MovieInfo result = null;
        try {
            oracle.connect();
            // This PreparedStatement works:
            PreparedStatement ps = oracle.conn.prepareStatement(
                    "SELECT Title, Duration, Genre, Censor FROM Movie " +
                            "WHERE Title = 'Tag'");

            // This one does not;
            /* PreparedStatement ps = oracle.conn.prepareStatement(
                    "SELECT Title, Duration, Genre, Censor FROM Movie " +
                            "WHERE Title = 'Tag'");
            ps.setString(1, movieTitle);
            */

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
