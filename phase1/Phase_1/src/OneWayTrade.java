import java.time.LocalDate;

public class OneWayTrade extends Trade{
    private Item item;


    /**
     * Default constructor of OneWayTrade
     * @param initiator the user who started this trade.
     * @param receiver the user who will receive the item in the trade.
     * @param location the location of the Trade
     * @param tradeDate the date the Trade will occur
     * @param item the item that initiator wants to trade
     * @param tradeType store what type the trade is (lend or borrow)
     */
    public OneWayTrade(Trader initiator, Trader receiver, String location,
                       LocalDate tradeDate, Item item, String tradeType){
        super(initiator, receiver, location, tradeDate, tradeType);
        this.item = item;
    }



    /**
     * @return Returns the String representation of OneWayTrade
     */
    @Override
    public String toString() {
        return "OneWayTrade{" +
                "initiator='" + super.getInitiator().toString() + '\'' +
                ", receiver='" + super.getReceiver().toString() + '\'' +
                ", tradedItem='" + item.toString() + '\''+
                ", tradedType='" + super.getTradeType() + '\''+
                ", tradeDate=" + super.getTradeDate().toString() + '\'' +
                ", location=" + super.getLocation()  + '\'' +
                ", permanent=" + super.isPermanent() + '\'' +
                ", isCompleted=" + super.isCompleted()  + '\'' +
                ", tradeStatus=" + super.getTradeStatus() + '\'' +
                ", returnDate=" + super.getReturnDate().toString()+ '\'' +
                '}';
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
     * getter for the traded item
     * @return the traded item
     */
    public Item getItem(){ return item; }
}