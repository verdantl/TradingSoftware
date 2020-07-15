import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class TradeManager {
    private Trader currentUser;
    private Trade processedTrade;
    private int limitOfTradesPerWeek;
    private int maxIncomplete;
    private int moreLendNeeded;

    /**
     * constructor for the TradeManager
     */
    public TradeManager(int limitOfTradesPerWeek, int moreLendNeeded, int maxIncomplete){
        this.limitOfTradesPerWeek = limitOfTradesPerWeek;
        this.moreLendNeeded = moreLendNeeded;
        this.maxIncomplete = maxIncomplete;
    }

    /**
     * Add a user who wants to request a new trade
     * @param currentUser The user who is currently using this manager
     */
    public void setCurrentUser(Trader currentUser){
        this.currentUser = currentUser;
        this.processedTrade = null;
        if(currentUser.getNumIncompleteTransactions() >= maxIncomplete){
            currentUser.setFlagged(true);
        }
    }

    /**
     * Add a user who wants to edit a trade
     * @param currentUser The user who is currently using this manager
     * @param trade The trade that the user wants to edit
     */
    public void setCurrentUser(Trader currentUser, Trade trade){
        this.currentUser = currentUser;
        this.processedTrade = trade;
        if(currentUser.getNumIncompleteTransactions() >= maxIncomplete){
            currentUser.setFlagged(true);
        }
    }

    /** Request to permanently borrow an item from another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     * @return the int representation of the trade's status
     */
    public int requestToBorrow(Trader receiver, String location,
                                LocalDate tradeDate, Item item){
        this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                tradeDate, item);
        currentUser.addToTrades(processedTrade);

        if(!isValid()){
            currentUser.removeFromTrades(processedTrade);
            return 1;
        }else if(currentUser.getTrades().size() == 0
                || (currentUser.getNumLent() - currentUser.getNumBorrowed()) < moreLendNeeded){
            currentUser.removeFromTrades(processedTrade);
            return 2;
        }else{
            processedTrade.setPermanent(true);
            receiver.addToTrades(processedTrade);
            return 3;
        }

    }

    /** Request to temporarily borrow an item from another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item The item that current user wants to borrow in the trade
     * @param returnDate The date that the user wants to return the item
     * @return the int representation of the trade's status
     */
    public int requestToBorrow(Trader receiver, String location,
                                LocalDate tradeDate, Item item, LocalDate returnDate){
        this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                tradeDate, item);
        currentUser.addToTrades(processedTrade);

        if(!isValid()){
            currentUser.removeFromTrades(processedTrade);
            return 1;
        }else if(currentUser.getTrades().size() == 0
                || (currentUser.getNumLent() - currentUser.getNumBorrowed()) < moreLendNeeded){
            currentUser.removeFromTrades(processedTrade);
            return 2;
        }else{
            processedTrade.setReturnDate(returnDate);
            processedTrade.setPermanent(false);
            receiver.addToTrades(processedTrade);
            return 3;
        }

    }
    //Since A borrows an item from B also means that B lends the item to A,
    // we don't need A to explicitly request a lend. So i comment out this method
    ///**Request to permanently lend an item to another user
     //* @param receiver The user who receives the request from the  current user
     //* @param location The location that current user wants the trade to take place in
     //* @param tradeDate The date that current user wants the trade to happen
     //* @param item The item that current user wants to borrow in the trade
     //*/
    //public void requestToLend(Trader receiver, String location,
                              //LocalDate tradeDate, Item item){
        //this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                //tradeDate, item);
        //processedTrade.setPermanent(true);
        //currentUser.addToTrades(processedTrade);
        //receiver.addToTrades(processedTrade);
    //}

    ///**Request to temporarily lend an item to another user
     //* @param receiver The user who receives the request from the  current user
     //* @param location The location that current user wants the trade to take place in
     //* @param tradeDate The date that current user wants the trade to happen
     //* @param item The item that current user wants to borrow in the trade
     //* @param returnDate The date that the user wants to return the item
     //*/
    //public void requestToLend(Trader receiver, String location,
                              //LocalDate tradeDate, Item item, LocalDate returnDate){
            //this.processedTrade = new OneWayTrade(currentUser, receiver, location,
                    //tradeDate, item);
            //processedTrade.setReturnDate(returnDate);
            //processedTrade.setPermanent(false);
            //currentUser.addToTrades(processedTrade);
            //receiver.addToTrades(processedTrade);


    //}

    /**Request to permanently exchange items with another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item1 The item that current user owns
     * @param item2 The item that receiver owns
     * @return the int representation of the trade's status
     */
    public int requestToExchange(Trader receiver, String location,
                                  LocalDate tradeDate, Item item1, Item item2){
        this.processedTrade = new TwoWayTrade(currentUser, receiver, tradeDate,
                location, item1, item2);
        currentUser.addToTrades(processedTrade);

        if(!isValid()){
            currentUser.removeFromTrades(processedTrade);
            return 1;
        }
        else{
            processedTrade.setPermanent(true);
            receiver.addToTrades(processedTrade);
            return 3;
        }
    }

    /**Request to temporarily exchange items with another user
     * @param receiver The user who receives the request from the  current user
     * @param location The location that current user wants the trade to take place in
     * @param tradeDate The date that current user wants the trade to happen
     * @param item1 The item that current user owns
     * @param item2 The item that receiver owns
     * @param returnDate The date that the user wants to return the item
     * @return the int representation of the trade's status
     */
    public int requestToExchange(Trader receiver, String location,
                                  LocalDate tradeDate, Item item1, Item item2, LocalDate returnDate){
        this.processedTrade = new TwoWayTrade(currentUser, receiver, tradeDate,
                location, item1, item2);
        currentUser.addToTrades(processedTrade);
        if(!isValid()){
            currentUser.removeFromTrades(processedTrade);
            return 1;
        }
        else{
            processedTrade.setPermanent(false);
            processedTrade.setReturnDate(returnDate);
            receiver.addToTrades(processedTrade);
            return 3;
        }
    }

    /**Allow the initiator borrows the item while the receiver lends the item
     * @param item The item that the initiator borrows
     * @param initiator The initiator of the processed trade
     * @param receiver The receiver of the processed trade
     */
    private void borrow(Item item, Trader initiator, Trader receiver){
        initiator.addToBorrowedItems(item);
        receiver.removeFromWantToLend(item);
        if(initiator.getWantToBorrow().contains(item)){
            initiator.removeFromWantToBorrow(item);
        }

        initiator.setNumBorrowed(initiator.getNumBorrowed() + 1);
        receiver.setNumLent(receiver.getNumLent() + 1);

        if(processedTrade.isPermanent()){item.setOwner(initiator);}
    }

    /**Allow the initiator exchanges the items
     * @param item1 The item that initiator owns and wants to trade
     * @param item2 The item that the receiver owns and wants to trade
     */
    private void exchange(Item item1, Item item2){
        Trader initiator = processedTrade.getInitiator();
        Trader receiver = processedTrade.getReceiver();
        borrow(item2,initiator,receiver);
        borrow(item1,receiver,initiator);
    }

    /**
     * Allow the current user to edit the processedTrade's date and location
     * If both users in processedTrade edit 3 times without agreeing,
     * cancel the Trade
     * @param date the new date for meeting of processedTrade
     * @param location the new location for location of processed Trade
     */
    public String editTradeMeeting(LocalDate date, String location){
        processedTrade.setTradeDate(date);
        if(!isValid()){
            return "You have reached the trade limit in that week, please choose another week";
        }

        Trader receiver = processedTrade.getReceiver();
        if((processedTrade.getNumberOfEdit(currentUser) + processedTrade.getNumberOfEdit(receiver)) == 6
                && (!processedTrade.getIsAgreed(currentUser) || !processedTrade.getIsAgreed(receiver))){
            processedTrade.setTradeStatus("Cancelled");
            return "The trade is cancelled due to both users reaching a limit of editing meeting 3 times.";
        }
        else if(processedTrade.getNumberOfEdit(currentUser) == 3){
            return "You have run out of edit times and you cannot edit";
        }
        else{
            processedTrade.setTradeDate(date);
            processedTrade.setLocation(location);
            processedTrade.increaseNumberOfEdits(currentUser);
            processedTrade.setIsAgreed(processedTrade.getInitiator(), false);
            processedTrade.setIsAgreed(receiver, false);
            processedTrade.setTradeStatus("In Progress");
            return "New date and location has been set. Trade in Progress";
        }
    }

    /**
     * Allow the current user to agree to the processedTrade's meeting
     * Confirm the meeting iff both users have agreed the trade
     */
    public String agreeToMeeting(){
        if(LocalDate.now().isAfter(processedTrade.getTradeDate())){
            return "Request refused: You have past the trade date";
        }else{
            processedTrade.setIsAgreed(currentUser, true);

            if(processedTrade.getIsAgreed(processedTrade.getInitiator())
                    && processedTrade.getIsAgreed(processedTrade.getReceiver())){
                processedTrade.setTradeStatus("Agreed, Waiting to be confirmed");
                return "Both users have agreed to the trade meeting. Waiting to be confirmed.";
            }
            return "You have agreed to the trade meeting. Waiting for other user to agree.";
        }
    }

    /**
     * Allow the current user to confirm the processedTrade has took place
     * Complete the processedTrade iff both users have confirmed trade
     */
    public String confirmTrade(){
        if(LocalDate.now().isBefore(processedTrade.getTradeDate())){
            return "Request refused: The trade should not take place before the meeting";
        }else{
            processedTrade.setIsConfirmed(currentUser,true);
            if(processedTrade.getIsConfirmed(processedTrade.getInitiator())
                    && processedTrade.getIsConfirmed(processedTrade.getReceiver())){
                processedTrade.setCompleted(true);
                processedTrade.setTradeStatus("Completed");
                completeTrade();
                return "Both user have confirmed the trade happened. Trade is completed.";
            }
            return "You have confirmed the trade happened. Waiting for other user to confirm.";
        }
    }

    /**
     * Complete the trade when the trade has taken place
     */
    private void completeTrade(){
        Trader initiator = processedTrade.getInitiator();
        Trader receiver = processedTrade.getReceiver();
        if(processedTrade instanceof OneWayTrade){borrow(((OneWayTrade) processedTrade).getItem(),initiator,receiver);}
        else if(processedTrade instanceof TwoWayTrade){
            exchange(((TwoWayTrade) processedTrade).getItem1(), ((TwoWayTrade) processedTrade).getItem2());
        }
    }

    /**check whether or not the requested trade is valid
     * @return whether or not the trade exceeds the max number of trades that the user can conduct in one week
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isValid(){
        Trader trader = currentUser;
        Trade trade = processedTrade;
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = trade.getTradeDate().get(weekFields.weekOfWeekBasedYear());
        ArrayList<Trade> trades = trader.getTrades();
        int i = Collections.binarySearch(trades, trade);
        int n = 0;
        for (int j = 0; n<limitOfTradesPerWeek; j++){
            if (i+j < trades.size() && trades.get(i+j).getTradeDate().get(weekFields.weekOfWeekBasedYear()) ==
                    weekNumber && trade.getTradeDate().getYear() == trades.get(i+j).getTradeDate().getYear()){
                n++;
            }
            else {
                break;
            }
        }
        for (int k = 1; n<limitOfTradesPerWeek; k++){
            if (i-k >= 0 && trades.get(i-k).getTradeDate().get(weekFields.weekOfWeekBasedYear()) ==
                    weekNumber && trade.getTradeDate().getYear() == trades.get(i-k).getTradeDate().getYear()){
                n++;
            }
            else {
                break;
            }
        }
        return n < limitOfTradesPerWeek;
    }

    /**Getter for the limitOfTradesPerWeek
     * @return the max number of trades that the user can conduct in one week
     */
    public int getLimitOfTradesPerWeek(){
        return limitOfTradesPerWeek;
    }

    /**Setter for the limitOfTradesPerWeek
     * @param limit the limitation of trades that the user can conduct in one week
     */
    public void setLimitOfTradesPerWeek(int limit){
        limitOfTradesPerWeek = limit;
    }

    /**Getter for the moreLendNeeded
     * @return the number of more items the user should have lent than the user have borrowed,
     * in order to make a non-lending trade
     */
    public int getMoreLendNeeded(){
        return moreLendNeeded;
    }

    /**Setter for the moreLendNeeded
     * @param num the number of more items the user should have lent than the user have borrowed,
     * in order to make a non-lending trade
     */
    public void setMoreLendNeeded(int num){
        moreLendNeeded = num;
    }

    /**Getter for the maxIncomplete
     * @return the maximum number of incomplete trades the user can have before his/her account is frozen
     */
    public int getMaxIncomplete(){
        return maxIncomplete;
    }

    /**Setter for the maxIncomplete
     * @param num the maximum number of incomplete trades the user can have before his/her account is frozen
     */
    public void setMaxIncomplete(int num){
        maxIncomplete = num;
    }


}
