package org.example.dao;

import org.example.entity.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao{

    Connection connection;

    //instantiating this class will get the connection
    public TicketDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void insertTicket(Ticket ticket) {
        String sql = "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, " +
                "ticket_date, ticket_status) values (default, ?, ?, ?, current_timestamp, ?);";

        try{
            //prepare the statement to send to the database using the given connection,
            //resulting in the returning of newly generated keys
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // fill in the placeholder values from our Employee object
            preparedStatement.setInt(1, ticket.getEmployeeId());
            preparedStatement.setDouble(2, ticket.getAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());

            //execute the statement to insert a new employee
            //variable determining how many rows were affected
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                //inform user employee added
                System.out.println("Ticket added successfully!");

                //get the result set from statement
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                // increment to the first element of the result set
                resultSet.next();
                // extract the id from the result set
                int id = resultSet.getInt(1);
                System.out.println("Generated ID is: " + id);
            }
            else {
                System.out.println("Something went wrong when adding the ticket!");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getTicketById(int id) {
        String sql = "select * from tickets where ticket_id = ?;";
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
                Ticket ticket = getTicket(resultSet);
                return ticket;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getAllTicketsByEmployeeId(int employeeId) {
        //create custom array list
        List<Ticket> tickets = new ArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ?";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //set username passed through parameter
            preparedStatement.setInt(1, employeeId);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Ticket ticket = getTicket(resultSet);

                // add employee to the list of employees
                tickets.add(ticket);
            }

            //return custom array list of tickets
            return tickets;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return null if nothing was found
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        // create a list of tickets to store our results:
        List<Ticket> tickets = new ArrayList<>();

        //query statement
        String sql = "select * from tickets;";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Ticket ticket = getTicket(resultSet);

                // add employee to the list of employees
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getPendingTickets(int employeeId) {
        // create a list of tickets to store our results:
        List<Ticket> tickets = new ArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? and ticket_status = 'pending';";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Ticket ticket = getTicket(resultSet);

                // add employee to the list of employees
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getPastTickets(int employeeId) {
        // create a list of tickets to store our results:
        List<Ticket> tickets = new ArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? and ticket_status in ('approved', 'denied');";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Ticket ticket = getTicket(resultSet);

                // add employee to the list of employees
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByDate(int employeeId) {
        // create a list of tickets to store our results:
        List<Ticket> tickets = new ArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ?;";
        //String sql = "select * from tickets where ticket_employee_id = ? and ticket_date = ?;";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            //preparedStatement.setTimestamp(2,date);

            //execute prepared statement to get all employees
            ResultSet resultSet = preparedStatement.executeQuery();

            //loop through the result set
            while(resultSet.next()) {
                //get employee from the result set
                Ticket ticket = getTicket(resultSet);

                // add employee to the list of employees
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public Ticket getTicket(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("ticket_id");
            int employeeId = resultSet.getInt("ticket_employee_id");
            double amount = resultSet.getDouble("ticket_amount");
            String description = resultSet.getString("ticket_description");
            Timestamp date = resultSet.getTimestamp("ticket_date");
            String status = resultSet.getString("ticket_status");

            //create new ticket object
            return new Ticket(id, employeeId, amount, description, date, status);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateTicket(Ticket ticket) {
        //update query statement
        String sql = "update tickets set ticket_employee_id = ?, ticket_amount = ?, ticket_description = ?, " +
                "ticket_status = ? where ticket_id = ?;";

        try {
            //prepare query statement with updated values
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticket.getEmployeeId());
            preparedStatement.setDouble(2, ticket.getAmount());
            preparedStatement.setString(3, ticket.getDescription());
            preparedStatement.setString(4, ticket.getStatus());
            preparedStatement.setInt(5, ticket.getId());

            //execute update and see if any rows were affected
            int count = preparedStatement.executeUpdate();
            if(count == 1) System.out.println("Update successful!");
            else System.out.println("Something went wrong with the update!");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTicket(int id) {
        //delete query statement
        String sql = "delete from tickets where ticket_id = ?;";
        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            //execute statement
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Deletion was successful!");
            }
            else {
                System.out.println("Something went wrong with the deletion!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
