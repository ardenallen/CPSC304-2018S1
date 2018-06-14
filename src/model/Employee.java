package model;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Employee extends User {
    public Employee(int userId) {
        super("employee", userId);
    }
}
