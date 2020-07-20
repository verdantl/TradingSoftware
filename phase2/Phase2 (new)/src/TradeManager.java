import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


public class TradeManager {
    private final HashMap<Integer, Trade> tradeInventory;
    private final HashMap<String, List<Integer>> trades;

    public TradeManager(HashMap<Integer, Trade> tradeInventory, HashMap<String, List<Integer>> trades){
        this.tradeInventory = tradeInventory;
        this.trades = trades;
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

    /**create a new trade
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @param isTemporary check whether or not the trade is temporary
     * @param items a list of items needed to be traded
     * @return a new trade
     */
    public Trade createTrade(String initiator, String receiver,
                             boolean isTemporary, List<Integer> items, String tradeType,
                             LocalDate createdDate, boolean hasMeeting){
        Trade trade = new Trade(items, isTemporary, initiator, receiver, createdDate, hasMeeting);
        trade.setTradeType(tradeType);
        return trade;

    }

    // not implement yet, but you can assume it exist
    public void completeTrade(int id, Trader initiator, Trader receiver){}

    // not implement yet, but you can assume it exist
    public boolean exceedWeeklyLimit(String trader, LocalDate createdDate){}

    // not implement yet, but you can assume it exist
    public boolean exceedMaxIncomplete(Trader trader){}


    // not implement yet, but you can assume it exist
    public boolean lackLend(Trader trader){}

    // You may not need this method, in the meetingManager,
    // confirmMeeting return true iff both trader confirm the meeting
    // which means that the meeting is completed
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


    /**get a list of trade's IDs
     * @param user a user who wants to get all his trades
     * @return a list of tradeIDs represents all the trades that the user has
     */
    public List<Integer> getTrades(String user) {
        return this.trades.get(user);
    }
}
