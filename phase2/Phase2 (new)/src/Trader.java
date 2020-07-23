import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trader extends User implements Serializable {
    private boolean frozen, flagged, requestToUnfreeze;
    private int numLent, numBorrowed;
    private final List<Integer> wishlist;
    private final List<Integer> borrowedItems;
    private final List<Integer> trades;

    /**
     * Constructor for a new trader
     * @param username username of the trader
     * @param password password of the trader
     */
    public Trader(String username, String password){
        super(username, password);
        frozen = false;
        flagged = false;
        requestToUnfreeze = false;
        numLent = 0;
        numBorrowed = 0;
        wishlist = new ArrayList<>();
        borrowedItems = new ArrayList<>();
        trades = new ArrayList<>();
    }

    /**
     * Converts the trader to a string representation.
     * @return a string of the admin's username, password, and date created
     */
    @Override
    public String toString(){
        String s = "Trader: "+ super.getUsername()+"\n";
        s += "Joined on: "+ super.getDateCreated()+"\n";
        s += "Frozen: "+frozen+" | Flagged: "+flagged+" | Request to Unfreeze: "+requestToUnfreeze+"\n\n";
        s += "Number of items borrowed: "+numBorrowed+" | Number of items lent: "+numLent;
        return s;
    }

    /**
     * Getter for user's frozen boolean
     * @return whether the account is frozen or not
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Setter for user's frozen boolean
     * @param frozen whether the account is frozen or not
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Getter for user's numLent
     * @return the number of items the user has lent
     */
    public int getNumLent() {
        return numLent;
    }

    /**
     * Setter for user's numLent
     * @param numLent the number of items this user has lent
     */
    public void setNumLent(int numLent) {
        this.numLent = numLent;
    }

    /**
     * Getter for user's numBorrowed
     * @return the number of items the user has borrowed
     */
    public int getNumBorrowed() {
        return numBorrowed;
    }

    /**
     * Setter for user's numBorrowed
     * @param numBorrowed the number of items this user has borrowed
     */
    public void setNumBorrowed(int numBorrowed) {
        this.numBorrowed = numBorrowed;
    }

    /**
     * Getter for user's flagged boolean
     * @return whether the user is flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Setter for user's flagged boolean
     * @param flagged whether the user is flagged
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Whether this user has requested to unfreeze or not.
     * @return true if they have requested to unfreeze, false otherwise.
     */
    public boolean isRequestToUnfreeze() {
        return requestToUnfreeze;
    }

    /**
     * Sets whether this user has requested to unfreeze.
     * @param requestToUnfreeze a boolean representing if the user has requested to unfreeze
     */
    public void setRequestToUnfreeze(boolean requestToUnfreeze) {
        this.requestToUnfreeze = requestToUnfreeze;
    }


    /**
     *
     * @return Trader's wishlist
     */
    public List<Integer> getWishlist() {
        return wishlist;
    }

    /**
     *
     * @return Trader's borrowed items
     */
    public List<Integer> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     * Adds the item to borrowedItems
     * @param id the id of the item to be added
     */
    public void addToBorrowedItems(Integer id){
        this.borrowedItems.add(id);
    }

    /**
     * Adds the item to wishlist
     * @param id the id of the item to be added
     */
    public void addToWishlist(Integer id){
        this.wishlist.add(id);
    }

    /**
     * Removes the item from borrowedItems
     * @param id the id of the item to be removed
     */
    public void removeFromWishlist(Integer id){
        this.wishlist.remove(id);
    }

    /**
     * Removes the item from borrowedItems
     * @param id the id of the item to be removed
     */
    public void removeFromBorrowedItems(Integer id){
        this.borrowedItems.remove(id);
    }

    /**
     * Deletes the item from this trader's records.
     * @param id the id of the item
     */
    public void deleteItem(Integer id){
        wishlist.remove(id);
        borrowedItems.remove(id);

    }

    /**return a list of trades' IDs
     * @return A list of trades' IDs that user has
     */
    public List<Integer> getTrades() {
        return trades;
    }

    /**add a trade to the user's list of trades
     * @param id the id of the trade
     */
    public void addTrades(int id){
        trades.add(id);
    }


}
