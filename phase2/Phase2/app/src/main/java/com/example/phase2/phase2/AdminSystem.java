package com.example.phase2.phase2;

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
    private final String currentAdmin;
    private final Scanner scanner;

    private final String toMainMenu = "0";

    /**
     * Constructor for the system that admins log into
     * @param currentAdmin the username of the current admin that is using the system
     * @param adminActions a class that can alter admin information
     * @param itemManager a class that can alter item information
     * @param tradeManager a class that can alter trade information
     * @param traderManager a class that can alter trader information
     * @param meetingManager a class that can alter meeting information
     */
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
                case 8:
                    undoActions();
                case 9:
                    stop();
                    break;

                default:
                    System.out.println("Command not recognized. Try again.");
                    break;
            }
        }
    }

    /**
     * Allows an admin to approve or reject administrative requests.
     */
    public void adminApproval(){
        adminPrompts.displayAdminApproval(adminActions.getAdminRequests());
        String option = scanner.next();
        if (option.equals(toMainMenu)){
            setToMainMenu();
        }
        else if (option.equals("all")) {
            System.out.println("Processing...");
            Boolean approved = approveOrReject();
            if (approved == null){
                adminApproval();
            }
            else {
                List<String> requests = adminActions.getRequestedAdmins();
                adminActions.approveAllAdmins(requests, approved);
                confirmApproval(approved);
            }
        }
        else if (adminActions.getAdminRequests().toString().contains(option)) {
            System.out.println(option);
            System.out.println("Processing");
            Boolean approved = approveOrReject();
            if (approved == null){
                adminApproval();
            }
            else {
                adminActions.approveAdmin(option, approved);
                confirmApproval(approved);
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
        adminPrompts.displayFreezeMenu();
        String option = scanner.next();
        HashMap<Integer, String> flagged;
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
                itemManager.getAllApprovedItemsIDs(flagged.get(chosenFlag));
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
            List<Integer> approvalNeeded = itemManager.getItemsNeedingApproval();
            List<Item> items = itemManager.getListOfItems(approvalNeeded);
            adminPrompts.displayItemMenu(items);
            String option = scanner.next();
            int itemNumber;
            try {
                itemNumber = Integer.parseInt(option);
            } catch (NumberFormatException e) {
                adminPrompts.commandNotRecognized();
                break;
            }
            if (itemNumber == 0) {
                setToMainMenu();
                break;
            } else if (approvalNeeded.contains(itemNumber)) {
                Boolean approved = approveOrReject();
                if (approved != null) {
                    itemManager.approveItem(itemNumber, approved);
                    confirmApproval(approved);
                }
            } else {
                adminPrompts.commandNotRecognized();
            }
        }
    }

//    private void itemSubMenu(String trader) {
//        String option = scanner.next();
//        Boolean approved;
//        if (option.equals("all")) {
//            approved = approveOrReject();
//            if (approved != null) {
//                itemManager.approveAllItems(trader, approved);
//                adminPrompts.confirmApproval(approved);
//            }
//        }
//        else {
//            int itemID;
//            try {
//                itemID = Integer.parseInt(option);
//            } catch (NumberFormatException e) {
//                adminPrompts.commandNotRecognized();
//                return;
//            }
//            List<Item> proposedItems = itemManager.getProposedItems(trader);
//            HashMap<Integer, Item> choiceToItem = choiceToItem(proposedItems);
//
//            if (choiceToItem.containsKey(itemID)) {
//                approved = approveOrReject();
//                if (approved!= null) {
//                    itemManager.approveItem(trader, choiceToItem.get(itemID).getId(), approved);
//                    confirmApproval(approved);
//                }
//            } else if (itemID != 0){
//                adminPrompts.commandNotRecognized();
//            }
//        }
//    }

//    private HashMap<Integer, Item> choiceToItem(List<Item> proposedItems){
//        HashMap<Integer, Item> temp = new HashMap<>();
//        for (int i = 1; i <= proposedItems.size(); i++){
//            temp.put(i, proposedItems.get(i - 1));
//        }
//        return temp;
//    }

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
                    adminPrompts.displayThresholdOption(traderManager.getMaxInComplete());
                    int newMaxIncomplete = scanner.nextInt();
                    traderManager.setMaxInComplete(newMaxIncomplete);
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
                case "2":
                    adminPrompts.displayThresholdOption(traderManager.getWeeklyLimit());
                    int newLimitOfTradesPerWeek = scanner.nextInt();
                    traderManager.setWeeklyLimit(newLimitOfTradesPerWeek);
                    adminPrompts.displaySuccessMessage(1, "Limit");
                    break;
                case "3":
                    adminPrompts.displayThresholdOption(traderManager.getMoreLend());
                    int newMoreLendNeeded = scanner.nextInt();
                    traderManager.setMoreLend(newMoreLendNeeded);
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
        if (!adminActions.login(currentAdmin, newPassword)){
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

    public void undoActions() {
        System.out.println("Please enter the username of the user whose actions you would like to" +
                "undo:");
        String username = scanner.nextLine();
        while (!traderManager.checkUsername(username)) {
            adminPrompts.displayErrorMessage();
            username = scanner.nextLine();
        }
        System.out.println("Please select the type of action you would like to undo, or [0] to" +
                "exit.");
        System.out.println("1 - Undo edit to trade meeting.");
        System.out.println("2 - Undo agreeing to a trade.");
        System.out.println("3 - Undo confirming a trade.");
        System.out.println("4 - Undo proposing a trade.");
        System.out.println("5 - Undo removing an item.");
        int type = Integer.parseInt(scanner.nextLine());
        //For now assume 1 = undo proposing trades
        switch (type) {
            case 0:
                System.out.println("Returning to Main Menu.");
                break;
            case 1:
                undoEditTrade(username);
                break;
            case 2:
                undoAgreeTrade(username);
                break;
            case 3:
                undoConfirmTrade(username);
                break;
            case 4:
                undoProposeTrade(username);
                break;
            case 5:
                undoRemoveFromWantToLend(username);
                break;
        }
    }
    public void undoEditTrade(String username){
        int choice;
        do {
            List<Integer> tempMeetings = new ArrayList<>();
            // Adding all of the given user's incomplete trades to a list.
            for (Integer i : tradeManager.getIncompleteTrades(traderManager.getTrades(username))){
                if (!meetingManager.meetingCanBeUndone(i)){
                    tempMeetings.add(i);
                }
            }
            // Removing all of the trades where the trader with the given username was not the last
            // to edit.
            for (Integer i : tempMeetings) {
                HashMap<String, Integer> tempHash = meetingManager.getEdits(i);
                // If the given trader was the initiator, if he was the last to edit then both
                // traders should have the same number of edits.
                if (tradeManager.getTradeInitiator(i).equals(username)){
                    if (tempHash.get(username) < tempHash.get(tradeManager.getTradeReceiver(i))) {
                        tempMeetings.remove(i);
                    }
                }
                // If the given trader was the initiator, if he was the last to edit then he should
                // have more trades than the other trader.
                else{
                    if (tempHash.get(username) == tempHash.get(tradeManager.getTradeReceiver(i))) {
                        tempMeetings.remove(i);
                    }
                }
            }

            System.out.println("Type 0 to return to main menu.");
            System.out.println("Type the number of the proposed trade you wish to unedit:");
            StringBuilder s = new StringBuilder();
            int j = 1;
            for (Integer i : tempMeetings) {
                s.append(j);
                s.append(" - ");
                s.append(meetingManager.getMeeting(i).toString());
                s.append("\n");
            }
            choice = Integer.parseInt(scanner.nextLine())-1;
            if(choice!=-1){
                while(choice!=-1) {
                    if (choice > -1 && choice < tempMeetings.size()) {
                        meetingManager.undoEdit(tempMeetings.get(choice), username);
                        System.out.println("Trade was unedited.");
                    } else if(choice != -1) {
                        System.out.println("Please enter a valid option.");
                        choice = Integer.parseInt(scanner.nextLine())-1;
                    }
                }
            }
        }while (choice != 0);
    }

    /**
     * Method that is used to undo a trade agreement for a user, given the right conditions are met.
     * @param username The username of the user
     */
    public void undoAgreeTrade(String username){
        Integer option;
        do {
            System.out.println("Press 0 to go back.");
            System.out.println("Enter the trade agreement you wish to undo:");
            List<Integer> incompleteTrades = meetingManager.getOnGoingMeetings(traderManager.getTrades(username));
            List<Integer> undoableMeetingAgreements = new ArrayList<>();
            for (Integer i : incompleteTrades) {
                if (meetingManager.canUndoAgree(i, username)) {
                    undoableMeetingAgreements.add(i);
                }
            }
            for (Integer i : undoableMeetingAgreements) {
                System.out.println(meetingManager.getMeetingDescription(i));
            }
            option = Integer.parseInt(scanner.nextLine());
            if(option ==0){
                break;
            }
            while (!undoableMeetingAgreements.contains(option)) {
                System.out.println("Please enter a valid option");
                option = Integer.parseInt(scanner.nextLine());
            }
            meetingManager.undoAgree(option, username);
        }while(option!=0);
    }

    public void undoConfirmTrade(String username){
        Integer option;
        do {
            System.out.println("Press 0 to go back.");
            System.out.println("Enter the trade confirmation you wish to undo:");
            List<Integer> incompleteTrades = meetingManager.getOnGoingMeetings(traderManager.getTrades(username));
            List<Integer> undoableMeetingConfirmations = new ArrayList<>();
            for (Integer i : incompleteTrades) {
                if (meetingManager.canUndoConfirm(i, username)) {
                    undoableMeetingConfirmations.add(i);
                }
            }
            for (Integer i : undoableMeetingConfirmations) {
                System.out.println(meetingManager.getMeetingDescription(i));
            }
            option = Integer.parseInt(scanner.nextLine());
            if(option ==0){
                break;
            }
            while (!undoableMeetingConfirmations.contains(option)) {
                System.out.println("Please enter a valid option");
                option = Integer.parseInt(scanner.nextLine());
            }
            meetingManager.undoConfirm(option, username);
        }while(option!=0);
    }

    public void undoProposeTrade(String username){
        int choice;
        do {
            List<Integer> tempMeetings = new ArrayList<>();
            for (Integer i : tradeManager.getIncompleteTrades(traderManager.getTrades(username))) {
                if (tradeManager.getTradeInitiator(i).equals(username)) {
                    if (meetingManager.meetingCanBeUndone(i)) {
                        tempMeetings.add(i);
                    }
                }
            }
            System.out.println("Type 0 to return to main menu.");
            System.out.println("Type the number of the proposed trade you wish to undo:");
            StringBuilder s = new StringBuilder();
            int j = 1;
            for (Integer i : tempMeetings) {
                s.append(j);
                s.append(" - ");
                s.append(meetingManager.getMeeting(i).toString());
                s.append("\n");
            }

            choice = Integer.parseInt(scanner.nextLine())-1;
            if(choice!=1){
                while(choice!=-1) {
                    if (choice > -1 && choice < tempMeetings.size()) {
                        meetingManager.undoMeetingProposal(tempMeetings.get(choice));
                        tradeManager.undoTradeProposal(tempMeetings.get(choice));
                        traderManager.undoTradeProposal(tempMeetings.get(choice));
                        System.out.println("Trade was undone.");
                    } else if(choice>tempMeetings.size()) {
                        System.out.println("Please enter a valid option.");
                        choice = Integer.parseInt(scanner.nextLine())-1;
                    }
                }
            }
        }while(choice!=-1);
    }

    public void undoRemoveFromWantToLend(String username){
        int choice;
        do {
            List<Integer> removedItems = itemManager.getRemovedItemIds(username);
            System.out.println("Type 0 to return to main menu.");
            System.out.println("Type the number of the removed Item you wish to undo:");
            StringBuilder s = new StringBuilder();
            int j=1;
            for(Integer i: removedItems){
                s.append(j);
                s.append(" - ");
                s.append(itemManager.getItemInString(i));
                s.append("\n");
            }
            choice = Integer.parseInt(scanner.nextLine())-1;
            if(choice!=1){
                while(choice!=-1) {
                    if (choice > -1 && choice < removedItems.size()) {
                        itemManager.undoRemoval(removedItems.get(choice));
                        System.out.println("Removal was undone");
                    } else if(choice>removedItems.size()) {
                        System.out.println("Please enter a valid option.");
                        choice = Integer.parseInt(scanner.nextLine())-1;
                    }
                }
            }
        }while(choice!=-1);

    }
}
