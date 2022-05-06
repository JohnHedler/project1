package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDaoImpl implements TestDao{

    Connection connection;

    public TestDaoImpl() {
        //when instantiating, we get this connection
        connection = ConnectionFactory.getConnection();
    }

    // we need this method if we're using the h2 database, keep in mind that our database gets "reset" every time,
    // we run the program
    @Override
    public void initTables() {
        // we don't see any ? placeholders because this statement will be the same every time
        //create tables
        String sql = "drop table if exists employees, tickets cascade;\n";
        sql += "create table employees (employee_id serial primary key, employee_type varchar(50), employee_first_name varchar(50), employee_last_name varchar(50), employee_username varchar(50) unique, employee_password varchar(50));\n";
        sql += "create table tickets (ticket_id serial primary key, ticket_employee_id int, ticket_amount numeric, ticket_description varchar(250), ticket_date timestamp, ticket_status varchar(50));\n";

        //alter tables
        sql += "alter table tickets add foreign key (ticket_employee_id) references employees (employee_id) on delete cascade on update cascade;";
        try{
            //create statement instead of preparing it
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillTables() {
        //insert into employees table
        String sql = "insert into employees(employee_id, employee_type, employee_first_name, employee_last_name, employee_username, employee_password) values (default, 'employee', 'Jimmy', 'Wilson', 'jwilson', '123')\n;";
        sql += "insert into employees(employee_id, employee_type, employee_first_name, employee_last_name, employee_username, employee_password) values (default, 'employee', 'Gregory', 'Harris', 'gharris', '5402');\n";
        sql += "insert into employees(employee_id, employee_type, employee_first_name, employee_last_name, employee_username, employee_password) values (default, 'employee', 'Tammy', 'Wilks', 'twilks', '6445');\n";
        sql += "insert into employees(employee_id, employee_type, employee_first_name, employee_last_name, employee_username, employee_password) values (default, 'manager', 'Sally', 'Thompson', 'sthompson', '9090');\n";

        //insert into tickets table
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 100.50, 'fast food', current_timestamp, 'pending');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 30.75, 'gas', current_timestamp, 'pending');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 1000.00, 'motel room', current_timestamp, 'pending');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 15.25, 'fast food', current_timestamp, 'pending');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 15.25, 'fast food', current_timestamp, 'approved');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '1', 15.25, 'fast food', current_timestamp, 'denied');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '2', 50, 'gas', current_timestamp, 'pending');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '3', 150, 'pay adjustment', current_timestamp, 'denied');\n";
        sql += "insert into tickets(ticket_id, ticket_employee_id, ticket_amount, ticket_description, ticket_date, ticket_status) values (default, '3', 10, 'fast food', current_timestamp, 'approved');";

        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
