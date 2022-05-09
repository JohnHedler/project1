package org.example;

import org.example.dao.DaoFactory;
import org.example.dao.TestDao;
import org.example.data_structure.CustomArrayList;
import org.example.entity.AuthenticatedEmployee;
import org.example.entity.Employee;
import org.example.entity.Ticket;
import org.example.service.EmployeeService;
import org.example.service.TicketService;
import org.example.servlets.*;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

        //reset authentication
        AuthenticatedEmployee.authenticatedEmployee = null;
    }

    /*=============================================================================================================
                                            EMPLOYEE / TICKET TESTS
     =============================================================================================================*/

    //test default constructor
    @Test
    public void testDefaultEmployeeConstructor() {
        Employee employee = new Employee();

        assertNotNull(employee);
    }

    //test to insert employee
    @Test
    public void testInsertEmployee() {
        Employee employee = new Employee("Manager", "Jace", "Johnson", "jj123", "90210");
        boolean inserted = EmployeeService.insertEmployee(employee);

        assertEquals(5, employee.getEmployee_id());
        assertTrue(inserted);
    }

    //test to get employee's first name after retrieving record from database
    @Test
    public void testGetEmployeeById() {
        Employee employee = EmployeeService.getEmployeeById(2);
        System.out.println(employee.toString());

        assertEquals("Gregory", employee.getEmployee_first_name());
    }

    //test to get employee's first name after retrieving record from database
    @Test
    public void testGetEmployeeByUserName() {
        Employee employee = EmployeeService.getEmployeeByUserName("gharris");

        assertEquals("employee", employee.getEmployee_type());
    }

    //test to get all employees and see if the list generated is NOT empty
    @Test
    public void testGetAllEmployees() {
        CustomArrayList<Employee> employees = EmployeeService.getAllEmployees();

        assertFalse(employees.isEmpty());
    }

    //test to update employee's information; checks to see if returned value is true
    @Test
    public void testUpdateEmployee() {
        Employee employee = EmployeeService.getEmployeeById(3);
        employee.setEmployee_type("employee");
        employee.setEmployee_first_name("Sandra");
        employee.setEmployee_last_name("Love");
        employee.setEmployee_username("slove");
        employee.setEmployee_password("9090*");

        boolean isUpdated = EmployeeService.updateEmployee(employee);

        assertTrue(isUpdated);
    }

    //test to delete employee from database
    @Test
    public void testDeleteEmployee() {
        boolean isDeleted = EmployeeService.deleteEmployee(1);

        assertTrue(isDeleted);
    }

    //test default constructor
    @Test
    public void testDefaultTicketConstructor() {
        Ticket ticket = new Ticket();

        assertNotNull(ticket);
    }

    //test to insert ticket
    @Test
    public void testInsertTicket() {
        Ticket ticket = new Ticket(3, 250, "food", null, "pending");
        TicketService.insertTicket(ticket);

        assertEquals(10, ticket.getTicket_id());
    }

    //test to insert ticket
    @Test
    public void testInsertTicket2() {
        Ticket ticket = new Ticket(3, 250, "food", null, "pending");
        TicketService.insertTicket(ticket);

        assertEquals(10, ticket.getTicket_id());
    }

    //test to get ticket by id
    @Test
    public void testGetTicketById() {
        Ticket ticket = TicketService.getTicketById(4);
        System.out.println(ticket.toString());

        assertEquals(4, ticket.getTicket_id());
        assertEquals(15.25, ticket.getTicket_amount(), 100);
    }

    //test to get all tickets by specific id
    @Test
    public void testGetAllTicketsByEmployeeId() {
        CustomArrayList<Ticket> tickets = TicketService.getAllTicketsByEmployeeId(20);

        assertTrue(tickets.isEmpty());
    }

    //test to get all tickets
    @Test
    public void testGetAllTickets() {
        CustomArrayList<Ticket> tickets = TicketService.getAllTickets();

        assertFalse(tickets.isEmpty());
    }

    //test to get all pending tickets
    @Test
    public void testGetPendingTicketsByEmployeeId() {
        CustomArrayList<Ticket> tickets = TicketService.getPendingTickets(1);

        assertEquals("motel room", tickets.get(2).getTicket_description());
    }

    //test to get all approved/denied tickets
    @Test
    public void testGetPastTicketsByEmployeeId() {
        CustomArrayList<Ticket> tickets = TicketService.getPastTickets(1);

        assertEquals("approved", tickets.get(0).getTicket_status());
        assertEquals("denied", tickets.get(1).getTicket_status());
    }

    //test to get all pending tickets
    @Test
    public void testGetAllPendingTickets() {
        CustomArrayList<Ticket> tickets = TicketService.getAllPendingTickets();

        assertEquals("motel room", tickets.get(2).getTicket_description());
    }

    //test to get all approved/denied tickets
    @Test
    public void testGetAllPastTickets() {
        CustomArrayList<Ticket> tickets = TicketService.getAllPastTickets();

        assertEquals("approved", tickets.get(0).getTicket_status());
        assertEquals("denied", tickets.get(1).getTicket_status());
    }

    //test to get tickets between dates and see if the dates match up
    @Test
    public void testGetTicketsByDate() {
        CustomArrayList<Ticket> tickets = TicketService.getTicketsByDate(3, Timestamp.valueOf("2022-05-01 00:00:00"), Timestamp.valueOf("2022-05-31 00:00:00"));
        if (!tickets.isEmpty()) {
            System.out.println(tickets.get(0).getTicket_date());
        } else {
            System.out.println("Empty.");
        }

//        Timestamp timestamp = Timestamp.valueOf("2022-05-09 00:00:00.00");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String substringDate = String.valueOf(timestamp).substring(0, 10);
        String substringResultDate = String.valueOf(tickets.get(0).getTicket_date()).substring(0, 10);

        assertEquals(substringDate, substringResultDate);
    }

    //test for update ticket
    @Test
    public void testUpdateTicket() {
        Ticket ticket = TicketService.getTicketById(1);
        ticket.setTicket_employee_id(1);
        ticket.setTicket_amount(200);
        ticket.setTicket_description("motel food");
        ticket.setTicket_date(null);
        ticket.setTicket_status("approved");

        boolean isUpdated = TicketService.updateTicket(ticket);

        assertTrue(isUpdated);

    }

    //test for delete ticket
    @Test
    public void testDeleteTicket() {
        boolean isDeleted = TicketService.deleteTicket(1);

        assertTrue(isDeleted);
    }

    /*=============================================================================================================
                                            CUSTOM DATA STRUCTURE TESTS
     =============================================================================================================*/

    //test create and add
    @Test
    public void testAddToCDS() {
        CustomArrayList<Integer> numbers = new CustomArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        int number = numbers.get(3);

        assertEquals(4, number);
    }

    //test print
    @Test
    public void testPrintFromCDS() {
        CustomArrayList<String> names = new CustomArrayList<>();
        names.add("Jimmy");
        names.add("Laura");
        names.add("Phillip");

        names.print();
    }

    //test get size
    @Test
    public void testGetSizeOfCDS() {
        CustomArrayList<Double> temps = new CustomArrayList<>();
        temps.add(75.5);
        temps.add(98.6);
        temps.add(99.0);
        temps.add(100.4);

        assertEquals(4, temps.getSize());
    }

    //test resize
    @Test
    public void testResizeCDS() {
        CustomArrayList<Integer> numbers = new CustomArrayList<>();

        //add numbers
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(10);

        //assert whether capacity has not changed yet due to resize (default is 10)
        assertEquals(10, numbers.getCapacity());

        //add another number and force a resize
        numbers.add(11);

        //assert that capacity has changed
        assertEquals(20, numbers.getCapacity());
    }

    //test add at specified index
    @Test
    public void testAddAtSpecifiedIndexToCDS() {
        CustomArrayList<Integer> numbers = new CustomArrayList<>();

        //add numbers
        numbers.add(1); //0
        numbers.add(2);
        numbers.add(3);
        numbers.add(5);

        //add new number to index 3, pushing the number 5 down the list
        numbers.add(3, 4);

        //print numbers
        numbers.print();

        //assert that the new number is indeed at specified index
        int numberAdded = numbers.get(3);
        assertEquals(4, numberAdded);
    }

    //test to check if empty
    @Test
    public void testIfListIsEmpty() {
        CustomArrayList<String> names = new CustomArrayList<>();

        names.add("Jimmy");
        names.add("Sarah");
        names.add("Ed");

        assertFalse(names.isEmpty());
    }

    //test to force resize due to add(index, element) method
    @Test
    public void testResizeThroughAdd() {
        CustomArrayList<Integer> numbers = new CustomArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(11); // 9

        //add new number
        numbers.add(9, 10);

        assertEquals(20, numbers.getCapacity());
    }

    //test index out of bounds of get
    @Test
    public void testIndexOutOfBoundsOfGet() {
        boolean outOfBounds = false;
        CustomArrayList<Integer> numbers = new CustomArrayList<>();
        numbers.add(1);

        try{
            System.out.println(numbers.get(1));
        }catch(Exception e) {
            outOfBounds = true;
        }

        assertTrue(outOfBounds);
    }

    //test index out of bounds of add(index, element)
    @Test
    public void testIndexOutOfBoundsOfAdd() {
        boolean outOfBounds = false;
        CustomArrayList<Integer> numbers = new CustomArrayList<>();
        numbers.add(1);
        numbers.add(2);

        try{
            numbers.add(3, 3);
        }catch(Exception e) {
            outOfBounds = true;
        }

        assertTrue(outOfBounds);
    }

    /*=============================================================================================================
                                          SERVLET / WEB APP MOCK TESTS
     =============================================================================================================*/

    //test static employee object
    @Test
    public void testStaticEmployeeObject() {
        AuthenticatedEmployee authenticatedEmployee = new AuthenticatedEmployee();
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        assertNotNull(AuthenticatedEmployee.authenticatedEmployee);
    }

    //mock servlet test of get pong
    @Test
    public void testMockGetPong() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        //mimic the writer
        when(response.getWriter()).thenReturn(writer);

        //create new ping servlet and send request
        new PingServlet().doGet(request, response);

        //flush writer
        writer.flush();

        //check if response matches the writer
        assertEquals("Project1> Pong!", stringWriter.toString());
    }

    //mock servlet test of the get account servlet
    @Test
    public void testMockGetAccountServletNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        //mimic the writer
        when(response.getWriter()).thenReturn(writer);

        // create a new account servlet and do the get method:
        new AccountServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Please supply login credentials."));
    }

    //mock servlet test of the get account servlet
    @Test
    public void testMockGetAccountServletWhenAuthenticated() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        //mimic the writer
        when(response.getWriter()).thenReturn(writer);

        // create a new account servlet and do the get method:
        new AccountServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the appropriate text
        assertTrue(stringWriter.toString().contains("You are already signed in."));
    }

    //mock servlet test to add new account
    @Test
    public void testMockAddNewAccount() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("new")).thenReturn("true");

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new AccountServlet().doPost(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("new");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Employee account created!"));

        // assert that the employee was inserted properly:
        Employee employee = EmployeeService.getEmployeeById(5);
        assertEquals(employee.getEmployee_type(), "employee");
        assertEquals(employee.getEmployee_first_name(), "Jason");
        assertEquals(employee.getEmployee_last_name(), "Berkley");
        assertEquals(employee.getEmployee_username(), "jberkley");
        assertEquals(employee.getEmployee_password(), "12345");
    }

    //mock servlet test to add new account, but the value for the parameter "new" is not "true"
    @Test
    public void testMockAddNewAccountWrongValue() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("new")).thenReturn("false");

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new AccountServlet().doPost(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("new");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Bad Request"));
    }

    //mock servlet test to add new account when one exists already
    @Test
    public void testMockAddNewAccountWhenOneExists() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("new")).thenReturn("true");

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_employee_exists.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new AccountServlet().doPost(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("new");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Unprocessable"));
    }

    //mock servlet test to sign in
    @Test
    public void testMockPostAccountSignIn() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_login_credentials.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee_login_credentials.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new AccountServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("You are signed in,"));
    }

    //mock servlet test to sign in with wrong credentials
    @Test
    public void testMockPostAccountSignInWithWrongCredentials() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_login_credentials.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee_login_wrong_credentials.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new AccountServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Could not validate"));
    }

    //mock servlet test of do put in account servlet (sign out)
    @Test
    public void testMockPutAccountSignOut() throws IOException, ServletException {
        //mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new AccountServlet().doPut(request, response);

        //flush the writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("You are not signed in!"));
    }

    //mock servlet test of do post in account servlet (sign in again)
    @Test
    public void testMockCreateAccountWhenSignedIn() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        //mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new AccountServlet().doPost(request, response);

        //flush the writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("get back to work"));
    }

    //mock servlet test of do put in account servlet when signed in (sign out)
    @Test
    public void testMockPutAccountSignOutWhenSignedIn() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        //mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new AccountServlet().doPut(request, response);

        //flush the writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("You are signed out"));
    }

    //mock servlet test of get employee by id
    @Test
    public void testMockGetEmployeeById() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("employee_id");
        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the specific employee:
        assertTrue(stringWriter.toString().contains("Employee{employee_id=1, employee_type='employee', employee_first_name='Jimmy', employee_last_name='Wilson', employee_username='jwilson', employee_password='123'}"));
    }

    //mock servlet test of get employee by id as employee
    @Test
    public void testMockGetEmployeeByIdAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);


        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock servlet test of get employee by id as not authenticated
    @Test
    public void testMockGetEmployeeByIdAsNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);


        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock servlet test of get employee by id as wrong type
    @Test
    public void testMockGetEmployeeByIdAsWrongType() throws IOException, ServletException {
        //get authenticated wrongly
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);


        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock servlet test of get all employees
    @Test
    public void testMockGetAllEmployees() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        // return the writer to mimic the response's writer:
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains employees:
        assertTrue(stringWriter.toString().contains("Employee{employee_id=1, employee_type='employee', employee_first_name='Jimmy', employee_last_name='Wilson', employee_username='jwilson', employee_password='123'}"));
        assertTrue(stringWriter.toString().contains("Employee{employee_id=2, employee_type='employee', employee_first_name='Gregory', employee_last_name='Harris', employee_username='gharris', employee_password='5402'}"));
        assertTrue(stringWriter.toString().contains("Employee{employee_id=3, employee_type='employee', employee_first_name='Tammy', employee_last_name='Wilks', employee_username='twilks', employee_password='6445'}"));
        assertTrue(stringWriter.toString().contains("Employee{employee_id=4, employee_type='manager', employee_first_name='Sally', employee_last_name='Thompson', employee_username='sthompson', employee_password='9090'}"));
    }

    //mock servlet test of get all employees as employee
    @Test
    public void testMockGetAllEmployeesAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        // return the writer to mimic the response's writer:
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock servlet test of get all employees as wrong type
    @Test
    public void testMockGetAllEmployeesAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        // return the writer to mimic the response's writer:
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock servlet test of get all employees as not authenticated
    @Test
    public void testMockGetAllEmployeesAsNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        // return the writer to mimic the response's writer:
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock test of insert employee
    @Test
    public void testMockInsertEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Employee record added."));

        // assert that the employee was inserted properly:
        Employee employee = EmployeeService.getEmployeeById(5);
        assertEquals(employee.getEmployee_type(), "employee");
        assertEquals(employee.getEmployee_first_name(), "William");
        assertEquals(employee.getEmployee_last_name(), "Porche");
        assertEquals(employee.getEmployee_username(), "wporche");
        assertEquals(employee.getEmployee_password(), "545421");
    }

    //mock test of insert employee with existing name
    @Test
    public void testMockInsertEmployeeWithExistingName() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_employee_exists.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Unprocessable"));
    }

    //mock test of insert employee as employee
    @Test
    public void testMockInsertEmployeeAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of insert employee as wrong type
    @Test
    public void testMockInsertEmployeeAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of insert employee as not authenticated
    @Test
    public void testMockInsertEmployeeAsNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/employee.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the employee record was added
        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock test of update employee
    @Test
    public void testMockUpdateEmployee() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/employee_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Employee record updated."));
        // assert that the employee was updated properly:
        Employee employee = EmployeeService.getEmployeeById(1);
        assertEquals(employee.getEmployee_first_name(), "Billy");
        assertEquals(employee.getEmployee_username(), "bporche");
    }

    //mock test of update employee with name that already exists
    @Test
    public void testMockUpdateEmployeeWithExistingName() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/new_employee_exists.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Unprocessable"));
    }

    //mock test of update employee as employee
    @Test
    public void testMockUpdateEmployeeAsEmployee() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/employee_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of update employee as wrong type
    @Test
    public void testMockUpdateEmployeeAsWrongType() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/employee_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of update employee as not authenticated
    @Test
    public void testMockUpdateEmployeeAsNotAuthenticated() throws ServletException, IOException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called employee_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/employee_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the post method:
        new EmployeeServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();
        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock test of delete employee
    @Test
    public void testMockDeleteEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("employee_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doDelete(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("employee_id");

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertEquals("Employee record 3 deleted.", stringWriter.toString());
        // make sure employee is deleted:
        assertNull(EmployeeService.getEmployeeById(3));
    }

    //mock test of delete employee as employee
    @Test
    public void testMockDeleteEmployeeAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("employee_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of delete employee as wrong type
    @Test
    public void testMockDeleteEmployeeAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("employee_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of delete employee as not authenticated
    @Test
    public void testMockDeleteEmployeeAsNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("employee_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new employee servlet and do the get method:
        new EmployeeServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock servlet test of doGet without authentication
    @Test
    public void testMockGetTicketNotAuthenticated() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("You must be signed in"));
    }

    //mock servlet test of get all tickets as employee
    @Test
    public void testMockGetAllTicketsAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=2, ticket_employee_id=1, ticket_amount=30.75, ticket_description='gas',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=3, ticket_employee_id=1, ticket_amount=1000.0, ticket_description='motel room',"));
    }

    //mock servlet test of get all tickets as wrong type
    @Test
    public void testMockGetAllTicketsAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock servlet test of get all pending tickets as employee
    @Test
    public void testMockGetAllPendingTicketsAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("e_get_all_pending_tickets")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=2, ticket_employee_id=1, ticket_amount=30.75, ticket_description='gas',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=3, ticket_employee_id=1, ticket_amount=1000.0, ticket_description='motel room',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=4, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
    }

    //mock servlet test of get all past tickets as employee
    @Test
    public void testMockGetAllPastTicketsAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("e_get_all_past_tickets")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=5, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=6, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
    }

    //mock servlet test of get all tickets between dates as employee
    @Test
    public void testMockGetAllTicketsBetweenDatesAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("e_ticket_start_date")).thenReturn(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
        when(request.getParameter("e_ticket_end_date")).thenReturn(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=5, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=6, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
    }

    //mock servlet test of get ticket by id
    @Test
    public void testMockGetTicketById() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("ticket_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("ticket_id");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));

        //get the ticket
        Ticket ticket = TicketService.getTicketById(1);

        //assert that the id contains specific information
        assertEquals(1, ticket.getTicket_id());
        assertEquals(1, ticket.getTicket_employee_id());
        assertEquals("pending", ticket.getTicket_status());
    }

    //mock servlet test of get ticket by employee id
    @Test
    public void testMockGetTicketByEmployeeId() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("ticket_employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("ticket_employee_id");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));

        //get the ticket
        Ticket ticket = TicketService.getTicketById(1);

        //assert that the id contains specific information
        assertEquals(1, ticket.getTicket_id());
        assertEquals(1, ticket.getTicket_employee_id());
        assertEquals("pending", ticket.getTicket_status());
    }

    //mock servlet test of get pending tickets by employee id
    @Test
    public void testMockGetPendingTicketsByEmployeeId() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("pending_tickets_by_employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("pending_tickets_by_employee_id");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));

        //get the ticket
        Ticket ticket = TicketService.getTicketById(1);

        //assert that the id contains specific information
        assertEquals(1, ticket.getTicket_id());
        assertEquals(1, ticket.getTicket_employee_id());
        assertEquals("pending", ticket.getTicket_status());
    }

    //mock servlet test of get past tickets by employee id
    @Test
    public void testMockGetPastTicketsByEmployeeId() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("past_tickets_by_employee_id")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("past_tickets_by_employee_id");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=5, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=6, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
    }

    //mock servlet test of get all tickets between dates as manager
    @Test
    public void testMockGetAllTicketsBetweenDatesAsManager() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("tickets_by_employee_id")).thenReturn("2");
        when(request.getParameter("ticket_start_date")).thenReturn(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
        when(request.getParameter("ticket_end_date")).thenReturn(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        //flush writer
        writer.flush();

        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=7, ticket_employee_id=2, ticket_amount=50.0, ticket_description='gas',"));
    }

    //mock servlet test of get all pending tickets as manager
    @Test
    public void testMockGetAllPendingTicketsAsManager() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("get_all_pending_tickets")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("get_all_pending_tickets");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=2, ticket_employee_id=1, ticket_amount=30.75, ticket_description='gas',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=4, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=7, ticket_employee_id=2, ticket_amount=50.0, ticket_description='gas',"));
    }

    //mock servlet test of get all past tickets as manager
    @Test
    public void testMockGetAllPastTicketsAsManager() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure/mocking the id query parameter (id = 1):
        when(request.getParameter("get_all_past_tickets")).thenReturn("1");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("get_all_past_tickets");
        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the specific ticket:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=5, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=8, ticket_employee_id=3, ticket_amount=150.0, ticket_description='pay adjustment',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=9, ticket_employee_id=3, ticket_amount=10.0, ticket_description='fast food',"));
    }

    //mock servlet test of get all tickets
    @Test
    public void testMockGetAllTickets() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        // return the writer to mimic the response's writer:
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doGet(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains tickets:
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=1, ticket_employee_id=1, ticket_amount=100.5, ticket_description='fast food',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=2, ticket_employee_id=1, ticket_amount=30.75, ticket_description='gas',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=3, ticket_employee_id=1, ticket_amount=1000.0, ticket_description='motel room',"));
        assertTrue(stringWriter.toString().contains("Ticket{ticket_id=4, ticket_employee_id=1, ticket_amount=15.25, ticket_description='fast food',"));
    }

    //mock test of insert ticket as employee
    @Test
    public void testMockInsertTicketAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(2);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called new_ticket.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_ticket.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the ticket record was added
        assertTrue(stringWriter.toString().contains("Ticket submitted!"));

        // assert that the ticket was inserted properly:
        Ticket ticket = TicketService.getTicketById(10);
        assertEquals(ticket.getTicket_employee_id(), 2);
        assertEquals(ticket.getTicket_amount(), 175, 100);
        assertEquals(ticket.getTicket_description(), "party");
        assertEquals(ticket.getTicket_status(), "pending");
    }

    //mock test of insert ticket as not authorized
    @Test
    public void testMockInsertTicketAsNotAuthorized() throws IOException, ServletException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called new_ticket.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_ticket.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the ticket record was added
        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock test of insert ticket as wrong type
    @Test
    public void testMockInsertTicketAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(2);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called new_ticket.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/new_ticket.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the ticket record was added
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of insert ticket
    @Test
    public void testMockInsertTicket() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called ticket.txt, this is simulating what we would put in body of the request
        FileReader fr = new FileReader("src/test/java/org/example/ticket.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPost(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result states that the ticket record was added
        assertTrue(stringWriter.toString().contains("Ticket record inserted!"));

        // assert that the ticket was inserted properly:
        Ticket ticket = TicketService.getTicketById(10);
        assertEquals(ticket.getTicket_employee_id(), 1);
        assertEquals(ticket.getTicket_amount(), 50, 100);
        assertEquals(ticket.getTicket_description(), "potluck");
        assertEquals(ticket.getTicket_status(), "pending");
    }

    //mock test of update ticket
    @Test
    public void testMockUpdateTicket() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called ticket_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/ticket_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Record updated!"));

        // assert that the ticket was updated properly:
        Ticket ticket = TicketService.getTicketById(1);
        assertEquals(ticket.getTicket_amount(), 50, 100);
        assertEquals(ticket.getTicket_description(), "potluck");
    }

    //mock test of update ticket as employee
    @Test
    public void testMockUpdateTicketAsEmployee() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called ticket_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/ticket_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the appropriate text:
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of update ticket as wrong type
    @Test
    public void testMockUpdateTicketAsWrongType() throws ServletException, IOException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called ticket_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/ticket_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the the appropriate text:
        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of update ticket as not authenticated
    @Test
    public void testMockUpdateTicketAsNotAuthenticated() throws ServletException, IOException {
        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // since we take in a buffered reader to read the body, we simulate it by putting
        // mock data in a local file called ticket_with_id.txt
        FileReader fr = new FileReader("src/test/java/org/example/ticket_with_id.txt");
        BufferedReader t = new BufferedReader(fr);
        // configure the request to use the buffered reader:
        when(request.getReader()).thenReturn(t);

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the post method:
        new TicketServlet().doPut(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        // assert that the result contains the the appropriate text:
        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    //mock test of delete ticket
    @Test
    public void testMockDeleteTicket() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(4);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("ticket_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doDelete(request, response);

        // verify the parameter:
        verify(request, atLeast(1)).getParameter("ticket_id");

        // flush the writer, make sure all the output is written:
        writer.flush();

        System.out.println(stringWriter);

        assertEquals("Record deleted successfully!", stringWriter.toString());
        // make sure ticket is deleted:
        assertNull(TicketService.getTicketById(3));
    }

    //mock test of delete ticket as employee
    @Test
    public void testMockDeleteTicketAsEmployee() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("ticket_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of delete ticket as wrong type
    @Test
    public void testMockDeleteTicketAsWrongType() throws IOException, ServletException {
        //get authenticated
        AuthenticatedEmployee.authenticatedEmployee = EmployeeService.getEmployeeById(1);
        AuthenticatedEmployee.authenticatedEmployee.setEmployee_type(".");

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("ticket_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Forbidden"));
    }

    //mock test of delete ticket as not authenticated
    @Test
    public void testMockDeleteTicketAsNotAuthenticated() throws IOException, ServletException {

        // mock the request/response:
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // configure the id parameter:
        when(request.getParameter("ticket_id")).thenReturn("3");

        // set up the print writer:
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // create a new ticket servlet and do the get method:
        new TicketServlet().doDelete(request, response);

        // flush the writer, make sure all the output is written:
        writer.flush();

        assertTrue(stringWriter.toString().contains("Unauthorized"));
    }

    /*=============================================================================================================
                                                   MISC TESTS
     =============================================================================================================*/

    //test misc test to produce errors
    @Test
    public void miscTestProduceErrors() {
        Employee employee = new Employee("manager", "Tye", "Johnson", "tjohnson", "123");
        EmployeeService.insertEmployee(employee);
        EmployeeService.insertEmployee(employee);

        employee = EmployeeService.getEmployeeById(1);
        employee.setEmployee_id(0);
        EmployeeService.updateEmployee(employee);

        employee = EmployeeService.getEmployeeById(0);

        employee = EmployeeService.getEmployeeByUserName("bob");

        EmployeeService.deleteEmployee(0);

        Ticket ticket = new Ticket(0, -500, null, null, null);
        TicketService.insertTicket(ticket);

        ticket = TicketService.getTicketById(1);
        ticket.setTicket_id(0);
        TicketService.updateTicket(ticket);

        ticket = TicketService.getTicketById(0);

        CustomArrayList<Ticket> tickets = new CustomArrayList<>();
        tickets = TicketService.getAllTicketsByEmployeeId(99);

        TicketService.deleteTicket(0);
    }

    //test misc test to call on dependency loader listener
    @Test
    public void miscTestDependencyLoaderListener() {
        DependencyLoaderListener dependencyLoaderListener = new DependencyLoaderListener();
        ServletContext a = new TestServletContext();
        dependencyLoaderListener.contextInitialized(new ServletContextEvent(a));
        dependencyLoaderListener.contextDestroyed(new ServletContextEvent(a));

        assertNotNull(dependencyLoaderListener);
    }

}
