import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Trader extends User {
        //private final ArrayList<Item> wantToBorrow,proposedItems, wantToLend, borrowedItems;
        private boolean frozen, flagged, requestToUnfreeze;
        private int numLent, numBorrowed;
        private ArrayList<Trade> trades;


    /**
     * Constructor for when the user is read in
     * @param username The user's username
     * @param password The user's password
     * @param dateCreated   The date the user was created
     * @param trades The user's list of trades
     * @param frozen Whether the user's account is frozen
     * @param flagged Whether this user's account is flagged for review by a moderator
     * @param requestToUnfreeze Whether this user has requested to unfreeze.
     * @param numLent  The number of times the user has lent an item
     * @param numBorrowed  The number of times the user has borrowed an item
     */
    public Trader(String username, String password, String dateCreated,
                   ArrayList<Trade> trades, boolean frozen, boolean flagged, boolean requestToUnfreeze, int numLent, int numBorrowed){
        super(username, password, dateCreated);
//        this.wantToBorrow = wantToBorrow;
//        this.proposedItems = proposedItems;
//        this.wantToLend = wantToLend;
        this.trades=trades;
        this.frozen = frozen;
        this.flagged = flagged;
        this.numBorrowed=numBorrowed;
        this.numLent=numLent;
       // this.borrowedItems = borrowedItems;
        this.requestToUnfreeze = requestToUnfreeze;
    }

    /**
     * Constructor for the trader class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
    public Trader(String username, String password){
        super(username,password);
//        wantToBorrow = new ArrayList<>();
//        proposedItems = new ArrayList<>();
//        wantToLend = new ArrayList<>();
        trades = new ArrayList<>();
        frozen = false;
        numBorrowed= 0;
        numLent=0;
       // borrowedItems = new ArrayList<>();
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
     * Converts the trader to a string representation.
     * @return a string of the admin's username, password, and date created
     */
    @Override
    public String toString() {
        return "Trader{" +
                "username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", dateCreated=" + super.getDateCreated().toString() +
//                ", wishlist='" + wantToBorrow + '\'' +
//                ", lending list=" + wantToLend +
                ", trades='" + trades + '\'' +
                ", frozen='" + frozen + '\'' +
                ", flagged=" + flagged +
                ", numBorrowed=" + numBorrowed +
                ", numLent='" + numLent + '\'' +
                '}';
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
     * Removes the given trade from Trades
     * @param trade a trade from the list of trades
     */
    public void removeFromTrades(Trade trade){this.trades.remove(trade);}



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
     * @param requestToUnfreeze a boolean representing if the user has requested to unfreeze
     */
    public void setRequestToUnfreeze(boolean requestToUnfreeze) {
        this.requestToUnfreeze = requestToUnfreeze;
    }
}
