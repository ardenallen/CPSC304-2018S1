package model;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeTest {
    //private Employee employee = new Employee(1234);

    @Test
    public void testGetMovieInfo() {
        int ticketSold = Employee.ticketSoldPerMoviePerShowtime("Incredibles 2", "2018-06-22 18:30:00");
        Assert.assertTrue(ticketSold == 34);
    }
}
