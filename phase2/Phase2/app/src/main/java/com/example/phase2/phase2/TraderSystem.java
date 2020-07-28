package com.example.phase2.phase2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class TraderSystem extends UserSystem{

    private final TraderPrompts traderPrompts;
    private final TraderManager traderManager;
    private final ItemManager itemManager;
    private final TradeManager tradeManager;
    private final MeetingManager meetingManager;

    private final String currentTrader;
    private final Scanner sc;

    /**
     * Constructor for TraderSystem.
     * @param currentTrader The trader using the TraderSystem
     * @param itemManager The ItemManager that this TraderSystem will use.
     * @param tradeManager The TradeManager that this Trader System will use.
     */
    public TraderSystem(String currentTrader, ItemManager itemManager,
                        TradeManager tradeManager, TraderManager traderManager,
                        MeetingManager meetingManager) {
        this.currentTrader = currentTrader;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
        this.traderManager = traderManager;
        this.meetingManager = meetingManager;
        this.traderPrompts = new TraderPrompts();
        sc = new Scanner(System.in);
        running = false;
    }

    public void run() {
        init();
        int option;
        while (running){

            boolean traderActivity = traderManager.isInactive(currentTrader);

            int numOptions = 9;
            ArrayList<Integer> validOptions = new ArrayList<>();
            for (int i = 0; i < numOptions + 1; i++) {
                validOptions.add(i);
            }

            // Present the options to the user here.
            traderPrompts.displayMainMenu();

            option = Integer.parseInt(sc.nextLine());

            // Ensuring that the user chooses a valid option.
            while (!validOptions.contains(option)) {
                traderPrompts.incorrectSelection();
                option = Integer.parseInt(sc.nextLine());
            }

            switch(option) {
                case 0:
                    // Exit the program
                    stop();
                    break;
                case 1:
                    // Propose an item to be lent
                    proposeItemToLend();
                    break;
                case 2:
                    // Remove an item from their want to lend list
                    removeItemFromWantToLend();
                    break;
                case 3:
                    //Remove an item from their wishlist
                    removeItemFromWishlist();
                    break;
                case 4:
                    // Browse their inventory
                    browseInventoryOfItems();
                    break;
                case 5:
                    // Browse list of On-Going trades
                    browseOnGoingTrades();
                    break;
                case 6:
                    // Check most recent 3 items the user has traded
                    showThreeMostRecentItemsTraded();
                    break;
                case 7:
                    // Get the user's top 3 trading partners
                    showTopThreeTradingPartners();
                    break;
                case 8:
                    // Request to unfreeze the account
                    requestUnfreeze();
                    break;
                case 9:
                    requestInactive();
                    break;
                case 10:
                    reactivateTrader();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    /**
     * Asks the user to enter the item's information that they want to propose to be added to currentTrader's wantToLend list.
     */
    private void proposeItemToLend(){
        List<String> temp = new ArrayList<>();
        // Copied from TraderPrompts.setUpProposeItemPrompts
        temp.add("Enter \"0\" at any time to exit");//0
        temp.add("Otherwise, enter the item name:");//1
        temp.add("Enter the item's Category:");//2
        temp.add("Enter a description for the item:");//3
        temp.add("Enter the item's quality rating from 1-10:");//4
        temp.add("Your item is waiting to be reviewed by an Administrator, please check back later.");//5

        int rating;
        ArrayList<String> itemAttributes = new ArrayList<>();

        itemAttributes.add("itemName");
        itemAttributes.add("category");
        itemAttributes.add("description");

        String o = "-1";

        int loopVar = 0;
        System.out.println(temp.get(loopVar));
        //If the user doesn't want to go back, displays the prompts to enter item specs and creates the item.
        while(!o.equals("0") && loopVar < 3){
            System.out.println(temp.get(loopVar + 1));
            o = sc.nextLine();
            itemAttributes.set(loopVar, o);
            loopVar+=1;
        }
        if(!o.equals("0")){
            System.out.println(temp.get(loopVar + 1));
            o = sc.nextLine();
            rating = Integer.parseInt(o);

            if (!o.equals("0")){
                int tempInt = itemManager.addItem(itemAttributes.get(0), currentTrader);
                itemManager.addItemDetails(tempInt, itemAttributes.get(1), itemAttributes.get(2),
                        rating);
                loopVar+=1;
                System.out.println(temp.get(loopVar+1));
            }
        }
        System.out.println("Returning to the Main Menu...");
    }

    /**
     * Method that removes the chosen item from the currentTrader's list of wantToLend items.
     */
    private void removeItemFromWantToLend(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        availableOptions.add(0);

        availableOptions.addAll(itemManager.getApprovedItemsIDs(currentTrader));
        System.out.println("Enter the ID of the item you want to remove from your inventory," +
                "or [0] to exit.");

        System.out.println(itemManager.getApprovedItemsInString(currentTrader));
        int o = Integer.getInteger(sc.nextLine());

        while(o!=0){
            while(!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.getInteger(sc.nextLine());
            }
            // If o passes while loop and isn't 0, o must be a valid input.
            if(o!=0){
                traderManager.deleteItem(o);
                traderPrompts.displayString("Item was removed.");
                availableOptions.remove(availableOptions.size()-1);
                System.out.println(itemManager.getApprovedItemsInString(currentTrader));
                o = Integer.parseInt(sc.nextLine());
            }
        }
    }

    /**
     * Method that removes the chosen items from currentTrader's wishlist.
     */
    private void removeItemFromWishlist(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        availableOptions.add(0);
        availableOptions.addAll(traderManager.getWishlistIds(currentTrader));

        traderPrompts.displayString("Type 0 if you would like to return to the main menu.");
        traderPrompts.displayString("Choose the item you want to remove by typing in its respective ID: ");
        System.out.println(itemManager.getListOfItemsInString(traderManager.getWishlistIds(currentTrader)));
        Integer o = Integer.getInteger(sc.nextLine());

        while(o!=0){
            while(!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.getInteger(sc.nextLine());
            }
            if(o!=0){
                traderManager.removeFromWishlist(currentTrader, o);
                traderPrompts.displayString("Item was removed.");
                availableOptions.remove(o);
                traderPrompts.displayString("Type 0 if you would like to return to the main menu.");
                traderPrompts.displayString("Choose the item you want to remove by typing in its respective number: ");
                System.out.println(itemManager.getListOfItemsInString(traderManager.getWishlistIds(currentTrader)));
                o = Integer.getInteger(sc.nextLine());
            }
        }
    }

    /**
     * This is the method where the user browses the inventory, and decides if they want to add items to their wantToBorrow list or propose trades.
     */
    private void browseInventoryOfItems(){
        List<Integer> availableOptions = new ArrayList<>();
        List<Integer> itemList = itemManager.getAllApprovedItemsIDs(currentTrader);

        availableOptions.add(0);
        availableOptions.addAll(itemList);

        Integer o;
        Integer o2;
        do{
            for (String str: itemManager.getListOfItemsInString(itemList)){
                System.out.println(str);
            }
            o = Integer.getInteger(sc.nextLine());
            while (!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.getInteger(sc.nextLine());
            }
            if (o != 0){
                System.out.println(itemManager.getItemInString(o));
                o2 = Integer.getInteger(sc.nextLine());
                if (o2 == 1){
                    if(!traderManager.getWishlistIds(currentTrader).contains(o)) {
                        traderManager.addToWishlist(currentTrader, o);
                        traderPrompts.displayString("Item was added to your wishlist.");
                    }
                    else{
                        traderPrompts.displayString("Item is already in your wishlist.");
                    }
                }
                else if (o2 == 2){
                    if (!traderManager.getIsFrozen(currentTrader)){
                        this.proposeTradeStart(o);
                    }
                    else{
                        traderPrompts.displayString("Your account is frozen. You cannot trade.");
                        traderPrompts.returnToMain();
                    }
                }
            }
        } while(o != 0);
    }

    /**
     * The start of the process for a user to propose a trade. Here, the user decides between a one-way or two-way
     * trade, and if the trade is permanent or temporary.
     * @param itemId The id of the item the user wishes to trade for.
     */
    private void proposeTradeStart(Integer itemId){
        String reciever = itemManager.getOwner(itemId);
        ArrayList<Integer> availableOptionsOne = new ArrayList<>();
        boolean oneWay;
        for (int i = 0; i <= 2; i++)
        {
            availableOptionsOne.add(i);
        }

        traderPrompts.displayProposalMenu();
        int o1;
        o1 = Integer.parseInt(sc.nextLine());

        while(!availableOptionsOne.contains(o1) || (o1 == 2
                && itemManager.getApprovedItemsIDs(currentTrader).size() < 1)){
            traderPrompts.incorrectSelection();
            if (o1 == 2 && itemManager.getApprovedItemsIDs(currentTrader).size() < 1){
                System.out.println("You must have items in your inventory to be able to initiate a" +
                        "two-way trade.");
            }
            o1 = Integer.parseInt(sc.nextLine());
        }

        switch(o1){
            case 1:
                oneWay = true;
                break;
            case 2:
                oneWay = false;
                break;
            default:
                return;
        }

        // This is where the user decides between temporary or permanent

        ArrayList<Integer> availableOptionsTwo = new ArrayList<>();
        boolean temporary;
        for (int i = 0; i <= 2; i++)
        {
            availableOptionsTwo.add(i);
        }

        System.out.println("Please select one of the following options:");
        System.out.println("0. Go back");
        System.out.println("1. Make a temporary trade");
        System.out.println("2. Make a permanent trade");

        int o2;
        o2 = Integer.parseInt(sc.nextLine());

        while(!availableOptionsTwo.contains(o2)){
            traderPrompts.incorrectSelection();
            o2 = Integer.parseInt(sc.nextLine());
        }

        switch(o2){
            case 1:
                temporary = true;
                break;
            case 2:
                temporary = false;
                break;
            default:
                return;
        }

        traderPrompts.displayString("Please enter a trade date in YYYY-MM-DD format:");

        String tradeDateStr;
        LocalDate tradeDate;
        tradeDateStr = sc.nextLine();

        // Checking if the user inputted a valid trade date
        while(true){
            try{
                tradeDate = LocalDate.parse(tradeDateStr);
                if (tradeDate.isAfter(LocalDate.now())) {
                    break;
                }
                else {
                    traderPrompts.displayString("Please enter a date after today.");
                }
            }
            catch (DateTimeParseException e){
                traderPrompts.displayString("Please enter a string in the format YYYY-MM-DD.");
            }
            tradeDateStr = sc.nextLine();
        }

        traderPrompts.displayString("Please enter a location for the trade:");

        String location;
        location = sc.nextLine();

        if (oneWay){
            proposeOneWay(reciever, itemId, temporary, tradeDate, location);
        }
        else{
            proposeTwoWay(reciever, itemId, temporary, tradeDate, location);
        }
    }

    /**
     * Where a new one-way trade is created depending on the choices the trader made previously.
     * @param receiver The owner of the other item.
     * @param itemId The ID of the item to be traded.
     * @param temporary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeOneWay(String receiver, Integer itemId, boolean temporary, LocalDate tradeDate, String location){
        int i;
        List<Integer> items = new ArrayList<>();
        items.add(itemId);

        // TODO: Make tradeType an enum in TradeManager/Trade
        i = tradeManager.createTrade(currentTrader, receiver, "ONEWAY", temporary, items);

        meetingManager.createMeeting(i, currentTrader, receiver, temporary);

        if (temporary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
            meetingManager.setMeetingInfo(i, tradeDate, returnDate, location, location);
        }
        else{
            meetingManager.setMeetingInfo(i, tradeDate, null, location, null);
        }

        // traderPrompts.displayTradeProcess(i);
    }

    /**
     * Where a new two-way trade is created depending on the choices the trader made previously. Additionally, where the
     * user decides on which item they want to give away in the exchange.
     * @param receiver The owner of the other item.
     * @param itemId The ID of the item to be traded.
     * @param temporary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeTwoWay(String receiver, Integer itemId, boolean temporary, LocalDate tradeDate, String location){
        int i;
        List<Integer> items = new ArrayList<>();
        items.add(itemId);

        traderPrompts.displayString("Here are the items you currently own. Please enter the ID of " +
                "the one you want to trade with your trading partner:");
        System.out.println(itemManager.getApprovedItemsInString(currentTrader));

        int itemChoice = Integer.getInteger(sc.nextLine());

        while(!itemManager.getApprovedItemsIDs(currentTrader).contains(itemChoice) ||
                itemChoice < 0){
            traderPrompts.incorrectSelection();
            itemChoice = Integer.parseInt(sc.nextLine());
        }

        if(itemChoice == 0){
            return;
        }

        // TODO: Make tradeType an enum in TradeManager/Trade
        i = tradeManager.createTrade(currentTrader, receiver, "TWOWAY", temporary, items);
        meetingManager.createMeeting(i, currentTrader, receiver, temporary);

        if (temporary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
            meetingManager.setMeetingInfo(i, tradeDate, returnDate, location, location);
        }
        else{
            meetingManager.setMeetingInfo(i, tradeDate, null, location, location);
        }
    }

    /**
     * Shows the user the three most recent items they have traded.
     */
    private void showThreeMostRecentItemsTraded(){
        List<Integer> trades = traderManager.getTrades(currentTrader);
        List<Integer> items = new ArrayList<>();

        int n = trades.size();
        for (int j = 1; j < n; j++) {
            Integer bruh = trades.get(j);
            int i = j-1;
            while (i > -1 && tradeManager.getDateCreated(trades.get(i))
                    .isAfter(tradeManager.getDateCreated(bruh))) {
                trades.set(trades.get(i+1), trades.get(i));
                i--;
            }
            trades.set(i+1, bruh);
        }

        // This is an ugly, ugly piece of code but I'm too tired to do better.
        // iterating over the list of this user's trades from front to back
        for (int j = trades.size() - 1; j >= 0; j--) {
            // if the trade is completed, then we'll consider it
            if (meetingManager.getMeetingStatus(trades.get(j)).equals("COMPLETED")) {
                // iterating over the 1-2 items involved with the trade
                for (Integer i: tradeManager.getItems(trades.get(j))) {
                    // if the item is now owned by the appropriate trader
                    if (itemManager.getOwner(i).equals(currentTrader)){
                        items.add(i);
                    }
                }
            }
        }

        System.out.println("Here are the items you have most recently traded:");
        for (Integer j: items){
            System.out.println(itemManager.getItemInString(j));
        }

        System.out.println("Press anything to return to the main menu:");
        sc.nextLine();
    }

    /**
     * Shows the user their top three most frequent trading partners.
     */
    private void showTopThreeTradingPartners(){
        TreeMap<String, Integer> tradingPartners = new TreeMap<>();
        List<Integer> trades = traderManager.getTrades(currentTrader);

        // iterating over the user's trades
        for(Integer i: trades){
            String traderToAdd;
            // getting the right trader from the pair of traders involved in the trade.
            if (tradeManager.getTradeInitiator(i).equals(currentTrader)) {
                traderToAdd = tradeManager.getTradeReceiver(i);
            }
            else {
                traderToAdd = tradeManager.getTradeInitiator(i);
            }
            // putting the other trader into the hashmap
            if (tradingPartners.containsKey(traderToAdd)) {
                tradingPartners.put(traderToAdd, tradingPartners.get(traderToAdd) + 1);
            }
            else {
                tradingPartners.put(traderToAdd, 1);
            }
        }

        System.out.println("Here are your most frequent trading partners:");

        // By the nature of TreeMap, the keys are already sorted in order of their values!
        TreeSet<String> traders = new TreeSet<>(tradingPartners.keySet());
        // Getting the last 3 elements of traders.
        for (int j = traders.size() - 1; j >= trades.size() - 4 && j >= 0; j--) {
            System.out.println(traders.toArray()[j]);
        }

        System.out.println("Press anything to return to the main menu:");
        sc.nextLine();
    }

    /**
     * The user requests to unfreeze their account.
     */
    private void requestUnfreeze(){

        // Again, I don't have a way of checking this at the moment.
        if(!traderManager.getIsFrozen(currentTrader)){
            traderPrompts.displayString("Only frozen users can request to unfreeze.");
            return;
        }
        traderPrompts.requestUnfreeze();

        // Same as checking if a user is frozen, I can't check this without calling an entity method
        // or storing the instance of the entity here.
        if(traderManager.getRequestToUnfreeze(currentTrader)){
            traderPrompts.displayString("You have already requested to unfreeze your account. Please wait.");
        }

        traderManager.setRequestToUnfreeze(currentTrader,true);
        int o;
        do {
            o = Integer.parseInt(sc.nextLine());
            if(o != 0){
                traderPrompts.incorrectSelection();
            }
        } while(o != 0);
    }

    /**
     * The user requests to view all of their ongoing trades and
     * selects trade to display options for selected trade.
     */
    private void browseOnGoingTrades(){
        // No way to get on going trades currently
        List<Integer> incompleteTrades = tradeManager.getIncompleteTrades(traderManager.getTrades(currentTrader));
        List<Integer> onGoingTrades = meetingManager.getOnGoingMeetings(incompleteTrades);

        // The user returns to main menu if no ongoing trades
        if (onGoingTrades.size() == 0){
            int type;
            traderPrompts.displayString("You have no ongoing trades.");
            do {
                traderPrompts.displayString("Enter [0] to return to main menu.");
                type = Integer.parseInt(sc.nextLine());
                if (type != 0){
                    traderPrompts.incorrectSelection();
                }
            }while(type != 0);
            return;
        }
        // traderPrompts.browseOnGoingTrades(onGoingTrades);

        // The user selects a trade from list
        int select;
        do {
            System.out.println("Type number listed with trade to select it or [0] to return to main menu.");
            //traderPrompts.displayString("Type number listed with trade to select it or [0] to return to main menu.");
            StringBuilder s = new StringBuilder();
            for(Integer i: onGoingTrades){
                s.append(tradeManager.getTradeInformation(currentTrader, i));
                s.append(tradeManager.getItemIds(i));
                s.append("\n");
            }
            select = Integer.parseInt(sc.nextLine());

            //This is what goes in the presenter:

            System.out.println(s);
            // If user enters number greater than number of trades or less than 0
            // display incorrect selection prompt and ask to enter again
            if (select > onGoingTrades.size() || select < 0){
                traderPrompts.incorrectSelection();
            }
        }while(select > onGoingTrades.size() || select < 0);
        if (select == 0){
            return;
        }

        // The user selects a option for the trade
        traderPrompts.displayTradeOptions();
        traderPrompts.displayString("Enter option:");
        int option = Integer.parseInt(sc.nextLine());
        do{
            switch (option){
                case 1:
                    String editOption = editMeeting(select);
                    traderPrompts.displayString(editOption);
                    if(editOption.equals("Cancelling edit")){
                        // traderPrompts.browseOnGoingTrades(onGoingTrades);
                        System.out.println("Cancelling edit");
                    }
                    break;
                case 2:
                    meetingManager.agreeOnTrade(select, currentTrader);
                    System.out.println("Agreed to trade");
                    //traderPrompts.displayString(tradeManager.agreeToMeeting());
                    break;
                case 3:
                    meetingManager.confirmMeeting(select, currentTrader);
                    System.out.println("Confirmed trade.");
                    //traderPrompts.displayString(tradeManager.confirmTrade());
                    break;
                default:
                    traderPrompts.incorrectSelection();
            }
            traderPrompts.displayString("Enter [0] to return to the main menu" +
                    "\nOr enter the option you still want to modify:");
            option = Integer.parseInt(sc.nextLine());
        }while(option != 0);

    }

    /**
     * The user inputs a new date and location for selected trade
     */
    private String editMeeting(Integer tradeID){
        System.out.println(meetingManager.getMeetingDescription(tradeID));
        System.out.println("Enter new date and location for trade meeting");
        System.out.println("Please enter a date in the format YYYY-MM-DD or enter [0] to cancel the edit.");

        String newDateStr;
        LocalDate newDate;
        newDateStr = sc.nextLine();
        if(newDateStr.equals("0")){
            return "Cancelling edit";
        }

        // Check if the user inputted a valid trade date
        while(true){
            try{
                newDate = LocalDate.parse(newDateStr);
                if (newDate.isAfter(LocalDate.now())) {
                    break;
                }
                else {
                    System.out.println("Please enter a date after today.");
                }
            }
            catch (DateTimeParseException e){
                System.out.println("Please enter a date in the format YYYY-MM-DD or enter [0] to cancel the edit.");
            }
            newDateStr = sc.nextLine();
        }
        // User enters a new location
        System.out.println("Enter a location or enter [0] to cancel the edit:");
        String location = sc.nextLine();

        if(location.equals("0")){
            return "Cancelling edit";
        }
        meetingManager.editDate(tradeID,newDate);
        meetingManager.editLocation(tradeID, location);
        meetingManager.increaseNumEdit(currentTrader,tradeID);
        return "Edit made Successfully";
    }

    private void requestInactive(){
        System.out.println("Do you wish to make your account inactive until you reactivate.");
        System.out.println("If you make your account inactive your items will become invisible to others and you won't get trade offers.");
        System.out.println("Type 1 to make your account inactive. Type 0 to return to the main menu.");
        int option = Integer.parseInt(sc.nextLine());
        if(option==1){
            System.out.println("Your account status has been set to inactive until reactivate.");
            traderManager.setTraderInactive(currentTrader,true);
            itemManager.setItemsInactive(itemManager.getApprovedItemsIDs(currentTrader),true);
        }
    }
    //
    private void reactivateTrader(){
        System.out.println("Your account has been reactivated.");
        traderManager.setTraderInactive(currentTrader,false);
        itemManager.setItemsInactive(itemManager.getApprovedItemsIDs(currentTrader), false);
    }
}
