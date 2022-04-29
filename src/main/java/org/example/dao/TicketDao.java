package org.example.dao;

import org.example.entity.Ticket;

import java.util.List;

//this interface contains all the methods used to access the database erdb -> tickets table
public interface TicketDao {
    // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
    public void insertTicket(Ticket ticket);
    public Ticket getTicketById(int id);
    public List<Ticket> getAllTicketsByEmployeeId(int employeeId);
    public List<Ticket> getAllTickets();
    public List<Ticket> getPendingTickets(int employeeId);
    public List<Ticket> getPastTickets(int employeeId);
    public List<Ticket> getTicketsByDate(int employeeId);
    public void updateTicket(Ticket ticket);
    public void deleteTicket(int id);

}
