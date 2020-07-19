import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AdminSystem extends UserSystem{

    private final AdminPrompts adminPrompts;

    private final AdminActions adminActions;

    private final ItemManager itemManager;
    private final TradeManager tradeManager;
    private final TraderManager traderManager;
    private final MeetingManager meetingManager;
    private boolean running;
    private String currentAdmin;
    private final Scanner scanner;

    private final String toMainMenu = "0";


    public AdminSystem(String currentAdmin, AdminActions adminActions, ItemManager itemManager,
                       TradeManager tradeManager, TraderManager traderManager, MeetingManager meetingManager) {
        this.adminActions = adminActions;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
        this.traderManager = traderManager;
        this.meetingManager = meetingManager;

        this.currentAdmin = currentAdmin;
        adminPrompts = new AdminPrompts();
        running = false;
        scanner = new Scanner(System.in);
    }

    protected void init() {
        running = true;
    }

    /**
     * Runs the program in a loop
     */
    public void run() {
        init();
        while (running) {
            adminPrompts.displayOptions();
            int option = scanner.nextInt();
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

    @Override
    public String getNextUser() {
        return null;
    }

    @Override
    protected int getNextSystem() {
        return 0;
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

    /**
     * Display the menu that allows the admin to manage frozen/unfrozen accounts
     */
    public void freezeMenu() {
        HashMap<String, Trader> traders = traderManager.getAllUsers();
        adminPrompts.displayFreezeMenu();
        String option = scanner.next();
        HashMap<Integer, String> flagged = new HashMap<>();
        switch (option) {
            case toMainMenu:
                setToMainMenu();
                break;
            case "1":
                List<String> flaggedAccounts = traderManager.getListOfFlagged();
                flagged = usernamesToHashMap(flaggedAccounts);
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
                boolean freeze = traderManager.freezeAccount(flagged.get(chosenFlag));
                adminPrompts.displayFreezeConfirmation(freeze, "Freeze");
                break;
            case "2":
                List<String> frozenAccounts = traderManager.getAllRequestsToUnfreeze();
                flagged = usernamesToHashMap(frozenAccounts);
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
                boolean unfreeze = traderManager.unfreezeAccount(flagged.get(chosenFrozen));
                adminPrompts.displayFreezeConfirmation(unfreeze, "Unfreeze");
                break;
            case "3":
                adminPrompts.displayFreezeOptions(3, traderManager.getTraders());
                option = scanner.next();
                flagged = usernamesToHashMap(traderManager.getTraders());
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
                boolean freezeGeneral = traderManager.freezeAccount(flagged.get(chosenAccount));
                adminPrompts.displayFreezeConfirmation(freezeGeneral, "Freeze");
                break;
            default:
                adminPrompts.commandNotRecognized();
                break;

        }
        freezeMenuHelper();
    }

    private HashMap<Integer, String> usernamesToHashMap(List<String> usernames){
        HashMap<Integer, String> temp = new HashMap<>();
        for (int i = 1; i <= usernames.size(); i++){
            temp.put(i, usernames.get(i - 1));
        }
        return temp;
    }

    private void freezeMenuHelper() {
        adminPrompts.displayFreezeHelper(toMainMenu);
        String option = scanner.next();
        if (toMainMenu.equals(option)) {
            setToMainMenu();
        } else {
            freezeMenu();
        }
    }

    /**
     * Display the menu that allows the admin to approve items
     */
    public void approveItemsMenu() {
        while (true) {
            HashMap<Integer, String> approvalNeeded = usernamesToHashMap(itemManager.getTradersNeedingItemApproval());
            adminPrompts.displayItemMenu(itemManager.getTradersNeedingItemApproval());
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
            } else if (approvalNeeded.containsKey(traderID)) {
                List<Item> items = itemManager.getWantToBorrow(approvalNeeded.get(traderID));
                adminPrompts.displayTraderProposedItems(items);
                itemSubMenu(approvalNeeded.get(traderID));
            } else {
                adminPrompts.commandNotRecognized();
            }
        }
    }

    private void itemSubMenu(String trader) {
        String option = scanner.next();
        Boolean approved;
        if (option.equals("all")) {
            approved = approveOrReject();
            if (approved != null) {
                itemManager.approveAllItems(trader, approved);
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
            List<Item> proposedItems = itemManager.getProposedItems(trader);
            HashMap<Integer, Item> choiceToItem = choiceToItem(proposedItems);

            if (choiceToItem.containsKey(itemID)) {
                approved = approveOrReject();
                if (approved!= null) {
                    itemManager.approveItem(trader, choiceToItem.get(itemID).getId(), approved);
                    confirmApproval(approved);
                }
            } else if (itemID != 0){
                adminPrompts.commandNotRecognized();
            }
        }
    }

    private HashMap<Integer, Item> choiceToItem(List<Item> proposedItems){
        HashMap<Integer, Item> temp = new HashMap<>();
        for (int i = 1; i <= proposedItems.size(); i++){
            temp.put(i, proposedItems.get(i - 1));
        }
        return temp;
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
                return null;
            default:
                adminPrompts.commandNotRecognized();
                return approveOrReject();
        }
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
                adminPrompts.displayAllTraders(traderManager.getAllUsers().values());
                break;
            default:
                adminPrompts.displayTrader(findTrader(user));
                break;
        }
        restartViewTraders();
    }

    private Trader findTrader(String user){
        if (traderManager.containTrader(user)){
            return traderManager.getAllUsers().get(user);
        }
        else {
            return null;
        }
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
     * Display the menu that allows the admin to change the limit
     */
    public void changeLimit(){
        String option;
        scanner.nextLine();
        do {
            adminPrompts.displayChangeLimitMenu();
            option = scanner.nextLine();
            switch(option){
                case "1":
                    adminPrompts.displayThresholdOption(traderManager.get.getMaxIncomplete());
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
        adminActions.changeUsername(currentAdmin, newName);
        return 1;
    }

    private int changePassword(){
        adminPrompts.displayEnterNewMessage("password");
        String newPassword = scanner.next();
        if (!adminActions.checkCredentials(currentAdmin, newPassword)){
            return 0;
        }
        adminActions.changePassword(currentAdmin, newPassword);
        return 1;

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
