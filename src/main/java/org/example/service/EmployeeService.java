package org.example.service;

import org.example.dao.DaoFactory;
import org.example.dao.EmployeeDao;
import org.example.entity.Employee;
import org.example.utilities.InputUtilities;

import java.util.List;

public class EmployeeService {

    //take input and put a new employee into the database
    public static void insertEmployee() {
        boolean validated = false;
        String userName, password, firstName, lastName;

        // notice that we don't ask for an id (this will be auto-generated)
        //validate to ensure usernames are unique
        //loop until validated and is proper length
        do{
            System.out.println("Please enter a username: ");
            userName = InputUtilities.ValidateString();

            validated = getEmployeeByUserName(userName);

            if(!validated){
                System.out.println("*Username already exists!");
            }

            if(userName.length() > 50) {
                validated = false;
                System.out.println("*Username is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while(!validated);

        //loop for password input until validated and is of proper length
        do{
            System.out.println("Please enter new password: ");
            password = InputUtilities.ValidateString();

            if(password.length() > 50) {
                validated = false;
                System.out.println("*Password is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //loop for first name input until validated and is of proper length
        do{
            System.out.println("Please enter first name: ");
            firstName = InputUtilities.ValidateString();

            if(firstName.length() > 50) {
                validated = false;
                System.out.println("*First name is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //loop for last name input until validated and is of proper length
        do{
            System.out.println("Please enter last name: ");
            lastName = InputUtilities.ValidateString();

            if(lastName.length() > 50) {
                validated = false;
                System.out.println("*Last name is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //create new Employee object
        Employee employee = new Employee("employee", firstName, lastName, userName, password);

        //call data access object factory and pass new employee object into it.
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        employeeDao.insertEmployee(employee);
    }

    public static void getEmployeeById() {
        System.out.println("Please enter the id of the employee: ");
        int id = InputUtilities.ValidateInteger();
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeById(id);
        System.out.println("Employee found:\n" + employee.toString());
    }

    public static Employee getEmployeeByUserName() {
        System.out.println("Please enter the username: ");
        String userName = InputUtilities.ValidateString();
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeByUserName(userName);
        if(employee == null){
            System.out.println("Username does not exist.");
            return null;
        }else{
            return employee;
        }
    }

    public static boolean getEmployeeByUserName(String userName) {
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        Employee employee = employeeDao.getEmployeeByUserName(userName);
        if(employee == null){
            return true;
        }else{
            return false;
        }
    }

    public static void getAllEmployees() {
        System.out.println("All employees:");
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        List<Employee> employees = employeeDao.getAllEmployees();
        for(Employee employee: employees) {
            System.out.println(employee);
        }
    }

    public static void updateEmployee() {
        boolean validated = false;
        String employeeType, userName, password, firstName, lastName;

        System.out.println("Update Employee> Please enter the id for the employee: ");
        int id = InputUtilities.ValidateInteger();

        //loop for employee type input until validated and is of proper length
        do{
            System.out.println("Update Employee> Please enter employee type: ");
            employeeType = InputUtilities.ValidateString();

            if(employeeType.length() > 50) {
                validated = false;
                System.out.println("*Employee type is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //validate to ensure usernames are unique
        do{
            System.out.println("Update Employee> Please enter username: ");
            userName = InputUtilities.ValidateString();

            validated = getEmployeeByUserName(userName);

            if(!validated){
                System.out.println("*Username already exists!");
            }

            if(userName.length() > 50) {
                validated = false;
                System.out.println("*Username is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while(!validated);

        //loop for password input until validated and is of proper length
        do{
            System.out.println("Update Employee> Please enter password: ");
            password = InputUtilities.ValidateString();

            if(password.length() > 50) {
                validated = false;
                System.out.println("*Password is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //loop for first name input until validated and is of proper length
        do{
            System.out.println("Update Employee> Please enter first name: ");
            firstName = InputUtilities.ValidateString();

            if(firstName.length() > 50) {
                validated = false;
                System.out.println("*First name is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        //loop for last name input until validated and is of proper length
        do{
            System.out.println("Update Employee> Please enter last name: ");
            lastName = InputUtilities.ValidateString();

            if(lastName.length() > 50) {
                validated = false;
                System.out.println("*Last name is too long (limit 50 characters)!");
            }else{
                validated = true;
            }
        }while (!validated);

        Employee employee = new Employee(id, employeeType, firstName, lastName, userName, password);

        //call the employee dao factory to update the record
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        // update the customer record:
        employeeDao.updateEmployee(employee);
    }

    public static void deleteEmployee() {
        System.out.println("Please enter the id of the employee to be deleted: ");
        int id = InputUtilities.ValidateInteger();
        EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
        employeeDao.deleteEmployee(id);
    }

}
