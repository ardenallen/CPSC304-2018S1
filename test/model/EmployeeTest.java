package model;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTest {
    //private Employee employee = new Employee(1234);

    @Ignore
    @Test
    public void testTicketSoldPerMoviePerShowtime() {
        int ticketSold = Employee.ticketSoldPerMoviePerShowtime("Incredibles 2", "2018-06-22 18:30:00");
        Assert.assertTrue(ticketSold == 34);
    }

    @Test
    public void testGetBooking() {
        BigDecimal price = new BigDecimal(13.25);
        Timestamp expectedTime = Timestamp.valueOf("2018-06-22 18:30:00");
        Ticket expectedTicket = new Ticket(1, price, "45685176799", "Incredibles 2", new Timestamp(expectedTime.getTime()), 10);
        List<Ticket> expectedTickets = new ArrayList<>();
        expectedTickets.add(expectedTicket);
        Booking expectedBooking = new Booking("45685176799", "Credit", "5531584671644409", 32, 49, expectedTickets);
        Booking actualBooking = Employee.getBooking("45685176799");
        Ticket actualTicket = actualBooking.getTickets().get(0);
        Assert.assertTrue(actualTicket.getTicketNum() == expectedTicket.getTicketNum());
        Assert.assertTrue(expectedBooking.getCardInfo().equals(actualBooking.getCardInfo()));
    }

    // This is only for running the refund method, and will actually delete stuff in the Ticket table
    // Please ignore, unless testing for the method
    @Ignore
    @Test
    public void testRefund() {
        Employee.refund("5531584671644409", 1);

    }

    @Test
    public void testGetAllMovieState() {
        List<MovieStat> result = Employee.getAllMovieStats();
        MovieStat first = result.get(0);
        MovieStat last = result.get(result.size() - 1);
        Assert.assertTrue(first.morePopular(last));
    }

    @Test
    public void testTotalBookingPrice() {
        BigDecimal actual = Employee.getTotalBookingPrice("86744569999");
        Assert.assertTrue(actual.compareTo(new BigDecimal(0)) == 1);
    }

    @Ignore
    @Test
    public void testGetLeastMostPopularMovie() {
        List<Movie> min = Employee.getLeastMostPopularMovie("MIN");
        List<Movie> max = Employee.getLeastMostPopularMovie("MAX");

    }
}
