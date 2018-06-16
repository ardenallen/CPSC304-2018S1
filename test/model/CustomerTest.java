package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CustomerTest {

    @Test
    public void testGetRecommendations() {
        List<String> actual = Customer.getRecommendations();
        Assert.assertTrue(actual.size() == 3);
    }
}
