package org.example.dao;

import org.example.data_structure.CustomArrayList;
import org.example.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {

    Connection connection;

    //instantiating this class will get the connection
    public EmployeeDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }


    @Override
    public void insertEmployee(Employee employee) {
        // sql data manipulation language statement
        String sql = "insert into employees (employee_id, employee_type, employee_first_name, employee_last_name, " +
                "employee_username, employee_password) values (default, ?, ?, ?, ?, ?);";

        try {
            //prepare the statement to send to the database using the given connection,
            //resulting in the returning of newly generated keys
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // fill in the placeholder values from our Employee object
            preparedStatement.setString(1, employee.getEmployeeType());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getUserName());
            preparedStatement.setString(5, employee.getPassword());

            //execute the statement to insert a new employee
            //variable determining how many rows were affected
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                //inform user employee added
                System.out.println("Employee added successfully!");

                //get the result set from statement
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                // increment to the first element of the result set
                resultSet.next();
                // extract the id from the result set
                int id = resultSet.getInt(1);
                employee.setId(id);
                System.out.println("Generated ID is: " + id);
            }
            else {
                System.out.println("Something went wrong when adding the employees!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql = "select * from employees where employee_id = ?;";
        try {
            //prepare statement using the connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set id passed through parameter
            preparedStatement.setInt(1, id);

            //execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            //check to see if the result set returned any records
            if (resultSet.next()) {
                // extract out the data
                Employee employee = getEmployee(resultSet);
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getEmployeeByUserName(String userName) {
        String sql = "select * from employees where employee_username = ?;";

        try {
            //prepare statement using the connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set username passed through parameter
            preparedStatement.setString(1, userName);

            //execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            //check to see if the result set returned any records
            if(resultSet.next()) {
                //extract out the data
                Employee employee = getEmployee(resultSet);
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return null if nothing is found
        return null;
    }

    @Override
    public CustomArrayList<Employee> getAllEmployees() {
        // create a list of employees to store our results:
        CustomArrayList<Employee> employees = new CustomArrayList<>();

        //query statement
        String sql = "select * from employees;";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Employee employee = getEmployee(resultSet);

                // add employee to the list of employees
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // get employee from a result set:
    public Employee getEmployee(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("employee_id");
            String employeeType = resultSet.getString("employee_type");
            String userName = resultSet.getString("employee_username");
            String password = resultSet.getString("employee_password");
            String firstName = resultSet.getString("employee_first_name");
            String lastName = resultSet.getString("employee_last_name");

            //create new employee object
            return new Employee(id, employeeType, firstName, lastName, userName, password);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        //update query statement
        String sql = "update employees set " +
                "employee_type = ?, employee_first_name = ?, employee_last_name = ?, employee_username = ?, employee_password = ? " +
                "where employee_id = ?;";

        try {
            //prepare query statement with updated values
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getEmployeeType());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getUserName());
            preparedStatement.setString(5, employee.getPassword());
            preparedStatement.setInt(6, employee.getId());

            //execute update and see if any rows were affected
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Update successful!");
                return true;
            }
            else {
                System.out.println("Something went wrong with the update!");
                return false;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteEmployee(int id) {
        //delete query statement
        String sql = "delete from employees where employee_id = ?;";
        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            //execute statement
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Deletion was successful!");
                return true;
            }
            else {
                System.out.println("Something went wrong with the deletion!");
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
