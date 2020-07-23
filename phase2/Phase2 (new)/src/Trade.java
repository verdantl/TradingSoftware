import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Trade implements Serializable {
    private final String initiator;
    private final String receiver;
    private List<Integer> items;
    private final int id;
    private final boolean isPermanent;
    private boolean isCompleted;
    private final String tradeType;
    private final LocalDate createdDate;
    //Instead of having a boolean, we could just make the location store "Online" if it is
    //a no meeting trade
    //even if it is a no meeting, they still need to decide on a date and stuff
//    private boolean hasMeeting;

    /**
     * @param items List of item ids they are going to trade
     * @param isPermanent Determines whether the trade is temporary
     */
    public Trade (List<Integer> items, boolean isPermanent,
                  String initiator, String receiver, LocalDate createdDate, String tradeType, Integer id){
        this.items = items;
        this.isPermanent = isPermanent;
        this.isCompleted = false;
        this.initiator = initiator;
        this.receiver = receiver;
        this.createdDate = createdDate;
        this.tradeType = tradeType;
//        this.hasMeeting = hasMeeting;
        this.id = id;
    }

    //Instead of this long constructor, we can use the core details first (like the initial menu
    //for Android) and then set additional details

    public Trade(Integer id, String initator, String receiver, String tradeType, boolean isPermanent){
        this.id = id;
        this.initiator = initator;
        this.receiver = receiver;
        this.tradeType = tradeType;
        this.isPermanent = isPermanent;
        createdDate = LocalDate.now();
    }

    public String toString(){

        return "-TradeID: " + getId() +
                "\n" +
                "-TradeType: " + getTradeType() +
                "\n" +
                "-Initiator: " + getInitiator() +
                "\n" +
                "-Receiver: " + getReceiver() +
                "\n" +
                "-TradeItems: " + getItems() +
                "\n" +
                "-IsPermanent: " + isPermanent() +
                "\n" +
                "-IsCompleted: " + getCompleted() +
                "\n" +
                "Trade is created on: " + getCreatedDate();
    }

    /**Getter for whether or not the trade is permanent
     * @return whether or not the trade is permanent
     */
    public boolean isPermanent() {
        return isPermanent;
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

}
