import java.util.Scanner;

public class TraderSystem extends UserSystem //if you want this system abstract class{
    //BY THE WAY BEFORE Y'ALL START take a look at my AdminSystem loop and
    //let me know in the chat if the loop works or if you want to make changes - Jeffrey



    //For this class we have to instantiate its respective prompts class so i did that in the constructor.
{
    private TraderActions traderActions;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private Trader currentTrader;
    private boolean running;
    private Scanner sc;

    //This is the prompts class
    //private TraderPrompts traderPrompts;
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
        sc = new Scanner(System.in);
        running = false;

        //Trader prompts instantiated
        //this.traderPrompts = new TraderPrompts();

    }

    private void init() {

    }

    @Override
    public void run() {
        running = true;
        int option;
        while (running){
            // This is where the user  will be used, and where the appropriate methods will be called.
            //This is also where the traderPrompts is used to provide the user with prompts.
            //Its better to code this part so its dynamic, where if we add in new prompts it allows you to choose those prompts
            //with little needed change to the code
            // Pretend that we present the options to the user here.


            option = Integer.getInteger(sc.nextLine());
            switch(option) {
                case 1:
                    // Option 1 goes here
                    break;
                case 2:
                    // Option 2 goes here
                    break;
                case 3:
                    // Option 3 goes here
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

}
