package org.example.dao;

public class DaoFactory {
    private static EmployeeDao employeeDao;
    private static TicketDao ticketDao;

    //test dao
    private static TestDao testDao;

    //prevent instantiation of this class
    private DaoFactory() {

    }

    //check to see if an instance of the factory already exists; if not, add it to the instance variable
    //otherwise, return the request for a new one (prevents excess memory use).
    public static EmployeeDao getEmployeeDao() {
        if (employeeDao == null) {
            employeeDao = new EmployeeDaoImpl();
        }
        return employeeDao;
    }

    public static TicketDao getTicketDao() {
        if (ticketDao == null) {
            ticketDao = new TicketDaoImpl();
        }
        return ticketDao;
    }

    public static TestDao getTestDao() {
        if (testDao == null) {
            testDao = new TestDaoImpl();
        }
        return testDao;
    }
}