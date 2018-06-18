package model;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class ManagerTest {

    @Ignore
    @Test
    public void testAddMovie() {

        Movie movie = new Movie("Jurassic World: Fallen Kingdom", 120, "Adventure", "PG");
        Manager.addMovie(movie.getTitle(), movie.getDuration(), movie.getGenre(), movie.getCensor());
        List<Movie> allMovies = Movie.getAllMovie();
        Movie last = allMovies.get(allMovies.size() - 1);
        Assert.assertTrue(last.equals(movie));
    }

    @Ignore
    @Test
    public void testRemoveMovie() {

        Movie movie = new Movie("Jurassic World: Fallen Kingdom", 120, "Adventure", "PG");
        Manager.removeMovie(movie.getTitle());
        List<Movie> allMovies = Movie.getAllMovie();
        Movie last = allMovies.get(allMovies.size() - 1);
        Assert.assertFalse(last.equals(movie));
    }

    @Ignore
    @Test
    public void testAddEmployee() {

        Manager.addEmployee(200, "Arden Allen",000000001, "5055555555");
        Employee emp = new Employee(200);
        List<Employee> allEmps = Manager.getAllEmployee();
        Employee last = allEmps.get(allEmps.size()-1);
        Assert.assertTrue(last.getUserId() == 200);
    }

    @Ignore
    @Test
    public void testRemoveEmployee() {

        Manager.removeEmployee(200);
        List<Employee> allEmps = Manager.getAllEmployee();
        Employee last = allEmps.get(allEmps.size()-1);
        Assert.assertFalse(last.getUserId() == 200);
    }

    @Test
    public void testUpdateEmployee() {

        Manager.updateEmployee(1, "TEST", "0000000000");
        Employee emp = Manager.getEmployeeFromId(1);
        Assert.assertEquals(emp.getName(), "TEST");
        Assert.assertEquals(emp.getPhone(), "0000000000");
        Manager.updateEmployee(1, "A", "abcdefghij");
        Assert.assertEquals(emp.getName(), "TEST");
        Assert.assertEquals(emp.getPhone(), "0000000000");
        Manager.updateEmployee(1, "Francesco Rojel", "6044987637");
    }

    @Test
    public void testGetEmployeeStats() {

        Date date = Date.valueOf("2018-06-22");
        List<Employee> max = Manager.getLeastMostSalesEmployee("MAX", date);
        List<Employee> min = Manager.getLeastMostSalesEmployee("MIN", date);
        Assert.assertNotEquals(max.get(0).getUserId(), min.get(0).getUserId());
    }

    @Test
    public void testGetBestCustomer() {

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
