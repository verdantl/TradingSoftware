import java.util.List;

public class Trade {
    private final String initiator;
    private final String receiver;
    private List<Integer> items; //list of item ids they are going to trade
    private final int id;
    private static int counter = 0;
    private boolean isTemporary;
    private boolean isCompleted;
    private String tradeType;

    /**
     * @param items List of item ids they are going to trade
     * @param isTemporary Determines whether the trade is temporary
     */
    public Trade (List<Integer> items, boolean isTemporary, String initiator, String receiver){
        this.items = items;
        this.isTemporary = isTemporary;
        this.isCompleted = false;
        this.initiator = initiator;
        this.receiver = receiver;
        id = counter;
        counter++;
    }

    /**Getter for whether or not the trade is temporary
     * @return whether or not the trade is temporary
     */
    public boolean isTemporary() {
        return isTemporary;
    }

    /**Getter for whether or not the trade is temporary
     * @param temporary whether or not the trade is temporary
     */
    public void setTemporary(boolean temporary) {
        isTemporary = temporary;
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

    public String getInitiator() {
        return initiator;
    }

    public String getReceiver() {
        return receiver;
    }
}
