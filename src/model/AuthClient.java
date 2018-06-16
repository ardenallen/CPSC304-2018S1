package model;

import utils.OracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Used exclusively to see if user exists in DB
 */
public class AuthClient {
    public static boolean tryAuth(User user) {
        Connection conn = OracleConnection.buildConnection();

        String tableToQuery;

        String customerTableQuery = "SELECT * FROM CUSTOMER WHERE CID = ?";
        String employeeTableQuery = "SELECT * FROM EMPLOYEE WHERE EID = ?";

        switch (user.getUserClass()) {
            case "customer":
                tableToQuery = customerTableQuery;
                break;

            default:
                tableToQuery = employeeTableQuery;
                break;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(tableToQuery);

            ps.setInt(1, user.getUserId());

            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        // return false by default
        return false;
    }
}
