package model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {

    public Manager(int userId) {
        super(userId);
    }

    public static boolean addMovie(String title, int duration, String genre, String censor) {
         try {
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO MOVIE " +
                             "(TITLE, DURATION, GENRE, CENSOR) " +
                             "VALUES(?,?,?,?)");
             ps.setString(1, title);
             ps.setInt(2, duration);
             ps.setString(3, genre);
             ps.setString(4, censor);
             ps.executeUpdate();

             return true;
         } catch (SQLException ex) {
             System.out.println("Message: " + ex.getMessage());
             System.out.println("Adding " + title + " to Movie table failed.");
         }

         return false;
    }

    public static boolean removeMovie (String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE MOVIE WHERE Title = ?");
            ps.setString(1, title);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing " + title + " from Movie table failed.");
        }

        return false;
    }

    public static boolean addShowtime(Timestamp startTime, String title, boolean cc, int aID) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO SHOWTIME1 " +
                            "(START_TIME, TITLE, AID) " +
                            "VALUES(?,?,?)");
            ps.setTimestamp(1, startTime);
            ps.setString(2, title);
            ps.setInt(3, aID);
            ps.executeUpdate();

            ps = conn.prepareStatement(
                    "INSERT INTO SHOWTIME2 " +
                            "(CC, TITLE, AID) " +
                            "VALUES(?,?,?)");
            ps.setBoolean(1, cc);
            ps.setString(2, title);
            ps.setInt(3, aID);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Adding " + startTime + "for movie " + title +  " failed.");
        }

        return false;
    }

    public static boolean removeShowtime (Timestamp startTime, String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE SHOWTIME1 " +
                            "WHERE START_TIME = ? AND TITLE = ?");
            ps.setTimestamp(1, startTime);
            ps.setString(2, title);
            ps.executeUpdate();

            List<Showtime> allTimes = Showtime.getAllShowtimes(title);

            if (allTimes.isEmpty()) {
                ps = conn.prepareStatement(
                        "DELETE SHOWTIME2 " +
                                "WHERE TITLE = ?");
                ps.setString(1, title);
                ps.executeUpdate();
            }

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing " + startTime + " for Movie " + title + " table failed.");
        }

        return false;
    }

    public static boolean addEmployee(int userId, String name, int SIN, String phone) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO EMPLOYEE " +
                            "(EID, NAME, SIN, PHONE) " +
                            "VALUES(?,?,?,?)");
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setInt(3, SIN);
            ps.setString(4, phone);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Adding employee " + name + " failed.");
        }

        return false;
    }

    public static boolean removeEmployee (int userID) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE EMPLOYEE " +
                            "WHERE EID = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Removing employee " + userID + " failed.");
        }

        return false;
    }

    public static boolean updateEmployee (int eId, String name, String phone) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE EMPLOYEE SET NAME = ?, PHONE = ? WHERE EID = ?");
            ps.setString(1,name);
            ps.setString(2, phone);
            ps.setInt(3, eId);
            ps.execute();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Updating employee " + eId + " failed.");
        }

        return false;
    }

    public static boolean promoteEmployeeToManager(int eId) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO MANAGER VALUES ?");
            ps.setInt(1, eId);
            ps.execute();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Updating employee " + eId + " failed.");
        }

        return false;
    }

    public static boolean demoteManagerToEmployee(int eId) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM MANAGER WHERE EID = ?");
            ps.setInt(1, eId);
            ps.execute();

            return true;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            System.out.println("Updating employee " + eId + " failed.");
        }

        return false;
    }

    public static int getNextAvailableEid() {
        int eId = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT max(EID) FROM EMPLOYEE");
            if (rs.next()) {
                eId = rs.getInt(1) + 1;
            }
            stmt.close();

            return eId;
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return eId;
    }

    public static Employee getEmployeeFromId(int eId) {
        return new Employee(eId);
    }

    public static List<Employee> getAllEmployee() {
        List<Employee> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM EMPLOYEE");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int eId = rs.getInt("eID");
                Employee employee = new Employee(eId);
                result.add(employee);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // Returns all the Employee who generated the max (most sales) or min (least sales) amount of ticket sales
    // Will not take into account of employee who did not sell any ticket on the given date
    public static List<EmployeeStat> getLeastMostSalesEmployee(String minMax, Date date) {
        List<EmployeeStat> result = new ArrayList<>();
        BigDecimal minMaxSales = Manager.getLeastMostSales(minMax, date);
        List<Integer> eIDs = Manager.getEmployeeIDFromSales(minMaxSales, date);
        String sqlEIDs = "";
        for (int eID : eIDs) {
            sqlEIDs += eID + ", ";
        }
        sqlEIDs = sqlEIDs.substring(0, sqlEIDs.length() - 2);
        String SQL = "SELECT * FROM EMPLOYEE " +
                "WHERE EID IN (%s)";
        SQL = String.format(SQL, sqlEIDs);

        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int eID = rs.getInt("EID");
                String name = rs.getString("Name");
                EmployeeStat x = new EmployeeStat(eID, name, minMaxSales);
                result.add(x);
            }

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }


    private static BigDecimal getLeastMostSales(String minMax, Date date) {
        BigDecimal result = new BigDecimal(-1);
        String SQL = "SELECT %s(SALES) FROM (" +
                "SELECT EID, SUM(PRICE) AS SALES " +
                "FROM TICKET T, BOOKING B " +
                "WHERE TRUNC(START_TIME) = ? "+
                "AND T.TRANSACTION = B.TRANSACTION " +
                "GROUP BY EID)";
        SQL = minMax.equalsIgnoreCase("Min")?
                String.format(SQL, "MIN"):
                String.format(SQL,"MAX");
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result = rs.getBigDecimal(1);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    // Extract employee ID given the date and amount of sales they generated
    private static List<Integer> getEmployeeIDFromSales(BigDecimal sales, Date date) {
        List<Integer> result = new ArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT EID, SUM(PRICE)" +
                            "FROM TICKET T, BOOKING B " +
                            "WHERE TRUNC(START_TIME) = ? "+
                            "AND T.TRANSACTION = B.TRANSACTION " +
                            "GROUP BY EID");
            ps.setDate(1, date);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                BigDecimal sum = rs.getBigDecimal(2);
                if (sum.compareTo(sales) == 0) {
                    int eID = rs.getInt("eID");
                    result.add(eID);
                }
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }

    public static List<Customer> getBestCustomer() {
        List<Customer> result = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT CID FROM CUSTOMER " +
                            "WHERE NOT EXISTS ((SELECT TITLE FROM MOVIE) " +
                            "MINUS (SELECT TITLE FROM TICKET " +
                            "WHERE EXISTS (SELECT * FROM BOOKING " +
                            "WHERE CUSTOMER.CID=BOOKING.CID AND BOOKING.TRANSACTION=TICKET.TRANSACTION)))");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int cId = rs.getInt("cID");
                Customer best = new Customer(cId);
                result.add(best);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return result;
    }


}
