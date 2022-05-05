package org.example.dao;

import org.example.entity.Ticket;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.StreamHandler;

//this interface contains all the methods used to access the database erdb -> tickets table
public interface TicketDao {
    // Methods that interact with the database (CRUD - Create, Read, Update, Delete)
    public void insertTicket(Ticket ticket);
    public Ticket getTicketById(int id);
    public List<Ticket> getAllTicketsByEmployeeId(int employeeId);
    public List<Ticket> getAllTickets();
    public List<Ticket> getPendingTickets(int employeeId);
    public List<Ticket> getPastTickets(int employeeId);
    public List<Ticket> getTicketsByDate(int employeeId, Timestamp startDate, Timestamp endDate);
    public boolean updateTicket(Ticket ticket);
    public boolean deleteTicket(int id);

}
