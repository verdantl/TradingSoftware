import java.time.LocalDate;
import java.util.ArrayList;

public class TradeManager {
    private Trader currentUser;
    private Trade processedTrade;

    /**
     * constructor for the TradeManager
     */
    public TradeManager(){
        this.currentUser = null;
        this.processedTrade = null;
    }

    /**
     * Add a user who wants to request a new trade
     * @param currentUser The user who is currently using this manager
     */
    public void setCurrentUser(Trader currentUser){
        this.currentUser = currentUser;
        this.processedTrade = null;
    }

    /**
     * Add a user who wants to edit a trade
     * @param currentUser The user who is currently using this manager
     * @param trade The trade that the user wants to edit
     */
    public void setCurrentUser(Trader currentUser, Trade trade){
        this.currentUser = currentUser;
        this.processedTrade = trade;
    }

    /** Request to borrow an item from another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     */
    public void requestToBorrow(Trader receiver, String location,
                                LocalDate tradeDate, Item item){
        this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                tradeDate, item, "Borrow");
        currentUser.addToTrades(processedTrade);
        receiver.addToTrades(processedTrade);
    }

    /**Request to lend an item to another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     */
    public void requestToLend(Trader receiver, String location,
                              LocalDate tradeDate, Item item){
        this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                tradeDate, item, "Lend");
        currentUser.addToTrades(processedTrade);
        receiver.addToTrades(processedTrade);
    }

    /**Request to exchange items with another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item1 The item that current user owns
     * @param item2 The item that receiver owns
     */
    public void requestToExchange(Trader receiver, String location,
                                  LocalDate tradeDate, Item item1, Item item2){
        this.processedTrade = new TwoWayTrade(currentUser, receiver, tradeDate,
                location, item1, item2);
        currentUser.addToTrades(processedTrade);
        receiver.addToTrades(processedTrade);
    }

    /**Allow the current user permanently borrows the item
     * @param item The item that the current user wants to borrow
     */
    public void borrow(Item item){
        currentUser.addToBorrowedItems(item);
        processedTrade.getReceiver().removeFromWantToLend(item);
        if(currentUser.getWantToBorrow().contains(item)){
            currentUser.removeFromWantToBorrow(item);
        }
        processedTrade.setPermanent(true);
        currentUser.setNumBorrowed(currentUser.getNumBorrowed() + 1);
        item.setOwner(currentUser);

    }

    /**Allow the current user temporarily borrows the item
     * @param item The item that the current user wants to borrow
     * @param returnDate The date that current user should return the item
     */
    public void borrow(Item item, LocalDate returnDate){
        borrow(item);
        item.setOwner(processedTrade.getReceiver());
        processedTrade.setPermanent(false);
        processedTrade.setReturnDate(returnDate);
    }

    /**Allow the current user permanently lend the item
     * @param item The item that the current user wants to lend
     */
    public void lend(Item item){
        currentUser.removeFromWantToLend(item);
        processedTrade.getReceiver().addToBorrowedItems(item);
        if(processedTrade.getReceiver().getWantToBorrow().contains(item)){
            processedTrade.getReceiver().removeFromWantToBorrow(item);
        }
        processedTrade.setPermanent(true);
        currentUser.setNumLent(currentUser.getNumLent() + 1);
        item.setOwner(processedTrade.getReceiver());
    }

    /**Allow the current user temporarily lend the item
     * @param item The item that the current user wants to lend
     * @param returnDate The date the receiver should return the item
     */
    public void lend(Item item, LocalDate returnDate){
        lend(item);
        item.setOwner(currentUser);
        processedTrade.setPermanent(false);
        processedTrade.setReturnDate(returnDate);
    }

    /**Allow the current user permanently exchanges the items
     * @param item1 The item that current user owns and wants to trade
     * @param item2 The item that the processedTrade's receiver owns and wants to trade
     */
    public void exchange(Item item1, Item item2){
        lend(item1);
        borrow(item2);
        currentUser.setNumLent(currentUser.getNumLent() - 1);
        currentUser.setNumBorrowed(currentUser.getNumBorrowed() - 1);
        processedTrade.setPermanent(true);
    }

    /**Allow the current user temporarily exchanges the items
     * @param item1 The item that current user owns and wants to trade
     * @param item2 The item that the processedTrade's receiver owns and wants to trade
     * @param returnDate The date that two users should return their items back
     */
    public void exchange(Item item1, Item item2, LocalDate returnDate){
        exchange(item1, item2);
        item1.setOwner(currentUser);
        item2.setOwner(processedTrade.getReceiver());
        processedTrade.setPermanent(false);
        processedTrade.setReturnDate(returnDate);
    }






}
