package test;

import model.*;
import org.junit.Before;
import org.junit.Test;
import utils.OracleConnection;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerTest {
    public static Connection conn = OracleConnection.buildConnection();
    private Customer loyal;  //1: 2718
    private Customer nonLoyal;  //2: -1
    private Movie aMovie;
    private Showtime aShowTime;

    @Before
    public void runBefore() {
        loyal = new Customer(1);
        loyal.setPoint(2718);
        nonLoyal = new Customer(2);
        nonLoyal.cancelMembership();

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM MOVIE WHERE TITLE = ?");
            ps.setString(1, "Tag");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String genre = rs.getString("GENRE");
                int duration = rs.getInt("DURATION");
                String censor = rs.getString("CENSOR");
                aMovie = new Movie(title, duration, genre, censor);
            }

            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM SHOWTIME1 WHERE TITLE = ?");
            ps2.setString(1, "Tag");
            ResultSet rs2 = ps2.executeQuery();


            while (rs2.next()) {
                Timestamp startTime = rs2.getTimestamp("START_TIME");
                String title = rs2.getString("TITLE");
                int aId = rs2.getInt("AID");
                aShowTime = new Showtime(startTime, title, true, aId);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Test
    public void testBuyTickets() {
        loyal.buyTickets(aMovie, aShowTime, 1, "D5531584671644409");
    }

    @Test
    public void testGetAllBookings() {
        Customer c = new Customer(7);
        List<Booking> ret = c.getAllBookings();
        for (int i = 0; i < ret.size(); i++) {
            Booking b = ret.get(i);
            System.out.println(b);
        }
    }

    @Test
    public void testGetAllTickets() {
        Customer c = new Customer(249);
        List<Ticket> ret = c.getAllTickets("10363122199");
        for (int i = 0; i < ret.size(); i++) {
            Ticket t = ret.get(i);
            System.out.println(t);
        }
    }

    @Test
    public void testCustomer() {
        String name1 = loyal.getName();
        String name2 = nonLoyal.getName();

        int point1 = loyal.getPointBalance();
        int point2 = nonLoyal.getPointBalance();

        assertEquals("Tanek Blair", name1);
        assertEquals("Katelyn Austin", name2);

        assertTrue(loyal.isLoyaltyMember());
        assertFalse(nonLoyal.isLoyaltyMember());

        assertTrue(point1 == 2718);
        assertTrue(point2 == -1);
    }

    @Test
    public void testRedeem() {
        loyal.redeem(2);
        assertTrue(loyal.getPointBalance() == 718);
        loyal.redeem(1);
        assertTrue(loyal.getPointBalance() == 718);
    }


    @Test
    public void testSingUpForLoyaltyMember() {
        nonLoyal.signUpForLoyaltyMember();
        assertTrue(nonLoyal.isLoyaltyMember());
        assertTrue(nonLoyal.getPointBalance() == 0);
    }

    @Test
    public void testAddPoint() {
        loyal.addPoint(1);
        assertTrue(loyal.getPointBalance() == 2768);
        loyal.addPoint(2);
        assertTrue(loyal.getPointBalance() == 2868);
    }
}
