import java.util.List;

public class Trade {
    private String initiator;
    private String receiver;
    private List<String> items; //list of item ids they are going to trade
    private int id;
    private static int counter = 0;
    private boolean isPermanent;

    /**
     *
     * @param init Initiator's username
     * @param rec Receiver's username
     * @param items List of item ids they are going to trade
     * @param isPermanent Determines whether the trade is permanent
     */
    public Trade (String init, String rec, List<String> items, boolean isPermanent){
        initiator = init;
        receiver = rec;
        this.items = items;
        this.isPermanent = isPermanent;
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
        isPermanent = permanent;
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
    public List<String> getItems() {
        return items;
    }

    /**
     * Getter for initiator
     * @return Initiator's username
     */
    public String getInitiator() {
        return initiator;
    }

    /**
     * Getter for receiver
     * @return Receiver's username
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Setter for initiator
     * @param initiator Initiator's username
     */
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    /**
     * Setter of items
     * @param items List of item ids
     */
    public void setItems(List<String> items) {
        this.items = items;
    }

    /**
     * Setter for receiver
     * @param receiver Receiver's username
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}
