package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDB {

    private OracleConnection oracle;

    // This returns the number of tickets show given the Movie Title and the start time of the Showtime
    public int ticketSoldPerMoviePerShowtime (String movieTitlle, String showTime) throws SQLException {
        oracle.connect();
        try {
            PreparedStatement ps = oracle.conn.prepareStatement(
                    "SELECT COUNT(*) FROM TICKET " +
                    "WHERE TITLE = ? AND START_TIME = ?");
            ps.setString(1, movieTitlle);
            ps.setString(2, showTime);
            ResultSet rs = ps.executeQuery();

            return rs.getFetchSize();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }
}
