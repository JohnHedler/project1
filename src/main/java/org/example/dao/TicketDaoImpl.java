package org.example.dao;

import org.example.data_structure.CustomArrayList;
import org.example.entity.Ticket;

import java.sql.*;

public class TicketDaoImpl implements TicketDao{

    Connection connection;

    //instantiating this class will get the connection
    public TicketDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public boolean insertTicket(Ticket ticket) {
        String sql = "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, " +
                "ticket_date, ticket_status) values (default, ?, ?, ?, current_timestamp, ?);";

        try{
            //prepare the statement to send to the database using the given connection,
            //resulting in the returning of newly generated keys
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // fill in the placeholder values from our Employee object
            preparedStatement.setInt(1, ticket.getTicket_employee_id());
            preparedStatement.setDouble(2, ticket.getTicket_amount());
            preparedStatement.setString(3, ticket.getTicket_description());
            preparedStatement.setString(4, ticket.getTicket_status());

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
                ticket.setTicket_id(id);
                System.out.println("Generated ID is: " + id);
                return true;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Ticket getTicketById(int id) {
        Ticket ticket = null;
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
                ticket = getTicket(resultSet);
            }else {
                throw new SQLException("Record for ID " + id + " does not exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public CustomArrayList<Ticket> getAllTicketsByEmployeeId(int employeeId) {
        //create custom array list
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? order by ticket_date";

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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return custom array list of tickets
        return tickets;
    }

    @Override
    public CustomArrayList<Ticket> getAllTickets() {
        // create a list of tickets to store our results:
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //query statement
        String sql = "select * from tickets order by ticket_date;";

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
    public CustomArrayList<Ticket> getPendingTickets(int employeeId) {
        // create a list of tickets to store our results:
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? and ticket_status = 'pending' order by ticket_date;";

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
    public CustomArrayList<Ticket> getPastTickets(int employeeId) {
        // create a list of tickets to store our results:
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? and ticket_status in ('approved', 'denied') order by ticket_date;";

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
    public CustomArrayList<Ticket> getTicketsByDate(int employeeId, Timestamp startDate, Timestamp endDate) {
        // create a list of tickets to store our results:
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //query statement
        String sql = "select * from tickets where ticket_employee_id = ? and " +
                "ticket_date::date between ?::date and ?::date order by ticket_date;";

        try {
            //prepare statement using query statement and submit through connection
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setTimestamp(2, startDate);
            preparedStatement.setTimestamp(3, endDate);

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
            int ticket_id = resultSet.getInt("ticket_id");
            int ticket_employee_id = resultSet.getInt("ticket_employee_id");
            double ticket_amount = resultSet.getDouble("ticket_amount");
            String ticket_description = resultSet.getString("ticket_description");
            Timestamp ticket_date = resultSet.getTimestamp("ticket_date");
            String ticket_status = resultSet.getString("ticket_status");

            //create new ticket object
            return new Ticket(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateTicket(Ticket ticket) {
        //update query statement
        String sql = "update tickets set ticket_employee_id = ?, ticket_amount = ?, ticket_description = ?, " +
                "ticket_status = ? where ticket_id = ?;";

        try {
            //prepare query statement with updated values
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticket.getTicket_employee_id());
            preparedStatement.setDouble(2, ticket.getTicket_amount());
            preparedStatement.setString(3, ticket.getTicket_description());
            preparedStatement.setString(4, ticket.getTicket_status());
            preparedStatement.setInt(5, ticket.getTicket_id());

            //execute update and see if any rows were affected
            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                System.out.println("Update successful!");
                return true;
            }
            else {
                System.out.println("Something went wrong with the update!");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteTicket(int id) {
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
                return true;
            }
            else {
                System.out.println("Something went wrong with the deletion!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CustomArrayList<Ticket> getAllPendingTickets() {
        //create custom array list
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //set sql string
        String sql = "select * from tickets where ticket_status = 'pending' order by ticket_date;";

        try{
            //prepare statement
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
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //return custom array list of tickets
        return tickets;
    }

    @Override
    public CustomArrayList<Ticket> getAllPastTickets() {
        //create custom array list
        CustomArrayList<Ticket> tickets = new CustomArrayList<>();

        //set sql string
        String sql = "select * from tickets where ticket_status in ('approved', 'denied') order by ticket_date;";

        try{
            //prepare statement
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
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //return the tickets
        return tickets;
    }
}
