import java.time.LocalDate;

public abstract class Trade {
    private Trader initiator;
    private Trader receiver;
    private LocalDate tradeDate;
    private String location;
    private boolean isPermanent;
    private boolean isConfirmed;
    private boolean isCompleted;
    private int numberOfEdits;
    private LocalDate returnDate;



    /**
     * Default constructor of Trade
     * @param initiator the user who started this trade.
     * @param receiver the user who will receive the item in the trade.
     * @param location the location of the Trade
     * @param tradeDate the date the Trade will occur
     */
    public Trade(Trader initiator, Trader receiver, String location, LocalDate tradeDate){
        this.initiator = initiator;
        this.receiver = receiver;
        this.location = location;
        this.tradeDate = tradeDate;
        this.isPermanent = false;
        isConfirmed = false;
        isCompleted = false;
        numberOfEdits = 0;
        returnDate = null;
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
     * Increase numberOfEdits by one every time an edit occurs
     */
    public void increaseEdits(){
        numberOfEdits += 1;
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

    /**
     * Getter for permanent
     * @return whether the Trade is permanent or not
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**
     * Getter for isConfirmed
     * @return whether the Trade is confirmed or not
     */
    public boolean isConfirmed() {
        return isConfirmed;
    }

    /**
     * Getter for isCompleted
     * @return whether the Trade is completed or not
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Getter for numberOfEdits
     * @return the number of edits for the Trade
     */
    public int getNumberOfEdits() {
        return numberOfEdits;
    }

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
     * Setter for isConfirmed
     * @param confirmed whether the Trade is confirmed or not
     */
    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
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
}
