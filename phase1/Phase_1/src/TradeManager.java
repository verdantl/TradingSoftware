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

    /** Request to permanently borrow an item from another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     */
    public void requestToBorrow(Trader receiver, String location,
                                LocalDate tradeDate, Item item){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else if(currentUser.getNumLent() <= currentUser.getNumBorrowed()){
            System.out.println("request refused: Please lend an item first");
        }else{
            this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                    tradeDate, item, "Borrow");
            processedTrade.setPermanent(true);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }

    }

    /** Request to temporarily borrow an item from another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     * @param returnDate The date that the user wants to return the item
     */
    public void requestToBorrow(Trader receiver, String location,
                                LocalDate tradeDate, Item item, LocalDate returnDate){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else if(currentUser.getNumLent() <= currentUser.getNumBorrowed()){
            System.out.println("request refused: Please lend an item first");
        }else{
            this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                    tradeDate, item, "Borrow");
            processedTrade.setReturnDate(returnDate);
            processedTrade.setPermanent(false);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }

    }

    /**Request to permanently lend an item to another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     */
    public void requestToLend(Trader receiver, String location,
                              LocalDate tradeDate, Item item){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else{
            this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                    tradeDate, item, "Lend");
            processedTrade.setPermanent(true);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }

    }

    /**Request to temporarily lend an item to another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     * @param returnDate The date that the user wants to return the item
     */
    public void requestToLend(Trader receiver, String location,
                              LocalDate tradeDate, Item item, LocalDate returnDate){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else{
            this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                    tradeDate, item, "Lend");
            processedTrade.setReturnDate(returnDate);
            processedTrade.setPermanent(false);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }

    }

    /**Request to permanently exchange items with another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item1 The item that current user owns
     * @param item2 The item that receiver owns
     */
    public void requestToExchange(Trader receiver, String location,
                                  LocalDate tradeDate, Item item1, Item item2){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else{
            this.processedTrade = new TwoWayTrade(currentUser, receiver, tradeDate,
                    location, item1, item2);
            processedTrade.setPermanent(true);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }

    }

    /**Request to temporarily exchange items with another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item1 The item that current user owns
     * @param item2 The item that receiver owns
     * @param returnDate The date that the user wants to return the item
     */
    public void requestToExchange(Trader receiver, String location,
                                  LocalDate tradeDate, Item item1, Item item2, LocalDate returnDate){
        if(currentUser.isFrozen()){
            System.out.println("Your account is frozen, you cannot trade");
        }else{
            this.processedTrade = new TwoWayTrade(currentUser, receiver, tradeDate,
                    location, item1, item2);
            processedTrade.setPermanent(false);
            processedTrade.setReturnDate(returnDate);
            currentUser.addToTrades(processedTrade);
            receiver.addToTrades(processedTrade);
        }
    }

    /**Allow the current user borrows the item
     * @param item The item that the trader borrows
     */
    public void borrow(Item item){
        currentUser.addToBorrowedItems(item);
        processedTrade.getReceiver().removeFromWantToLend(item);
        if(currentUser.getWantToBorrow().contains(item)){
            currentUser.removeFromWantToBorrow(item);
        }
        currentUser.setNumBorrowed(currentUser.getNumBorrowed() + 1);
        processedTrade.getReceiver().setNumLent(processedTrade.getReceiver().getNumLent() + 1);

        if(processedTrade.isPermanent()){item.setOwner(currentUser);}

    }

    /**Allow the current user lends the item
     * @param item The item that the trader lends
     */
    public void lend(Item item){
        currentUser.removeFromWantToLend(item);
        processedTrade.getReceiver().addToBorrowedItems(item);
        if(processedTrade.getReceiver().getWantToBorrow().contains(item)){
            processedTrade.getReceiver().removeFromWantToBorrow(item);
        }
        currentUser.setNumLent(currentUser.getNumLent() + 1);
        processedTrade.getReceiver().setNumBorrowed(processedTrade.getReceiver().getNumBorrowed() + 1);

        if(processedTrade.isPermanent()){item.setOwner(processedTrade.getReceiver());}

    }

    /**Allow the current user exchanges the items
     * @param item1 The item that current user owns and wants to trade
     * @param item2 The item that the processedTrade's receiver owns and wants to trade
     */
    public void exchange(Item item1, Item item2){
        lend(item1);
        borrow(item2);

    }

    /**
     * Allow the current user to edit the processedTrade's date and location
     * If both users in processedTrade edit 3 times without agreeing,
     * cancel the Trade
     * @param date the new date for meeting of processedTrade
     * @param location the new location for location of processed Trade
     */
    public void editTradeMeeting(LocalDate date, String location){
        Trader receiver = processedTrade.getReceiver();
        if((processedTrade.getNumberOfEdit(currentUser) + processedTrade.getNumberOfEdit(receiver)) == 6
                && (!processedTrade.getIsAgreed(currentUser) || !processedTrade.getIsAgreed(receiver))){
            processedTrade.setTradeStatus("Cancelled");
        }
        else if(processedTrade.getNumberOfEdit(currentUser) == 3){
            System.out.println("You have run out of edit times and you cannot edit");
        }
        else{
            processedTrade.setTradeDate(date);
            processedTrade.setLocation(location);
            processedTrade.increaseNumberOfEdits(currentUser);
            processedTrade.setIsAgreed(currentUser, false);
            processedTrade.setIsAgreed(receiver, false);
            processedTrade.setTradeStatus("In Progress");
        }
    }

    /**
     * Allow the current user to agree to the processedTrade's meeting
     * Confirm the meeting iff both users have agreed the trade
     */
    public void agreeToMeeting(){
        if(LocalDate.now().isAfter(processedTrade.getTradeDate())){
            System.out.println("request refused: You have past the trade date");
        }else{
            processedTrade.setIsAgreed(currentUser, true);

            if(processedTrade.getIsAgreed(currentUser) && processedTrade.getIsAgreed(processedTrade.getReceiver())){
                processedTrade.setTradeStatus("Agreed, Waiting to be confirmed");
            }
        }
    }

    /**
     * Allow the current user to confirm the processedTrade has took place
     * Complete the processedTrade iff both users have confirmed trade
     */
    public void confirmTrade(){
        if(LocalDate.now().isBefore(processedTrade.getTradeDate())){
            System.out.println("request refused: The trade should not take place before the meeting");
        }else{
            processedTrade.setIsConfirmed(currentUser,true);
            if(processedTrade.getIsConfirmed(currentUser) && processedTrade.getIsConfirmed(processedTrade.getReceiver())){
                processedTrade.setCompleted(true);
                processedTrade.setTradeStatus("Completed");
            }
        }
    }


}
