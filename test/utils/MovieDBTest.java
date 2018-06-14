package utils;

import org.junit.Assert;
import org.junit.Test;

public class MovieDBTest {
    private MovieDB movieDB;


    @Test
    public void testGetMovieInfo() {
        movieDB = new MovieDB();
        MovieInfo actual = movieDB.getMovieInfo("Tag");
        MovieInfo expected = new MovieInfo(100, "Comedy", "14A");
        Assert.assertTrue(expected.equals(actual));
    }
}
