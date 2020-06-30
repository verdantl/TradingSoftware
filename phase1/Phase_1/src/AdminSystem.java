import java.io.*;
import java.util.ArrayList;
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
        adminPrompts.displayOptions();

    }

    /**
     * Runs the program in a loop
     */
    @Override
    public void run() {
        init();
        while (running){
            int option = scanner.nextInt();
            switch (option){
                case 1:
                    adminApproval();
                    break;
                case 2:
                    freezeMenu();
                    break;
                case 3:
                    approveItems();
                    break;
                case 4:
                    viewTraders();
                    break;
                case 5:
                    changeLimit();
                    break;
                case 6:
                    changeUserInfo();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Command not recognized. Try again.");
                    init();
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


    //I commented out his original lines and added mine in
    //So for here I added a printAccounts function that returns a string representation of all the accounts
    //I like being able to input the username better tbh but he didn't do that here (Jeffrey)
    /**
     * Display the menu that allows the admin to manage frozen/unfrozen accounts
     */
    public void freezeMenu(){
        ArrayList<Trader> traders = traderActions.getTraders();
        adminPrompts.displayFreezeMenu();
        int option = scanner.nextInt();
        do {
            switch (option) {
                //We need to decide how we're going to loop back. Will we just go back to the main menu each time?
                // I did make a setToMainMenu method

                //we could have like enter [1] to change the option or enter [-1] to return to Main Menu
                //I did that in a do while loop instead of doing it in a recursive way
                case 1:
                    ArrayList<Trader> flaggedAccounts =
                            adminActions.getListOfFlaggedAccounts(traders, atLeast, maxIncomplete, maxWeekly);
                    adminPrompts.displayFreezeOptions(1, flaggedAccounts);
//                System.out.println("(input an number from 1 to"+ flaggedAccounts.size()+")");
                    int chosenFlag = scanner.nextInt();
                    //I don't think you need to subtract 1 since your printAccounts method starts at 0
                    boolean freeze = adminActions.freezeAccount(flaggedAccounts.get(chosenFlag));
                    adminPrompts.displayFreezeConfirmation(freeze, "Freeze");
                    break;
                case 2:
                    ArrayList<Trader> frozenAccounts = adminActions.getFreezeAccount();
                    adminPrompts.displayFreezeOptions(2, frozenAccounts);
//                System.out.println("(input an number from 1 to"+ frozenAccounts.size() +")");
//                System.out.println(frozenAccounts);
                    int chosenFrozen = scanner.nextInt();
                    boolean unfreeze = adminActions.unfreezeAccount(frozenAccounts.get(chosenFrozen - 1));
                    adminPrompts.displayFreezeConfirmation(unfreeze, "Unfreeze");
                    break;
                case 3:
                    adminPrompts.displayFreezeOptions(3, traders);
//                System.out.println("(input an number from 1 to"+ allTraders.size()+")");
//                System.out.println(traders);
                    int chosenAccount = scanner.nextInt();
                    boolean freezeGeneral = adminActions.freezeAccount(traders.get(chosenAccount - 1));
                    adminPrompts.displayFreezeConfirmation(freezeGeneral, "Freeze");
                    break;
                default:
                    //I haven't handled this part in the prompts yet
                    System.out.println("Selection not recognized.");
            }
            System.out.print("Enter ["+toMainMenu+"] to return to the main menu or enter any number return to Freeze/" +
                    "Unfreeze Menu: ");
            option = scanner.nextInt();
        }while(option != -1); //if the option == -1, the execution will end
        //displayFreezeMenu();
    }

    /**
     * Display the menu that allows the admin to approve items
     */
    public void approveItems() {

        int option;
        //do {
        System.out.println("Here is the list of traders.");
        //we can do it like this if we are going to have a class variable that stores traderActions.
        //i think printAccounts method should be in TraderActions class. so we can just do traderActions.printAccounts();
        //instead of passing list of traders around.

        System.out.println(traderActions.printAccounts());
        System.out.print("Enter the number beside trader that you want to view: ");
        int traderID = scanner.nextInt();

        //same here, we could have a method in TraderActions class that returns a trader object based on index
        //for now, I will just use the ArrayList method assuming that it is a valid input
        Trader trader = traderActions.getTraders().get(traderID);

        //we can have a method searchTrader(int index) that returns a trader object or null
        //Trader trader = traderActions.searchTrader(traderId);
        //if (trader == null){
        //  S.o.pln("Invalid input")
        //}else{
        // proceed to approve/reject screen
        //}
        //S.o.pln("Enter -1 to return to main menu or enter anything to return to Aprrove items menu");
        //option = scanner.nextInt();
        //something like this

            System.out.println("[1] Approve/reject all the items this trader proposed");
            System.out.println("[2] Approve/reject a specific item this trader proposed");



        //}while(option != toMainMenu);

//        ArrayList<Trader> allTraders = traderActions.getTraders();
//        System.out.print(printAccounts(allTraders));
//        System.out.println("Here are all the traders, please enter the number of the account you wish to view:");
//        int num = scanner.nextInt();
//        Trader chosenTrader = allTraders.get(num - 1);
//        System.out.println("Enter the number for your next selection:");
//        System.out.println("[1] approve/reject all the items this trader proposed");
//        System.out.println("[2] approve/reject a specific item this trader proposed");
//        int option = scanner.nextInt();
//        switch (option){
//            case 1:
//                System.out.println("You want to approve(true) or disapprove(false)?");
//                boolean choice1 = scanner.nextBoolean();
//                boolean approveAll = adminActions.approveAllItems(chosenTrader, choice1);
//                if(approveAll){
//                    System.out.println("You have approved all the items");
//                }else{
//                    System.out.println("You have disapproved all the items");
//                }
//                break;
//            case 2:
//                System.out.println("Here are all the items that the user proposed, please choose one");
//                System.out.println("(input a number from 1 to" + chosenTrader.getProposedItems().size()+")");
//                System.out.println(chosenTrader.getProposedItems());
//                int chosenItem = scanner.nextInt();
//                System.out.println("You want to approve(true) or disapprove(false) this item?");
//                boolean choice2 = scanner.nextBoolean();
//                Item item = chosenTrader.getProposedItems().get(chosenItem - 1);
//                boolean approve = adminActions.approveItem(chosenTrader, item, choice2);
//                if(approve){
//                    System.out.println("You have approved the item");
//                }else{
//                    System.out.println("You have disapproved the item");
//                }
//                break;
//        }

    }


    /**
     * Allows an admin to approve or reject administrative requests.
     */
    public void adminApproval(){
        adminPrompts.displayAdminApproval(adminActions.getAdminRequests());
        String option = scanner.nextLine();
        Boolean approved = approveOrReject();
        boolean loop = false;
        if (approved == null){
            loop = true;
        }
        else {
            if (option.equals("0")) {
                setToMainMenu();
            } else if (option.equals("all")) {
                System.out.println("Processing...");
                confirmApproval(adminActions.approveAllAdmins(approved));
            } else if (adminActions.getAdminRequests().toString().contains(option)) {
                System.out.println("Processing");
                confirmApproval(adminActions.approveAdmin(option, approved));
            } else {
                loop = true;
                System.out.println("Input not recognized.");
            }
        }
        if (loop){
            adminApproval();
        }
    }

    private void confirmApproval(boolean approved){
        adminPrompts.confirmAdminApproval(approved);
        adminApproval();
    }

    private Boolean approveOrReject(){
        adminPrompts.displayApproveOrReject();
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
        String option;
        int succeed = -1;
        do {
            System.out.print("Enter [1] to change username or enter [2] to change password: ");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    succeed = changeUsername();
                    break;
                case "2":
                    succeed = changePassword();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }

            if (succeed != -1) {
                if (succeed == 1) {
                    System.out.println("Username/password successfully changed.");
                } else {
                    System.out.println("Failed to change username/password.");
                }
            }
            System.out.print("Enter ["+toMainMenu+"] to return to the main menu or enter anything to return to the" +
                    "change Username/Password menu");
            option = scanner.nextLine();
        }while(!option.equals(toMainMenu));
    }

    /**
     * Change limit on transactions
     */
    public void changeLimit(){
        //Option for main menu?
        String option;
        int succeed = -1;
        do{
            System.out.println("Enter [1] to change Weekly Transaction limit,");
            System.out.println("Enter [2] to change Maximum Incomplete Transaction limit, or");
            System.out.print("Enter [3] to change atLeast value: ");
            option = scanner.nextLine();
            switch (option){
                case "1":
                case "2":
                case "3":
                    succeed = setNewLimit(option);
                    break;
                default:
                    System.out.println("Invalid input");
            }
            if (succeed != -1) {
                if (succeed == 1) {
                    System.out.println("Limit successfully changed.");
                } else {
                    System.out.println("Failed to change limit.");
                }
            }
            System.out.print("Enter ["+toMainMenu+"] to return to the main menu or enter anything to return to" +
                    "change limit menu: ");
            option = scanner.nextLine();

        }while(!option.equals(toMainMenu));
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
    public void viewTraders(){
        adminPrompts.displayTraderMenu();
        String user = scanner.nextLine();
        switch (user){
            case "0":
                setToMainMenu();
            case "all":
                adminPrompts.displayAllTraders(traderActions.getTraders());
                break;
            default:
                adminPrompts.displayTrader(findTrader(user));
        }
        restartViewTraders();
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
        adminPrompts.displayRestartTrader();
        String choice = scanner.nextLine();
        switch (choice){
            case "0":
                setToMainMenu();
            case "1":
                viewTraders();
                break;
            default:
                adminPrompts.commandNotRecognized();
                restartViewTraders();
                break;
        }
    }

    public void setToMainMenu(){
        adminPrompts.setToMainMenu();
        stop();
        init();
    }
}
