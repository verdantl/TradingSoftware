public class Configuration {
    private TraderManager traderManager;
    private AdminActions adminActions;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;

    public TraderManager getTraderManager(){
        return traderManager;
    }

    public AdminActions getAdminActions(){
        return adminActions;
    }

    public ItemManager getItemManager(){
        return itemManager;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
    }

    public MeetingManager getMeetingManager() {
        return meetingManager;
    }

    public void saveInfo(UserSystem controller, String path){

    }

}
