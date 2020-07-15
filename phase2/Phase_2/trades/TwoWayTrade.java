import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * An object that represents two way trade between users
 * @author Junhee Jung
 */
public class TwoWayTrade extends Trade implements Comparable<Trade> {
    private Item item1;
    private Item item2;

    /**
     * Constructor for TwoWayTrade
     * @param initiator The user who want to trade their item with another user's item
     * @param receiver The user that the initiator wants to trade with
     * @param tradeDate The date of trade
     * @param location The location where trade will take place
     * @param item1 The initiator's item
     * @param item2 The receiver's item
     */

    public TwoWayTrade(Trader initiator, Trader receiver, LocalDate tradeDate, String location,
                       Item item1, Item item2){
        super(initiator, receiver, location, tradeDate);
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     * Alternative constructor for TwoWayTrade
     * @param initiator The user who wants to trade their item with another user
     * @param receiver The user that the initiator wants to trade with
     * @param location The location of the trade
     * @param tradeDate The date of the trade
     * @param isPermanent Whether the trade is permanent or not
     * @param isCompleted Whether the trade is completed
     * @param returnDate The return date of the trade
     * @param isConfirmed Whether the trade is confirmed
     * @param numberOfEdits The number of edits made to this trade
     * @param isAgreed Whether the traders have agreed to this trade
     * @param tradeStatus the status of the trade
     * @param item1 the item that the initiator wishes to trade
     * @param item2 the item that the receiver wishes to trade
     */
    public TwoWayTrade(Trader initiator, Trader receiver, String location, LocalDate tradeDate, boolean isPermanent, boolean isCompleted,
                       LocalDate returnDate, HashMap<String, Boolean> isConfirmed, HashMap<String, Integer> numberOfEdits,
                       HashMap<String, Boolean> isAgreed, String tradeStatus, Item item1, Item item2){
        super(initiator, receiver, location, tradeDate, isPermanent, isCompleted,
                returnDate, isConfirmed, numberOfEdits, isAgreed, tradeStatus);
        this.item1 = item1;
        this.item2 = item2;
    }

    /**
     *
     * @return Returns initiator's item
     */

    public Item getItem1() {
        return item1;
    }

    /**
     *
     * @return Returns receiver's item
     */

    public Item getItem2(){
        return item2;
    }

    /**
     *
     * @return Returns the String representation of TwoWayTrade
     */
    @Override
    public String toString() {
        return "TwoWayTrade{" +
                "initiator='" + super.getInitiator().getUsername() + " item: " + item1.toString()+ '\'' +
                ", receiver='" + super.getReceiver().getUsername() + " item: " + item2.toString()+  '\'' +
                ", tradeDate=" + super.getTradeDate().toString() + '\'' +
                ", location=" + super.getLocation() + '\'' +
                ", permanent=" + super.isPermanent() + '\'' +
                ", tradeStatus=" + super.getTradeStatus()+ '\'' +
                ", isCompleted=" + super.isCompleted() + '\'' +
                ", returnDate=" + super.returnDateString() + '\'' +
                "} ";
    }

    /**
     * Compares the date of this Trade to another Trade.
     * @param trade The other Trade whose date is going to be compared.
     * @return An integer representing the result of the comparison.
     */
    @Override
    public int compareTo(Trade trade) {
        return super.compareTo(trade);
    }

    /**
     * A getter for the list of items in this trade
     * @return an arraylist of the items in this TwoWayTrade
     */
    @Override
    public ArrayList<Item> getItems() {
        ArrayList<Item> items= new ArrayList<>();
        items.add(item1);
        items.add(item2);
        return items;
    }
}
