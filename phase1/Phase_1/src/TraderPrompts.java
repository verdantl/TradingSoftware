import java.sql.SQLOutput;
import java.util.ArrayList;

public class TraderPrompts {
    ArrayList<String> mainMenuPrompts, proposeItemPrompts;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";

    public TraderPrompts(){
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
    public void displayTraderItems(Trader trader){
        System.out.println("Type 0 if you would like to return to the main menu.");
        System.out.println("Choose the item you want to remove by typing in its respective number: ");

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

            items.append(".\n");
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

            items.append(".\n");
        }

        System.out.println(items);
    }

    /**
     * Method we use to display the inventory of items that are available
     * @param items The items available in the inventory
     */
    public void browseInventory(ArrayList<Item> items){
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
    }

    /**
     * Method to view the an item's details from the browse menu.
     * @param item The item whose details we wish to view
     */
    public void viewItem(Item item){
        System.out.println(item);
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
        for (Trade i : onGoingTrades){
            System.out.println(i.toString());
        }
        // Insert your options here, Jinesh.
    }

    public void incorrectSelection(){
        System.out.println("That is not a valid option, please try again.");
    }

    /**
     * Displays the options for a selected trade
     */
    public void displayTradeOptions(){
        System.out.println("Type 0 to return to main menu");
        System.out.println("Here are options for the selected trade.");
        System.out.println("Type 1 to edit the trade's meeting");
        System.out.println("Type 2 to agree to the trade's meeting");
        System.out.println("Type 3 to confirm the trade happened");
    }
}
