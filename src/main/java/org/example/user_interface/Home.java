package org.example.user_interface;

import org.example.entity.Employee;
import org.example.service.EmployeeService;
import org.example.utilities.AppUtilities;
import org.example.utilities.InputUtilities;

public class Home {

    //display the home screen to the user
    public static void HomeScreen(){
        boolean done = false;

        do{
            //clear screen
            AppUtilities.ClearScreen();

            //display divider
            AppUtilities.DisplayDivider();

            //display header
            AppUtilities.DisplayHeaderHome();

            //display options to user
            System.out.println("Please choose an option:" +
                    "\n1) Employee Login" +
                    "\n2) Employee Registration" +
                    "\n3) Exit");

            //ask user for input
            System.out.print("\nOption (1-3): ");
            done = HomeChoice();
        }while(!done);
    }

    //HomeChoice method: takes user input and decides what the program should execute
    public static boolean HomeChoice(){
        int choice;
        do{
            choice = InputUtilities.ValidateInteger();

            switch(choice){
                case 1:
                    //employee login
                    Login();
                    break;
                case 2:
                    //employee registration
                    EmployeeService.insertEmployee();
                    break;
                case 3:
                    //exit application choice
                    break;
                default:
                    System.out.print("*Invalid option! Please try again." +
                            "\nOption (1-3): ");
                    break;
            }
        }while(choice != 1 && choice != 2 && choice != 3);

        //if user chooses 1 or 2, return false to keep asking for options until they are done
        //if user chooses 3, return true to exit application
        if(choice != 3){
            return false;
        }else{
            return true;
        }
    }

    //Employee Login method: allows user to sign in and access their bank accounts
    public static void Login() {
        //login variables
        String input;
        int counter = 3;
        Employee employee;

        employee = EmployeeService.getEmployeeByUserName();

        if(employee != null){
            do{
                System.out.println("Please enter your password: ");
                input = InputUtilities.ValidateString();

                if(input.equals(employee.getPassword())){
                    counter = 0;
                    EmployeeAccount.EmployeeAccountScreen(employee);
                }else{
                    System.out.println("The password you entered is incorrect.");
                    counter--;
                    System.out.println("Tries remaining: " + counter);
                }
            }while(counter > 0);

        }
    }
}
