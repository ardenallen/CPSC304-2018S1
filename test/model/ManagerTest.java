package model;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.util.List;


public class ManagerTest {


    @Test
    public void testGetEmployeeStats() {

        Date date = Date.valueOf("2018-06-22");
        List<Employee> max = Manager.getLeastMostSalesEmployee("MAX", date);
        List<Employee> min = Manager.getLeastMostSalesEmployee("MIN", date);
        Assert.assertNotEquals(max.get(0).getUserId(), min.get(0).getUserId());
    }

    @Test
    public void testGetBestEmployee() {

        List<Customer> best = Manager.getBestCustomer();
        int bestID = best.get(0).getUserId();
        String bestName = best.get(0).getName();
        Assert.assertEquals(bestID, 2);
        Assert.assertEquals(bestName, "Katelyn Austin");
        bestID = best.get(1).getUserId();
        bestName = best.get(1).getName();
        Assert.assertEquals(bestID, 29);
        Assert.assertEquals(bestName, "Hedley Howell");
    }
}
