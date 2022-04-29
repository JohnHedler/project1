package org.example.service;

import org.example.dao.DaoFactory;
import org.example.dao.TicketDao;
import org.example.entity.Ticket;
import org.example.utilities.InputUtilities;

import java.util.List;

public class TicketService {
    //take input and put a new employee into the database
    public static void insertTicket(int employeeId) {
        boolean validated = false;
        double amount = 0;
        String description = "";
        String status = "pending";

        //loop until amount is validated and is the proper length
        do {
            System.out.print("Please enter the request amount: ");
            amount = InputUtilities.ValidateDouble();

            //validate length
            if (String.valueOf(amount).length() > 9) {
                validated = false;
                System.out.println("*Amount is too long (limit 9 numbers)!");
            } else {
                validated = true;
            }
        } while (!validated);

        //loop until description is validated and is the proper length
        do {
            System.out.println("Please enter the reason for request below. ");
            description = InputUtilities.ValidateString();

            //validate length
            if (description.length() > 250) {
                validated = false;
                System.out.println("*Reason is too long (limit 250 characters)!");
            } else {
                validated = true;
            }
        } while (!validated);

        //create new ticket object
        Ticket ticket = new Ticket(employeeId, amount, description, null, status);

        //call dao factory to get the ticket dao and send the ticket object into it
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.insertTicket(ticket);
    }

    public static void getTicketById() {
        System.out.println("Please enter the id of the ticket: ");
        int id = InputUtilities.ValidateInteger();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket ticket = ticketDao.getTicketById(id);
        System.out.println("Ticket found:\n" + ticket.toString());
    }

    public static void getAllTickets() {
        System.out.println("All tickets:");
        TicketDao ticketDao = DaoFactory.getTicketDao();
        List<Ticket> tickets = ticketDao.getAllTickets();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public static void getPendingTickets(int employeeId) {
        System.out.println("Pending tickets:");
        TicketDao ticketDao = DaoFactory.getTicketDao();
        List<Ticket> tickets = ticketDao.getPendingTickets(employeeId);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public static void getPastTickets(int employeeId) {
        System.out.println("All Approved/Rejected tickets:");
        TicketDao ticketDao = DaoFactory.getTicketDao();
        List<Ticket> tickets = ticketDao.getPastTickets(employeeId);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    //TODO: GET A FUNCTION TO HANDLE DATES
    public static void getTicketsByDate(int employeeId) {
        System.out.println("All Tickets by date:");
        TicketDao ticketDao = DaoFactory.getTicketDao();
        List<Ticket> tickets = ticketDao.getTicketsByDate(employeeId);
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

    public static void updateTicket() {
        boolean validated = false;
        double amount = 0;
        String description = "";
        String status = "pending";

        //ask for ticket id
        System.out.println("Update Ticket> Please enter the id for the ticket: ");
        int id = InputUtilities.ValidateInteger();

        //ask for employee id
        System.out.println("Update Ticket> Please enter the id for the employee: ");
        int employeeId = InputUtilities.ValidateInteger();

        //loop until amount is validated and is the proper length
        do {
            System.out.print("Update Ticket> Please enter the request amount: ");
            amount = InputUtilities.ValidateDouble();

            //validate length
            if (String.valueOf(amount).length() > 9) {
                validated = false;
                System.out.println("*Amount is too long (limit 9 numbers)!");
            } else {
                validated = true;
            }
        } while (!validated);

        //loop until description is validated and is the proper length
        do {
            System.out.println("Update Ticket> Please enter the updated reason below. ");
            description = InputUtilities.ValidateString();

            //validate length
            if (description.length() > 250) {
                validated = false;
                System.out.println("*Reason is too long (limit 250 characters)!");
            } else {
                validated = true;
            }
        } while (!validated);

        //loop until status is validated and is the proper length
        do {
            System.out.println("Update Ticket> Please enter the updated ticket status: ");
            status = InputUtilities.ValidateString();

            //validate length
            if (status.length() > 50) {
                validated = false;
                System.out.println("*Status is too long (limit 50 characters)!");
            } else {
                validated = true;
            }
        } while (!validated);

        //create new ticket object
        Ticket ticket = new Ticket(id, employeeId, amount, description, null, status);

        //call dao factory to get the ticket dao and send the ticket object into it
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.updateTicket(ticket);
    }

    public static void deleteTicket() {
        System.out.println("Please enter the id of the ticket to be deleted: ");
        int id = InputUtilities.ValidateInteger();
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.deleteTicket(id);
    }
}
