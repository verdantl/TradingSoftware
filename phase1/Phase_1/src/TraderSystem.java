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
                        TradeManager tradeManager) {
        this.currentTrader = currentTrader;
        this.traderActions = traderActions;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
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


            option = Integer.getInteger(sc.nextLine());

            // Ensuring that the user chooses a valid option.
            while (!validOptions.contains(option)) {
                // Present an error message to the user
                option = Integer.getInteger(sc.nextLine());
            }

            switch(option) {
                case 0:
                    // Exit the program
                    running = false;
                    break;
                case 1:
                    // Propose an item to be lent
                    proposeItemToLend(currentTrader);
                    break;
                case 2:
                    // Remove an item from their want to lend list
                    break;
                case 3:
                    // Browse their inventory
                    break;
                case 4:
                    // Browse list of On-Going trades
                    break;
                case 5:
                    // Check most recent 3 items the user has traded
                    break;
                case 6:
                    // Get the user's top 3 trading partners
                    break;
                case 7:
                    // Request to unfreeze the account
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

    }

    public void proposeItemToLend(Trader trader){
        ArrayList<String> temp = traderPrompts.getProposeItemPrompts();
        String itemName, category, description;
        int rating;
        ArrayList<String> itemAttributes = new ArrayList<>();
        Item item;
        itemAttributes.add("itemName");
        itemAttributes.add("category");
        itemAttributes.add("description");

        System.out.println(temp.get(0));
        System.out.println(temp.get(1));
        String o = sc.nextLine();
        if(o.equals("0")){
            System.out.println("Returning to the Main Menu..");
        }
        else{
            int loopVar = 0;
            while(!o.equals("0") && loopVar <2){
                itemAttributes.set(loopVar, o);
                loopVar+=1;
                System.out.println(temp.get(loopVar+2));
                o = sc.nextLine();
            }
            if(!o.equals("0")){
                rating = Integer.parseInt(o);
                item = new Item(itemAttributes.get(0), itemAttributes.get(1), itemAttributes.get(2), trader, rating);
                trader.addToProposedItems(item);
                loopVar+=1;
                System.out.println(temp.get(loopVar+2));
            }
            System.out.println("Returning to the Main Menu...");
        }

    }
    public void setCurrentTrader(Trader trader){
        currentTrader = trader;
    }


}
