package model;

import utils.OracleConnection;

import java.sql.*;

public class Auditorium {
    public static int getAuditoriumCapacity(int aId) {
        Connection conn = OracleConnection.buildConnection();

        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM AUDITORIUM WHERE AID = ?");

            ps.setInt(1, aId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("CAPACITY");
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return -1;
    }
}
