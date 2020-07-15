package adminsys;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminSystem{

    private AdminPrompts adminPrompts;

    private TraderActions traderActions;
    private AdminActions adminActions;
    private TradeManager tradeManager;

    private boolean running;
    private Admin currentAdmin;
    private Scanner scanner;

    private final String toMainMenu = "0";


    /**
     * Constructor for the admin system
     *
     * @param traderActions the actions that a user can take involving traders
     * @param adminActions  the actions a user can take involving admins
     * @param tradeManager  the manager class for trades
     */
    public AdminSystem(TraderActions traderActions, AdminActions adminActions, TradeManager tradeManager) {
        adminPrompts = new AdminPrompts();
        //I think we should read in from files.
        this.traderActions = traderActions;
        this.adminActions = adminActions;
        this.tradeManager = tradeManager;

        running = false;
        scanner = new Scanner(System.in);

//        ArrayList<Admin> admins = new ArrayList<>();
//        //gotta read this too
//        ArrayList<Admin> adminRequests = new ArrayList<>();
//
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(fileName));
//            String line = in.readLine();
//            while (line != null){
//                String[] info = line.split(",");
//                admins.add(new Admin(info[0], info[1]));
//                line = in.readLine();
//            }
//            in.close();
//
//        }catch (IOException iox){
//            System.out.println("File Not Found");
//        }
//        adminActions = new AdminActions(admins, adminRequests);
//        traderActions = new TraderActions(new ArrayList<>());
//        tradeManager = new TradeManager();
//        //May need to change constructor
//        adminPrompts = new AdminPrompts();
//        //may want to change the following


    }

    /**
     * Sets the current admin for the admin system
     *
     * @param admin the current admin
     */
    public void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }


    //Everything below here right now is part of the loop
    //This method helps set up some stuff
    private void init() {
        running = true;
        //this is a temporary holder

    }

    /**
     * Runs the program in a loop
     */
    public void run() {
        init();
        while (running) {
            adminPrompts.displayOptions();
            int option = scanner.nextInt();
            //to remove dummy line
            //scanner.nextLine();
            switch (option) {
                case 1:
                    adminApproval();
                    break;
                case 2:
                    freezeMenu();
                    break;
                case 3:
                    approveItemsMenu();
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
                    stop();
                    break;
                default:
                    System.out.println("Command not recognized. Try again.");
                    break;
            }
        }
    }

    protected void stop() {
        running = false;
    }

    /**
     * Display the menu that allows the admin to manage frozen/unfrozen accounts
     */
    public void freezeMenu() {
        ArrayList<Trader> traders = traderActions.getTraders();
        adminPrompts.displayFreezeMenu();
        String option = scanner.next();
        switch (option) {
            case "0":
                setToMainMenu();
                break;
            case "1":
                ArrayList<Trader> flaggedAccounts =
                        traderActions.getListOfFlaggedAccounts();
                adminPrompts.displayFreezeOptions(1, flaggedAccounts);
                option = scanner.next();
                int chosenFlag;
                try {
                    chosenFlag = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    adminPrompts.commandNotRecognized();
                    break;
                }
                if (chosenFlag == 0) {
                    break;
                }
                boolean freeze = adminActions.freezeAccount(flaggedAccounts.get(chosenFlag - 1));
                adminPrompts.displayFreezeConfirmation(freeze, "Freeze");
                break;
            case "2":
                ArrayList<Trader> frozenAccounts = traderActions.getAllRequestToUnfreeze();
                adminPrompts.displayFreezeOptions(2, frozenAccounts);
                option = scanner.next();
                int chosenFrozen;
                try {
                    chosenFrozen = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    adminPrompts.commandNotRecognized();
                    break;
                }
                if (chosenFrozen == 0) {
                    break;
                }
                boolean unfreeze = adminActions.unfreezeAccount(frozenAccounts.get(chosenFrozen - 1));
                adminPrompts.displayFreezeConfirmation(unfreeze, "Unfreeze");
                break;
            case "3":
                adminPrompts.displayFreezeOptions(3, traders);
                option = scanner.next();
                int chosenAccount;
                try {
                    chosenAccount = Integer.parseInt(option);
                } catch (NumberFormatException e) {
                    adminPrompts.commandNotRecognized();
                    break;
                }
                if (chosenAccount == 0) {
                    break;
                }
                boolean freezeGeneral = adminActions.freezeAccount(traders.get(chosenAccount - 1));
                adminPrompts.displayFreezeConfirmation(freezeGeneral, "Freeze");
                break;
            default:
                adminPrompts.commandNotRecognized();
                break;

        }
        freezeMenuHelper();
    }

    private void freezeMenuHelper() {
        adminPrompts.displayFreezeHelper(toMainMenu);
        String option = scanner.next();
        if (toMainMenu.equals(option)) {
            setToMainMenu();
        } else {
            freezeMenu();
        }

        //I don't think these two lines are needed since dowhile loop is already taking care of that
//        adminPrompts.setToMainMenu();
//        setToMainMenu();
    }

    /**
     * Display the menu that allows the admin to approve items
     */
    public void approveItemsMenu() {
        while (true) {
            adminPrompts.displayItemMenu(traderActions.getTradersNeedingApproval());
            String option = scanner.next();
            int traderID;
            try {
                traderID = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                adminPrompts.commandNotRecognized();
                break;
            }
            if (traderID == 0) {
                setToMainMenu();
                break;
            } else if (traderID <= traderActions.getTradersNeedingApproval().size()) {
                Trader trader = traderActions.getTradersNeedingApproval().get(traderID - 1);
                adminPrompts.displayTraderProposedItems(trader.getProposedItems());
                itemSubMenu(trader);
            } else {
                adminPrompts.commandNotRecognized();
            }
        }
    }

    private void itemSubMenu(Trader trader) {
        String option = scanner.next();
        Boolean approved;
        if (option.equals("all")) {
            approved = approveOrReject();
            if (approved != null) {
                adminActions.approveAllItems(trader, approved);
                adminPrompts.confirmApproval(approved);
            }
        }
        else {
            int itemID;
            try {
                itemID = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                adminPrompts.commandNotRecognized();
                return;
            }
            if (itemID == 0) {
            } else if (Integer.parseInt(option) <= trader.getProposedItems().size()) {
                approved = approveOrReject();
                if (approved!= null) {
                    adminActions.approveItem(trader, trader.getProposedItems().get(itemID - 1), approved);
                    confirmApproval(approved);
                }
            } else {
                adminPrompts.commandNotRecognized();
            }
        }
    }
    /**
     * Allows an admin to approve or reject administrative requests.
     */
    public void adminApproval(){
        adminPrompts.displayAdminApproval(adminActions.getAdminRequests());
        String option = scanner.next();
        Boolean approved;
        if (option.equals(toMainMenu)){
            setToMainMenu();
        }
        else if (option.equals("all")) {
            System.out.println("Processing...");
            approved = approveOrReject();
            if (approved == null){
                adminApproval();
            }
            else {
                confirmApproval(adminActions.approveAllAdmins(approved));
            }
        }
        else if (adminActions.getAdminRequests().toString().contains(option)) {
            //TODO FIX THIS PART AND THE APPROVAL FUNCTION IS DONE
            //            String username = option.replaceFirst("(\\d+)(\\.{1})", "");
            System.out.println(option);
            System.out.println("Processing");
            approved = approveOrReject();
            if (approved == null){
                adminApproval();
            }
            else {
                confirmApproval(adminActions.approveAdmin(option, approved));
            }
        } else {
                System.out.println("Input not recognized.");
                adminApproval();
        }
    }
    private void confirmApproval(boolean approved){
        adminPrompts.confirmApproval(approved);
    }

    private Boolean approveOrReject(){
        adminPrompts.displayApproveOrReject();
        String choice = scanner.next();
        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
            case "0":
                //The method returns a boolean so
                //for now, I changed it to false (Junhee)
                //I made it a reference type Boolean which has null, the null case
                // is handled in the adminApproval method which is a bit messy rn
                //but will fix (Jeffrey)
                return null;
            default:
                adminPrompts.commandNotRecognized();
                return approveOrReject();
        }
    }
    
    /**
     * Display the menu that allows the admin to change the limit
     */
    public void changeLimit(){
        //to remove dummy line
        String option;
        scanner.nextLine();
        do {
            adminPrompts.displayChangeLimitMenu();
            option = scanner.nextLine();
            switch(option){
                case "1":
                    adminPrompts.displayThresholdOption(tradeManager.getMaxIncomplete());
                    int newMaxIncomplete = scanner.nextInt();
                    tradeManager.setMaxIncomplete(newMaxIncomplete);
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
                case "2":
                    adminPrompts.displayThresholdOption(tradeManager.getLimitOfTradesPerWeek());
                    int newLimitOfTradesPerWeek = scanner.nextInt();
                    tradeManager.setLimitOfTradesPerWeek(newLimitOfTradesPerWeek);
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
                case "3":
                    adminPrompts.displayThresholdOption(tradeManager.getMoreLendNeeded());
                    int newMoreLendNeeded = scanner.nextInt();
                    tradeManager.setMoreLendNeeded(newMoreLendNeeded);
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
                default:
                    adminPrompts.commandNotRecognized();
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
            }
            //to remove dummy line
            scanner.nextLine();
            adminPrompts.displayReturnToMainMenu();
            option = scanner.nextLine();
        }while(!option.equals(toMainMenu));
        //setToMainMenu();
    }


    /**
     * Change username or password of an admin.
     */
    public void changeUserInfo(){
        //Maybe we want to add the option to go back to the main menu
        String option;
        int succeed = -1;
        do {
            adminPrompts.displayChangeUserInfoMenu();
            option = scanner.next();
            switch (option) {
                case "1":
                    succeed = changeUsername();
                    break;
                case "2":
                    succeed = changePassword();
                    break;
                default:
                    adminPrompts.displayErrorMessage();
            }

            if (succeed != -1) {
                adminPrompts.displaySuccessMessage(succeed, "username/password");
            }
            adminPrompts.displayReturnToMainMenu();
            option = scanner.next();
        }while(!option.equals(toMainMenu));
    }

    private int changeUsername(){
        adminPrompts.displayEnterNewMessage("username");
        String newName = scanner.next();
        if (newName.equals(currentAdmin.getUsername())){
            return 0;
        }
        currentAdmin.setUsername(newName);
        return 1;
    }

    private int changePassword(){
        adminPrompts.displayEnterNewMessage("password");
        String newPassword = scanner.next();
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
        String user = scanner.next();
        switch (user){
            case "0":
                setToMainMenu();
                break;
            case "all":
                adminPrompts.displayAllTraders(traderActions.getTraders());
                break;
            default:
                adminPrompts.displayTrader(findTrader(user));
                break;
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
        String choice = scanner.next();
        switch (choice){
            case "0":
                setToMainMenu();
                break;
            case "1":
                viewTraders();
                break;
            default:
                adminPrompts.commandNotRecognized();
                restartViewTraders();
                break;
        }
    }

    /**
     * Returns the administrator to the main menu for administrator options
     */
    public void setToMainMenu(){
        adminPrompts.setToMainMenu();
        stop();
        init();
    }
}
