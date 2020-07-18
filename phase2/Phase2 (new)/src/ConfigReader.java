import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ConfigReader {
    private ArrayList<Trader> traders;
    private ArrayList<Admin> admins;
    private ArrayList<Item> items;

    private TraderActions traderActions;
    private AdminActions adminActions;
    private ItemManager itemManager;
    private TradeManager tradeManager;

    private ArrayList<User> users;

    public ConfigReader(String fileIn) throws IOException {
        BufferedReader fileInput = new BufferedReader(new FileReader(fileIn));
        traders = new ArrayList<>();
        admins = new ArrayList<>();
        users = new ArrayList<>();
        items = new ArrayList<>();
        String[] input;
        String line = fileInput.readLine();
        while(!line.equals("end")) {
            line = fileInput.readLine();
            input = line.split(",");
                Trader tempTrader = new Trader(input[0], input[1], input[2], new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), Boolean.parseBoolean(input[3]), Boolean.parseBoolean(input[4]),Boolean.parseBoolean(input[5]),
                    Integer.parseInt(input[6]), Integer.parseInt(input[7]));

            line = fileInput.readLine();

            if(line.equals("WantToLend:")){
                line = fileInput.readLine();
                Item tempItem;
                while(!line.equals("ProposedItems:")){
                    input = line.split(",");
                    tempItem = new Item(input[0], input[1], input[2], tempTrader, Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    items.add(tempItem);
                    tempTrader.addToWantToLend(tempItem);
                    line=fileInput.readLine();
                }
                line = fileInput.readLine();

                while(!line.equals("Trader:") && !line.equals("end")){
                    input = line.split(",");
                    tempItem = new Item(input[0], input[1], input[2], tempTrader, Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    tempTrader.addToProposedItems(tempItem);
                    line=fileInput.readLine();
                }
            }
            traders.add(tempTrader);
        }

        line=fileInput.readLine();

        Trader tempTrader;
        Trader tempOwner;
        Item tempItem;
        if(line.equals("Wishlists:")){
            line = fileInput.readLine();
            while(!line.equals("BorrowedItems:")){
                input = line.split(",");

                tempTrader = findTrader(input[0]);
                for(int i = 1; i < input.length-1;i+=2) {
                    tempOwner = findTrader(input[i+1]);
                    assert tempOwner != null;
                    tempItem = findItem(tempOwner, Integer.parseInt(input[i]));
                    assert tempTrader != null;
                    tempTrader.addToWantToBorrow(tempItem);
                }
                line = fileInput.readLine();
            }
        }
        line = fileInput.readLine();
        while(!line.equals("Trades:")){
            input = line.split(",");
            tempTrader = findTrader(input[0]);
            tempOwner = findTrader(input[6]);
            tempItem = new Item(input[1],input[2],input[3], tempOwner, Integer.parseInt(input[4]));
            assert tempTrader != null;
            tempTrader.addToBorrowedItems(tempItem);
            items.add(tempItem);
            line = fileInput.readLine();
        }
        line = fileInput.readLine();
        while(!line.equals("end")){
            input = line.split(",");
            HashMap<String, Boolean> isConfirmed, isAgreed;
            HashMap<String, Integer> numberOfEdits;
            isConfirmed = new HashMap<>();
            String tradeType = input[0];
            Trader initiator = findTrader(input[1]);
            Trader receiver = findTrader(input[2]);
            String location = input[3];
            LocalDate tradeDate = LocalDate.parse(input[4]);
            boolean isPermanent = Boolean.parseBoolean(input[5]);
            boolean isCompleted = Boolean.parseBoolean(input[6]);
            LocalDate returnDate = LocalDate.parse(input[7]);
            String tradeStatus = input[16];
            isAgreed = new HashMap<>();
            numberOfEdits = new HashMap<>();
            //InitiatorUsername
            isConfirmed.put(input[8], Boolean.parseBoolean(input[9]));
            numberOfEdits.put(input[8], Integer.parseInt(input[10]));
            isAgreed.put(input[8], Boolean.parseBoolean(input[11]));
            //ReceiverUsername
            isConfirmed.put(input[12], Boolean.parseBoolean(input[13]));
            numberOfEdits.put(input[12], Integer.parseInt(input[14]));
            isAgreed.put(input[12], Boolean.parseBoolean(input[15]));

            Item item1;
            Item item2;
            OneWayTrade oneWayTrade;
            TwoWayTrade twoWayTrade;
            line = fileInput.readLine();
            input = line.split(",");
            if (isInItems(Integer.parseInt(input[5]))) {
                item1 = getInItems(Integer.parseInt(input[5]));
            } else {
                item1 = new Item(input[1], input[2], input[3], findTrader(input[6]), Integer.parseInt(input[4]), Integer.parseInt(input[5]));
            }
            if (tradeType.equals("OneWayTrade")) {
                oneWayTrade = new OneWayTrade(initiator, receiver, location, tradeDate, isPermanent,
                        isCompleted, returnDate, isConfirmed, numberOfEdits, isAgreed, tradeStatus, item1);
                assert initiator != null;
                initiator.addToTrades(oneWayTrade);
                assert receiver != null;
                receiver.addToTrades(oneWayTrade);
            } else {
                line = fileInput.readLine();
                input = line.split(",");
                if (isInItems(Integer.parseInt(input[5]))) {
                    item2 = getInItems(Integer.parseInt(input[5]));
                } else {
                    item2 = new Item(input[1], input[2], input[3], findTrader(input[6]), Integer.parseInt(input[4]), Integer.parseInt(input[5]));
                }
                twoWayTrade = new TwoWayTrade(initiator, receiver, location, tradeDate, isPermanent,
                        isCompleted, returnDate, isConfirmed, numberOfEdits, isAgreed, tradeStatus, item1, item2);
                assert initiator != null;
                initiator.addToTrades(twoWayTrade);
                assert receiver != null;
                receiver.addToTrades(twoWayTrade);
            }
            Collections.sort(initiator.getTrades());
            Collections.sort(receiver.getTrades());

            line = fileInput.readLine();
        }

        traderActions = new TraderActions(this.traders);
            line = fileInput.readLine();
            while(!line.equals("end")) {
            input = line.split(",");
            admins.add(new Admin(input[0], input[1], input[2]));
            line = fileInput.readLine();
        }
            line = fileInput.readLine();
            ArrayList<Admin> adminRequest = new ArrayList<>();
            while(!line.equals("end")) {
                input = line.split(",");
                adminRequest.add(new Admin(input[0], input[1]));
                line = fileInput.readLine();
            }
            adminActions = new AdminActions(admins, adminRequest);
            users.addAll(admins);
            users.addAll(traders);

             line = fileInput.readLine();
             while(!line.equals("end")){
                 input = line.split(",");
                 int limitOfTradesPerWeek = Integer.parseInt(input[1]);
                 int moreLendNeeded = Integer.parseInt(input[3]);
                 int maxIncomplete = Integer.parseInt(input[5]);
                 tradeManager = new TradeManager(limitOfTradesPerWeek, moreLendNeeded, maxIncomplete);
                 line = fileInput.readLine();
             }

            }

    /**
     * given the trader's username returns that trader
     * @param username the string representing the trader
     * @return the Trader with the given username
     */
    private Trader findTrader(String username){
        for (Trader t: traders){
            if(username.equals(t.getUsername())){
                return t;
            }
        }
        return null;
    }

    /**
     * given the trader and a item ID returns that item
     * @param trader the Trader with the item
     * @param ID the item's id
     * @return the Item with the given id and owner
     */
    private Item findItem(Trader trader, int ID){
        for(Item t: trader.getWantToLend()){
            if(t.getId() == ID){
                return t;
            }
        }
        return null;
    }

    /**
     * checks to see if a given item id is in the config's items arraylist
     * @param id the item's id
     * @return a boolean of if the item is in the config's items arraylist
     */
    private boolean isInItems(int id){
        for(Item i: items){
            if(i.getId()==id){
                return true;
            }
        }
        return false;
    }

    /**
     * gets the item in the  config's items arraylist with the given item id
     * @param id the item's id
     * @return the Item with the given id
     */
    private Item getInItems(int id){
        for(Item i: items){
            if(i.getId()==id){
                return i;
            }
        }
        return null;
    }

    /**
     * a getter for traderActions
     * @return the TraderActions class made by the config constructor
     */
    public TraderActions getTraderActions(){
        return traderActions;
    }

    /**
     * a getter for adminActions
     * @return the AdminActions class made by the config constructor
     */
    public AdminActions getAdminActions(){
        return adminActions;
    }

    /**
     * a getter for itemManager
     * @return the ItemManager class made by the config constructor
     */
    public ItemManager getItemManager(){
        return itemManager;
    }

    /**
     * a getter for tradeManager
     * @return the TradeManager class made by the config constructor
     */
    public TradeManager getTradeManager(){
        return tradeManager;
    }

}

