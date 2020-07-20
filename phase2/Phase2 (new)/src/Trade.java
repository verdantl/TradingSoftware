import java.time.LocalDate;
import java.util.List;

public class Trade {
    private final String initiator;
    private final String receiver;
    private List<Integer> items; //list of item ids they are going to trade
    private final int id;
    private static int counter = 0;
    private boolean isPermanent;
    private boolean isCompleted;
    private String tradeType;
    private LocalDate createdDate;
    private boolean hasMeeting;

    /**
     * @param items List of item ids they are going to trade
     * @param isPermanent Determines whether the trade is temporary
     */
    public Trade (List<Integer> items, boolean isPermanent,
                  String initiator, String receiver, LocalDate createdDate, boolean hasMeeting){
        this.items = items;
        this.isPermanent = isPermanent;
        this.isCompleted = false;
        this.initiator = initiator;
        this.receiver = receiver;
        this.createdDate = createdDate;
        this.hasMeeting = hasMeeting;
        id = counter;
        counter++;
    }

    /**Getter for whether or not the trade is permanent
     * @return whether or not the trade is permanent
     */
    public boolean isPermanent() {
        return isPermanent;
    }

    /**Getter for whether or not the trade is permanent
     * @param permanent whether or not the trade is permanent
     */
    public void setPermanent(boolean permanent) {
        isPermanent= permanent;
    }

    /**
     * Getter for id
     * @return Id of the trade
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return List of item ids
     */
    public List<Integer> getItems() {
        return items;
    }

    /**
     * Setter of items
     * @param items List of item ids
     */
    public void setItems(List<Integer> items) {
        this.items = items;
    }

    /**Setter for isCompleted
     * @param isCompleted whether or not the trade is completed
     */
    public void setCompleted(Boolean isCompleted){this.isCompleted = isCompleted;}

    /**Getter for isCompleted
     * @return whether or not the trade is completed
     */
    public boolean getCompleted(){return this.isCompleted;}

    /**Setter for the type of the trade
     * @param tradeType the type of this trade
     */
    public void setTradeType(String tradeType){this.tradeType = tradeType;}

    /**Getter for tradeType
     * @return the type of the trade
     */
    public String getTradeType(){return tradeType;}

    /**
     * Getter for initiator
     * @return the initiator of the trade
     */
    public String getInitiator() {
        return initiator;
    }

    /**
     * Getter for receiver
     * @return the receiver of the trade
     */
    public String getReceiver() {
        return receiver;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isHasMeeting() {
        return hasMeeting;
    }

    public void setHasMeeting(boolean hasMeeting) {
        this.hasMeeting = hasMeeting;
    }
}
