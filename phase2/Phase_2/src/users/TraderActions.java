package users;

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

    /**
     * Removes the given item from the given trader's wantToLend list
     * @param trader The given trader.
     * @param item The item they desire to be removed.
     */
    public void removeFromWantToLend(Trader trader, Item item){
        trader.removeFromWantToLend(item);
    }

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
     * Returns a list of items that are available for borrowing/trading for the given trader
     * @return The list of items that are available for borrowing for the given trader.
     */
    public ArrayList<Item> browseItems(Trader trader){
        ArrayList<Item> availableItems = new ArrayList<>();
        for(Trader t: traders){
            if(!t.getUsername().equals(trader.getUsername())){
                availableItems.addAll(t.getWantToLend());
            }
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

    public Trader login(String username, String password){
        for(Trader t: traders){
            if (username.equals(t.getUsername())){
                if (password.equals(t.getPassword())){
                return t;
                }
            }
        }
        return null;
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


    /**
     *
     * @return A formatted string that has every trader's username.
     */
    public StringBuilder printAccounts(){
        StringBuilder accounts = new StringBuilder();
        for (int i = 0; i < traders.size(); i++){
            if (accounts.length() > 80){
                accounts.append("\n");
            }
            accounts.append(i).append(". ");
            accounts.append(traders.get(i).getUsername());
        }
        return accounts;
    }

    /**Getter for the traders
     * @return a list of traders in this system
     */
    public ArrayList<Trader> getTraders(){return this.traders;}


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

    /**
     * Getter for traders who have items awaiting approval
     * @return a list of traders in the system with proposedItems length with more than 0 items
     */
    public ArrayList<Trader> getTradersNeedingApproval() {
        ArrayList<Trader> accounts = new ArrayList<>();
        for (Trader trader : traders) {
            if (trader.getProposedItems().size() >= 1) {
                accounts.add(trader);
            }
        }
        return accounts;
    }

    /**
     * @return Return a list of flagged trader accounts that needs to be frozen
     */

    public ArrayList<Trader> getListOfFlaggedAccounts(){
        ArrayList<Trader> accounts = new ArrayList<>();
        for (Trader trader: traders){
            if(trader.isFlagged()){accounts.add(trader);}
        }
        return accounts;
    }

    /**Getter for the frozen accounts
     * @return a list of frozen accounts in this system
     */
    public ArrayList<Trader> getFrozenTraders(){
        ArrayList<Trader> accounts = new ArrayList<>();
        for (Trader trader : traders) {
            if (trader.isFrozen()) {
                accounts.add(trader);
            }
        }
        return accounts;
    }

    /**
     * Returns a list of all of the users who have requested to be unfrozen.
     * @return An arraylist of all users who have requested to be unfrozen.
     */
    public ArrayList<Trader> getAllRequestToUnfreeze(){
        ArrayList<Trader> temp = new ArrayList<>();
        for(Trader t: traders){
            if(t.isRequestToUnfreeze()){
                temp.add(t);
            }
        }
        return temp;
    }
}
