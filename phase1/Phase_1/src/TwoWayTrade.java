import java.time.LocalDate;


/**
 * An object that represents two way trade between users
 * @author Junhee Jung
 */
public class TwoWayTrade extends Trade {
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
        super(initiator, receiver, location, tradeDate, "Exchange");
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
                "initiator='" + super.getInitiator().toString() + " item: " +item1.toString()+ '\'' +
                ", receiver='" + super.getReceiver().toString() + " item: " +item2.toString()+  '\'' +
                ", tradedType='" + super.getTradeType() + '\''+
                ", tradeDate=" + super.getTradeDate().toString() + '\'' +
                ", location=" + super.getLocation() + '\'' +
                ", permanent=" + super.isPermanent() + '\'' +
                ", tradeStatus=" + super.getTradeStatus()+ '\'' +
                ", isCompleted=" + super.isCompleted() + '\'' +
                ", returnDate=" + super.getReturnDate().toString() + '\'' +
                "} ";
    }
}
