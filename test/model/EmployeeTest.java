package model;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeTest {
    private Employee employee = new Employee(1234);

    @Test
    public void testGetMovieInfo() {
        int ticketSold = Employee.ticketSoldPerMoviePerShowtime("Tag", "2018-06-15 12:15:00");
        Assert.assertTrue(ticketSold == 0);
    }
}
