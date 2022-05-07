package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data_structure.CustomArrayList;
import org.example.entity.AuthenticatedEmployee;
import org.example.entity.Ticket;
import org.example.service.TicketService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;


public class TicketServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // set up writer:
        PrintWriter out = resp.getWriter();
        // this will store the id that we pass in through postman:
        int ticketId;
        int employeeId;
        Timestamp startDate;
        Timestamp endDate;
        resp.setContentType("application/json");

        //check if user is signed in at all
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }

        //check employee status to see what methods are available to them.
        if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
            //employee - get all pending tickets
            if(req.getParameter("e_get_all_pending_tickets") != null) {
                //set response status
                resp.setStatus(200);

                //get all pending tickets for employees and return them
                CustomArrayList<Ticket> allPendingTickets = TicketService.getPendingTickets(AuthenticatedEmployee.authenticatedEmployee.getEmployee_id());
                //check if list is empty; if so, notify user and return
                if(allPendingTickets.isEmpty()) {
                    out.println("You have no pending tickets right now.");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < allPendingTickets.getSize(); i++) {
                    out.println(allPendingTickets.get(i));
                }
                return;
            }
            //employee - get all past tickets
            else if(req.getParameter("e_get_all_past_tickets") != null) {
                //set response status
                resp.setStatus(200);

                //get all past tickets for employees and return them
                CustomArrayList<Ticket> allPastTickets = TicketService.getPastTickets(AuthenticatedEmployee.authenticatedEmployee.getEmployee_id());
                //check if list is empty; if so, notify user and return
                if(allPastTickets.isEmpty()) {
                    out.println("You have no tickets that have been approved or denied yet.");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < allPastTickets.getSize(); i++) {
                    out.println(allPastTickets.get(i));
                }
                return;
            }
            //employee - get all tickets ordered by specific dates
            else if (req.getParameter("e_ticket_start_date") != null &&
                    req.getParameter("e_ticket_end_date") != null) {
                //get employee id, start date, end date from Postman
                employeeId = AuthenticatedEmployee.authenticatedEmployee.getEmployee_id();
                startDate = Timestamp.valueOf(req.getParameter("e_ticket_start_date"));
                endDate = Timestamp.valueOf(req.getParameter("e_ticket_end_date"));

                //set response status
                resp.setStatus(200);

                //get tickets for employee and between specified dates supplied by Postman
                CustomArrayList<Ticket> dateEmployeeTickets = TicketService.getTicketsByDate(employeeId, startDate, endDate);
                //check if list is empty; if so, notify user and return
                if(dateEmployeeTickets.isEmpty()) {
                    out.println("No tickets matching the specific dates were found.");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < dateEmployeeTickets.getSize(); i++) {
                    out.println(dateEmployeeTickets.get(i));
                }
                return;
            }

            //by default, return all tickets for particular employee
            CustomArrayList<Ticket> allEmployeeTickets = TicketService.getAllTicketsByEmployeeId(AuthenticatedEmployee.authenticatedEmployee.getEmployee_id());
            //check if list is empty; if so, notify user and return
            if(allEmployeeTickets.isEmpty()) {
                out.println("No tickets have been submitted yet.");
                return;
            }
            //if not empty, loop through it and display the records
            for (int a = 0; a < allEmployeeTickets.getSize(); a++) {
                out.println(allEmployeeTickets.get(a));
            }
            return;
        }
        else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
            //get tickets by id
            if (req.getParameter("ticket_id") != null) {
                //get id from Postman
                ticketId = Integer.parseInt(req.getParameter("ticket_id"));

                //set response status
                resp.setStatus(200);

                //get ticket by specified id from Postman
                Ticket ticket = TicketService.getTicketById(ticketId);
                if(ticket == null) {
                    out.println("No record found matching Ticket ID " + ticketId + ".");
                    return;
                }

                out.println(ticket);
                return;
            }
            //get tickets by employee id
            else if (req.getParameter("ticket_employee_id") != null) {
                //get employee id from Postman
                employeeId = Integer.parseInt(req.getParameter("ticket_employee_id"));

                //set response status
                resp.setStatus(200);

                //get tickets by employee id specified from Postman
                CustomArrayList<Ticket> employeeTickets = TicketService.getAllTicketsByEmployeeId(employeeId);
                //check if list is empty; if so, notify user and return
                if(employeeTickets.isEmpty()) {
                    out.println("No records found for employee ID " + employeeId + ".");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < employeeTickets.getSize(); i++) {
                    out.println(employeeTickets.get(i));
                }
                return;
            }
            //get pending tickets
            else if (req.getParameter("pending_tickets_by_employee_id") != null) {
                //get employee id from Postman
                employeeId = Integer.parseInt(req.getParameter("pending_tickets_by_employee_id"));

                //set response status
                resp.setStatus(200);

                //get past tickets by employee id specified from Postman
                CustomArrayList<Ticket> pendingEmployeeTickets = TicketService.getPendingTickets(employeeId);
                //check if list is empty; if so, notify user and return
                if(pendingEmployeeTickets.isEmpty()) {
                    out.println("No pending tickets found for employee ID " + employeeId);
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < pendingEmployeeTickets.getSize(); i++) {
                    out.println(pendingEmployeeTickets.get(i));
                }
                return;
            }
            //get past tickets
            else if (req.getParameter("past_tickets_by_employee_id") != null) {
                //get employee id from Postman
                employeeId = Integer.parseInt(req.getParameter("past_tickets_by_employee_id"));

                //set response status
                resp.setStatus(200);

                //get past tickets by employee id specified from Postman
                CustomArrayList<Ticket> pastEmployeeTickets = TicketService.getPastTickets(employeeId);
                //check if list is empty; if so, notify user and return
                if(pastEmployeeTickets.isEmpty()) {
                    out.println("No past tickets found for employee ID " + employeeId);
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < pastEmployeeTickets.getSize(); i++) {
                    out.println(pastEmployeeTickets.get(i));
                }
                return;
            }
            //get tickets between dates
            else if (req.getParameter("tickets_by_employee_id") != null &&
                    req.getParameter("ticket_start_date") != null &&
                    req.getParameter("ticket_end_date") != null) {
                //get employee id, start date, end date from Postman
                employeeId = Integer.parseInt(req.getParameter("tickets_by_employee_id"));
                startDate = Timestamp.valueOf(req.getParameter("ticket_start_date"));
                endDate = Timestamp.valueOf(req.getParameter("ticket_end_date"));

                //set response status
                resp.setStatus(200);

                //get tickets for employee and between specified dates supplied by Postman
                CustomArrayList<Ticket> dateEmployeeTickets = TicketService.getTicketsByDate(employeeId, startDate, endDate);

                //check if list is empty; if so, notify user and return
                if(dateEmployeeTickets.isEmpty()) {
                    out.println("No tickets matching the specified dates were found for Employee " + employeeId + ".");
                    return;
                }

                //if not empty, loop through it and display the records
                for (int i = 0; i < dateEmployeeTickets.getSize(); i++) {
                    out.println(dateEmployeeTickets.get(i));
                }
                return;
            }
            //get all pending tickets
            else if(req.getParameter("get_all_pending_tickets") != null) {
                //set response status
                resp.setStatus(200);

                //get all pending tickets for employees and return them
                CustomArrayList<Ticket> allPendingTickets = TicketService.getAllPendingTickets();
                //check if list is empty; if so, notify user and return
                if(allPendingTickets.isEmpty()) {
                    out.println("No pending ticket records found.");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < allPendingTickets.getSize(); i++) {
                    out.println(allPendingTickets.get(i));
                }
                return;
            }
            //get all past tickets
            else if(req.getParameter("get_all_past_tickets") != null) {
                //set response status
                resp.setStatus(200);

                //get all past tickets for employees and return them
                CustomArrayList<Ticket> allPastTickets = TicketService.getAllPastTickets();
                //check if list is empty; if so, notify user and return
                if(allPastTickets.isEmpty()) {
                    out.println("No past ticket records found.");
                    return;
                }
                //if not empty, loop through it and display the records
                for (int i = 0; i < allPastTickets.getSize(); i++) {
                    out.println(allPastTickets.get(i));
                }
                return;
            }

            //set response status
            resp.setStatus(200);

            //if parameters are empty above, return all tickets
            CustomArrayList<Ticket> allTickets = TicketService.getAllTickets();
            //check if list is empty; if so, notify user and return
            if(allTickets.isEmpty()) {
                out.println("No records for tickets found.");
                return;
            }
            //if not empty, loop through it and display the records
            for (int x = 0; x < allTickets.getSize(); x++) {
                out.println(allTickets.get(x));
            }
        }else {
            resp.setStatus(403);
            out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
            return;
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        // mapping the json request to a Java object.
        ObjectMapper mapper = new ObjectMapper();

        //check if user is signed in at all
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }

        //check if employee or manager
        if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
            //get the values from the json
            Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);

            //assign the employee ticket id
            ticket.setTicket_employee_id(AuthenticatedEmployee.authenticatedEmployee.getEmployee_id());
            //assign the ticket status
            ticket.setTicket_status("pending");

            // call the service and return a response based on the boolean
            boolean inserted = TicketService.insertTicket(ticket);
            if (inserted) {
                resp.setStatus(201);
                out.print("Ticket submitted!");
            } else {
                resp.setStatus(500);
                out.print("Error: Could not submit ticket!");
            }
            return;
        }
        else if (AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")) {
            //get the values from json
            Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);

            // call the service and return a response based on the boolean
            boolean inserted = TicketService.insertTicket(ticket);
            if (inserted) {
                resp.setStatus(201);
                out.print("Record inserted!");
            } else {
                resp.setStatus(500);
                out.print("Error: Could not insert record!");
            }
        }else {
            resp.setStatus(403);
            out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        //check if user is signed in at all
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }

        //check employee type
        if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
            //by default, return an error
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou are not allowed.");
            return;
        }
        else if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")){
            //map the json request to java object
            ObjectMapper mapper = new ObjectMapper();
            //get the values from json
            Ticket ticket = mapper.readValue(req.getReader(), Ticket.class);
            //call the service and return a response based on the boolean
            boolean updated = TicketService.updateTicket(ticket);
            if (updated) {
                resp.setStatus(200);
                out.print("Record updated!");
            } else {
                resp.setStatus(500);
                out.print("Error: Could not update record!");
            }
        }else {
            resp.setStatus(403);
            out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
        }
    }


    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();

        //check if user is signed in at all
        if(AuthenticatedEmployee.authenticatedEmployee == null) {
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou must be signed in to perform this action!");
            return;
        }

        //check employee type
        if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("employee")) {
            //by default, return an error
            resp.setStatus(401);
            out.print("401: Unauthorized\n\nYou are not allowed.");
        }
        else if(AuthenticatedEmployee.authenticatedEmployee.getEmployee_type().equals("manager")){
            // get the id from the request parameter:
            int idToDelete = Integer.parseInt(req.getParameter("ticket_id"));

            // call the service:
            boolean success = TicketService.deleteTicket(idToDelete);

            // check if deletion was successful and send the appropriate response:
            if (success) {
                resp.setStatus(200);
                out.write("Record deleted successfully!");
            } else {
                resp.setStatus(500);
                out.write("Error: Could not delete record!");
            }
        }else {
            resp.setStatus(403);
            out.print("403: Forbidden\n\nCould not validate employee type. Please contact Human Resources.");
        }
    }
}
