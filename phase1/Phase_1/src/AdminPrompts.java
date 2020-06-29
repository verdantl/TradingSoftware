import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AdminPrompts {
    private int atLeast;
    private int maxIncomplete;
    private int maxWeekly;
    private ArrayList<String> options;
    private AdminActions adminActions;
    private TraderActions traderActions;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";

    /**
     * Constructor for the AdminPrompts class
     */
    public AdminPrompts(){
        options = new ArrayList<>();

        //for now, I manually typed in the options
        //we can change this to reading in files using setOptions or we can just do it in here
        options.add("Add/remove new admin");
        options.add("Manage frozen/unfrozen accounts");
        options.add("Approve Items");
        options.add("View trader's status");
        options.add("Change limit");
        options.add("Quit Program");
    }

    /**
     * Setter for the  list of options displayed to the user
     * @param options an arraylist of new options
     */
    //Here we have overloading, we could replace this with a single method and using Collections
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    /**
     * Alternate setter for the list of options displayed to the user
     * @param options an array of new options
     */
    public void setOptions(String[] options){
        this.options.clear();
        this.options.addAll(Arrays.asList(options));
    }


    public void setToMainMenu(){
        System.out.println("Going back to main menu...");
    }

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
        for (int i = 0; i < options.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(options.get(i));
            selections.append(' ');
        }
        System.out.println(instruction);
        System.out.println(selections);
    }

    private StringBuilder printAccounts(ArrayList<Trader> traders){
        StringBuilder accounts = new StringBuilder();
        for (int i = 0; i < traders.size(); i++){
            if (accounts.length() > 80){
                accounts.append("\n");
            }
            accounts.append(i).append(". ");
            accounts.append(traders.get(i).getUsername());
        }
        return accounts;
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


    public void displayFreezeOptions(int i, ArrayList<Trader> accounts){
        System.out.print(printAccounts(accounts));
        switch (i){
            case 1:
                System.out.println("Here are all the flagged accounts, please enter the number of the account you " +
                        "wish to freeze.");
            case 2:
                System.out.println("Here are all the frozen accounts, please enter the number of the account" +
                        " you wish to unfreeze:");
            case 3:
                System.out.println("Here are all the accounts, please enter the number of the account you wish " +
                        "to freeze:");
        }
    }

    public void displayFreezeConfirmation(boolean value, String string){
        if (value){
            System.out.println(string + " successful.");
        } else{
            System.out.println(string + " failed.");
        }
    }

    public void displayItemMenu(){

    }



    ///Below here is all Qinyu's stuff from before

    /**
     * Display the menu that allows the admin to approve items
     */
    public void displayApproveItemsMenu(){
        ArrayList<Trader> allTraders = traderActions.getTraders();
        System.out.println("Here are all the traders, please choose one you want to approve:");
        System.out.println("(input an number from 1 to"+ allTraders.size()+")");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        Trader chosenTrader = allTraders.get(num - 1);
        System.out.println("What you want to do next:");
        System.out.println("1. approve/disapprove all the items this trader proposed");
        System.out.println("2. approve/disapprove a specific item this trader proposed");
        int option = input.nextInt();
        switch (option){
            case 1:
                System.out.println("You want to approve(true) or disapprove(false)?");
                boolean choice1 = input.nextBoolean();
                boolean approveAll = adminActions.approveAllItems(chosenTrader, choice1);
                if(approveAll){
                    System.out.println("You have approved all the items");
                }else{
                    System.out.println("You have disapproved all the items");
                }
                break;
            case 2:
                System.out.println("Here are all the items that the user proposed, please choose one");
                System.out.println("(input a number from 1 to" + chosenTrader.getProposedItems().size()+")");
                System.out.println(chosenTrader.getProposedItems());
                int chosenItem = input.nextInt();
                System.out.println("You want to approve(true) or disapprove(false) this item?");
                boolean choice2 = input.nextBoolean();
                Item item = chosenTrader.getProposedItems().get(chosenItem - 1);
                boolean approve = adminActions.approveItem(chosenTrader, item, choice2);
                if(approve){
                    System.out.println("You have approved the item");
                }else{
                    System.out.println("You have disapproved the item");
                }
                break;
        }

    }

    public void displayAdminApproval(StringBuilder requests){
        String message = "Enter the username of the administrator you wish to approve/reject, or enter" +
                "[all] to approve/reject all of the admin requests. Enter [0] to go back to the main menu.";
        System.out.println(requests);
        System.out.println(message);

    }

    public void displayApproveOrReject(){

        System.out.println("Do you wish to approve or reject? [1] Approve | [2] Reject | [3] Go back");
    }

    public void confirmAdminApproval(boolean approved){
        System.out.println("Processing...");
        if (approved){
            System.out.println("Successfully approved!");
        }
        else{
            System.out.println("Successfully denied.");
        }
    }

    public void displayTraderMenu(){
        String message = "Please enter the username of the account you wish to view. Enter [0]" +
                " to go back to the main menu. Enter [all] to view all trader accounts.";
        System.out.println(message);
    }

    public void displayRestartTrader(){
        System.out.println("Enter [0] to go back to main menu. Enter [1] to continue viewing traders.");
    }

    public void displayAllTraders(ArrayList<Trader> traders){
        printAccounts(traders);
    }

    public void displayTrader(Trader trader){
        try{
            System.out.println(trader);
        } catch (NullPointerException e){
            System.out.println("User not found.");
        }
    }

}
