import java.util.HashMap;
import java.util.List;


public class TradeManager {
    private final HashMap<Integer, Trade> trades;

    public TradeManager(HashMap<Integer, Trade> trades){
        this.trades = trades;
    }

    /**return the trade by the id
     * @param id the id of the trade
     * @return the trade with the trade's id
     */
    public Trade getTrade(int id){
        if(!trades.containsKey(id)){
            return null;
        }

        return trades.get(id);
    }

    /**create a new trade
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @param isTemporary check whether or not the trade is temporary
     * @param items a list of items needed to be traded
     * @return a new trade
     */
    public Trade createTrade(String initiator, String receiver,
                             boolean isTemporary, List<Integer> items, String tradeType){
        Trade trade = new Trade(items, isTemporary, initiator, receiver);
        trade.setTradeType(tradeType);
        return trade;

    }

    /**
     * Checks whether the trade with the given id is completed.
     * @param id The trade id
     * @return True iff trade with ID id is completed.
     */
    public boolean isTradeCompleted(int id){
        return trades.get(id).getCompleted();
    }

    /**
     * Returns the given trade's initiator
     * @param id The id of the trade
     * @return the username of the initiator
     */
    public String getTradeInitiator(int id){
        return trades.get(id).getInitiator();
    }

    /**
     * Returns the given trade's receiver
     * @param id The id of the trade
     * @return the username of the receiver
     */
    public String getTradeReceiver(int id){
        return trades.get(id).getInitiator();
    }


}
