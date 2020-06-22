import java.time.LocalDate;

public class OneWayTrade extends Trade{
    private Trader initiator;
    private Trader receiver;
    private LocalDate tradeDate;
    private String location;
    private boolean isPermanent;
    private boolean isConfirmed;
    private boolean isCompleted;
    private int numberOfEdits;
    private LocalDate returnDate;
    private Item item;
    private String tradeType;

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
        super(initiator, receiver, location, tradeDate);
        this.item = item;
        this.tradeType = tradeType;
    }



    /**
     * @return Returns the String representation of OneWayTrade
     */
    @Override
    public String toString() {
        return "OneWayTrade{" +
                "initiator='" + initiator.toString() + '\'' +
                ", receiver='" + receiver.toString() + '\'' +
                ", tradedItem='" + item.toString() + '\''+
                ", tradedType='" + tradeType + '\''+
                ", tradeDate=" + tradeDate.toString() + '\'' +
                ", location=" + location + '\'' +
                ", permanent=" + isPermanent + '\'' +
                ", isConfirmed=" + isConfirmed + '\'' +
                ", isCompleted=" + isCompleted + '\'' +
                ", numberOfEdits=" + numberOfEdits + '\'' +
                ", returnDate=" + returnDate.toString() + '\'' +
                '}';
    }

    /**
     * getter for the traded item
     * @return the traded item
     */
    public Item getItem(){ return item; }


}