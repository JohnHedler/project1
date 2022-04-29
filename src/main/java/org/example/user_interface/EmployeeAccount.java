package org.example.user_interface;

import org.example.entity.Employee;
import org.example.utilities.AppUtilities;
import org.example.utilities.InputUtilities;

public class EmployeeAccount {
    //displays employee account screen page to user
    public static void EmployeeAccountScreen(Employee employee) {
        boolean done = false;

        do {
            //clear screen
            AppUtilities.ClearScreen();

            //display divider
            AppUtilities.DisplayDivider();

            //display header
            AppUtilities.DisplayHeaderEmployeeAccount();

            //display employee options
            if(employee.getEmployeeType().equals("manager")) {
                System.out.println("\nPlease choose an option:" +
                        "\n1) Submit Ticket" +
                        "\n2) View Pending Tickets" +
                        "\n3) View Past Tickets" +
                        "\n4) View Tickets By Date" +
                        "\n5) Log Out" +
                        "\n6) Manage Employees" +
                        "\n7) Manage Tickets");
            }else{
                System.out.println("\nPlease choose an option:" +
                        "\n1) Submit Ticket" +
                        "\n2) View Pending Tickets" +
                        "\n3) View Past Tickets" +
                        "\n4) View Tickets By Date" +
                        "\n5) Log Out");
            }

            //ask user for input
            if(employee.getEmployeeType().equals("manager")) {
                System.out.print("\nOption (1-7): ");
            }else{
                System.out.print("\nOption (1-5): ");
            }
            done = EmployeeReimbursementChoice(employee);
        } while (!done);
    }

    public static boolean EmployeeReimbursementChoice(Employee employee) {
        int choice;
        do{
            choice = InputUtilities.ValidateInteger();

            switch(choice){
                case 1:
                    //submit new ticket

                    break;
                case 2:
                    //view pending tickets

                    break;
                case 3:
                    //view past tickets
                    break;
                case 4:
                    //view tickets by date

                    break;
                case 5:
                    //log out
                    break;
                case 6:
                    //proceed to manage employees dialog if manager
                    //else, inform employee that they must choose the specified options
                    if(employee.getEmployeeType().equals("manager")){
                        ManageEmployees.ManageEmployeesScreen();
                    }else{
                        System.out.println("*Invalid option! Please choose 1-5.");
                    }
                    break;
                case 7:
                    //proceed to manage employees dialog if admin
                    //else, inform employee that they must choose the specified options
                    if(employee.getEmployeeType().equals("manager")){
                        ManageTickets.ManageTicketsScreen();
                    }else{
                        System.out.println("*Invalid option! Please choose 1-5.");
                    }
                    break;
                default:
                    if(employee.getEmployeeType().equals("manager")) {
                        System.out.print("*Invalid option! Please try again." +
                                "\nOption (1-7): ");
                    }else{
                        System.out.print("*Invalid option! Please try again." +
                                "\nOption (1-5): ");
                    }
                    break;
            }
        }while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7);

        if(choice != 5){
            return false;
        }else{
            return true;
        }
    }
}
