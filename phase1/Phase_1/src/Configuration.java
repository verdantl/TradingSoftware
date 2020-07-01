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
                         ArrayList<Admin> adminRequests, ArrayList<Trader> unfreezeRequests){
        this.traders = traders;
        this.admins = admins;
        this.users = new ArrayList<>();
        users.addAll(traders);
        users.addAll(admins);

        traderActions = new TraderActions(this.traders);
        adminActions = new AdminActions(this.admins, adminRequests, unfreezeRequests);
        itemManager = new ItemManager();
        tradeManager = new TradeManager();
    }

    public Configuration (BufferedReader fileInput) throws IOException {
        traders = new ArrayList<>();
        admins = new ArrayList<>();
        users = new ArrayList<>();
        tradeManager = new TradeManager();

        String[] input;
        //adds the traders
        String line = fileInput.readLine();
        while(!line.equals("end")) {
            input = line.split(",");
            traders.add(new Trader(input[0], input[1], input[2], new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), Boolean.parseBoolean(input[3]), Boolean.parseBoolean(input[4]),
                    Integer.parseInt(input[5]), Integer.parseInt(input[6])));
            line = fileInput.readLine();
        }
        traderActions = new TraderActions(this.traders);
        users.addAll(traders);

        //adds the items
        int i = 0;
        Item tempItem;
        Trader tempTrader;
        line = fileInput.readLine();
        while(!line.equals("end")) {
            while (line.equals("next")){
                i++;
                line = fileInput.readLine();
            }
            tempTrader = getByUsername(line);
            line = fileInput.readLine();
            input = line.split(",");
            tempItem = new Item(input[0], input[1], input[2], getByUsername(input[3]), Integer.parseInt(input[4]));
            itemManager.addApprovedItem(tempItem);
            addItemToTrader(tempTrader, tempItem, i);
            line = fileInput.readLine();
        }

        //adds trades
        line = fileInput.readLine();
        Trade tempTrade;
        Trader t1, t2;
        while(!line.equals("end")) {
            input = line.split(",");
            t1 = getByUsername(input[0]);
            t2 = getByUsername(input[1]);
            tempTrade = new Trade(t1, t2, input[2], LocalDate.parse(input[3]), input[4]);
            t1.addToTrades(tempTrade);
            t2.addToTrades(tempTrade);
        }
        //adds the admins
        line = fileInput.readLine();
        while(!line.equals("end")) {
            input = line.split(",");
            admins.add(new Admin(input[0], input[1]));
            line = fileInput.readLine();
        }
        adminActions = new AdminActions(admins);
        //adds
        line = fileInput.readLine();
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

    private Trader getByUsername(String username){
        for (Trader t : traders){
            if (t.getUsername().equals(username)){
                return t;
            }
        }
        return null;
    }

    private void addItemToTrader(Trader t, Item i, int j){
        switch (j){
            //case 0 exits
            case 1: t.addToWantToBorrow(i);
            break;
            case 2: t.addToProposedItems(i);
            break;
            case 3: t.addToWantToLend(i);
            break;
            case 4: t.addToBorrowedItems(i);
        }
    }
}
