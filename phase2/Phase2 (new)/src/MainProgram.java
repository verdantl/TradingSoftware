import java.io.IOException;

public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private String username;
    private int nextSystem;
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

//        HashMap<String, Admin> admins = new HashMap<>();
//        adminActions = new AdminActions(admins);
//        itemManager = new ItemManager(new HashMap<>());
//        meetingManager = new MeetingManager(new HashMap<>());
//        tradeManager = new TradeManager(new HashMap<>(), new HashMap<>(), 3, 1, 1);
//        traderManager = new TraderManager(new HashMap<>());
    }

    private void init() {
        currentSystem = new LoginSystem(traderManager, adminActions);
        running = true;
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
            break;
            case 1: currentSystem = new SignupSystem(traderManager, adminActions);
            break;
            case 2: currentSystem = new TraderSystem(username, itemManager, tradeManager, traderManager,
                    meetingManager);
            break;
            case 3: currentSystem = new AdminSystem(username, adminActions,
                    itemManager, tradeManager, traderManager, meetingManager);
            break;

            case 4: stop();
            break;
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
