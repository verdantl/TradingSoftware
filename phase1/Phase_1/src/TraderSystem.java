import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class TraderSystem extends UserSystem //if you want this system abstract class{
    //BY THE WAY BEFORE Y'ALL START take a look at my AdminSystem loop and
    //let me know in the chat if the loop works or if you want to make changes - Jeffrey
{
    private TraderPrompts traderPrompts;
    private TraderActions traderActions;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private AdminActions adminActions;
    private Trader currentTrader;
    private boolean running;
    private Scanner sc;

    //Because i assumed we get the current user as a parametre in the run class, it might be best to remove the currentTrader variable here
    /**
     * Constructor for TraderSystem.
     * @param currentTrader The trader using the TraderSystem
     * @param traderActions The TraderActions instance that this TraderSystem will use.
     * @param itemManager The ItemManager that this TraderSystem will use.
     * @param tradeManager The TradeManager that this Trader System will use.
     */
    public TraderSystem(Trader currentTrader, TraderActions traderActions, ItemManager itemManager,
                        TradeManager tradeManager, AdminActions adminActions) {
        this.currentTrader = currentTrader;
        this.traderActions = traderActions;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
        this.adminActions = adminActions;
        this.traderPrompts = new TraderPrompts();
        sc = new Scanner(System.in);
        running = false;
    }

    private void init() {

    }

    @Override
    public void run() {
        running = true;
        int option;
        while (running){
            // This is where the user  will be used, and where the appropriate methods will be called.
            // This is also where the traderPrompts is used to provide the user with prompts.
            // Its better to code this part so its dynamic, where if we add in new prompts it allows you to choose those prompts
            // with little needed change to the code

            // Setting up the options available to the user by default.
            // There will always be an option 0 to exit the program
            int numOptions = 8;
            ArrayList<Integer> validOptions = new ArrayList<>();
            for (int i = 0; i < numOptions + 1; i++) {
                validOptions.add(i);
            }

            // Present the options to the user here.

            //traderPrompts.displayMainMenu();

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
                    // Browse their inventory
                    browseInventoryOfItems();
                    break;
                case 4:
                    // Browse list of On-Going trades
                    browseOnGoingTrades();
                    break;
                case 5:
                    // Check most recent 3 items the user has traded
                    showThreeMostRecentItemsTraded();
                    break;
                case 6:
                    // Get the user's top 3 trading partners
                    showTopThreeTradingPartners();
                    break;
                case 7:
                    // Request to unfreeze the account
                    requestUnfreeze();
                    break;
            }
        }
        //Once the method ends we return to LoginSystem
    }

    @Override
    protected void update() {

    }

    @Override
    protected void stop() {
        running = false;
    }

    /**
     * Asks the user to enter the item's information that they want to propose to be added to currentTrader's wantToLend list.
     */
    private void proposeItemToLend(){
        ArrayList<String> temp = traderPrompts.getProposeItemPrompts();
        String itemName, category, description;
        int rating;
        ArrayList<String> itemAttributes = new ArrayList<>();
        Item item;
        itemAttributes.add("itemName");
        itemAttributes.add("category");
        itemAttributes.add("description");

        traderPrompts.displayString(temp.get(0));
        traderPrompts.displayString(temp.get(1));
        String o = sc.nextLine();
        if(o.equals("0")){
            traderPrompts.returnToMain();
        }
        else{
            int loopVar = 0;
            while(!o.equals("0") && loopVar <2){
                itemAttributes.set(loopVar, o);
                loopVar+=1;
                traderPrompts.displayString(temp.get(loopVar+2));
                o = sc.nextLine();
            }
            if(!o.equals("0")){
                rating = Integer.parseInt(o);
                item = new Item(itemAttributes.get(0), itemAttributes.get(1), itemAttributes.get(2), currentTrader, rating);
                traderActions.addProposedItem(currentTrader, item);
                loopVar+=1;
                traderPrompts.displayString(temp.get(loopVar+2));
            }
            traderPrompts.returnToMain();
        }

    }

    /**
     * A method to set the currentUser variable.
     * @param trader The logged-in user we will manipulate.
     */
    public void setCurrentTrader(Trader trader){
        currentTrader = trader;
    }

    /**
     * Method that removes the chosen item from the currentTrader's list of wantToLend items.
     */
    private void removeItemFromWantToLend(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        availableOptions.add(0);
        for (int i=0; i < currentTrader.getWantToLend().size(); i++){
            availableOptions.add(i+1);
        }

        int o = Integer.parseInt(sc.nextLine());
        while(!availableOptions.contains(o)){
            traderPrompts.incorrectSelection();
            o = Integer.parseInt(sc.nextLine());
        }

        while(o!=0){
            traderPrompts.displayTraderItems(currentTrader);
            availableOptions = new ArrayList<>();
            availableOptions.add(0);
            for (int i=0; i < currentTrader.getWantToLend().size(); i++){
                availableOptions.add(i+1);
            }
            while(!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.parseInt(sc.nextLine());
            }
            traderActions.removeFromWantToLend(currentTrader, currentTrader.getWantToLend().get(o-1) );
            traderPrompts.displayString("Item was removed.");
        }
    }

    /**
     * This is the method where the user browses the inventory, and decides if they want to add items to their wantToBorrow list or propose trades.
     */
    private void browseInventoryOfItems(){
        ArrayList<Integer> availableOptions = new ArrayList<>();
        ArrayList<Item> itemList = traderActions.browseItems();

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
                traderPrompts.viewItem(itemList.get(o));
                o2 = Integer.parseInt(sc.nextLine());
                if (o2 == 1){
                    traderActions.addToWantToBorrow(currentTrader, itemList.get(o));
                }
                else if (o2 == 2){
                    //THIS IS WHERE YOU DO THE PROPOSE TRADE CODE
                    //This might be a good place to tell the user they can't trade if their account is frozen.
                    //If their account is frozen just don't do anything, the code should return to the browsing items loop

                    if (!currentTrader.isFrozen()){
                        this.proposeTradeStart(itemList.get(o));
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
        // This is where the user decides between one-way or two-way

        ArrayList<Integer> availableOptionsOne = new ArrayList<>();
        boolean oneWay;
        availableOptionsOne.add(0);
        for (int i = 1; i <= 2; i++)
        {
            availableOptionsOne.add(i);
        }

        tradeManager.setCurrentUser(currentTrader);
        //TODO: Move the following to TraderPrompts
        traderPrompts.displayString("Please select one of the following options:");
        traderPrompts.displayString("0. Go back");
        traderPrompts.displayString("1. Initiate a one-way trade");
        traderPrompts.displayString("2. Initiate a two-way trade");

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

        traderPrompts.displayString("Please select one of the following options:");
        traderPrompts.displayString("0. Go back");
        traderPrompts.displayString("1. Make a temporary trade");
        traderPrompts.displayString("2. Make a permanent trade");

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
            proposeOneWay(item, temporary, tradeDate, location);
        }
        else{
            proposeTwoWay(item, temporary, tradeDate, location);
        }
    }

    /**
     * Where a new one-way trade is created depending on the choices the trader made previously.
     * @param item The item to be traded.
     * @param temporary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeOneWay(Item item, boolean temporary, LocalDate tradeDate, String location){
        if (temporary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
            tradeManager.requestToBorrow(item.getOwner(), location, tradeDate, item, returnDate);
        }
        else{
            tradeManager.requestToBorrow(item.getOwner(), location, tradeDate, item);
        }
    }

    /**
     * Where a new two-way trade is created depending on the choices the trader made previously. Additionally, where the
     * user decides on which item they want to give away in the exchange.
     * @param item The item to be traded.
     * @param tempoary Whether or not the trade is to have a return date or not.
     * @param tradeDate The date for the trade to occur.
     * @param location The location for the trade to occur.
     */
    private void proposeTwoWay(Item item, boolean tempoary, LocalDate tradeDate, String location){
        traderPrompts.displayString("Here are the items you currently own. Please select one of them to trade with " +
                "your trading partner:");
        traderPrompts.displayTraderItemsTwo(currentTrader);

        int itemChoice = Integer.parseInt(sc.nextLine());

        while(itemChoice >= currentTrader.getWantToLend().size()){
            traderPrompts.incorrectSelection();
            itemChoice = Integer.parseInt(sc.nextLine());
        }

        if(itemChoice == 0){
            return;
        }

        Item itemToTrade = currentTrader.getWantToLend().get(itemChoice - 1);

        if (tempoary){
            // We are assuming that the return date is the date of the trade plus one month.
            LocalDate returnDate = tradeDate.plusMonths(1);
            tradeManager.requestToExchange(item.getOwner(), location, tradeDate, item, itemToTrade, returnDate);
        }
        else{
            tradeManager.requestToExchange(item.getOwner(), location, tradeDate, item, itemToTrade);
        }
    }

    /**
     * Shows the user the three most recent items they have traded.
     */
    private void showThreeMostRecentItemsTraded(){
        traderPrompts.viewListOfRecentItems(traderActions.getMostRecentItems(currentTrader));
        int o;
        do {
            o = Integer.parseInt(sc.nextLine());
            if(o != 0){
                traderPrompts.incorrectSelection();
            }
        }while(o != 0);
    }

    /**
     * Shows the user their top three most frequent trading partners.
     */
    private void showTopThreeTradingPartners(){
        traderPrompts.viewListOfTradingPartners(traderActions.mostFrequentTradingPartners(currentTrader));
        int o;
        do {
            o = Integer.parseInt(sc.nextLine());
            if(o!=0){
                traderPrompts.incorrectSelection();
            }
        }while(o!=0);
    }

    /**
     * The user requests to unfreeze their account.
     */
    private void requestUnfreeze(){
        //This is where i do the unfreeze thing but im not sure how we do that yet.
        traderPrompts.requestUnfreeze();
        adminActions.setUnfreezeRequests(currentTrader);
        int o;
        do {
            o = Integer.parseInt(sc.nextLine());
            if(o != 0){
                traderPrompts.incorrectSelection();
            }
        } while(o != 0);
    }

    /**
     * The user requests to view all of their ongoing trades.
     */
    private void browseOnGoingTrades(){
        ArrayList<Trade> onGoingTrades = traderActions.getOnGoingTrades(currentTrader);
        traderPrompts.browseOnGoingTrades(onGoingTrades);

        // The user selects a trade from list
        traderPrompts.displayString("Type number listed with trade to select it: ");
        int select = sc.nextInt();
        tradeManager.setCurrentUser(currentTrader, onGoingTrades.get(select-1));

        // The user selects a option for the trade
        traderPrompts.displayTradeOptions();
        traderPrompts.displayString("Enter option:");
        int option = sc.nextInt();
        switch (option){
            case 0:
                //Type code to return to main menu
                break;
            case 1:
                editMeeting();
                break;
            case 2:
                tradeManager.agreeToMeeting();
                break;
            case 3:
                tradeManager.confirmTrade();
                break;
        }
    }

    /**
     * The user inputs a new date and location for selected trade
     */
    private void editMeeting(){
        System.out.println("Enter new date and location for selected trade");
        System.out.println("Enter year: ");
        int year = sc.nextInt();
        System.out.println("Enter month: ");
        int month = sc.nextInt();
        System.out.println("Enter day of month: ");
        int dayOfMonth = sc.nextInt();
        System.out.println("Enter location: ");
        String location = sc.nextLine();
        tradeManager.editTradeMeeting(LocalDate.of(year, month, dayOfMonth), location);
    }
}
