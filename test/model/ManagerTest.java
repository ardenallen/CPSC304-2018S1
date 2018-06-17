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
        Assert.assertNotEquals(max.get(0).eID, min.get(0).eID);
    }
}
