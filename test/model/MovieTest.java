package model;

import org.junit.Assert;
import org.junit.Test;

public class MovieTest {

    @Test
    public void testGetAllMovies() {
        int size = Movie.getAllMovie().size();
        Assert.assertEquals(size, 13);
    }
}
