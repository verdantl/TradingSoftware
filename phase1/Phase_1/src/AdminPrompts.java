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
    private final String toMainMenu = "-1";

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
        for (int i = 0; i < menuOptions.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(menuOptions.get(i));
            selections.append(' ');
        }
        String instruction = "Please enter the number of the option " +
                "you would like to select.";
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


    public void displayFreezeOptions(int i, ArrayList<Trader> accounts){
        System.out.print(printAccounts(accounts, false));
        switch (i){
            case 1:
                System.out.println("Here are all the flagged accounts, please enter the number of the account you " +
                        "wish to freeze.");
            case 2:
                System.out.println("Here are all the unfreeze requests, please enter the number of the account" +
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

    public void displayItemMenu(ArrayList<Trader> traders){
        System.out.println("Here is the list of all traders awaiting item approval:");
        System.out.print(printAccounts(traders, true));
    }

    public void displayTraderProposedItems(ArrayList<Item> items){
        System.out.print(printItems(items));
        System.out.println("Please enter the number for the item you wish to approve. Enter" +
                " [all] to approve all items.");
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
        System.out.println("Do you wish to approve or reject? [1] Approve | [2] Reject | [0] Go back");
    }

    public void confirmApproval(boolean approved){
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
        printAccounts(traders, false);
    }

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
        System.out.println("Enter [1] to change Weekly Transaction limit,");
        System.out.println("Enter [2] to change Maximum Incomplete Transaction limit, or");
        System.out.print("Enter [3] to change atLeast value: ");
    }

    /**
     * Display menu for change username/password
     */
    public void displayChangeUserInfoMenu(){
        System.out.println("Enter [1] to change username");
        System.out.print("Enter [2] to change password: ");
    }

    /**
     * Display return to main menu message
     */
    public void displayReturnToMainMenu(){
        System.out.print("Enter ["+toMainMenu+"] to return to the main menu or enter anything to return to" +
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
        System.out.print("Enter new"+subject+": ");
    }

}
