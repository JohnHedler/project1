package org.example.service;

import org.example.dao.DaoFactory;
import org.example.dao.TicketDao;
import org.example.data_structure.CustomArrayList;
import org.example.entity.Ticket;

import java.sql.Timestamp;

public class TicketService {

    public static Ticket insertTicket(Ticket ticket) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.insertTicket(ticket);
        return ticket;
    }

    public static Ticket getTicketById(int id) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getTicketById(id);
    }

    public static CustomArrayList<Ticket> getAllTicketsByEmployeeId(int id) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getAllTicketsByEmployeeId(id);
    }

    public static CustomArrayList<Ticket> getAllTickets() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getAllTickets();
    }

    public static CustomArrayList<Ticket> getPendingTickets(int id) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getPendingTickets(id);
    }

    public static CustomArrayList<Ticket> getPastTickets(int id) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getPastTickets(id);
    }

    public static CustomArrayList<Ticket> getTicketsByDate(int id, Timestamp startDate, Timestamp endDate) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        return ticketDao.getTicketsByDate(id, startDate, endDate);
    }

    public static Ticket updateTicket(Ticket ticket) {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        // update the Ticket:
        ticketDao.updateTicket(ticket);
        return ticket;
    }

    public static boolean deleteTicket(int id) {
        TicketDao TicketDao = DaoFactory.getTicketDao();
        // update the Ticket, return whether deletion was successful:
        return TicketDao.deleteTicket(id);
    }
}
