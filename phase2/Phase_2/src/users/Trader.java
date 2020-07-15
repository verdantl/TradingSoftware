package users;

import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDate;

public class Trader extends User {
        private ArrayList<Item> wantToBorrow,proposedItems, wantToLend, borrowedItems;
        private boolean frozen, flagged, requestToUnfreeze;
        private int numLent, numBorrowed;
        private ArrayList<Trade> trades;

    /**
     * Constructor for when the user is read in
     * @param username The user's username
     * @param password The user's password
     * @param dateCreated   The date the user was created
     * @param wantToBorrow  The user's wishlist
     * @param proposedItems The user's items that have to be approved
     * @param wantToLend The user's items that they can lend
     * @param trades The user's list of trades
     * @param borrowedItems The user's list of items they're borrowing.
     * @param frozen Whether the user's account is frozen
     * @param flagged Whether this user's account is flagged for review by a moderator
     * @param requestToUnfreeze Whether this user has requested to unfreeze.
     * @param numLent  The number of times the user has lent an item
     * @param numBorrowed  The number of times the user has borrowed an item
     */
    public Trader(String username, String password, String dateCreated, ArrayList<Item> wantToBorrow,
                  ArrayList<Item> proposedItems, ArrayList<Item> wantToLend, ArrayList<Trade> trades,
                  ArrayList<Item> borrowedItems, boolean frozen, boolean flagged, boolean requestToUnfreeze, int numLent, int numBorrowed){
        super(username, password, dateCreated);
        this.wantToBorrow = wantToBorrow;
        this.proposedItems = proposedItems;
        this.wantToLend = wantToLend;
        this.trades=trades;
        this.frozen = frozen;
        this.flagged = flagged;
        this.numBorrowed=numBorrowed;
        this.numLent=numLent;
        this.borrowedItems = borrowedItems;
        this.requestToUnfreeze = requestToUnfreeze;
    }

    /**
     * Constructor for the trader class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
    public Trader(String username, String password){
        super(username,password);
        wantToBorrow = new ArrayList<>();
        proposedItems = new ArrayList<>();
        wantToLend = new ArrayList<>();
        trades = new ArrayList<>();
        frozen = false;
        numBorrowed= 0;
        numLent=0;
        borrowedItems = new ArrayList<>();
    }

    /**
     * Converts the trader to a string representation.
     * @return a string of the admin's username, password, and date created
     */
    @Override
    public String toString() {
        return "Trader{" +
                "username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", dateCreated=" + super.getDateCreated().toString() +
                ", wishlist='" + wantToBorrow + '\'' +
                ", lending list=" + wantToLend +
                ", trades='" + trades + '\'' +
                ", frozen='" + frozen + '\'' +
                ", flagged=" + flagged +
                ", numBorrowed=" + numBorrowed +
                ", numLent='" + numLent + '\'' +
                '}';
    }

    /**
     * Getter for user's wantToBorrow
     * @return an arraylist of the user's wishlist
     */
    public ArrayList<Item> getWantToBorrow() {
        return wantToBorrow;
    }

    /**
     * Setter for user's wantToBorrow
     * @param wantToBorrow an arraylist of the user's wishlist
     */
    public void setWantToBorrow(ArrayList<Item> wantToBorrow) {
        this.wantToBorrow = wantToBorrow;
    }

    /**
     * Getter for user's proposedItems
     * @return an arraylist of the items that the user wishes to lend that need to be approved
     */
    public ArrayList<Item> getProposedItems() {
        return proposedItems;
    }

    /**
     * Setter for user's proposedItems
     * @param proposedItems an arraylist of the items the user wishes to lend that need to be approved
     */
    public void setProposedItems(ArrayList<Item> proposedItems) {
        this.proposedItems = proposedItems;
    }

    /**
     * Getter for user's wantToLend
     * @return an arraylist of the user's lending list
     */
    public ArrayList<Item> getWantToLend() {
        return wantToLend;
    }

    /**
     * Setter for user's wantToLend
     * @param wantToLend an arraylist representing a lending list
     */
    public void setWantToLend(ArrayList<Item> wantToLend) {
        this.wantToLend = wantToLend;
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
     * Getter for user's trades
     * @return an arraylist of the trades the user has made
     */
    public ArrayList<Trade> getTrades() {
        return trades;
    }

    /**
     * Setter for user's trades
     * @param trades an arraylist of the trades the user has made
     */
    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
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
     * Adds the given item to proposedItems
     * @param item an item that the user wishes to add to the approval list
     */
    public void addToProposedItems(Item item){
            this.proposedItems.add(item);
    }

    /**
     * Adds the given item to wantToLend
     * @param item an item that will be added to the lending list
     */
    public void addToWantToLend(Item item){
            this.wantToLend.add(item);
    }

    /**
     * Adds the given trade to trades, ordering by tradeDate.
     * @param trade a trade that the user has made
     */
    public void addToTrades(Trade trade){
        this.trades.add(trade);
        Collections.sort(trades);
    }

    /**
     * Adds an item to wantToBorrow
     * @param item an item that will be added to the wishlist
     */
    public void addToWantToBorrow(Item item){
            this.wantToBorrow.add(item);
    }

    /**
     * Removes the given item from wantToBorrow
     * @param item an item from the wishlist
     */
    public void removeFromWantToBorrow(Item item){
            this.wantToBorrow.remove(item);
    }

    /**
     * Removes the given item from wantToLend
     * @param item an item from the lending list
     */
    public void removeFromWantToLend(Item item){
            this.wantToLend.remove(item);
    }

    /**
     * Removes the given item from proposedItems
     * @param item an item from the list of proposed items for approval
     */
    public void removeFromProposedItems(Item item){
            this.proposedItems.remove(item);
    }

    /**
     * Removes the given trade from Trades
     * @param trade a trade from the list of trades
     */
    public void removeFromTrades(Trade trade){this.trades.remove(trade);}

    /**
     * Getter for user's borrowedItems
     * @return an arraylist of the items the user has borrowed
     */
    public ArrayList<Item> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     * Setter for user's borrowedItems
     * @param borrowedItems an arraylist of the items the user has borrowed
     */
    public void setBorrowedItems(ArrayList<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    /**
     * Adds the given item to the user's borrowedItems
     * @param item an item that the user has borrowed
     */
    public void addToBorrowedItems(Item item){
        this.borrowedItems.add(item);
    }

    /**
     * Removes the given item from the user's borrowedItems
     * @param item an item from the user's list of borrowed items
     */
    public void removeFromBorrowedItems(Item item){
        this.borrowedItems.remove(item);
    }

    /**
     * @return Return the number of transactions that are incomplete
     */
    public int getNumIncompleteTransactions(){
        int numIncomplete = 0;
        for(Trade trade: trades){
            if (trade.getTradeDate().isBefore(LocalDate.now()) && !trade.getIsConfirmed(trade.getInitiator()) &&
                    !trade.getIsConfirmed(trade.getReceiver())){
                numIncomplete++;
            }
        }

        return numIncomplete;
    }

    /**
     * @return Return the number of transactions in this week
     */
    public int getNumWeeklyTransactions(){
        int numTransactions = 0;

        for(Trade trade: trades){
            //code goes here
        }

        return numTransactions;
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
     * @param requestToUnfreeze
     */
    public void setRequestToUnfreeze(boolean requestToUnfreeze) {
        this.requestToUnfreeze = requestToUnfreeze;
    }
}
