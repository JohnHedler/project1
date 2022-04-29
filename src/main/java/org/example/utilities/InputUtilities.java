package org.example.utilities;

import java.util.Scanner;

//Class InputUtilities
//Function: Validate input from user
public class InputUtilities {

    //validate string from user input
    public static String ValidateString() {
        String input = null;
        boolean validated = false;
        Scanner scanner = new Scanner(System.in);

        do {
            try{
                input = scanner.nextLine();
                if (!input.trim().isEmpty()) {
                    validated = true;
                }else{
                    validated = false;
                    throw new Exception();
                }
            }catch(Exception e){
                System.out.print("*Input cannot be white-spaces, blank, or null: ");
            }
        }while (!validated);

        return input;
    }

    //validate integer from user input
    public static int ValidateInteger(){
        String input;
        int intNum = 0;
        boolean validated = false;
        Scanner scanner = new Scanner(System.in);

        do {
            try{
                input = scanner.nextLine();
                intNum = Integer.parseInt(input);
                if (intNum >= 0) {
                    validated = true;
                }else{
                    validated = false;
                    throw new Exception();
                }
            }catch(Exception e){
                System.out.print("*Please enter a valid number: ");
            }
        }while (!validated);

        return intNum;
    }

    //validate integer from user input
    public static double ValidateDouble(){
        String input;
        double doubleNum = 0;
        boolean validated = false;
        Scanner scanner = new Scanner(System.in);

        do {
            try{
                input = scanner.nextLine();
                doubleNum = Double.parseDouble(input);
                if (doubleNum >= 0) {
                    validated = true;
                }else{
                    validated = false;
                    throw new Exception();
                }
            }catch(Exception e){
                System.out.print("*Please enter a valid number: ");
            }
        }while (!validated);

        return doubleNum;
    }
}
