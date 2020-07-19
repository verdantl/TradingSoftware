import java.util.*;


public class TraderActions {
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private TraderManager traderManager;

    /**
     * This is the constructor for the trader actions class which acts as a facade.
     * @param itemManager The item manager for the class
     * @param meetingManager The meeting manager for the class
     * @param tradeManager The trade manager for the class
     * @param traderManager the trader manager for the class
     */
    public TraderActions(ItemManager itemManager, MeetingManager meetingManager, TradeManager tradeManager,
                         TraderManager traderManager){
        this.itemManager = itemManager;
        this.meetingManager = meetingManager;
        this.tradeManager = tradeManager;
        this.traderManager = traderManager;
    }

    /**
     * A getter for the approved items owned by the user with the given username
     * @param username The username of the trader
     * @return An arraylist of all approved items owned by the trader
     */
    public ArrayList<Item> getApprovedItems(String username){
        return itemManager.getApprovedItems(username);
    }

    public List<Item> getWishList(String username){
        return itemManager.getWantToBorrow(username);
    }

    public void addToWishList(String username, int id){
        itemManager.addToWantToBorrow(username, id);
    }

    public void removeFromWishList(String username, int id){
        itemManager.removeFromWantToBorrow(username, id);
    }

    public


    //TODO Reimplement once trade classes are done.
    /**
     * This returns a list of the top 3 most frequent trading partners in descending order.
     * If there are less than 3 trading partners, it returns however many there are. If there is more than one
     * top trading partner with the same frequency, it returns the top 3 trading partners, where the users returned are chosen at random.
     * This code is based on the work of "arshajii" at : https://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
     * @param trader The trader who's partners are to be checked.
     * @return An Arraylist of the trader's most frequent trading partners
     */
    public ArrayList<Trader> mostFrequentTradingPartners(Trader trader){
        ArrayList<Trade> trades = trader.getTrades();
        HashMap<Trader, Integer> mostFrequent = new HashMap<>();
        for(Trade t: trades){
            Integer num;
            if(t.isCompleted()){
                if(t.getInitiator().getUsername().equals(trader.getUsername())){
                    num = mostFrequent.get(t.getReceiver());
                    mostFrequent.put(t.getReceiver(), num == null? 1:num+1);
                }
                else{
                    num = mostFrequent.get(t.getInitiator());
                    mostFrequent.put(t.getInitiator(), num == null? 1:num+1);
                }
            }
        }
        ArrayList<Trader> topThree = new ArrayList<>();
        HashMap.Entry<Trader, Integer> max = null;
        Set<HashMap.Entry<Trader,Integer>> entrySet = mostFrequent.entrySet();

        for (int i= 0; i< 3; i++){
            for(HashMap.Entry<Trader,Integer> u: entrySet){
                if(max == null || u.getValue()>max.getValue()) {
                    max = u;
                }
            }

            if(max!=null){
                topThree.add(max.getKey());
                entrySet.remove(max);
            }
            max = null;
        }

        return topThree;
    }

//    /**
//     * Checks if the given username is taken or not.
//     * @param username The username that needs to be checked
//     * @return Returns true if the username is not taken
//     */
//    public boolean checkUsername(String username){
//        for(Trader t: traders){
//            if (username.equals(t.getUsername())){
//                return false;
//            }
//        }
//        return true;
//    }
//
    
//    /**
//     * Returns the three most recent trades in decreasing order (Most Recent to least Recent)
//     * @param trader The trader who's trades we want
//     * @return An ArrayList of the three trades.
//     */
//    public ArrayList<Trade> getThreeRecentTrades(Trader trader){
//        ArrayList<Trade> mostRecentThreeTrades = new ArrayList<>();
//        for (int i = trader.getTrades().size(); i>0; i--){
//            if(trader.getTrades().get(i).isCompleted()){
//                mostRecentThreeTrades.add(trader.getTrades().get(i));
//            }
//            if(mostRecentThreeTrades.size()==3){
//                return mostRecentThreeTrades;
//            }
//        }
//        return mostRecentThreeTrades;
//    }
    //TODO Remimplement once trade classes are done.
    /**
     * Returns an arraylist of the three (if there are three) most recently traded items.
     * Here by recenetly traded we assume it means any item involved in a trade.
     * @param trader The trader whose recent items we wish to check
     * @return The list of items the threeMostRecentItems
     */
    public ArrayList<Item> getMostRecentItems(Trader trader){
        ArrayList<Item> mostRecentThreeItems = new ArrayList<>();
        for (int i = trader.getTrades().size(); i>0; i--){
            if(trader.getTrades().get(i-1).isCompleted()){
                ArrayList<Item> temp = trader.getTrades().get(i-1).getItems();
                for(Item item: temp){
                    if(mostRecentThreeItems.size()<3){
                        mostRecentThreeItems.add(item);
                    }
                }

            }
            if(mostRecentThreeItems.size()==3){
                return mostRecentThreeItems;
            }
        }
        return mostRecentThreeItems;
    }


//    /**
//     *
//     * @return A formatted string that has every trader's username.
//     */
//    public StringBuilder printAccounts(){
//        StringBuilder accounts = new StringBuilder();
//        for (int i = 0; i < traders.size(); i++){
//            if (accounts.length() > 80){
//                accounts.append("\n");
//            }
//            accounts.append(i).append(". ");
//            accounts.append(traders.get(i).getUsername());
//        }
//        return accounts;
//    }

//    /**Getter for the traders
//     * @return a list of traders in this system
//     */
//    public ArrayList<Trader> getTraders(){return this.traders;}

    //TODO Move this to TradeManager or wherever its supposed to be moved to
    /**
     * Returns all trades from a trader that have yet to be completed.
     * @param trader The trader whose trades are being examined.
     * @return A list of trader's unfinished/on-going trades.
     */
    public ArrayList<Trade> getOnGoingTrades(Trader trader){
        ArrayList<Trade> allTrades = trader.getTrades();
        ArrayList<Trade> onGoingTrades = new ArrayList<>();
        for (Trade i : allTrades){
            if (!i.isCompleted()){
                onGoingTrades.add(i);
            }
        }
        return onGoingTrades;
    }

//
//    /**
//     * Getter for traders who have items awaiting approval
//     * @return a list of traders in the system with proposedItems length with more than 0 items
//     */
//    public ArrayList<Trader> getTradersNeedingApproval() {
//        ArrayList<Trader> accounts = new ArrayList<>();
//        for (Trader trader : traders) {
//            if (trader.getProposedItems().size() >= 1) {
//                accounts.add(trader);
//            }
//        }
//        return accounts;
//    }


//    /**Getter for the frozen accounts
//     * @return a list of frozen accounts in this system
//     */
//    public ArrayList<Trader> getFrozenTraders(){
//        ArrayList<Trader> accounts = new ArrayList<>();
//        for (Trader trader : traders) {
//            if (trader.isFrozen()) {
//                accounts.add(trader);
//            }
//        }
//        return accounts;
//    }

//    /**
//     * Returns a list of all of the users who have requested to be unfrozen.
//     * @return An arraylist of all users who have requested to be unfrozen.
//     */
//    public ArrayList<Trader> getAllRequestToUnfreeze(){
//        ArrayList<Trader> temp = new ArrayList<>();
//        for(Trader t: traders){
//            if(t.isRequestToUnfreeze()){
//                temp.add(t);
//            }
//        }
//        return temp;
//    }
}
