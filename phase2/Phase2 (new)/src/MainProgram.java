import java.io.IOException;


public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private String username; //this will be changed in the future
    private int nextSystem; //Might change this to string depending on how we implement this.
    private boolean running;

    private final ConfigGateway configGateway;
    private final AdminActions adminActions;
    private final ItemManager itemManager;
    private final MeetingManager meetingManager;
    private final TraderManager traderManager;
    private final TradeManager tradeManager;

    private final String ADMINPATH = "src/configfiles/admins.ser";
    private final String ITEMPATH = "src/configfiles/items.ser";
    private final String MEETINGPATH = "src/configfiles/meetings.ser";
    private final String TRADERPATH = "src/configfiles/traders.ser";
    private final String TRADEPATH = "src/configfiles/trade.ser";

    /**
     * Sets up the main program for the application.
     */
    public MainProgram() throws IOException, ClassNotFoundException {
        configGateway = new ConfigGateway();
        adminActions = (AdminActions) configGateway.readInfo(ADMINPATH);
        itemManager = (ItemManager) configGateway.readInfo(ITEMPATH);
        meetingManager = (MeetingManager) configGateway.readInfo(MEETINGPATH);
        tradeManager = (TradeManager) configGateway.readInfo(TRADEPATH);
        traderManager = (TraderManager) configGateway.readInfo(TRADERPATH);
    }

    private void init() {
        currentSystem = new LoginSystem(traderManager, adminActions);
    }

    /**
     * Runs the program and updates the current system/user each loop.
     */
    @Override
    public void run() {
        init();

        while (running) {
            currentSystem.run();
            try {
                saveInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
            username = currentSystem.getNextUser();
            nextSystem = currentSystem.getNextSystem();
            setCurrentSystem();
        }
    }

    private void setCurrentSystem() {
        switch (nextSystem){
            case 0: currentSystem = new LoginSystem(traderManager, adminActions);
            case 1: currentSystem = new SignupSystem(traderManager, adminActions);
            case 2: currentSystem = new TraderSystem(username, itemManager, tradeManager, traderManager,
                    meetingManager);
            case 3: currentSystem = new AdminSystem(username, adminActions,
                    itemManager, tradeManager, traderManager, meetingManager);

            case 4: stop();
        }
    }

    private void stop(){
        running = false;
    }

    private void saveInfo() throws IOException {
        configGateway.saveInfo(ADMINPATH, adminActions);
        configGateway.saveInfo(ITEMPATH, itemManager);
        configGateway.saveInfo(MEETINGPATH, meetingManager);
        configGateway.saveInfo(TRADERPATH, traderManager);
        configGateway.saveInfo(TRADEPATH, tradeManager);
    }
}
