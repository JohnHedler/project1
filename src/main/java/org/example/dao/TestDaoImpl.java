package org.example.dao;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.sql.SQLException;

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
        String sql = "drop table if exists customers, accounts, applied_accounts, transfer_funds, employees, transaction_log cascade;";
        sql += "CREATE TABLE customers(customer_id SERIAL PRIMARY KEY, customer_user_name VARCHAR(50) UNIQUE, customer_password VARCHAR(50), customer_social INT UNIQUE, customer_first_name VARCHAR(50), customer_last_name VARCHAR(50), customer_age INT, customer_street_address VARCHAR(50), customer_city VARCHAR(50), customer_state VARCHAR(2), customer_zip INT, customer_phone INT, customer_email VARCHAR(50));\n";
        sql += "create table accounts (account_id serial primary key, account_holder int unique, account_number int unique, account_balance numeric);\n";
        sql += "create table applied_accounts (applied_id serial primary key, applied_holder int unique, applied_balance numeric);\n";
        sql += "create table transfer_funds (transfer_id serial primary key, transfer_account int, transfer_destination_account int, transfer_amount numeric, transfer_posted timestamp);\n";
        sql += "create table employees (employee_id serial primary key, employee_user_name varchar(50), employee_password varchar(50), employee_first_name varchar(50), employee_last_name varchar(50));\n";
        sql += "create table transaction_log (transaction_id serial primary key, transaction_account_number int, transaction_action varchar(50), transaction_amount numeric, transaction_desc varchar(100), transaction_posted timestamp);\n";

        //alter tables
        sql += "alter table accounts add foreign key (account_holder) references customers(customer_id) on delete cascade on update cascade;\n";
        sql += "alter table applied_accounts add foreign key (applied_holder) references customers(customer_id) on delete cascade on update cascade;\n";
        sql += "alter table transfer_funds add foreign key (transfer_account) references accounts(account_number) on delete cascade on update cascade;\n";
        sql += "alter table transfer_funds add foreign key (transfer_destination_account) references accounts(account_number) on delete cascade on update cascade;";
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
        //insert into customers table
        String sql = "insert into customers(customer_id, customer_user_name, customer_password, customer_social, customer_first_name, customer_last_name, customer_age, customer_street_address, customer_city, customer_state, customer_zip, customer_phone, customer_email) values (default, 'jhudson', '123', 000, 'Jimmy', 'Hudson', 23, '123 Rock Road', 'Rockwell', 'NM', 12034, 1235555555, 'hudsonj@gmail.com');\n";
        sql += "insert into customers(customer_id, customer_user_name, customer_password, customer_social, customer_first_name, customer_last_name, customer_age, customer_street_address, customer_city, customer_state, customer_zip, customer_phone, customer_email) values (default, 'swilliams', '7338', 109, 'Sally', 'Williams', 31, '1 First St', 'FirstCity', 'AZ', 12034, 1235555556, 'WilliamsS98@gmail.com');\n";
        sql += "insert into customers(customer_id, customer_user_name, customer_password, customer_social, customer_first_name, customer_last_name, customer_age, customer_street_address, customer_city, customer_state, customer_zip, customer_phone, customer_email) values (default, 'bjohnson', '50589234', 0912, 'Blake', 'Johnson', 27, '2 First St', 'FirstCity', 'AZ', 12034, 1235555557, 'blakej123@hotmail.com');\n";
        sql += "insert into customers(customer_id, customer_user_name, customer_password, customer_social, customer_first_name, customer_last_name, customer_age, customer_street_address, customer_city, customer_state, customer_zip, customer_phone, customer_email) values (default, 'ljackson', '1339', 123123, 'Larry', 'Jackson', 30, '1 Xbox Ln', 'MicrosoftCity', 'NY', 12034, 1235555558, 'whatzit@toya.com');\n";

        //insert into accounts table
        sql += "insert into accounts(account_id, account_holder, account_number, account_balance) values (default, 1, 3299039, 100.00);\n";
        sql += "insert into accounts(account_id, account_holder, account_number, account_balance) values (default, 2, 8489493, 1000.75);\n";
        sql += "insert into accounts(account_id, account_holder, account_number, account_balance) values (default, 3, 34894738, 150.50);\n";

        //insert into applied accounts table
        sql += "insert into applied_accounts(applied_id, applied_holder, applied_balance) values (default, 1, 100.00);\n";

        //insert into transfer funds table
        sql += "insert into transfer_funds(transfer_id, transfer_account, transfer_destination_account, transfer_amount, transfer_posted) values (default, 8489493, 34894738, 10.00, current_timestamp);\n";
        sql += "insert into transfer_funds(transfer_id, transfer_account, transfer_destination_account, transfer_amount, transfer_posted) values (default, 34894738, 8489493, 10.00, current_timestamp);\n";
        sql += "insert into transfer_funds(transfer_id, transfer_account, transfer_destination_account, transfer_amount, transfer_posted) values (default, 34894738, 3299039, 10.00, current_timestamp);\n";

        //insert into employees table
        sql += "insert into employees(employee_id, employee_user_name, employee_password, employee_first_name, employee_last_name) values (default, 'admin123', '9090', 'Admin', 'Admin');\n";
        sql += "insert into employees(employee_id, employee_user_name, employee_password, employee_first_name, employee_last_name) values (default, 'lRichards', '123', 'Larry', 'Richards');\n";
        sql += "insert into employees(employee_id, employee_user_name, employee_password, employee_first_name, employee_last_name) values (default, 'pHenderson', '653432', 'Phillip', 'Henderson');\n";
        sql += "insert into employees(employee_id, employee_user_name, employee_password, employee_first_name, employee_last_name) values (default, 'tWall', '8622434', 'Tina', 'Wall');\n";

        //insert into transaction log table
        sql += "insert into transaction_log(transaction_id, transaction_account_number, transaction_action, transaction_amount, transaction_desc, transaction_posted) values (default, 3299039, 'deposit', 100, '', current_timestamp);\n";
        sql += "insert into transaction_log(transaction_id, transaction_account_number, transaction_action, transaction_amount, transaction_desc, transaction_posted) values (default, 8489493, 'deposit', 1000.75, '', current_timestamp);\n";
        sql += "insert into transaction_log(transaction_id, transaction_account_number, transaction_action, transaction_amount, transaction_desc, transaction_posted) values (default, 34894738, 'deposit', 150.50, '', current_timestamp);";

        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
