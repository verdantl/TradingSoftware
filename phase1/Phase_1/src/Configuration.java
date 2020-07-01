import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.ArrayList;

public class Configuration {
    ArrayList<Trader> traders;
    ArrayList<Admin> admins;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;

    ArrayList<User> users;


    public Configuration(ArrayList<Trader> traders, ArrayList<Admin> admins,
                         ArrayList<Admin> adminRequests){
        this.traders = traders;
        this.admins = admins;
        this.users = new ArrayList<>();
        users.addAll(traders);
        users.addAll(admins);

        traderActions = new TraderActions(this.traders);
        adminActions = new AdminActions(this.admins, adminRequests);
        itemManager = new ItemManager();
        tradeManager = new TradeManager();
    }

    public ArrayList<Trader> getTraders() {
        return traders;
    }

    public void setTraders(ArrayList<Trader> traders) {
        this.traders = traders;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public TraderActions getTraderActions() {
        return traderActions;
    }

    public void setTraderActions(TraderActions traderActions) {
        this.traderActions = traderActions;
    }

    public AdminActions getAdminActions() {
        return adminActions;
    }

    public void setAdminActions(AdminActions adminActions) {
        this.adminActions = adminActions;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public TradeManager getTradeManager() {
        return tradeManager;
    }

    public void setTradeManager(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public void addToUsers(User user){
        this.users.add(user);
    }


}
