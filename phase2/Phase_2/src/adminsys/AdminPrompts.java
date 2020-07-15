package adminsys;

import items.Item;
import users.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminPrompts {
    private int atLeast;
    private int maxIncomplete;
    private int maxWeekly;
    private ArrayList<String> menuOptions;
    private AdminActions adminActions;
    private TraderActions traderActions;
    private final String toMainMenu = "0";

    /**
     * Constructor for the AdminPrompts class
     */
    public AdminPrompts(){
        menuOptions = new ArrayList<>();

        //for now, I manually typed in the options
        //we can change this to reading in files using setOptions or we can just do it in here
        setOptions(new String[]{"Add/remove new admin", "Manage Frozen/Unfrozen Accounts",
        "Approve Items", "View Trader's Status", "Change Limits", "Change Account Information",
        "Quit Program"});

    }

    /**
     * Setter for the  list of options displayed to the user
     * @param options an array of options
     */

    public void setOptions(String[] options){
        menuOptions.clear();
        menuOptions.addAll(Arrays.asList(options));
    }


    /**
     * Displays the message to the administrator when going back to the main menu.
     */
    public void setToMainMenu(){
        System.out.println("Going back to main menu...");
    }


    /**
     * Displays the message to the administrator when an option is invalid/command is not recognized.
     */
    public void commandNotRecognized(){
        System.out.println("Command not recognized.");
    }

    /**
     * Prints a list of messages to the screen
     * @param messages an array of strings representing the messages displayed to the
     *                 administrator
     */
    public void displayMessage(String[] messages){
        for (String message: messages){
            System.out.println(message);
        }
    }

    /**
     * Prints the options available to the administrator
     */
    public void displayOptions(){
        StringBuilder selections = new StringBuilder();
        for (int i = 0; i < menuOptions.size(); i++){
            selections.append(i + 1);
            selections.append(". ");
            selections.append(menuOptions.get(i));
            selections.append(' ');
        }
        String instruction = "Please enter the number of the option " +
                "you would like to select. Enter [0] at any point to return to this menu.";
        System.out.println(instruction);
        System.out.println(selections);
    }

    private StringBuilder printAccounts(ArrayList<Trader> traders, boolean usernameOnly){
        StringBuilder accounts = new StringBuilder();
        for (int i = 0; i < traders.size(); i++){
            if (accounts.length() > 80){
                accounts.append("\n");
            }
            accounts.append(i + 1).append(". ");
            if (usernameOnly) {
                accounts.append(traders.get(i).getUsername());
            }
            else{
                accounts.append(traders.get(i).toString());
            }
            accounts.append(" ");
        }
        return accounts;
    }

    private StringBuilder printItems(ArrayList<Item> proposedItems){
        StringBuilder items = new StringBuilder();
        for (int i = 0; i < proposedItems.size(); i++){
            if (items.length() > 80){
                items.append("\n");
            }
            items.append(i + 1).append(". ");
            items.append(proposedItems.get(i));
        }
        return items;
    }

    /**
     * Display the menu that allows the admin to manage frozen/unfrozen accounts
     */
    public void displayFreezeMenu(){
        System.out.println("Please choose what you want to do next:");
        System.out.println("[0] Back to main menu");
        System.out.println("[1] View flagged accounts");
        System.out.println("[2] View unfreeze requests");
        System.out.println("[3] View all the traders");
    }

    /**
     * Displays the message to the admin in the freeze menu to return to main menu
     * @param mainMenu the main menu option from AdminSystem
     */
    public void displayFreezeHelper(String mainMenu){
        System.out.println("Enter ["+mainMenu+"] to return to the main menu or enter any number to return to Freeze/" +
                "Unfreeze Menu: ");
    }


    /**
     * Displays the account information of an arraylist of accounts passed in as a parameter
     * @param i the option that the administrator has selected
     * @param accounts the arraylist of accounts that are to be displayed
     */
    public void displayFreezeOptions(int i, ArrayList<Trader> accounts){
        System.out.print(printAccounts(accounts, false));
        switch (i){
            case 1:
                System.out.println("Here are all the flagged accounts. Enter [0] to go back, or enter the number of the account you " +
                        "wish to freeze:");
                break;
            case 2:
                System.out.println("Here are all the unfreeze requests. Enter [0] to go back, or enter the number of the account" +
                        " you wish to unfreeze:");
                break;
            case 3:
                System.out.println("Here are all the accounts. Enter [0] to go back, or enter the number of the account you wish " +
                        "to freeze:");
                break;
        }
    }

    /**
     * Displays a confirmation message for the administrator during a freeze/unfreeze attempt
     * @param value a boolean representing if the freeze/unfreeze was successful
     * @param string a string representing if the admin was trying freeze or unfreeze
     */
    public void displayFreezeConfirmation(boolean value, String string){
        if (value){
            System.out.println(string + " successful.");
        } else{
            System.out.println(string + " failed.");
        }
    }

    /**
     * Displays the menu for the item approval option for administrators.
     * @param traders an arraylist of all traders in the system who are awaiting item approval
     */
    public void displayItemMenu(ArrayList<Trader> traders){
        System.out.println("Here is the list of all traders awaiting item approval:");
        System.out.print(printAccounts(traders, true));
        System.out.println("\nEnter the number beside trader that you want to view: ");
    }

    /**
     * Displays the list of items awaiting approval
     * @param items an arraylist of items awaiting approval
     */
    public void displayTraderProposedItems(ArrayList<Item> items){
        System.out.print(printItems(items));
        System.out.println("\nPlease enter the number for the item you wish to select. Enter" +
                " [all] to select all items.");
    }

    /**
     * Displays a list of admin requests and the approval option for the administrator
     * @param requests a StringBuilder representing a list of all the admin requests currently in the system
     */
    public void displayAdminApproval(StringBuilder requests){
        String message = "This is the list of admins awaiting approval:";
        String instructions = "Enter the username of the administrator you wish to approve/reject, or enter" +
                "[all] to approve/reject all of the admin requests. Enter [0] to go back to the main menu.";
        System.out.println(message);
        System.out.println(requests);
        System.out.println(instructions);
    }

    /**
     * Displays the option to approve or reject a given item/admin request
     */
    public void displayApproveOrReject(){
        System.out.println("Do you wish to approve or reject? [1] Approve | [2] Reject | [0] Go back");
    }

    /**
     * Displays the confirmation of approval message to an administrator
     * @param approved a boolean representing if the administrator approved or denied a request/item
     */
    public void confirmApproval(boolean approved){
        System.out.println("Processing...");
        if (approved){
            System.out.println("Successfully approved!");
        }
        else{
            System.out.println("Successfully denied.");
        }
    }

    /**
     * Displays the submenu for viewing traders by an administrator
     */
    public void displayTraderMenu(){
        String message = "Please enter the username of the account you wish to view. Enter [0]" +
                " to go back to the main menu. Enter [all] to view all trader accounts.";
        System.out.println(message);
    }

    /**
     * Displays the options for restarting the trader menu
     */
    public void displayRestartTrader(){
        System.out.println("\nEnter [0] to go back to main menu. Enter [1] to continue viewing traders.");
    }

    /**
     * Displays a string representation of the given traders in the system
     * @param traders an arraylist of traders
     */
    public void displayAllTraders(ArrayList<Trader> traders){
        System.out.print(printAccounts(traders, false));
    }

    /**
     * Displays the information for a trader
     * @param trader the trader that should be displayed
     */
    public void displayTrader(Trader trader){
        try{
            System.out.println(trader);
        } catch (NullPointerException e){
            System.out.println("User not found.");
        }
    }

    /**
     * Display menu for change limit
     */
    public void displayChangeLimitMenu(){
        System.out.println("Enter [1] to change Maximum Incomplete Transaction limit");
        System.out.println("Enter [2] to change Weekly Transaction limit");
        System.out.print("Enter [3] to change atLeast lend value: ");
    }

    /**
     * Display menu for change username/password
     */
    public void displayChangeUserInfoMenu(){
        System.out.println("Enter [1] to change username.");
        System.out.print("Enter [2] to change password: ");
    }

    /**
     * Display return to main menu message
     */
    public void displayReturnToMainMenu(){
        System.out.print("Enter ["+toMainMenu+"] to return to the main menu or enter anything to return to the " +
                "previous menu: ");
    }

    /**
     * @param successful Represents the success of the changeLimit method. 1 represents true and 0 represents false.
     * @param subject The value that changed
     * Display a message that tells whether the value was successfully changed
     */
    public void displaySuccessMessage(int successful, String subject){
        if(successful == 1){
            System.out.println(subject + " changed successfully");
        }else{
            System.out.println("Failed to change "+subject);
        }
    }

    /**
     * Display error message due to invalid input
     */
    public void displayErrorMessage(){
        System.out.println("Invalid input");
    }

    /**
     * @param subject The value that needs to be entered
     * Display a message that prompts user to enter
     */
    public void displayEnterNewMessage(String subject){
        System.out.print("Enter new "+subject+": ");
    }


    /**Display the menu when admin is changing the threshold
     * @param currentValue the current value for the threshold
     */
    public void displayThresholdOption(int currentValue){
        System.out.println("The current value is " + currentValue);
        System.out.println("Please type a value you want to change:");
    }

}
