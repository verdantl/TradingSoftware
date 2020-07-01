import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConfigReader {
    ArrayList<Trader> traders;
    ArrayList<Admin> admins;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;

    ArrayList<User> users;

    public ConfigReader (BufferedReader fileInput) throws IOException {
        traders = new ArrayList<>();
        admins = new ArrayList<>();
        users = new ArrayList<>();
        tradeManager = new TradeManager();

        String[] input;
        //adds the traders
        String line = fileInput.readLine();
        //if the line is end it'll move to the next class in this case item
        while(!line.equals("end")) {
            //the cvs method
            //eg. Username,Password,12-12-2020,false,false,2,1
            //    Username2,Password2,12-12-2020,true,false,2,1
            input = line.split(",");
            //makes and adds the empty trader to traders
            traders.add(new Trader(input[0], input[1], input[2], new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), Boolean.parseBoolean(input[3]), Boolean.parseBoolean(input[4]),
                    Integer.parseInt(input[5]), Integer.parseInt(input[6])));
            //goes to the next trader or "end" to the next class
            line = fileInput.readLine();
        }
        //makes the TA
        traderActions = new TraderActions(this.traders);
        //adds to users
        users.addAll(traders);

        //this is what i want you finish and is a failed atempted
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

        //adds trades one way then two way
        line = fileInput.readLine();
        Trade tempTrade;
        Trader t1, t2;
        while(!line.equals("end")) {
            input = line.split(",");
            t1 = getByUsername(input[0]);
            t2 = getByUsername(input[1]);
            tempTrade = new OneWayTrade(t1, t2, input[2], LocalDate.parse(input[3]), null);
            t1.addToTrades(tempTrade);
            t2.addToTrades(tempTrade);
        }
        line = fileInput.readLine();
        while(!line.equals("end")) {
            input = line.split(",");
            t1 = getByUsername(input[0]);
            t2 = getByUsername(input[1]);
            tempTrade = new TwoWayTrade(t1, t2, LocalDate.parse(input[3]), input[2], null,null);
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
        adminActions = new AdminActions(admins, null, null);
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
