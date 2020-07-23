import java.io.*;

public class Configuration {
    private final ConfigAdmin configAdmin;
    private final ConfigItems configItems;
    private final ConfigMeetings configMeetings;
    private final ConfigTraders configTraders;
    private final ConfigTrades configTrades;
//
//    private final Config config;
//    private final AdminActions adminActions;
//    private final ItemManager itemManager;
//    private final MeetingManager meetingManager;
//    private final TraderManager traderManager;
//    private final TradeManager tradeManager;

    private final String ADMINPATH = "src/configfiles/admins.ser";
    private final String ITEMPATH = "src/configfiles/items.ser";
    private final String MEETINGPATH = "src/configfiles/meetings.ser";
    private final String TRADERPATH = "src/configfiles/traders.ser";
    private final String TRADEPATH = "src/configfiles/trade.ser";


    public Configuration(){
        configAdmin = new ConfigAdmin();
        configItems = new ConfigItems();
        configMeetings = new ConfigMeetings();
        configTraders = new ConfigTraders();
        configTrades = new ConfigTrades();
//        config = new Config();
    }

    public TraderManager getTraderManager() throws IOException, ClassNotFoundException {
        return configTraders.readInfo(TRADERPATH);
    }

    public AdminActions getAdminActions() throws IOException, ClassNotFoundException {
        return configAdmin.readInfo(ADMINPATH);
    }

    public ItemManager getItemManager() throws IOException, ClassNotFoundException {
        return configItems.readInfo(ITEMPATH);
    }

    public TradeManager getTradeManager() throws IOException, ClassNotFoundException {
        return configTrades.readInfo(TRADEPATH);
    }

    public MeetingManager getMeetingManager() throws IOException, ClassNotFoundException {
        return configMeetings.readInfo(MEETINGPATH);
    }


    public void saveInfo() throws IOException {
        configAdmin.saveInfo(ADMINPATH);
        configItems.saveInfo(ITEMPATH);
        configMeetings.saveInfo(MEETINGPATH);
        configTraders.saveInfo(TRADERPATH);
        configTrades.saveInfo(TRADEPATH);
    }
}
