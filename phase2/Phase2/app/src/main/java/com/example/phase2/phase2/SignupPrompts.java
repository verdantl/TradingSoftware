package com.example.phase2.phase2;

public class SignupPrompts {

    /**
     * Asks the user if they want to sign up for an admin or a trader account
     */
    public void adminOrTrader(){
        System.out.println("Enter [0] if you would like to signup for an admin request." +
                " Enter [1] to sign up for an account. Enter [2] to go back to the login screen");
    }

    /**
     * Asks the user to input the username they want
     */
    public void displayCreateUserName(){
        System.out.println("Please enter the username you would like to create. Do not include any spaces.");
    }

    /**
     * Displays the username verification message
     * @param value if the username is available or not
     */
    public void displayUserAvailable(boolean value){
        if (value){
            System.out.println("Username is available.");
        }
        else{
            System.out.println("Username is unavailable. Try another.");
        }
    }

    /**
     * Displays the command not recognized message
     */
    public void commandNotRecognized(){
        System.out.println("Command not recognized. Returning to Login.");
    }

    /**
     * Displays the prompt for the user to create the password
     */
    public void displayCreatePassword(){
        System.out.println("Please enter the password you would like to use for this account.");
    }

    /**
     * Displays the successful account creation message
     */
    public void displayAccountSuccessful(){
        System.out.println("Account successfully created! Returning to login menu...");
    }
}
