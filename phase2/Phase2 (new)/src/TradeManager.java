import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;


public class TradeManager {
    private final HashMap<Integer, Trade> tradeInventory;
    private final HashMap<String, List<Integer>> trades;
    private int weeklyLimit;
    private int maxInComplete;
    private int moreLend;
    private int counter;

    public TradeManager(HashMap<Integer, Trade> tradeInventory, HashMap<String, List<Integer>> trades,
                        int weeklyLimit, int maxInComplete, int moreLend){
        this.tradeInventory = tradeInventory;
        this.trades = trades;
        this.weeklyLimit = weeklyLimit;
        this.maxInComplete = maxInComplete;
        this.moreLend = moreLend;
        counter = Collections.max(tradeInventory.keySet()) + 1;
    }

    /**return the trade by the id
     * @param id the id of the trade
     * @return the trade with the trade's id
     */
    public Trade getTrade(int id){
        if(!tradeInventory.containsKey(id)){
            return null;
        }
        return tradeInventory.get(id);
    }

    /**get a string representation of user's trade list
     * @param user the user who wants to browse his trade list
     * @return a string representation of trade list
     */
    public String getListOfTrades(String user){
        int i = 1;
        StringBuilder s = new StringBuilder();
        for(int id: trades.get(user)){
            s.append(i);
            s.append(tradeInventory.get(id).toString());
            s.append("\n");
            i++;
        }

        return s.toString();
    }

    /**create a trade
     * @param isPermanent whether or not the trade is permanent
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @return the trade whether or not the trade is successfully created
     */
    public int createTrade(String initiator, String receiver, String tradeType, boolean isPermanent,
                           List<Integer> items){
        Trade trade = new Trade(counter, initiator, receiver, tradeType, isPermanent);
        trade.setItems(items);
        counter++;
        addToTradeInventory(trade);
        addToTrades(initiator, trade);
        addToTrades(receiver, trade);
        return trade.getId();
    }

    /**add a trade to the tradeInventory
     * @param trade the trade needed to be added in inventory
     * @return whether or not the trade is successfully added in inventory
     */
    public boolean addToTradeInventory(Trade trade){
        if(!tradeInventory.containsKey(trade.getId())){
            tradeInventory.put(trade.getId(), trade);
            return true;
        }else{
            return false;
        }
    }

    /**remove a trade from tradeInventory
     * @param id the id of the trade
     * @return whether or not the trade is successfully removed from inventory
     */
    public boolean removeFromInventory(int id){
        if(!tradeInventory.containsKey(id)){
            return false;
        }else{
            tradeInventory.remove(id);
            return true;
        }
    }

    /**add a trade to user's trade list
     * @param user the user who wants to add trade to trades
     * @param trade the trade needed to be added
     * @return whether or not the trade is successfully added in user's trade list
     */
    public boolean addToTrades(String user, Trade trade){
        if(!trades.containsKey(user)){
            List<Integer> list = new ArrayList<>();
            list.add(trade.getId());
            trades.put(user, list);
            return true;
        }

        if(!trades.get(user).contains(trade.getId())){
            trades.get(user).add(trade.getId());
            return true;
        }else{
            return false;
        }
    }

    /**remove a trade from user's trade list
     * @param user the user who wants to remove the trade
     * @param id the id of the trade
     * @return whether or not the trade is successfully removed from user's trade list
     */
    public boolean removeFromTrades(String user, int id){
        if(!trades.containsKey(user)){
            return false;
        }

        if(!trades.get(user).contains(id)){
            return false;
        }else{
            trades.get(user).remove(id);
            return true;
        }
    }

