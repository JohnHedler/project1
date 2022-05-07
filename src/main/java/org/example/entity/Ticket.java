package org.example.entity;

import java.sql.Timestamp;

public class Ticket {
    //region ticket variables
    private int ticket_id;
    private int ticket_employee_id;
    private double ticket_amount;
    private String ticket_description;
    private Timestamp ticket_date;
    private String ticket_status;
    //endregion

    //region constructor
    public Ticket() {super();};

    public Ticket(int id, int employeeId, double amount, String description, Timestamp date, String status) {
        this.ticket_id = id;
        this.ticket_employee_id = employeeId;
        this.ticket_amount = amount;
        this.ticket_description = description;
        this.ticket_date = date;
        this.ticket_status = status;
    }

    public Ticket(int employeeId, double amount, String description, Timestamp date, String status) {
        this.ticket_employee_id = employeeId;
        this.ticket_amount = amount;
        this.ticket_description = description;
        this.ticket_date = date;
        this.ticket_status = status;
    }
    //endregion

    //region ticket properties

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getTicket_employee_id() {
        return ticket_employee_id;
    }

    public void setTicket_employee_id(int ticket_employee_id) {
        this.ticket_employee_id = ticket_employee_id;
    }

    public double getTicket_amount() {
        return ticket_amount;
    }

    public void setTicket_amount(double ticket_amount) {
        this.ticket_amount = ticket_amount;
    }

    public String getTicket_description() {
        return ticket_description;
    }

    public void setTicket_description(String ticket_description) {
        this.ticket_description = ticket_description;
    }

    public Timestamp getTicket_date() {
        return ticket_date;
    }

    public void setTicket_date(Timestamp ticket_date) {
        this.ticket_date = ticket_date;
    }

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }


    //endregion


    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", ticket_employee_id=" + ticket_employee_id +
                ", ticket_amount=" + ticket_amount +
                ", ticket_description='" + ticket_description + '\'' +
                ", ticket_date=" + ticket_date +
                ", ticket_status='" + ticket_status + '\'' +
                '}';
    }
}
