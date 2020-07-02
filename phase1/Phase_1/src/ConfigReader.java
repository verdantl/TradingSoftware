import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigReader {
    ArrayList<Trader> traders;
    ArrayList<Admin> admins;
    ArrayList<Item> items;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;


    ArrayList<User> users;

    public ConfigReader (BufferedReader fileInput) throws IOException {
        //TODO Change buffer reader to passing in filePath and instantiating bufferedReader
        traders = new ArrayList<>();
        admins = new ArrayList<>();
        users = new ArrayList<>();
        tradeManager = new TradeManager();
        items = new ArrayList<>();
        String[] input;
        String line = fileInput.readLine();
        while(!line.equals("end")) {
            //Example entry for a trader:
            //Format: username, password, creationDate, isFrozen, isFlagged,requestedtounfreeze,numlent,numborrowed
            //eg. Username,Password,12-12-2020,false,false,false,2,1
            //    Username2,Password2,12-12-2020,true,false,false,2,1
            input = line.split(",");
            //makes and adds the empty trader to traders
            Trader tempTrader = new Trader(input[0], input[1], input[2], new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(), Boolean.parseBoolean(input[3]), Boolean.parseBoolean(input[4]),Boolean.parseBoolean(input[5]),
                    Integer.parseInt(input[6]), Integer.parseInt(input[7]));

            //Goes through the trader's wantToLend items.
            //Entry format for trader's wantToLend:
            //itemName, category,description,rating, itemID
            line = fileInput.readLine();
            if(line.equals("wantToLend:")){
                line = fileInput.readLine();
                Item tempItem;
                while(!line.equals("ProposedItems:")){
                    input = line.split(",");
                    tempItem = new Item(input[0], input[1], input[2], tempTrader, Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    items.add(tempItem);
                    tempTrader.addToWantToLend(tempItem);
                    line=fileInput.readLine();
                }
                //Goes through the trader's proposedItems. The entry format is:
                //itemName, category,description,rating, itemID
                line = fileInput.readLine();
                while(!line.equals("Trader") && !line.equals("end")){
                    input = line.split(",");
                    tempItem = new Item(input[0], input[1], input[2], tempTrader, Integer.parseInt(input[3]), Integer.parseInt(input[4]));
                    tempTrader.addToProposedItems(tempItem);
                    line=fileInput.readLine();
                }
            }
            traders.add(tempTrader);
            //Note that at the end of the traders we write an end.
            //An example input up to this part would be the following:
            /*
             * Username,Password,12-12-2020,false,false,false,2,1
             * WantToLend:
             * Bike,Sports,It’s a bike,10,1
             * Book,Literature,It’s a book,9,2
             * ProposedItems:
             * England,Country,I’m the King,10,3
             * Trader:
             * user2,goodbye,2020-07-01,false,false,0,0
             * WantToLend:
             * Laptop,Electronics,It’s a laptop,5,4
             * Wallet,Accessories,It’s a Wallet,9,5
             * ProposedItems:
             * end
             */
        }

        line=fileInput.readLine();
        Trader tempTrader;
        Trader tempOwner;
        Item tempItem;
        //This part reads in the all the items in user's wishlists and adds them using their reference created before
        //Entry format for this part is:
        //Users who's wishlist we want to add items to, item1's ID, item1's owner's username, item2's id, item2's user's username, ....
        if(line.equals("Wishlists:")){
            while(!line.equals("BorrowedItems:")){
                input = line.split(",");

                tempTrader = findTrader(input[0]);
                for(int i = 1; i < input.length-1;i+=2) {
                    tempOwner = findTrader(input[i+1]);
                    tempItem = findItem(tempOwner, Integer.parseInt(input[i]));
                    tempTrader.addToWantToBorrow(tempItem);
                }
                line = fileInput.readLine();
            }
        }
        //This part reads in all of the borrowed items this user has, as of now we assume even permanent trades's items get added to the borrowed items list
        //The reason this part is not treated like the wishlist and the wantToLend list is because we need to have all the traders' references
        //Otherwise we won't be able to set these items' owners
        //Entry format for this part is:
        //User's username who's borrowedItems we're adding to, Item name, Category, Description, rating, itemID,ownner's username
        line = fileInput.readLine();
        while(!line.equals("Trades:")){
            input = line.split(",");
            tempTrader = findTrader(input[0]);
            tempOwner = findTrader(input[6]);
            tempItem = new Item(input[1],input[2],input[3], tempOwner, Integer.parseInt(input[4]));
            tempTrader.addToBorrowedItems(tempItem);
            items.add(tempItem);
            line = fileInput.readLine();
        }
        line = fileInput.readLine();
        //This is the part we add in trades.
        //The entry format for trades would be the following:
        //TradeType(OneWay or TwoWay), initator's username, receiver's username, location, the date the trade will occur, isPermanent, isCompleted, returnDate(note that if a trade is permanent the date here is recorded as 0000-00-00),Initiator's username, isConfirmed(for initiator), numberOfEdits(for initiator), isAgreed(for initiator), receiver's username, isConfirmed(for reciever), numberOfEdits(for receiver), isAgreed(for receiver), TradeStatus.
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
            LocalDate returnDate;
            if(!input[7].equals("null")) {
                returnDate = LocalDate.parse(input[7]);
            }
            else{
                returnDate = LocalDate.parse("0000-00-00");
            }
            String tradeStatus = input[18];
            isAgreed = new HashMap<>();
            numberOfEdits = new HashMap<>();
            //InitiatorUsername
            isConfirmed.put(input[10], Boolean.parseBoolean(input[11]));
            numberOfEdits.put(input[10], Integer.parseInt(input[12]));
            isAgreed.put(input[10], Boolean.parseBoolean(input[13]));
            //ReceiverUsername
            isConfirmed.put(input[14], Boolean.parseBoolean(input[15]));
            numberOfEdits.put(input[14], Integer.parseInt(input[16]));
            isAgreed.put(input[14], Boolean.parseBoolean(input[17]));
            Item item1;
            Item item2;
            OneWayTrade oneWayTrade;
            TwoWayTrade twoWayTrade;
            line = fileInput.readLine();
            input = line.split(",");
            if(isInItems(Integer.parseInt(input[6]))){
                item1 = getInItems(Integer.parseInt(input[6]));
            }
            else{
                item1 = new Item(input[0], input[1], input[2], findTrader(input[5]), Integer.parseInt(input[3]), Integer.parseInt(input[4]));
            }
            if(tradeType.equals("OneWay")) {
                oneWayTrade = new OneWayTrade(initiator, receiver, location, tradeDate, isPermanent,
                        isCompleted, returnDate, isConfirmed, numberOfEdits, isAgreed, tradeStatus, item1);
                initiator.addToTrades(oneWayTrade);
                receiver.addToTrades(oneWayTrade);
            }
            else{
                line = fileInput.readLine();
                input = line.split(",");
                if(isInItems(Integer.parseInt(input[6]))){
                    item2 = getInItems(Integer.parseInt(input[6]));
                }
                else{
                    item2 = new Item(input[0], input[1], input[2], findTrader(input[5]), Integer.parseInt(input[3]), Integer.parseInt(input[4])) ;
                }
                twoWayTrade = new TwoWayTrade(initiator, receiver, location, tradeDate, isPermanent,
                        isCompleted, returnDate, isConfirmed, numberOfEdits, isAgreed, tradeStatus, item1,item2);
                initiator.addToTrades(twoWayTrade);
                receiver.addToTrades(twoWayTrade);
            }
            line = fileInput.readLine();
            }
            //This is as far as i got for reading in.
            //TODO Finish reading in admins and adminRequests.


//        traderActions = new TraderActions(this.traders);
//        //adds to users
//        users.addAll(traders);
//
//        //this is what i want you finish and is a failed attempted
//        int i = 0;
//        Item tempItem;
//        Trader tempTrader;
//        line = fileInput.readLine();
//        while(!line.equals("end")) {
//            while (line.equals("next")){
//                i++;
//                line = fileInput.readLine();
//            }
//            tempTrader = getByUsername(line);
//            line = fileInput.readLine();
//            input = line.split(",");
//            tempItem = new Item(input[0], input[1], input[2], getByUsername(input[3]), Integer.parseInt(input[4]));
//            itemManager.addApprovedItem(tempItem);
//            addItemToTrader(tempTrader, tempItem, i);
//            line = fileInput.readLine();
//        }
//
//        //adds trades one way then two way
//        line = fileInput.readLine();
//        Trade tempTrade;
//        Trader t1, t2;
//        while(!line.equals("end")) {
//            input = line.split(",");
//            t1 = getByUsername(input[0]);
//            t2 = getByUsername(input[1]);
//            tempTrade = new OneWayTrade(t1, t2, input[2], LocalDate.parse(input[3]), null);
//            t1.addToTrades(tempTrade);
//            t2.addToTrades(tempTrade);
//        }
//
//        line = fileInput.readLine();
//        while(!line.equals("end")) {
//            input = line.split(",");
//            t1 = getByUsername(input[0]);
//            t2 = getByUsername(input[1]);
//            tempTrade = new TwoWayTrade(t1, t2, LocalDate.parse(input[3]), input[2], null,null);
//            t1.addToTrades(tempTrade);
//            t2.addToTrades(tempTrade);
//        }
//        //adds the admins
//        line = fileInput.readLine();
//        while(!line.equals("end")) {
//            input = line.split(",");
//            admins.add(new Admin(input[0], input[1]));
//            line = fileInput.readLine();
//        }
//        //adminActions = new AdminActions(admins, null, null);
    }
//
//    private Trader getByUsername(String username){
//        for (Trader t : traders){
//            if (t.getUsername().equals(username)){
//                return t;
//            }
//        }
//        return null;
//    }
//
//    private void addItemToTrader(Trader t, Item i, int j){
//        switch (j){
//            //case 0 exits
//            case 1: t.addToWantToBorrow(i);
//                break;
//            case 2: t.addToProposedItems(i);
//                break;
//            case 3: t.addToWantToLend(i);
//                break;
//            case 4: t.addToBorrowedItems(i);
//        }
//    }

    private Trader findTrader(String username){
        for (Trader t: traders){
            if(username.equals(t.getUsername())){
                return t;
            }
        }
        return null;
    }
    private Item findItem(Trader trader, int ID){
        for(Item t: trader.getWantToLend()){
            if(t.getId() == ID){
                return t;
            }
        }
        return null;
    }

    private boolean isInItems(int id){
        for(Item i: items){
            if(i.getId()==id){
                return true;
            }
        }
        return false;
    }
    private Item getInItems(int id){
        for(Item i: items){
            if(i.getId()==id){
                return i;
            }
        }
        return null;
    }
}

