package com.example.phase2.phase2;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;


public class TradeManager implements Serializable {
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

    /**
     * Returns a list of username's incomplete trades.
     * @param trades a list of trades that user has (represented in Ids)
     * @return A list of the user's incomplete trades
     */
    public List<Integer> getIncompleteTrades(List<Integer> trades){
        List<Integer> temp = new ArrayList<>();
        for(Integer i: trades){
            if(tradeInventory.get(i).getCompleted()){
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
    public int createTrade(String initiator, String receiver, String tradeType, boolean isPermanent,
                           List<Integer> items){
        Trade trade = new Trade(counter, initiator, receiver, tradeType, isPermanent);
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


    //Returns trade info in {Created trade date, Other trade username}

    /**
     * Returns a string representation of the trade
     * @param username Username of use who's requesting the tradeInformation
     * @param tradeId The id of the trade
     * @return An arraylist of info about the trade.
     */
    public List<String> getTradeInformation(String username, Integer tradeId){
        List<String> temp = new ArrayList<>();
        Trade tempTrade = tradeInventory.get(tradeId);
        temp.add(tempTrade.getCreatedDate().toString());
        if(tempTrade.getInitiator().equals(username)){
            temp.add(tempTrade.getReceiver());
        }
        else{
            temp.add(tempTrade.getInitiator());
        }

        return temp;
    }

    /**
     * Returns a list of the items in the given trade
     * @param tradeId the trade's item
     * @return A list of item ids in the trade
     */
    public List<Integer> getItemIds(int tradeId){
        return tradeInventory.get(tradeId).getItems();
    }
}
