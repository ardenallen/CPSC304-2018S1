package model;

public class EmployeeStat {
    public String eId;
    public String name;
    public int sales; // per day

    public EmployeeStat(String eId, String name, int sales) {
        this.eId = eId;
        this.name = name;
        this.sales = sales;
    }
}
