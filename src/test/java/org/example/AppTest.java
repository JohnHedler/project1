package org.example;

import org.example.dao.DaoFactory;
import org.example.dao.EmployeeDao;
import org.example.dao.TestDao;
import org.example.dao.TicketDao;
import org.example.data_structure.CustomArrayList;
import org.example.entity.Employee;
import org.example.entity.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class AppTest {
    private TestDao testDao;

    //should run before each test
    @Before
    public void testInitializeTables() {
        //call dao factory to get test dao to initialize tables
        testDao = DaoFactory.getTestDao();

        //call methods to initialize tables
        testDao.initTables();
        testDao.fillTables();
    }

    //test to insert employee
    @Test
    public void testInsertEmployee() {
        Employee employee = new Employee("Manager", "Jace", "Johnson", "jj123", "90210");
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        employeeDao.insertEmployee(employee);

        assertEquals(5, employee.getEmployee_id());
    }

    //test to get employee's first name after retrieving record from database
    @Test
    public void testGetEmployeeById() {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeById(2);
        System.out.println(employee.toString());

        assertEquals("Gregory", employee.getEmployee_first_name());
    }

    //test to get employee's first name after retrieving record from database
    @Test
    public void testGetEmployeeByUserName() {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeByUserName("gharris");

        assertEquals("employee", employee.getEmployee_type());
    }

    //test to get employee's first name after retrieving record from database
    @Test
    public void testGetAllEmployees() {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        CustomArrayList<Employee> employees = employeeDao.getAllEmployees();

        assertFalse(employees.isEmpty());
    }

    //test to update employee's information
    @Test
    public void testUpdateEmployee() {
        boolean isUpdated = false;
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeById(3);
        employee.setEmployee_type("employee");
        employee.setEmployee_first_name("Sandra");
        employee.setEmployee_last_name("Love");
        employee.setEmployee_username("slove");
        employee.setEmployee_password("9090*");

        isUpdated = employeeDao.updateEmployee(employee);

        assertTrue(isUpdated);
    }

    //test to delete employee from database
    @Test
    public void testDeleteEmployee() {
        boolean isDeleted = false;
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        isDeleted = employeeDao.deleteEmployee(1);

        assertTrue(isDeleted);
    }

    //test to insert ticket
    @Test
    public void testInsertTicket() {
        Ticket ticket = new Ticket(3, 250, "food", null, "pending");
        TicketDao ticketDao = DaoFactory.getTicketDao();
        ticketDao.insertTicket(ticket);

        assertEquals(10, ticket.getTicket_id());
    }

    //test to get ticket by id
    @Test
    public void testGetTicketById() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket ticket = ticketDao.getTicketById(4);
        System.out.println(ticket.toString());

        assertEquals(4, ticket.getTicket_id());
        assertEquals(15.25, ticket.getTicket_amount(), 100);
    }

    //test to get all tickets by specific id
    @Test
    public void testGetAllTicketsByEmployeeId() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        CustomArrayList<Ticket> tickets = ticketDao.getAllTicketsByEmployeeId(20);

        assertTrue(tickets.isEmpty());
    }

    //test to get all tickets
    @Test
    public void testGetAllTickets() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        CustomArrayList<Ticket> tickets = ticketDao.getAllTickets();

        assertFalse(tickets.isEmpty());
    }

    //test to get all pending tickets
    @Test
    public void testGetPendingTickets() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        CustomArrayList<Ticket> tickets = ticketDao.getPendingTickets(1);

        assertEquals("motel room", tickets.get(2).getTicket_description());
    }

    //test to get all approved/denied tickets
    @Test
    public void testGetPastTickets() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        CustomArrayList<Ticket> tickets = ticketDao.getPastTickets(1);

        assertEquals("approved", tickets.get(0).getTicket_status());
        assertEquals("denied", tickets.get(1).getTicket_status());
    }

    //test to get tickets between dates
    @Test
    public void testGetTicketsByDate() {
        TicketDao ticketDao = DaoFactory.getTicketDao();
        CustomArrayList<Ticket> tickets = ticketDao.getTicketsByDate(3, Timestamp.valueOf("2022-05-01 00:00:00"), Timestamp.valueOf("2022-05-05 00:00:00"));
        if (!tickets.isEmpty()) {
            System.out.println(tickets.get(0).getTicket_date());
        } else {
            System.out.println("Empty.");
        }

        Timestamp timestamp = Timestamp.valueOf("2022-05-04 00:00:00.00");
        String substringDate = String.valueOf(timestamp).substring(0, 10);
        String substringResultDate = String.valueOf(tickets.get(0).getTicket_date()).substring(0, 10);

        assertEquals(substringDate, substringResultDate);
    }

    //test for update ticket
    @Test
    public void testUpdateTicket() {
        boolean isUpdated = false;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        Ticket ticket = ticketDao.getTicketById(1);
        ticket.setTicket_employee_id(1);
        ticket.setTicket_amount(200);
        ticket.setTicket_description("motel food");
        ticket.setTicket_date(null);
        ticket.setTicket_status("approved");
        isUpdated = ticketDao.updateTicket(ticket);

        assertTrue(isUpdated);

    }

    //test for delete ticket
    @Test
    public void testDeleteTicket() {
        boolean isUpdated = false;
        TicketDao ticketDao = DaoFactory.getTicketDao();
        isUpdated = ticketDao.deleteTicket(1);

        assertTrue(isUpdated);
    }


}
