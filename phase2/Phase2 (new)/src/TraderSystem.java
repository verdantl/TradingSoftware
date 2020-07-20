import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TraderSystem extends UserSystem{

    private final TraderPrompts traderPrompts;
    private final TraderActions traderActions;
    private final TraderManager traderManager;
    private final ItemManager itemManager;
    private final TradeManager tradeManager;
    private final MeetingManager meetingManager;

    private String currentTrader;
    private boolean running;
    private final Scanner sc;

    /**
     * Constructor for TraderSystem.
     * @param currentTrader The trader using the TraderSystem
     * @param traderActions The TraderActions instance that this TraderSystem will use.
     * @param itemManager The ItemManager that this TraderSystem will use.
     * @param tradeManager The TradeManager that this Trader System will use.
     */
    public TraderSystem(String currentTrader, TraderActions traderActions, ItemManager itemManager,
                        TradeManager tradeManager, TraderManager traderManager,
                        MeetingManager meetingManager) {
        this.currentTrader = currentTrader;
        this.traderActions = traderActions;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
        this.traderManager = traderManager;
        this.meetingManager = meetingManager;
        this.traderPrompts = new TraderPrompts(itemManager, tradeManager);
        sc = new Scanner(System.in);
        running = false;
    }

    protected void init() {
        running = true;
    }

    public void run() {
        init();
        int option;
        while (running){
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
                    this.stop();
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
     * Asks the user to enter the item's information that they want to propose to be added to currentTrader's wantToLend list.
     */
    private void proposeItemToLend(){
        //I have commented out all the things related to prompts, etc. Since we're doing gui we might have to rewrite all of this.
        //ArrayList<String> temp = traderPrompts.getProposeItemPrompts();
//        String itemName, category, description;
        int rating;
        ArrayList<String> itemAttributes = new ArrayList<>();
        //Item item;
        itemAttributes.add("itemName");
        itemAttributes.add("category");
        itemAttributes.add("description");

        //traderPrompts.displayString(temp.get(0));

        String o = null;

        int loopVar = 0;
        //If the user doesn't want to go back, displays the prompts to enter item specs and creates the item.
        while(!o.equals("0") && loopVar < 3){
            //traderPrompts.displayString(temp.get(loopVar + 1));
            o = sc.nextLine();
            itemAttributes.set(loopVar, o);
            loopVar+=1;
        }
        if(!o.equals("0")){
            //traderPrompts.displayString(temp.get(loopVar + 1));
            rating = Integer.parseInt(o);
            //itemManager.addToProposedItems(currentTrader, itemAttributes.get(0), itemAttributes.get(1), itemAttributes.get(2), rating);
            // item = new Item(itemAttributes.get(0), itemAttributes.get(1), itemAttributes.get(2), currentTrader, rating);
            // traderActions.addProposedItem(currentTrader, item);

            // ~~~~ We HAVE to call a method from the User class here, might just be better to
            // store only the username, and require all public methods in our use cases to take
            // in usernames instead of Traders ~~~

            if (!o.equals("0")){
                itemManager.addToProposedItems(currentTrader, itemAttributes.get(0),
                        itemAttributes.get(1), itemAttributes.get(2), rating);
                //loopVar+=1;
                //traderPrompts.displayString(temp.get(loopVar+1));
            }
        }
        //traderPrompts.displayString("Returning to the Main Menu...");
    }

    /**
     * Method that removes the chosen item from the currentTrader's list of wantToLend items.
     */
    private void removeItemFromWantToLend(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        availableOptions.add(0);
        for (int i=0; i < itemManager.getWantToLend(currentTrader).size(); i++){
            // TODO: Add method in itemManager that returns all ID's given a list of items or something
            // so I don't have to call getId on Item, which is an entity.
            availableOptions.add(itemManager.getWantToLend(currentTrader).get(i).getId());
        }
        traderPrompts.displayTraderItems(currentTrader);
        int o = Integer.parseInt(sc.nextLine());

        while(o!=0){
            while(!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.parseInt(sc.nextLine());
            }
            if(o==0){
                System.out.println("break");
                break;
            }
            // TODO: Fix this, so that the program asks for the id of the item instead of the
            // position of the item in the trader's wantToLend list. This avoids calling the getId
            // method.
            itemManager.removeFromWantToLend(currentTrader, o);
            traderPrompts.displayString("Item was removed.");
            availableOptions.remove(availableOptions.size()-1);
            traderPrompts.displayTraderItems(currentTrader);
            o = Integer.parseInt(sc.nextLine());
        }
    }

    /**
     * Method that removes the chosen items from currentTrader's wishlist.
     */
    private void removeItemFromWishlist(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        availableOptions.add(0);
        for (int i=0; i < itemManager.getWantToLend(currentTrader).size(); i++){
            availableOptions.add(itemManager.getWantToLend(currentTrader).get(i).getId());
        }
        itemManager.getWantToBorrow(currentTrader);
        //System.out.println(currentTrader.getWantToBorrow());
        traderPrompts.displayString("Type 0 if you would like to return to the main menu.");
        traderPrompts.displayString("Choose the item you want to remove by typing in its respective ID: ");
        traderPrompts.displayItems(itemManager.getWantToBorrow(currentTrader));
        int o = Integer.parseInt(sc.nextLine());

        while(o!=0){
            while(!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.parseInt(sc.nextLine());
            }
            if(o==0){
                break;
            }
            itemManager.removeFromWantToBorrow(currentTrader, o);
            traderPrompts.displayString("Item was removed.");
            availableOptions.remove(availableOptions.remove(o));
            traderPrompts.displayString("Type 0 if you would like to return to the main menu.");
            traderPrompts.displayString("Choose the item you want to remove by typing in its respective number: ");
            traderPrompts.displayItems(itemManager.getWantToBorrow(currentTrader));
            o = Integer.parseInt(sc.nextLine());
        }
    }

    /**
     * This is the method where the user browses the inventory, and decides if they want to add items to their wantToBorrow list or propose trades.
     */
    private void browseInventoryOfItems(){
        List<Integer> availableOptions = new ArrayList<>();
        List<Item> itemList = itemManager.getApprovedItems(currentTrader);

        availableOptions.add(0);
        for (int i = 0; i < itemList.size(); i++){
            availableOptions.add(i+1);
        }
        int o;
        int o2;
        do{
            traderPrompts.browseInventory(itemList);
            o = Integer.parseInt(sc.nextLine());
            while (!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.parseInt(sc.nextLine());
            }
            if (o != 0){
                traderPrompts.viewItem(itemList.get(o - 1));
                o2 = Integer.parseInt(sc.nextLine());
                if (o2 == 1){
                    if(!itemManager.getWantToBorrow(currentTrader).contains(itemList.get(o-1))) {
                        itemManager.addToWantToBorrow(currentTrader, itemList.get(o-1).getId());
                        traderPrompts.displayString("Item was added to your wishlist.");
                    }
                    else{
                        traderPrompts.displayString("Item is already in your wishlist.");
                    }
                }
                else if (o2 == 2){
                    // I have no way of checking if currentUser's account is frozen.
                    // Currently, currentTrader is a string for its username. However, traderManager
                    // does not allow met to get a trader given a username. Even it it could, we
                    // would be violating Clean Architecture by calling Trader.isFrozen() here.
                    // Instead, we could get TraderManager to return a list of frozen accounts'
                    // usernames, and check if currentTrader is in that list.
                    if (traderManager.getIsFrozen(currentTrader)){
                        this.proposeTradeStart(itemList.get(o - 1));
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
     * @param item The item the user wishes to trade for.
     */
    private void proposeTradeStart(Item item){

        ArrayList<Integer> availableOptionsOne = new ArrayList<>();
        boolean oneWay;
        availableOptionsOne.add(0);
        for (int i = 1; i <= 2; i++)
        {
            availableOptionsOne.add(i);
        }

        traderPrompts.displayProposalMenu();
        int o1;
        o1 = Integer.parseInt(sc.nextLine());

        while(!availableOptionsOne.contains(o1)){
            traderPrompts.incorrectSelection();
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

        // This is where the user decides between temporary or permanenet

        ArrayList<Integer> availableOptionsTwo = new ArrayList<>();
        boolean temporary;
        availableOptionsTwo.add(0);
        for (int i = 1; i <= 2; i++)
        {
            availableOptionsTwo.add(i);
        }

        //TODO: Move the following to TraderPrompts
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

        // TODO: Move this to TraderPrompts
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
                    // TODO: Move this to TraderPrompts
                    traderPrompts.displayString("Please enter a date after today.");
                }
            }
            catch (DateTimeParseException e){
                // TODO: Move this to TraderPrompts
                traderPrompts.displayString("Please enter a string in the format YYYY-MM-DD.");
            }
            tradeDateStr = sc.nextLine();
        }

        // TODO: Move this to TraderPrompts
        traderPrompts.displayString("Please enter a location for the trade:");

        // I have no way of checking which user owns this item.
        String reciever = "bruh";

        String location;
        location = sc.nextLine();

        if (oneWay){
            proposeOneWay(reciever, item, temporary, tradeDate, location);
        }
        else{
            proposeTwoWay(reciever, item, temporary, tradeDate, location);
        }
    }

    /**
     * Where a new one-way trade is created depending on the choices the trader made previously.
     * @param reciever The owner of the other item.
     * @param item The item to be traded.
     * @param temporary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeOneWay(String reciever, Item item, boolean temporary, LocalDate tradeDate, String location){
        Trade i;
        List<Integer> items = new ArrayList<>();
        items.add(item.getId());
        if (temporary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
        }

        i = tradeManager.createTrade(currentTrader, reciever, temporary, items, "One Way");

        // I have no idea how to make a new meeting in MeetingManager without instancing the
        // meeting here.

        // traderPrompts.displayTradeProcess(i);
    }

    /**
     * Where a new two-way trade is created depending on the choices the trader made previously. Additionally, where the
     * user decides on which item they want to give away in the exchange.
     * @param reciever The owner of the other item.
     * @param item The item to be traded.
     * @param temporary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeTwoWay(String reciever, Item item, boolean temporary, LocalDate tradeDate, String location){
        List<Integer> items = new ArrayList<>();
        items.add(item.getId());

        // TODO: Move this to TraderPrompts
        traderPrompts.displayString("Here are the items you currently own. Please select one of them to trade with your trading " +
                "partner:");
        // traderPrompts.displayTraderItemsTwo(currentTrader);

        int itemChoice = Integer.parseInt(sc.nextLine());

        while(itemChoice > itemManager.getWantToLend(currentTrader).size() || itemChoice < 0){
            traderPrompts.incorrectSelection();
            itemChoice = Integer.parseInt(sc.nextLine());
        }

        if(itemChoice == 0){
            return;
        }

        Item itemToTrade = itemManager.getWantToLend(currentTrader).get(itemChoice - 1);
        items.add(itemToTrade.getId());

        Trade i;

        if (temporary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
        }
        i = tradeManager.createTrade(currentTrader, reciever, temporary, items, "Two Way");

        // traderPrompts.displayTradeProcess(i);
    }

    /**
     * Shows the user the three most recent items they have traded.
     */
    private void showThreeMostRecentItemsTraded(){
        traderActions.getMostRecentItems(currentTrader);
        //gives u the list of item ids that are traded^^

        // Here, we should pass these ids into our prompts class, which will take care of the rest
        // of the displaying stuff part of this

//        traderPrompts.viewListOfRecentItems(traderActions.getMostRecentItems(currentTrader));
//        int o;
//        do {
//            o = Integer.parseInt(sc.nextLine());
//            if(o != 0){
//                traderPrompts.incorrectSelection();
//            }
//        }while(o != 0);
    }

    /**
     * Shows the user their top three most frequent trading partners.
     */
    private void showTopThreeTradingPartners(){
        traderActions.mostFrequentTradingPartners(currentTrader);
        //gives u the list of usernames of traders most frequently traded with

        // Here, we should pass these usernames into our prompts class, which will take care of the
        // rest of the displaying stuff part of this

//        traderPrompts.viewListOfTradingPartners();
//        int o;
//        do {
//            o = Integer.parseInt(sc.nextLine());
//            if(o!=0){
//                traderPrompts.incorrectSelection();
//            }
//        }while(o!=0);
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

        // Holy shit, I just realised how bad this is for clean architecture lol im dumb
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
        List<Integer> onGoingTrades = tradeManager.getTrades(currentTrader);

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
            traderPrompts.displayString("Type number listed with trade to select it or [0] to return to main menu.");
            select = Integer.parseInt(sc.nextLine());
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
                    String editOption = editMeeting();
                    traderPrompts.displayString(editOption);
                    if(editOption.equals("Cancelling edit")){
                        // traderPrompts.browseOnGoingTrades(onGoingTrades);
                    }
                    break;
                case 2:
                    traderPrompts.displayString(tradeManager.agreeToMeeting());
                    break;
                case 3:
                    traderPrompts.displayString(tradeManager.confirmTrade());
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
    private String editMeeting(){
        traderPrompts.displayString("Enter new date and location for trade meeting");
        traderPrompts.displayString("Please enter a date in the format YYYY-MM-DD or enter [0] to cancel the edit.");
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
                    traderPrompts.displayString("Please enter a date after today.");
                }
            }
            catch (DateTimeParseException e){
                traderPrompts.displayString("Please enter a date in the format YYYY-MM-DD or enter [0] to cancel the edit.");
            }
            newDateStr = sc.nextLine();
        }
        // User enters a new location
        traderPrompts.displayString("Enter a location or enter [0] to cancel the edit:");
        String location = sc.nextLine();
        if(location.equals("0")){
            return "Cancelling edit";
        }
        return tradeManager.editTradeMeeting(newDate, location);
    }
}
