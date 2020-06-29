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

    /**
     * Display the menu that allows the admin to manage frozen/unfrozen accounts
     */
    public void displayFreezeMenu(){
        ArrayList<Trader> traders = traderActions.getTraders();
        System.out.println("Please choose what you want to do next:");
        System.out.println("1. view flagged accounts");
        System.out.println("2. view unfreeze requests");
        System.out.println("3. view all the traders");
        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        switch(option){
            case 1:
                List<Trader> flaggedAccounts =
                        adminActions.getListOfFlaggedAccounts(traders, atLeast, maxIncomplete, maxWeekly);
                System.out.println("Here are all the flagged accounts, please choose one to freeze:");
                System.out.println("(input an number from 1 to"+ flaggedAccounts.size()+")");
                System.out.println(flaggedAccounts);
                int chosenFlag = input.nextInt();
                boolean freeze = adminActions.freezeAccount(flaggedAccounts.get(chosenFlag - 1));
                if(freeze){
                    System.out.println("You have successfully frozen the account");
                }else{
                    System.out.println("freeze fails");
                }
                break;
            case 2:
                ArrayList<Trader> frozenAccounts = adminActions.getFreezeAccount();
                System.out.println("Here are all the frozen accounts, please choose one to unfreeze:");
                System.out.println("(input an number from 1 to"+ frozenAccounts.size() +")");
                System.out.println(frozenAccounts);
                int chosenFrozen = input.nextInt();
                boolean unfreeze = adminActions.unfreezeAccount(frozenAccounts.get(chosenFrozen - 1));
                if(unfreeze){
                    System.out.println("You have successfully frozen the account");
                }else{
                    System.out.println("freeze fails");
                }
                break;
            case 3:
                ArrayList<Trader> allTraders = traderActions.getTraders();
                System.out.println("Here are all the accounts, please choose one to freeze:");
                System.out.println("(input an number from 1 to"+ allTraders.size()+")");
                System.out.println(traders);
                int chosenAccount = input.nextInt();
                boolean freeze2 = adminActions.freezeAccount(allTraders.get(chosenAccount - 1));
                if(freeze2){
                    System.out.println("You have successfully frozen the account");
                }else{
                    System.out.println("freeze fails");
                }
                break;

        }
    }

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


}
