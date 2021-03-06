package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
        //connection variable
        private static Connection connection = null;

        //region constructor
        //make sure class cannot be instantiated
        private ConnectionFactory() {

        }
        //endregion

        // this method will return a connection the SQL
        public static Connection getConnection() {
            //with the boolean, we can turn testing on or off
            boolean test = true;
//            boolean test = false;

            //determine the driver and config files
            String driver = test ? "org.h2.Driver" : "org.postgresql.Driver";
            String configFile = test ? "dbConfigTest" : "dbConfig";

            //check if connection is null
            if(connection == null) {
                // if we don't have a connection yet, we can create one:

                //FIRST: specify the postgres driver
                try {
                    Class.forName(driver);
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // access these values from a different file through the configFile value selected above.
                ResourceBundle bundle = ResourceBundle.getBundle(configFile);
                String url = bundle.getString("url");
                String username = bundle.getString("username");
                String password = bundle.getString("password");

                try {
                    connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                    System.out.println("Something went wrong when creating the connection!");
                    e.printStackTrace();
                }
            }
            return connection;
        }
}
