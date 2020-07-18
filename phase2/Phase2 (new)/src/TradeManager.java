import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeManager {
    private HashMap<String, List<Trade>> trades;


    /**
     *
     * @param username Trader's username
     * @return List of trades that the Trader has. If the username does not exist, return null
     */
    public List<Trade> getTrades(String username){
        if (!trades.containsKey(username)){
            return null;
        }
        return trades.get(username);
    }

    /**
     *
     * @param username Trader's username
     * @param tradeId Id of the trade that is being return
     * @return Trade that the Trader has. If the username or the tradeId does not exist, return null
     */
    public Trade getTrade(String username, int tradeId){
        if (!trades.containsKey(username)){
            return null;
        }
        return getTradeWithTradeId(trades.get(username), tradeId);
    }

    private Trade getTradeWithTradeId(List<Trade> listOfTrades, int tradeId){
        for (Trade t: listOfTrades){
            if (t.getId() == tradeId){
                return t;
            }
        }
        return null;
    }

}
