package utils;

import org.junit.Assert;
import org.junit.Test;

public class TicketDBTest {
    private TicketDB ticketDB;

    @Test
    public void testTicketSoldPerMoviePerShowtime() {
        ticketDB = new TicketDB();
        int ticketsSold = ticketDB.ticketSoldPerMoviePerShowtime("The Incredibles 2", "2018-06-15 12:15:00");
        Assert.assertTrue(ticketsSold == -1);
    }

}
