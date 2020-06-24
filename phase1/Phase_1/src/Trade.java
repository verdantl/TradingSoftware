import java.time.LocalDate;
import java.util.HashMap;

public abstract class Trade implements Comparable<Trade> {
    private Trader initiator;
    private Trader receiver;
    private LocalDate tradeDate;
    private String location;
    private boolean isPermanent;
    private boolean isCompleted;
    private LocalDate returnDate;
    private String tradeType;
    private HashMap<String, Boolean> isConfirmed;
    private HashMap<String, Integer> numberOfEdits;
    private HashMap<String, Boolean> isAgreed;
    private String tradeStatus;



    /**
     * Default constructor of Trade
     * @param initiator the user who started this trade.
     * @param receiver the user who will receive the item in the trade.
     * @param location the location of the Trade
     * @param tradeDate the date the Trade will occur
     */
    public Trade(Trader initiator, Trader receiver, String location, LocalDate tradeDate, String tradeType){
        this.initiator = initiator;
        this.receiver = receiver;
        this.location = location;
        this.tradeDate = tradeDate;
        isPermanent = false;
        isCompleted = false;
        returnDate = null;
        isConfirmed = new HashMap<>();
        isConfirmed.put(initiator.getUsername(), false);
        isConfirmed.put(receiver.getUsername(), false);
        numberOfEdits = new HashMap<>();
        numberOfEdits.put(initiator.getUsername(), 0);
        numberOfEdits.put(receiver.getUsername(), 0);
        isAgreed = new HashMap<>();
        isAgreed.put(initiator.getUsername(), false);
        isAgreed.put(receiver.getUsername(), false);
        this.tradeStatus = "In Progress";
        this.tradeType = tradeType;
    }

    /**
     * Converts this Trade into String representation.
     * @return this Trade's String representation.
     */
    @Override
    public String toString() {
        return "Trade{" +
                "initiator='" + initiator.toString() + '\'' +
                ", receiver='" + receiver.toString() + '\'' +
                ", tradedType='" + tradeType + '\''+
                ", tradeDate=" + tradeDate.toString() + '\'' +
                ", location=" + location + '\'' +
                ", permanent=" + isPermanent + '\'' +
                ", isCompleted=" + isCompleted + '\'' +
                ", tradeStatus=" + tradeStatus + '\'' +
                ", returnDate=" + returnDate.toString() + '\'' +
                '}';
    }

    /**
     * Compares the date of this Trade to another Trade.
     * @param trade The other Trade whose date is going to be compared.
     * @return An integer representing the result of the comparison.
     */
    public int compareTo(Trade trade){
        if (this.tradeDate.isBefore(trade.tradeDate)){
            return -1;
        }
        else if (this.tradeDate.isAfter(trade.tradeDate)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    /**
     * Getter for initiator
     * @return the Trade's initiator
     */
    public Trader getInitiator() {
        return initiator;
    }

    /**
     * Getter for receiver
     * @return the Trade's receiver
     */
    public Trader getReceiver() {
        return receiver;
    }

    /**
     * Getter for tradeDate
     * @return the Trade's tradeDate
     */
    public LocalDate getTradeDate() {
        return tradeDate;
    }

    /**
     * Getter for location
     * @return the Trade's location
     */
    public String getLocation() {
        return location;
    }

    /**Getter for trade's status
     * @return the trade's status
     */
    public String getTradeStatus() {return tradeStatus;}

    /**Getter for the type of the trade
     * @return the type of the trade
     */
    public String getTradeType() {return tradeType;}

    /**Getter for isConfirmed
     * @param trader the trader involved in this trade
     * @return whether or not this trader confirm the trade
     */
    public boolean getIsConfirmed(Trader trader) {return isConfirmed.get(trader.getUsername());}

    /**Getter for isAgreed
     * @param trader the trader involved in this trade
     * @return whether or not this trader agree the trade
     */
    public boolean getIsAgreed(Trader trader) {return isAgreed.get(trader.getUsername());}

    /**Getter for numberOfEdit
     * @param trader the trader involved in this trade
     * @return the number that this trader has edited this trade
     */
    public int getNumberOfEdit(Trader trader) {return numberOfEdits.get(trader.getUsername());}

    /**
     * Getter for permanent
     * @return whether the Trade is permanent or not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isPermanent() {
        return this.isPermanent;
    }

    /**
     * Getter for isCompleted
     * @return whether the Trade is completed or not
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCompleted() { return isCompleted; }

    /**
     * Getter for returnDate
     * @return the Trade's returnDate
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Setter for initiator
     * @param initiator the Trade's new initiator
     */
    public void setInitiator(Trader initiator) {
        this.initiator = initiator;
    }

    /**
     * Setter for receiver
     * @param receiver the Trade's new receiver
     */
    public void setReceiver(Trader receiver) {
        this.receiver = receiver;
    }

    /**
     * Setter for TradeDate
     * @param tradeDate the Trade's new tradeDate
     */
    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * Setter for location
     * @param location the Trade's new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Setter for permanent
     * @param permanent whether the Trade is permanent or temporary
     */
    public void setPermanent(boolean permanent) {
        this.isPermanent = permanent;
    }


    /**
     * Setter for isCompleted
     * @param completed whether the Trade is completed or not
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Setter for returnDate
     * @param returnDate the Trade's new returnDate
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**Setter for tradeStatus
     * @param tradeStatus the current status of the trade
     */
    public void setTradeStatus(String tradeStatus) {this.tradeStatus = tradeStatus;}

    /**Setter for isConfirmed
     * @param trader the trader who wants to confirm this trade
     * @param confirm the boolean showing whether or not the trader confirm this trade
     */
    public void setIsConfirmed(Trader trader, Boolean confirm) {
        isConfirmed.replace(trader.getUsername(), confirm);
    }

    /**Setter for isAgreed
     * @param trader the trader who wants to agree the trade meeting
     * @param agree the boolean showing whether or not the trader agree this meeting
     */
    public void setIsAgreed(Trader trader, Boolean agree) {
        isAgreed.replace(trader.getUsername(), agree);
    }

    /**increase the number of edits when the user edits the trade
     * @param trader the trader who edit this trade
     */
    public void increaseNumberOfEdits(Trader trader) {
        numberOfEdits.replace(trader.getUsername(), numberOfEdits.get(trader.getUsername()) + 1);
    }
}
