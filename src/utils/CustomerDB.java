package utils;
import model.MovieInfo;

import java.sql.*;

public class CustomerDB {
    private OracleConnection oracle;

    public CustomerDB() {
        this.oracle = new OracleConnection();
    }

    public MovieInfo getMovieInfo(String movieTitle) {
        MovieInfo result = null;

        try {
            this.oracle.connect();
            Connection con = this.oracle.returnConnection();
            // This PreparedStatement works:
            PreparedStatement ps = con.prepareStatement("SELECT Title, Duration, Genre, Censor " +
                        "FROM Movie " + "WHERE Title = ?");

            ps.setString(1, "Tag");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
