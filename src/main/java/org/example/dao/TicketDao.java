package org.example.dao;

import org.example.data_structure.CustomArrayList;
import org.example.entity.Ticket;

import java.sql.Timestamp;

//this interface contains all the methods used to access the database erdb -> tickets table
public interface TicketDao {
    // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
    public void insertTicket(Ticket ticket);
    public Ticket getTicketById(int id);
    public CustomArrayList<Ticket> getAllTicketsByEmployeeId(int employeeId);
    public CustomArrayList<Ticket> getAllTickets();
    public CustomArrayList<Ticket> getPendingTickets(int employeeId);
    public CustomArrayList<Ticket> getPastTickets(int employeeId);
    public CustomArrayList<Ticket> getTicketsByDate(int employeeId, Timestamp startDate, Timestamp endDate);
    public boolean updateTicket(Ticket ticket);
    public boolean deleteTicket(int id);

}
