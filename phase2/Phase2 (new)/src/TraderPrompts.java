import java.util.ArrayList;
import java.util.List;

public class TraderPrompts {
    ArrayList<String> mainMenuPrompts, proposeItemPrompts;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";
    private final ItemManager itemManager;

    public TraderPrompts(ItemManager itemManager){
        this.itemManager = itemManager;
        setUpMainMenuPrompts();
        setUpProposeItemPrompts();
    }

    /**
     * Method used in the constructor to set up the main menu prompts.
     */
    private void setUpMainMenuPrompts(){
        mainMenuPrompts = new ArrayList<>();
        mainMenuPrompts.add(" - Exit the program.");
        mainMenuPrompts.add(" - Propose an item you want to lend.");
        mainMenuPrompts.add(" - View/remove items from your lending list.");
        mainMenuPrompts.add(" - View/remove items from your wishlist");
        mainMenuPrompts.add(" - Browse all available items.");
        mainMenuPrompts.add(" - Browse your on-going trades.");
        mainMenuPrompts.add(" - Browse your three most recently traded items.");
        mainMenuPrompts.add(" - Browse your three most traded with trading partners.");
        mainMenuPrompts.add(" - Request to unfreeze your account.");
    }

    /**
     * Method used in the constructor to set up the ProposeItemPrompts.
     */
    private void setUpProposeItemPrompts(){
        proposeItemPrompts = new ArrayList<>();
        proposeItemPrompts.add("Enter \"0\" to go back to the main menu at any time."); //0
        proposeItemPrompts.add("Otherwise, enter the item name:"); //1
        proposeItemPrompts.add("Enter the item's Category:");//2
        proposeItemPrompts.add("Enter a description for the item:");//3
        proposeItemPrompts.add("Enter the item's quality rating from 1-10:");//4
        proposeItemPrompts.add("Your item is waiting to be reviewed by an Administrator, please check back later.");//5
    }

    /**
     * Getter for MainMenuPrompts
     * @return the arraylist of mainMenuPrompts
     */
    public ArrayList<String> getMainMenuPrompts(){
        return this.mainMenuPrompts;
    }

    /**
     * Getter for ProposeItemPrompts
     * @return Arraylist of propose item prompts.
     */
    public ArrayList<String> getProposeItemPrompts(){
        return this.proposeItemPrompts;
    }

    /**
     * This method is used to display the main menu prompts with the correct number options
     */
    public void displayMainMenu(){
        StringBuilder selections = new StringBuilder();
        for (int i = 0; i < mainMenuPrompts.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(mainMenuPrompts.get(i));
            selections.append('\n');
        }
        System.out.println(instruction);
        System.out.println(selections);
    }

    /**
     * This is the method used to display a user's items by displaying the item name, category, description, and rating.
     * @param trader The trader who's items are to be displayed.
     */
    public void displayTraderItems(String trader){
        System.out.println("Type 0 if you would like to return to the main menu.");
        System.out.println("Choose the item you want to remove by typing in its respective number: ");

        List<Item> itemList = itemManager.getWantToLend(trader);

        StringBuilder items = new StringBuilder();
        for(int i=1; i< itemList.size()+1; i++){
            items.append(i);

            items.append(" - Name: ");
            items.append(itemList.get(i-1).getName());

            items.append(". Category: ");
            items.append(itemList.get(i-1).getCategory());

            items.append(". Description: ");
            items.append(itemList.get(i-1).getDescription());

            items.append(". Rating: ");
            items.append(itemList.get(i-1).getQualityRating());
            if(i!=itemList.size()) {
                items.append(".\n");
            }
        }
        System.out.println(items);
    }

    /**
     * This is the same method as the one above, but doesn't include the first two sysout statements to allow for more
     * versatility. This should be replaced before the final submission.
     * @param trader The trader who's items are to be displayed.
     */
    public void displayTraderItemsTwo(Trader trader){
        System.out.println("Type 0 if you would like to return to the main menu.");

        StringBuilder items = new StringBuilder();
        for(int i=1; i< trader.getWantToLend().size()+1; i++){
            items.append(i);

            items.append(" - Name: ");
            items.append(trader.getWantToLend().get(i-1).getName());

            items.append(". Category: ");
            items.append(trader.getWantToLend().get(i-1).getCategory());

            items.append(". Description: ");
            items.append(trader.getWantToLend().get(i-1).getDescription());

            items.append(". Rating: ");
            items.append(trader.getWantToLend().get(i-1).getQualityRating());
            if(i!=trader.getWantToLend().size()) {
                items.append(".\n");
            }
        }

        System.out.println(items);
    }

    /**
     * Method that prints the give list of items.
     * @param itemsList The list of items to be printed.
     */
    public void displayItems(List<Item> itemsList){
        StringBuilder items = new StringBuilder();
        for(int i=1; i< itemsList.size()+1; i++) {
            items.append(i);

            items.append(" - Name: ");
            items.append(itemsList.get(i - 1).getName());

            items.append(". Category: ");
            items.append(itemsList.get(i - 1).getCategory());

            items.append(". Description: ");
            items.append(itemsList.get(i - 1).getDescription());

            items.append(". Rating: ");
            items.append(itemsList.get(i - 1).getQualityRating());
            if (i != itemsList.size()) {
                items.append(".\n");
            }
        }

        System.out.println(items);
    }

