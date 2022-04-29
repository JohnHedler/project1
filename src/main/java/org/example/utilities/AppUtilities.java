package org.example.utilities;

//Class AppUtilities
//Function: Modifies console or displays text to user specifying what section user is at
public class AppUtilities {

    //region screen methods

    //clear screen
    public static void ClearScreen(){
        System.out.print("\n\n\n\n");
    }

    //display divider
    public static void DisplayDivider(){
        System.out.println("---------------------------------\n");
    }

    //display application header to user
    public static void DisplayHeaderHome(){
        System.out.println("===========================================================");
        System.out.println("|   Project 1 Employee Reimbursement Management System    |");
        System.out.println("===========================================================\n");
    }

    //display employee login to user
    public static void DisplayHeaderEmployeeLogin(){
        System.out.println("===========================");
        System.out.println("|     Employee Login      |");
        System.out.println("===========================\n");
    }

    //display employee account to user
    public static void DisplayHeaderEmployeeAccount(){
        System.out.println("===========================");
        System.out.println("|    Employee Account     |");
        System.out.println("===========================\n");
    }

    //display employee account to user
    public static void DisplayHeaderManageEmployees(){
        System.out.println("===========================");
        System.out.println("|    Manage Employees     |");
        System.out.println("===========================\n");
    }

    //endregion
}