import java.time.LocalDate;
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



    public String confirmTrade(String user, Meeting meeting, Trade trade){
        if(LocalDate.now().isBefore(meeting.getTradeDate())){
            return "Request refused: The trade should not take place before the meeting";
        }else{
            meeting.setConfirm(user, true);
            if(checkBothConfirmed(meeting)){
                meeting.setTradeStatus("Completed");
                completeTrade(trade);
                return "Both user have confirmed the trade happened. Trade is completed.";
            }
            return "You have confirmed the trade happened. Waiting for other user to confirm.";
        }

    }

    private boolean checkBothConfirmed(Meeting meeting){
        boolean bothConfirmed = true;
        for(boolean confirm: meeting.getIsConfirmed().values()){
            if(!confirm){
                bothConfirmed = false;
                break;
            }
        }
        return bothConfirmed;
    }

    private void completeTrade(Trade trade){
        if(trade.getTradeType().equals("OneWayTrade")){
            conductOneWayTrade(trade);
        }else if(trade.getTradeType().equals("TwoWayTrade")){
            conductTwoWayTrade(trade);
        }
    }

    private void conductOneWayTrade(Trade trade){}

    private void conductTwoWayTrade(Trade trade){}

}
