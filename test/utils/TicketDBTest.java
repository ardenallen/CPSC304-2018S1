package utils;

import org.junit.Test;

public class TicketDBTest {
    public TicketDB ticketDB;

    @Test
    public void testTicketSoldPerMoviePerShowtime() {
        ticketDB = new TicketDB();
        int ticketsSold = ticketDB.ticketSoldPerMoviePerShowtime("The Incredibles 2", "2018-06-15 12:15:00");

    }

}
