package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data_structure.CustomArrayList;
import org.example.entity.Ticket;
import org.example.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class TicketServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        // set up writer:
        PrintWriter out = res.getWriter();
        // this will store the id that we pass in through postman:
        int idToGet;
        try {
            // try to parse the id from parameters. If it fails, that means we didn't pass one in:
            idToGet = Integer.parseInt(req.getParameter("ticket_employee_id"));
        } catch (NumberFormatException e) {
            // if we didn't pass in an id, we want all books:
            CustomArrayList<Ticket> tickets = TicketService.getAllTickets();
            out.print(tickets);
            return;
        }

        // if the catch block didn't trigger, that means we did pass in an id so we can use that to get a specific book:
        Ticket ticket = TicketService.getTicketById(idToGet);
        out.print(ticket);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // mapping the json request to a Java object.
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);
        // call the service and return the ticket:
        Ticket result = TicketService.insertTicket(ticket);
        PrintWriter out = res.getWriter();
        out.write(result.toString());
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);

        Ticket result = TicketService.updateTicket(ticket);
        PrintWriter out = res.getWriter();
        out.write(result.toString());
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();

        // get the id from the request parameter:
        int idToDelete = Integer.parseInt(req.getParameter("ticket_employee_id"));
        // call the service:
        boolean success = TicketService.deleteTicket(idToDelete);
        // check if deletion was successful and send the appropriate response:
        if(success) {
            out.write("Deleted successfully!");
        }
        else {
            out.write("Deletion failed!");
        }


    }



    
}
