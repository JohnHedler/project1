package org.example;

import org.example.dao.DaoFactory;
import org.example.dao.TestDao;
import org.example.user_interface.Home;

public class App
{
    public static void main(String[] args) {
        //Initialize tables for H2 database
//        TestDao testDao = DaoFactory.getTestDao();
//        testDao.initTables();
//        testDao.fillTables();

        //Program Start> call welcome screen
        Home.HomeScreen();

        //Program End> end of program display
        System.out.println("\nThank you for using the Project1 Employee Reimbursement Management System." +
                "\nHave a good day!");
    }
}
