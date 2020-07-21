package gateway;

import gateway.ConfigReader;
import gateway.ConfigWriter;

import java.io.IOException;

public class Configuration {
    private ConfigAdmin configAdmin;
    private ConfigItems configItems;
    private ConfigTraders configTraders;
    private ConfigTrades configTrades;

//
//    private TraderActions traderActions;
//    private AdminActions adminActions;
//    private ItemManager itemManager;
//    private TradeManager tradeManager;
//
//
//    public Configuration(String path) throws IOException {
//        configReader = new ConfigReader(path);
//        configWriter = new ConfigWriter();
//
//        traderActions = configReader.getTraderActions();
//        adminActions = configReader.getAdminActions();
//        itemManager = configReader.getItemManager();
//        tradeManager = configReader.getTradeManager();
//
//    }
//
//    public TraderActions getTraderActions(){
//        return traderActions;
//    }
//
//    public AdminActions getAdminActions() {
//        return adminActions;
//    }
//
//    public ItemManager getItemManager() {
//        return itemManager;
//    }
//
//    public TradeManager getTradeManager() {
//        return tradeManager;
//    }
//
//    public void saveInfo(String path){
////        configWriter.saveFile(path, traderActions, adminActions);
//    }
}
