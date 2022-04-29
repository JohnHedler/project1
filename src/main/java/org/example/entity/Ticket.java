package org.example.entity;

import java.sql.Timestamp;

public class Ticket {
    //region ticket variables
    private int id;
    private int employeeId;
    private double amount;
    private String description;
    private Timestamp date;
    private String status;
    //endregion

    //region constructor
    public Ticket(int id, int employeeId, double amount, String description, Timestamp date, String status) {
        this.id = id;
        this.employeeId = employeeId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public Ticket(int employeeId, double amount, String description, Timestamp date, String status) {
        this.employeeId = employeeId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.status = status;
    }
    //endregion

    //region ticket properties

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employee_id) {
        this.employeeId = employeeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //endregion


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", employee_id=" + employeeId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