    /**check whether or not the user exceed weekly trade limit
     * @param user the user who wants to create a new trade
     * @param createDate the date that the user requests to create the trade
     * @return whether or not the user exceed weekly trade limit
     */
    public boolean exceedWeeklyLimit(String user, LocalDate createDate){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = createDate.get(weekFields.weekOfWeekBasedYear());
        int n = 0;
        for(int id: trades.get(user)){
            LocalDate date = tradeInventory.get(id).getCreatedDate();
            if(date.getYear() == createDate.getYear()
                    && date.get(weekFields.weekOfWeekBasedYear()) == weekNumber){
                n++;
            }
        }
        return n > weeklyLimit;
    }

    /**Check whether or not the user exceed the max number of incomplete trades
     * @param user the usr who wants to request to trade
     * @return whether or not the user exceed the max number of incomplete trades
     */
    public boolean exceedMaxInComplete(String user){
        int total = 0;
        for(int id: trades.get(user)){
            if(!tradeInventory.get(id).getCompleted()){
                total += 1;
            }
        }
        return total > maxInComplete;
    }

    /**check whether or not the user need to lend more items 
     * @param user the user who wants to trade
     * @param numOfLend the number of items that user has lent
     * @param numOfBorrow the number of items that user has borrowed
     * @return whether or not the user need to lend more items
     */
    public boolean needMoreLend(String user, int numOfLend, int numOfBorrow){
        if(trades.get(user).isEmpty()){
            return false;
        }else{
            return numOfLend - numOfBorrow < moreLend;
        }
    }

    /**set the assigned trade with id to be completed
     * @param id the id of the trade
     */
    public void setTradeCompleted(int id){
        tradeInventory.get(id).setCompleted(true);
    }


    // You may not need this method for a trade having meeting, in the meetingManager,
    // confirmMeeting return true iff both trader confirm the meeting
    // which means that the meeting is completed
    // I include this method here just in order to implement exceedMaxIncomplete
    /**
     * Checks whether the trade with the given id is completed.
     * @param id The trade id
     * @return True iff trade with ID id is completed.
     */
    public boolean isTradeCompleted(int id){
        return tradeInventory.get(id).getCompleted();
    }

    /**
     * Returns the given trade's initiator
     * @param id The id of the trade
     * @return the username of the initiator
     */
    public String getTradeInitiator(int id){
        return tradeInventory.get(id).getInitiator();
    }

    /**
     * Returns the given trade's receiver
     * @param id The id of the trade
     * @return the username of the receiver
     */
    public String getTradeReceiver(int id){
        return tradeInventory.get(id).getInitiator();
    }

    /**return the given trade's tradeType
     * @param id the id of the trade
     * @return which type the trade is(OneWay or TwoWay)
     */
    public String getTradeType(int id){return tradeInventory.get(id).getTradeType();}

    /**Getter for trades
     * @return a hashMap storing in the format <trader, a list of trades(id) the trader has>
     */
    public HashMap<String, List<Integer>> getTrades() {
        return trades;
    }

    /**Getter for weeklyLimit
     * @return the maximum number of trades a trade can have in a week
     */
    public int getWeeklyLimit() {
        return weeklyLimit;
    }

    /**Setter for weeklyLimit
     * @param weeklyLimit the maximum number of trades a trade can have in a week
     */
    public void setWeeklyLimit(int weeklyLimit) {
        this.weeklyLimit = weeklyLimit;
    }

    /**Getter for maxInComplete
     * @return the max number of incomplete trades that a user could have
     */
    public int getMaxInComplete() {
        return maxInComplete;
    }

    /**Setter for maxInComplete
     * @param maxInComplete the max number of incomplete trades that a user could have
     */
    public void setMaxInComplete(int maxInComplete) {
        this.maxInComplete = maxInComplete;
    }

    /**Getter for moreLend
     * @return the number that the trader need to lend before they can borrow
     */
    public int getMoreLend() {
        return moreLend;
    }

    /**Setter for moreLend
     * @param moreLend the number that the trader need to lend before they can borrow
     */
    public void setMoreLend(int moreLend) {
        this.moreLend = moreLend;
    }
}
