package org.example.user_interface;

import org.example.service.EmployeeService;
import org.example.utilities.AppUtilities;
import org.example.utilities.InputUtilities;

public class ManageEmployees {

    public static void ManageEmployeesScreen() {
        boolean done = false;

        do {
            //clear screen
            AppUtilities.ClearScreen();

            //display divider
            AppUtilities.DisplayDivider();

            //display header
            AppUtilities.DisplayHeaderManageEmployees();

            //display manage customer options
            System.out.println("\nPlease choose an option:" +
                    "\n1) Insert Employee" +
                    "\n2) Get Employee By Id" +
                    "\n3) Get All Employees" +
                    "\n4) Update Employee" +
                    "\n5) Delete Employee" +
                    "\n6) Go Back");

            //ask user for input
            System.out.print("\nOption: ");
            done = ManageEmployeeChoice();
        } while (!done);
    }

    public static boolean ManageEmployeeChoice() {
        int choice;
        do {
            choice = InputUtilities.ValidateInteger();

            switch (choice) {
                case 1:
                    //call employee service -> insert employee
                    EmployeeService.insertEmployee();
                    break;
                case 2:
                    //call employee service -> get employee by id
                    EmployeeService.getEmployeeById();
                    break;
                case 3:
                    //call employee service -> get all employees
                    EmployeeService.getAllEmployees();
                    break;
                case 4:
                    //call employee service -> update employee
                    EmployeeService.updateEmployee();
                    break;
                case 5:
                    //call employee service -> delete employee
                    EmployeeService.deleteEmployee();
                    break;
                case 6:
                    //go back one page
                    break;
                default:
                    System.out.print("*Invalid option! Please try again." +
                            "\nOption: ");
                    break;
            }
        } while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6);

        if (choice != 6) {
            return false;
        } else {
            return true;
        }
    }
}