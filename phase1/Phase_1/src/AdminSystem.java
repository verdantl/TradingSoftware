import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminSystem extends UserSystem{
    private boolean running;
    private AdminActions adminActions;
    private Admin currentAdmin;
    private AdminPrompts adminPrompts;
    private boolean changeDisplay;
    private String fileName;
    private int atLeast;
    private int maxIncomplete;
    private int maxWeekly;
    private TraderActions traderActions;
    private Scanner scanner;
    private final String toMainMenu = "-1";


    /**
     * Constructor for the AdminSystem
     * @param admin The admin user that has logged into the system
     * @param fileName The name of the file
     */
    public AdminSystem(Admin admin, String fileName){
        //I think we should read in from files.
        this.fileName = fileName;
        currentAdmin = admin;
        scanner = new Scanner(System.in);

        ArrayList<Admin> admins = new ArrayList<>();
        //gotta read this too
        ArrayList<Admin> adminRequests = new ArrayList<>();
        //something like this
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null){
                String[] info = line.split(",");
                admins.add(new Admin(info[0], info[1]));
                line = in.readLine();
            }
            in.close();

        }catch (IOException iox){
            System.out.println("File Not Found");
        }
        adminActions = new AdminActions(admins, adminRequests);
        //May need to change constructor
        adminPrompts = new AdminPrompts();
        //may want to change the following


    }


    //Everything below here right now is part of the loop
    //This method helps set up some stuff
    private void init(){
        running = true;

        //this is a temporary holder
        adminPrompts.setOptions(new String[2]);
    }


    /**
     * Runs the program in a loop
     */
    @Override
    public void run() {
        //this is the loop for the system
        while (running){
            adminPrompts.displayOptions();
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option){
                case 1:
                    adminApproval();
                case 2:
                    adminPrompts.displayFreezeMenu();
                    break;
                case 3:
                    adminPrompts.displayApproveItemsMenu();
                    break;
                case 4:
                case 5:
                    changeLimit();
                    break;
                case 6:
                    changeUserInfo();
                    break;
                case 7:
                    running = false;
            }

        }
    }

    //This might be better off in phase 2 as a listener, but here we're updating every iteration
    //and calling other methods that will update the controller
    protected void update(){
    }

    @Override
    protected void stop() {
        running = false;
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


    /**
     * Allows an admin to approve or reject administrative requests.
     */
    public void adminApproval(){
        String message = "Enter the username of the administrator you wish to approve/reject, or enter" +
                "[all] to approve/reject all of the admin requests. Enter [0] to go back to the main menu.";
        System.out.println(adminActions.getAdminRequests());
        System.out.println(message);
        String option = scanner.nextLine();
        Boolean approved = approveOrReject();
        boolean loop = false;
        if (approved == null){
            loop = true;
        }
        else {
            if (option.equals("0")) {
                System.out.println("Going back...");
                //We gotta go back to the main screen
            } else if (option.equals("all")) {
                System.out.println("Processing...");
                confirmApproval(adminActions.approveAllAdmins(approved));
            } else if (adminActions.getAdminRequests().toString().contains(option)) {
                System.out.println("Processing");
                confirmApproval(adminActions.approveAdmin(option, approved));
            } else {
                loop = true;
            }
        }
        if (loop){
            System.out.println("Input not recognized.");
            adminApproval();
        }
    }

    private void confirmApproval(boolean approved){
        if (approved){
            System.out.println("Successfully approved!");
        }
        else{
            System.out.println("Successfully denied.");
        }
        adminApproval();
    }

    private Boolean approveOrReject(){
        System.out.println("Do you wish to approve or reject? [1] Approve | [2] Reject | [3] Go back");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
            case "3":
                //The method returns a boolean so
                //for now, I changed it to false (Junhee)
                //I made it a reference type Boolean which has null, the null case
                // is handled in the adminApproval method which is a bit messy rn
                //but will fix (Jeffrey)
                return null;
            default:
                System.out.println("Command not recognized.");
                return approveOrReject();
        }
    }


    /**
     * Change username or password of an admin.
     */
    public void changeUserInfo(){
        //Maybe we want to add the option to go back to the main menu
        System.out.print("Enter [1] to change username or enter [2] to change password: ");
        String option = scanner.nextLine();
        int succeed = -1;

        do {
            switch (option) {
                case "1":
                    succeed = changeUsername();
                    break;
                case "2":
                    succeed = changePassword();
                    break;
                default:
                    System.out.println("Invalid input");
                    System.out.println("Try again or enter ["+toMainMenu+"] to return to the main menu");
                    option = scanner.nextLine();
                    break;
            }

            if (succeed != -1) {
                if (succeed == 1) {
                    System.out.println("Username/password successfully changed.");
                } else {
                    System.out.println("Failed to change username/password.");
                }
                System.out.println("Enter [" +toMainMenu+ "] to return to main menu.");
                option = scanner.nextLine();
            }
        }while(!option.equals(toMainMenu));
    }

    /**
     * Change limit on transactions
     */
    public void changeLimit(){
        //Option for main menu?
        System.out.println("Enter [1] to change Weekly Transaction limit,");
        System.out.println("Enter [2] to change Maximum Incomplete Transaction limit, or");
        System.out.print("Enter [3] to change atLeast value: ");
        String option = scanner.nextLine();
        int succeed = -1;
        do{
            switch (option){
                case "1":
                case "2":
                case "3":
                    succeed = setNewLimit(option);
                    break;
                default:
                    System.out.println("Invalid input");
                    System.out.println("Try again or enter ["+toMainMenu+"] to return to the main menu");
                    option = scanner.nextLine();
                    break;
            }
            if (succeed != -1) {
                if (succeed == 1) {
                    System.out.println("Limit successfully changed.");
                } else {
                    System.out.println("Failed to change limit.");
                }
                System.out.println("Enter [" +toMainMenu+ "] to return to main menu.");
                option = scanner.nextLine();
            }

        }while(option.equals(toMainMenu));
    }

    private int setNewLimit(String option){
        System.out.print("Enter new limit: ");
        int newValue = scanner.nextInt();
        if (newValue < 0) {
            return 0;
        }else {
            switch (option) {
                case "1":
                    maxWeekly = newValue;
                    break;
                case "2":
                    maxIncomplete = newValue;
                    break;
                case "3":
                    atLeast = newValue;
                    break;
            }
            return 1;
        }
    }

    private int changeUsername(){
        System.out.print("Enter new username: ");
        String newName = scanner.nextLine();
        if (newName.equals(currentAdmin.getUsername())){
            return 0;
        }
        currentAdmin.setUsername(newName);
        return 1;
    }

    private int changePassword(){
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        if (newPassword.equals(currentAdmin.getPassword())){
            return 0;
        }
        currentAdmin.setPassword(newPassword);
        return 1;

    }

    /**
     * Prints a trader with the input username to the screen
     */
    public void viewTrader(){
        String message = "Please enter the username of the account you wish to view. Enter [0]" +
                " to go back to the main menu. Enter [all] to view all trader accounts.";
        String user = scanner.nextLine();
        switch (user){
            case "0":
                //We don't do anything here because we want to go back to main menu
            case "all":
                printAllTraders();
                break;
            default:
                try{
                    System.out.println(findTrader(user));
                } catch (NullPointerException e){
                    System.out.println("User not found.");
                }
        }
        restartViewTraders();
    }

    private void printAllTraders(){
        for (Trader trader: traderActions.getTraders()){
            System.out.println(trader);
        }
    }
    private Trader findTrader(String user){
        for (Trader trader: traderActions.getTraders()){
            if (trader.getUsername().equals(user)){
                return trader;
            }
        }
        return null;
    }

    private void restartViewTraders() {
        System.out.println("Enter [0] to go back to main menu. Enter [1] to continue viewing traders.");
        String choice = scanner.nextLine();
        switch (choice){
            case "0": //Go back to main menu
                System.out.println("Yes");
            case "1":
                viewTrader();
                break;
            default:
                System.out.println("Command not recognized.");
                restartViewTraders();
                break;
        }
    }
}