    /**
     * Method we use to display the inventory of items that are available
     * @param items The items available in the inventory
     */
    public void browseInventory(List<Item> items){
        System.out.println("Type '0 if you would like to return to the main menu.");
        System.out.println("Type the number of the item you wish to view in more detail.");
        StringBuilder itemsList = new StringBuilder();
        for(int i=1; i< items.size()+1; i++){
            itemsList.append(i);

            itemsList.append(" - Name:");
            itemsList.append(items.get(i-1).getName());

            itemsList.append(". Category: ");
            itemsList.append(items.get(i-1).getCategory());

            itemsList.append(". Rating: ");
            itemsList.append(items.get(i-1).getQualityRating());

            itemsList.append(".\n");
        }
        System.out.println(itemsList);
    }

    /**
     * Method to view the an item's details from the browse menu.
     * @param item The item whose details we wish to view
     */
    public void viewItem(Item item){
        System.out.println("Name: "+ item.getName()+ " | Category: "+ item.getCategory()+ " | Description: " + item.getDescription() + " | Rating: "+ item.getQualityRating());
        System.out.println("Type 1 if you want to add this item to your wishlist.");
        System.out.println("Type 2 if you want to propose a trade.");
        System.out.println("Type any other number to browsing items.");
    }

    /**
     * Method to view the items within the given list
     */
    public void viewListOfRecentItems(ArrayList<Item> items){
        System.out.println("Press 0 to return to the main menu.");
        StringBuilder itemsList = new StringBuilder();
        for(int i=1; i< items.size()+1; i++){
            itemsList.append(i);

            itemsList.append(" - Name:");
            itemsList.append(items.get(i-1).getName());

            itemsList.append(". Category: ");
            itemsList.append(items.get(i-1).getCategory());

            itemsList.append(". Rating: ");
            itemsList.append(items.get(i-1).getQualityRating());

            itemsList.append(".\n");
        }
        System.out.println(itemsList);
    }

    /**
     * Displays the usernames of the given list of users.
     * @param partners The list of users
     */
    public void viewListOfTradingPartners(ArrayList<Trader> partners){
        System.out.println("Type \"0\" to return to the main menu");
        System.out.println("Here are (if available) your top three most frequent trading partners:");
        StringBuilder userList = new StringBuilder();
        for(int i=1; i< partners.size()+1; i++){
            userList.append(i);

            userList.append(" - Name:");
            userList.append(partners.get(i-1).getUsername());
        }
        System.out.println(userList);
    }

    public void requestUnfreeze(){
        System.out.println("You have requested to unfreeze your account. Please wait until an adminsitrator reviews your request.");
        System.out.println("Type \"0\" to return to the main menu");
    }

    /**
     * Displays a list of a Trader's on-going trades.
     * @param onGoingTrades The Trader's on-going trades.
     */
    public void browseOnGoingTrades(ArrayList<Trade> onGoingTrades){
        System.out.println("Here is a list of all of your on-going trades:");

        StringBuilder trades = new StringBuilder();
        int i = 1;
        for(Trade trade : onGoingTrades){
            trades.append(i);

            trades.append(" - Initiator: ");
            trades.append(trade.getInitiator().getUsername());
            trades.append("\n");

            trades.append(". Receiver: ");
            trades.append(trade.getReceiver().getUsername());
            trades.append("\n");

            trades.append(". Item[s]: ");
            trades.append(trade.getItems().get(0).getName());
            trades.append("\n");

            if(trade.getItems().size() == 2){
                trades.append(", ");
                trades.append(trade.getItems().get(1).getName());
                trades.append("\n");
            }

            trades.append(". Is Permanent: ");
            trades.append(trade.isPermanent());
            trades.append("\n");

            trades.append(". Trade date: ");
            trades.append(trade.getTradeDate().toString());
            trades.append("\n");

            if(!trade.isPermanent()){
                trades.append(". Return date:");
                trades.append(trade.getReturnDate().toString());
                trades.append("\n");
            }

            trades.append(". Location: ");
            trades.append(trade.getLocation());
            trades.append("\n");

            trades.append(". Number of edits from initiator: ");
            trades.append(trade.getNumberOfEdit(trade.getInitiator()));
            trades.append("\n");

            trades.append(". Number of edits from receiver: ");
            trades.append(trade.getNumberOfEdit(trade.getReceiver()));
            trades.append("\n");

            trades.append(" tradeStatus: ");
            trades.append(trade.getTradeStatus());
            trades.append("\n");

            i++;
        }

        System.out.println(trades);
    }

    public void incorrectSelection(){
        System.out.println("That is not a valid option, please try again.");
    }

    /**
     * Displays the options for a selected trade
     */
    public void displayTradeOptions(){
        System.out.println("Here are options for the selected trade.");
        System.out.println("Type 1 to edit the trade's meeting");
        System.out.println("Type 2 to agree to the trade's meeting");
        System.out.println("Type 3 to confirm the trade happened");
    }

    /**
     * Displays a single string to screen.
     * @param string The string that is to be displayed.
     */
    public void displayString(String string){
        System.out.println(string);
    }

    /**
     * Displays the string "Returning to Main Menu..."
     */
    public void returnToMain(){
        System.out.println("Returning to Main Menu...");
    }

    /**describe the process of requesting a trade
     * @param i the integer represents different states in the process of requesting a trade
     */
    public void displayTradeProcess(int i){
        switch (i){
            case 1:
                System.out.println("Request fails: You have achieved the max number of weekly trades");
                break;
            case 2:
                System.out.println("Request fails: Please lend or trade before borrowing");
                break;
            case 3:
                System.out.println("Request successes: Your trade is now in progress");
                break;
        }
    }

    /**
     * Displays the submenu for the proposal screen
     */
    public void displayProposalMenu(){
        System.out.println("Please select one of the following options:");
        System.out.println("0. Go back");
        System.out.println("1. Initiate a one-way trade");
        System.out.println("2. Initiate a two-way trade");

    }
}
