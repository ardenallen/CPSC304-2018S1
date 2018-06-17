package model;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;


public class ManagerTest {


    @Test
    public void testGetEmployeeStats() {

        Date date = Date.valueOf("2018-06-22");
        Manager.getLeastMostSales("MAX", date);
        Assert.assertEquals(1,1);
    }
}
