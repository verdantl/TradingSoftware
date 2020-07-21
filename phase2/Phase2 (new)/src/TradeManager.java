import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TradeManager {
    private final HashMap<Integer, Trade> tradeInventory;
    private final HashMap<String, List<Integer>> trades;
    private int weeklyLimit;

    public TradeManager(HashMap<Integer, Trade> tradeInventory, HashMap<String, List<Integer>> trades,
                        int weeklyLimit){
        this.tradeInventory = tradeInventory;
        this.trades = trades;
        this.weeklyLimit = weeklyLimit;
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

    /**create a trade
     * @param items a list of items involved in the trade
     * @param isPermanent whether or not the trade is permanent
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @param createdDate the time that the trade is created
     */
    public Trade createTrade(List<Integer> items, boolean isPermanent,
                            String initiator, String receiver, LocalDate createdDate, String tradeType){
        return new Trade(items, isPermanent, initiator, receiver, createdDate, tradeType);
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




    // You may not need this method for a trade having meeting, in the meetingManager,
    // confirmMeeting return true iff both trader confirm the meeting
    // which means that the meeting is completed
    ///**
     //* Checks whether the trade with the given id is completed.
     //* @param id The trade id
     //* @return True iff trade with ID id is completed.
     //*/
    //public boolean isTradeCompleted(int id){
        //return tradeInventory.get(id).getCompleted();
    //}

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
}
