import java.util.ArrayList;

public class Trader extends User {
        private ArrayList<Item> wantToBorrow,proposedItems, wantToLend, borrowedItems;
        private boolean frozen, flagged;
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
     * @param frozen Whether the user's account is frozen
     * @param flagged Whether this user's account is flagged for review by a moderator
     * @param numLent  The number of times the user has lent an item
     * @param numBorrowed  The number of times the user has borrowed an item
     */
        public Trader(String username, String password, String dateCreated, ArrayList<Item> wantToBorrow, ArrayList<Item> proposedItems, ArrayList<Item> wantToLend, ArrayList<Trade> trades,ArrayList<Item> borrowedItems, boolean frozen, boolean flagged, int numLent, int numBorrowed){
            super(username, password, dateCreated);
            this.wantToBorrow = wantToBorrow;
            this.proposedItems = proposedItems;
            this.wantToLend = wantToLend;
            this.trades=trades;
            this.frozen = frozen;
            this.numBorrowed=numBorrowed;
            this.numLent=numLent;
            this.flagged = flagged;
            this.borrowedItems = borrowedItems;
        }

    /**
     * Constructor for the trader class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
        public Trader(String username, String password){
            super(username,password);
            this.wantToBorrow = new ArrayList<Item>();
            this.proposedItems = new ArrayList<Item>();;
            this.wantToLend = new ArrayList<Item>();;
            this.trades = new ArrayList<Trade>();
            this.frozen = false;
            this.numBorrowed= 0;
            this.numLent=0;
            this.borrowedItems = new ArrayList<Item>();
        }

    /**
     * Getter for user's wantToBorrow
     * @return
     */
    public ArrayList<Item> getWantToBorrow() {
        return wantToBorrow;
    }

    /**
     * Setter for user's wantToBorrow
     * @param wantToBorrow
     */
    public void setWantToBorrow(ArrayList<Item> wantToBorrow) {
        this.wantToBorrow = wantToBorrow;
    }

    /**
     * Getter for user's proposedItems
     * @return
     */
    public ArrayList<Item> getProposedItems() {
        return proposedItems;
    }

    /**
     * Setter for user's proposedItems
     * @param proposedItems
     */
    public void setProposedItems(ArrayList<Item> proposedItems) {
        this.proposedItems = proposedItems;
    }

    /**
     * Getter for user's wantToLend
     * @return
     */
    public ArrayList<Item> getWantToLend() {
        return wantToLend;
    }

    /**
     * Setter for user's wantToLend
     * @param wantToLend
     */
    public void setWantToLend(ArrayList<Item> wantToLend) {
        this.wantToLend = wantToLend;
    }

    /**
     * Getter for user's frozen boolean
     * @return
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Setter for user's frozen boolean
     * @param frozen
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Getter for user's numLent
     * @return
     */
    public int getNumLent() {
        return numLent;
    }

    /**
     * Setter for user's numLent
     * @param numLent
     */
    public void setNumLent(int numLent) {
        this.numLent = numLent;
    }

    /**
     * Getter for user's numBorrowed
     * @return
     */
    public int getNumBorrowed() {
        return numBorrowed;
    }

    /**
     * Setter for user's numBorrowed
     * @param numBorrowed
     */
    public void setNumBorrowed(int numBorrowed) {
        this.numBorrowed = numBorrowed;
    }

    /**
     * Getter for user's trades
     * @return
     */
    public ArrayList<Trade> getTrades() {
        return trades;
    }

    /**
     * Setter for user's trades
     * @param trades
     */
    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    /**
     * Getter for user's flagged boolean
     * @return
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Setter for user's flagged boolean
     * @param flagged
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Adds the given item to proposedItems
     * @param item
     */
    public void addToProposedItems(Item item){
            this.proposedItems.add(item);
    }

    /**
     * Adds the given item to wantToLend
     * @param item
     */
    public void addToWantToLend(Item item){
            this.wantToLend.add(item);
    }

    /**
     * Adds the given trade to trades
     * @param trade
     */
    public void addToTrades(Trade trade){
            this.trades.add(trade);
    }

    /**
     * Adds an item to wantToBorrow
     * @param item
     */
    public void addToWantToBorrow(Item item){
            this.wantToBorrow.add(item);
    }

    /**
     * Removes the given item from wantToBorrow
     * @param item
     */
    public void removeFromWantToBorrow(Item item){
            this.wantToBorrow.remove(item);
    }

    /**
     * Removes the given item from wantToLend
     * @param item
     */
    public void removeFromWantToLend(Item item){
            this.wantToLend.remove(item);
    }

    /**
     * Removes the given item from proposedItems
     * @param item
     */
    public void removeFromProposedItems(Item item){
            this.proposedItems.remove(item);
    }

    /**
     * Removes the given trade from Trades
     * @param trade
     */
    public void removeFromTrades(Trade trade){this.trades.remove(trade);}

    /**
     * Setter for user's borrowedItems
     * @return
     */
    public ArrayList<Item> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     * Getter for user's borrowedItems
     * @param borrowedItems
     */
    public void setBorrowedItems(ArrayList<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    /**
     * Adds the given item to the user's borrowedItems
     * @param item
     */
    public void addToBorrowedItems(Item item){
        this.borrowedItems.add(item);
    }

    /**
     * Removes the given item from the user's borrowedItems
     * @param item
     */
    public void removeFromBorrowedItems(Item item){
        this.borrowedItems.remove(item);
    }
}
