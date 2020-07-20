import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trader extends User {
    private boolean frozen, flagged, requestToUnfreeze;
    private int numLent, numBorrowed;
    private List<Integer> tradeIds;
    private List<Item> wishlist;
    private List<Item> borrowedItems;
    private List<Item> proposedItems;
    private List<Item> approvedItems;


    /**
     * Constructor for when the user is read in
     * @param username The user's username
     * @param password The user's password
     * @param dateCreated   The date the user was created
     * @param ids The user's list of trade ids
     * @param frozen Whether the user's account is frozen
     * @param flagged Whether this user's account is flagged for review by a moderator
     * @param requestToUnfreeze Whether this user has requested to unfreeze.
     * @param numLent  The number of times the user has lent an item
     * @param numBorrowed  The number of times the user has borrowed an item
     */

    public Trader(String username, String password, String dateCreated, List<Integer> ids, boolean frozen,
                  boolean flagged, boolean requestToUnfreeze, int numLent, int numBorrowed){
        super(username, password, dateCreated);

        this.tradeIds = ids;
        this.frozen = frozen;
        this.flagged = flagged;
        this.numBorrowed=numBorrowed;
        this.numLent=numLent;
        this.requestToUnfreeze = requestToUnfreeze;
    }

    /**
     * Constructor for the trader class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
    public Trader(String username, String password){
        super(username,password);

        tradeIds = new ArrayList<>();
        frozen = false;
        numBorrowed= 0;
        numLent=0;
//        this.wishlist = wishlist;
//        this.borrowedItems = borrowedItems;
//        this.approvedItems = approvedItems;
//        this.proposedItems = proposedItems;
    }

    /**
     * Adds the given trade to trades, ordering by tradeDate.
     * @param id an id of the trade that the user has made
     */
    public void addToTradeIds(int id){
        tradeIds.add(id);
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
        s += "\nInventory\n";

        s += "Wishlist:\n";
        s += formatItems(wishlist)+"\n";
        s += "Approved Items:\n";
        s += formatItems(approvedItems)+"\n";
        s += "Borrowed Items:\n";
        s += formatItems(borrowedItems)+"\n";
        s += "Proposed Items:\n";
        s += formatItems(proposedItems);

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
     * Getter for user's tradeIds
     * @return an arraylist of the tradeIds the user has made
     */
    public List<Integer> getTradeIds() {
        return tradeIds;
    }

    /**
     * Setter for user's tradeIds
     * @param tradeIds an arraylist of the trade Ids the user has made
     */
    public void setTradeIds(List<Integer> tradeIds) {
        this.tradeIds = tradeIds;
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
     * Removes the given trade id from tradeIds
     * @param id an Id of trade from the list of trade ids
     */
    public void removeFromTrades(int id){
        tradeIds.remove(id);
    }

//
//    /**
//     * @return Return the number of transactions that are incomplete
//     */
//    public int getNumIncompleteTransactions(){
//        int numIncomplete = 0;
//        for(Trade trade: trades){
//            if (trade.getTradeDate().isBefore(LocalDate.now()) && !trade.getIsConfirmed(trade.getInitiator()) &&
//                    !trade.getIsConfirmed(trade.getReceiver())){
//                numIncomplete++;
//            }
//        }
//
//        return numIncomplete;
//    }

//    /**
//     * @return Return the number of transactions in this week
//     */
//    public int getNumWeeklyTransactions(){
//        int numTransactions = 0;
//
//        for(Trade trade: trades){
//            //code goes here
//        }
//
//        return numTransactions;
//    }

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
    public List<Item> getWishlist() {
        return wishlist;
    }

    /**
     *
     * @return Trader's borrowed items
     */

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     *
     * @return Trader's items
     */

    public List<Item> getApprovedItems() {
        return approvedItems;
    }

    /**
     *
     * @return Trader's items waiting to be approved
     */

    public List<Item> getProposedItems() {
        return proposedItems;
    }

//    /**
//     *
//     * @param approvedItems Trader's items
//     */
//
//    public void setApprovedItems(List<Item> approvedItems) {
//        this.approvedItems = approvedItems;
//    }
//
//    /**
//     *
//     * @param borrowedItems Items that the Trader borrowed
//     */
//
//    public void setBorrowedItems(List<Item> borrowedItems) {
//        this.borrowedItems = borrowedItems;
//    }
//
//    /**
//     *
//     * @param proposedItems Items that the Trader prposed
//     */
//
//    public void setProposedItems(List<Item> proposedItems) {
//        this.proposedItems = proposedItems;
//    }
//
//    /**
//     *
//     * @param wishlist Trader's wishlist
//     */
//
//    public void setWishlist(List<Item> wishlist) {
//        this.wishlist = wishlist;
//    }

    private String formatItems(List<Item> items){
        String s = "";
        for (Item i: items){
            s += i.toString()+"\n";
        }
        return s;

    }

    /**
     * Adds the item to borrowedItems
     * @param item the item to be added
     */
    public void addToBorrowedItems(Item item){
        this.borrowedItems.add(item);
    }
    /**
     * Adds the item to approvedItems
     * @param item the item to be added
     */
    public void addToApprovedItems(Item item){
        this.approvedItems.add(item);
    }
    /**
     * Adds the item to proposedItems
     * @param item the item to be added
     */
    public void addProposedItems(Item item){
        this.proposedItems.add(item);
    }
    /**
     * Adds the item to wishlist
     * @param item the item to be added
     */
    public void addToWishlist(Item item){
        this.wishlist.add(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromProposedItems(Item item){
        this.proposedItems.remove(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromWishlist(Item item){
        this.wishlist.remove(item);
    }
    /**
     * Removes the item from approvedItems
     * @param item the item to be removed
     */
    public void removeFromApprovedItems(Item item){
        this.approvedItems.remove(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromBorrowedItems(Item item){
        this.borrowedItems.remove(item);
    }

    /**
     * Approves or rejects all of the items in proposed items
     * @param approved Whether or not the items should be approved or not
     */
    public void approveAllItems(boolean approved){
        if (approved) {
            approvedItems.addAll(proposedItems);
        }
        proposedItems.clear();
    }
}
