package utils;

import model.MovieInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDB {

    private OracleConnection oracle;

    public MovieDB() {
        this.oracle = new OracleConnection();
    }

    // This returns the movie's info (duration, genre and censor) given a movieTitle
    public MovieInfo getMovieInfo (String movieTitle) {
        MovieInfo result = null;

        try {
            this.oracle.connect();
            Connection con = this.oracle.returnConnection();
            // This PreparedStatement works:
            PreparedStatement ps = con.prepareStatement("SELECT Title, Duration, Genre, Censor " +
                    "FROM Movie " + "WHERE Title = ?");

            ps.setString(1, "Tag");

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
        }
        return result;
    }
}
