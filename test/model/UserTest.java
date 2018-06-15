package model;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    private User user = new User("TestUser", 1234);

    @Test
    public void testGetMovieInfo() {
        MovieInfo actual = user.getMovieInfo("Tag");
        MovieInfo expected = new MovieInfo("Tag", 100, "Comedy","14A");
        Assert.assertTrue(actual.equals(expected));
    }
}
