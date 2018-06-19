package model;

import java.math.BigDecimal;

public class EmployeeStat {
    public int eId;
    public String name;
    public BigDecimal sales; // per day

    public EmployeeStat(int eId, String name, BigDecimal sales) {
        this.eId = eId;
        this.name = name;
        this.sales = sales;
    }
}
