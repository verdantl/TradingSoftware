import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TraderActions {
    private ArrayList<Trader> traders;

    /**
     * This is the constructor for reading in users.
     * @param traders A list of traders that were read in to the program
     */
    public TraderActions(ArrayList<Trader> traders){
        this.traders = traders;
    }

    /**
     * Adds item to list of wantToBorrow items of the Trader
     * @param trader The given trader whose item is to be added
     * @param item The item to be added
     */
    public void addToWantToBorrow(Trader trader, Item item){
        trader.addToWantToBorrow(item);
    }

    /**
     * Removes items from list of wantToBorrow items of the Trader
     * @param trader The trader who's item is to be removed
     * @param item The item to be removed
     */
    public void removeFromWantToBorrow(Trader trader, Item item){
        trader.removeFromWantToBorrow(item);
    }

    /**
     * Adds an item to a Trader's proposed items list.
     * @param trader The trader whose item is to be added.
     * @param item The item to be added.
     */
    public void addProposedItem(Trader trader, Item item) { trader.addToProposedItems(item); }

    /**
     * Remove an item from a Trader's proposed items list.
     * @param trader The trader whose item is to be removed.
     * @param item The item to be removed.
     */
    public void removeProposedItem(Trader trader, Item item) { trader.removeFromProposedItems(item); }

    /*Okay so browsing items probably works with the presenter, so this method will return a list of available items that
    can be lent, and i will leave it to the presenter class to present it to the user.
    The current way im doing it isn't efficient because we have to compute a list of all the items everytime a user wants to
    browse them.*/

    /**
     * Returns a list of items that are available for lending
     * @return The list of items that are available for lending in the system
     */
    public ArrayList<Item> browseItems(){
        ArrayList<Item> availableItems = new ArrayList<>();
        for(Trader t: traders){
            availableItems.addAll(t.getWantToLend());
        }
        return availableItems;
    }

    /**
     * Adds the given trader to the list of traders.
     * @param trader The trader that is to be added to the system
     */
    public void addTrader(Trader trader){
        if(!traders.contains(trader)){
            traders.add(trader);
        }
    }

    /**
     * Adds the given trade to the list of the user's trades and returns true if the trade can be added.
     * Otherwise it returns false
     * @param trader trader The trader whose trade is to be added.
     * @param trade trade The trade to be added.
     * @return whether the trade has been added or not
     */
    public boolean addTrade(Trader trader, Trade trade){
        if(isVaildTrade(trader, trade)){
        trader.addToTrades(trade);
        return true;
        }
        else {
        return false;}
    }

    /**
     * This returns a list of the top 3 most frequent trading partners in descending order.
     * If there are less than 3 trading partners, it returns however many there are. If there is more than one
     * top trading partner with the same frequency, it returns the top 3 trading partners, where the users returned are chosen at random.
     * @param trader The trader who's partners are to be checked.
     * @return An Arraylist of the trader's most frequent trading partners
     */
    // Some code was used from the following resource, just going to link it here until I can cite it later:
    //https://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
    public ArrayList<User> mostFrequentTradingPartners(Trader trader){

        ArrayList<Trade> trades = trader.getTrades();
        HashMap<User, Integer> mostFrequent = new HashMap<>();
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
        ArrayList<User> topThree = new ArrayList<>();
        HashMap.Entry<User, Integer> max = null;
        Set<HashMap.Entry<User,Integer>> entrySet = mostFrequent.entrySet();

        for (int i= 0; i< 3; i++){
            for(HashMap.Entry<User,Integer> u: entrySet){
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

    /**
     * Checks if the given username is taken or not.
     * @param username The username that needs to be checked
     * @return Returns true if the username is not taken
     */
    public boolean checkUsername(String username){
        for(Trader t: traders){
            if (username.equals(t.getUsername())){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the three most recent trades in decreasing order (Most Recent to least Recent)
     * @param trader The trader who's trades we want
     * @return An ArrayList of the three trades.
     */
    public ArrayList<Trade> getThreeRecentTrades(Trader trader){
        ArrayList<Trade> mostRecentThreeTrades = new ArrayList<>();
        for (int i = trader.getTrades().size(); i>0; i--){
            if(trader.getTrades().get(i).isCompleted()){
                mostRecentThreeTrades.add(trader.getTrades().get(i));
            }
            if(mostRecentThreeTrades.size()==3){
                return mostRecentThreeTrades;
            }
        }
        return mostRecentThreeTrades;
    }

    private boolean isVaildTrade(Trader trader, Trade trade){
        //needs the global var here
        int limitOfTradesPerWeek = 3;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = trade.getTradeDate().get(weekFields.weekOfWeekBasedYear());
        //int week = trade.getTradeDate().getDayOfYear()/7;
        ArrayList<Trade> trades = trader.getTrades();
        int i = Collections.binarySearch(trades, trade);
        //what if the trades are at the same time
        int n = 0;
        for (int j = 0; n<limitOfTradesPerWeek; j++){
            //add the check of if it's the same year
            //if (i+j < trades.size() && trades.get(i+j).getTradeDate().getDayOfYear()/7 == week){
            if (i+j < trades.size() && trades.get(i+j).getTradeDate().get(weekFields.weekOfWeekBasedYear()) ==
                    weekNumber && trade.getTradeDate().getYear() == trades.get(i+j).getTradeDate().getYear()){
                n++;
            }
            else {
                break;
            }
        }
        for (int k = 1; n<limitOfTradesPerWeek; k++){
            //if (i-k >= 0 && trades.get(i-k).getTradeDate().getDayOfYear()/7 == week){
            if (i-k >= 0 && trades.get(i-k).getTradeDate().get(weekFields.weekOfWeekBasedYear()) ==
                    weekNumber && trade.getTradeDate().getYear() == trades.get(i-k).getTradeDate().getYear()){
                n++;
            }
            else {
                break;
            }
        }
        return n < limitOfTradesPerWeek;
    }


}
