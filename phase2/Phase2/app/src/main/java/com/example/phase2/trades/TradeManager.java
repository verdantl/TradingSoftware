package com.example.phase2.trades;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class TradeManager extends Manager implements Serializable {
    private final HashMap<Integer, Trade> tradeInventory;
    private int counter;


    public TradeManager(HashMap<Integer, Trade> tradeInventory){
        this.tradeInventory = tradeInventory;
        if (tradeInventory.keySet().size() != 0) {
            counter = Collections.max(tradeInventory.keySet()) + 1;
        }
        else{
            counter = 0;
        }
    }

    /**
     * Returns a list of username's incomplete trades.
     * @param trades a list of trades that user has (represented in Ids)
     * @return A list of the user's incomplete trades
     */
    public List<Integer> getIncompleteTrades(List<Integer> trades){
        List<Integer> temp = new ArrayList<>();
        for(Integer i: trades){
            if(!Objects.requireNonNull(tradeInventory.get(i)).getCompleted()){
                temp.add(i);
            }
        }
        return temp;
    }


    /**create a trade
     * @param isPermanent whether or not the trade is permanent
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade
     * @return the trade whether or not the trade is successfully created
     */
    public Integer createTrade(String initiator, String receiver, String tradeType, boolean isPermanent,
                           List<Integer> items){
        Trade trade = new Trade(counter, initiator, receiver, tradeType, isPermanent);
        trade.setCompleted(false);
        trade.setItems(items);
        counter++;
        addToTradeInventory(trade);
        return trade.getId();
    }

    /**add a trade to the tradeInventory
     * @param trade the trade needed to be added in inventory
     */
    public void addToTradeInventory(Trade trade){
        if(!tradeInventory.containsKey(trade.getId())){
            tradeInventory.put(trade.getId(), trade);
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

    /**set the assigned trade with id to be completed
     * @param id the id of the trade
     */
    public void setTradeCompleted(int id){
        tradeInventory.get(id).setCompleted(true);
    }

    /**
     * Returns the given trade's initiator
     * @param id The id of the trade
     * @return the username of the initiator
     */
    //TODO: unused method
    public String getTradeInitiator(int id){
        return tradeInventory.get(id).getInitiator();
    }

    /**
     * Returns the given trade's receiver
     * @param id The id of the trade
     * @return the username of the receiver
     */
    //TODO: unused method
    public String getTradeReceiver(int id){
        return tradeInventory.get(id).getReceiver();
    }

    /**return the given trade's tradeType
     * @param id the id of the trade
     * @return which type the trade is(OneWay or TwoWay)
     */
    public String getTradeType(int id){return tradeInventory.get(id).getTradeType();}

    /**
     * Returns the given trade's dateCreated.
     * @param id the id of the trade in question.
     * @return the date the trade was completed.
     */
    public LocalDate getDateCreated(int id){return tradeInventory.get(id).getCreatedDate();}

    /**
     * Returns the items in the given trade.
     * @param id the id of the trade in question.
     * @return the list of ids corresponding to the items in the trade.
     */
    public List<Integer> getItems(int id){return tradeInventory.get(id).getItems();}

    /**
     * Method to check if the given trade id is permanent.
     * @param id The id of the trade
     * @return true iff trade is permanent.
     */
    public boolean isTradePermanent(int id){
        return Objects.requireNonNull(tradeInventory.get(id)).isPermanent();
    }

    /**
     * Returns the other trader in the trade with the given id
     * @param id The trade id
     * @param username The trader's username
     * @return The other trader's username
     */
    public String getOtherTrader(int id, String username){
        return Objects.requireNonNull(tradeInventory.get(id)).getOtherTrader(username);
    }

    @Override
    public String getIdentifier() {
        return "TradeManager";
    }
}
