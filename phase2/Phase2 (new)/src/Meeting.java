import users.Trader;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.HashMap;

public class Meeting implements Comparable<Meeting>{
//    private String initiator;
//    private String receiver;
//    private LocalDate requestMeetingDate;
    private int tradeId;
    private LocalDate tradeDate;
    private LocalDate returnDate;
    private String location;
    private String tradeStatus;
    private HashMap<String, Integer> numberOfEdits;
    private HashMap<String, Boolean> isAgreed;
    private HashMap<String, Boolean> isConfirmed;
//    private String tradeType;
//    private HashMap<String, String> items;


    @Override
    public int compareTo(Meeting meeting) {
        if(this.tradeDate.isBefore(meeting.getRequestMeetingDate())){
            return -1;
        }
        else if (this.tradeDate.isBefore(meeting.getRequestMeetingDate())){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**Getter for the requestMeetingDate
     * @return the requestMeetingDate
     */
    //public LocalDate getRequestMeetingDate(){return this.requestMeetingDate;}

    /**Setter for the requestMeetingDate
     * @param requestMeetingDate the requestMeetingDate
     */
    //public void setRequestMeetingDate(LocalDate requestMeetingDate){this.requestMeetingDate = requestMeetingDate;}

    /**Getter for the tradeDate
     * @return the date that the trade
     */
    public LocalDate getTradeDate(){return this.tradeDate;}

    /**Setter for the tradeDate
     * @param tradeDate the date that the trade will take place
     */
    public void setTradeDate(LocalDate tradeDate){this.tradeDate = tradeDate;}

    /** Getter for the initiator
     * @return the initiator
     */
    //public String getInitiator(){return this.initiator;}

    /**Setter for the initiator
     * @param initiator the initiator
     */
    //public void setInitiator(String initiator){this.initiator = initiator;}

    /**Getter for the receiver
     * @return the receiver
     */
    //public String getReceiver(){return this.receiver;}

    /**Setter for the receiver
     * @param receiver the receiver
     */
    //public void setReceiver(String receiver){this.receiver = receiver;}

    /**Getter for the location
     * @return the location of the meeting
     */
    public String getLocation(){return this.location;}

    /**Setter for the location
     * @param location the location that the trade may take place
     */
    public void setLocation (String location){this.location = location;}

    /**Getter for the trade status
     * @return the status for the trade
     */
    public String getTradeStatus(){return this.tradeStatus;}

    /**Setter for the trade status
     * @param tradeStatus the status of the trade
     */
    public void setTradeStatus(String tradeStatus){this.tradeStatus = tradeStatus;}

    /**Getter for the type of the trade
     * @return the type of the trade
     */
    //public String getTradeType(){return this.tradeType;}

    /**Setter for the tradeType
     * @param tradeType the type of the trade
     */
    //public void setTradeType(String tradeType){this.tradeType = tradeType;}

    /**Getter the returnDate of the trade
     * @return the returnDate of the trade
     */
    public LocalDate getReturnDate(){return this.returnDate;}

    /**Setter for the returnDate
     * @param returnDate the date that the second meeting should take place
     */
    public void setReturnDate(LocalDate returnDate){this.returnDate = returnDate;}

    /**Setter for isAgreed
     * @param isAgreed a hashmap storing whether or not both trader agree the meeting
     */
    public void setIsAgreed(HashMap<String, Boolean> isAgreed) {this.isAgreed = isAgreed;}

    /**Getter for isAgreed
     * @return a hashmap storing whether or not both trader agree the meeting
     */
    public HashMap<String, Boolean> getIsAgreed() {
        return isAgreed;
    }

    /**Getter for isConfirmed
     * @return a hashmap storing whether or not both trader confirm the trade
     */
    public HashMap<String, Boolean> getIsConfirmed() {
        return isConfirmed;
    }

    /**Setter for isConfirmed
     * @param isConfirmed a hashmap storing whether or not both trader confirm the trade
     */
    public void setIsConfirmed(HashMap<String, Boolean> isConfirmed){this.isConfirmed = isConfirmed;}

    /**Getter for numberOfEdits
     * @return a hashmap storing the number of times each trader edits the meeting information
     */
    public HashMap<String, Integer> getNumberOfEdits() {
        return numberOfEdits;
    }

    /**Setter for numberOfEdits
     * @param numberOfEdits a hashmap storing the number of times each trader edits the meeting information
     */
    public void setNumberOfEdits(HashMap<String, Integer> numberOfEdits){this.numberOfEdits = numberOfEdits;}

    /**Getter for items
     * @return a list of hashmap storing items(values) and their owner's name
     */
//    public HashMap<String, String> getItems() {
//        return items;
//    }

    /**Setter for items
     * @param items a list of hashmap storing items(values) and their owner's name
     */
//    public void setItems(HashMap<String, String> items) {
//        this.items = items;
//    }

    /**increase the number of edits when the user edits the trade
     * @param trader the trader who edit this trade
     */

    public void increaseNumberOfEdits(Trader trader) {
        numberOfEdits.replace(trader.getUsername(), numberOfEdits.get(trader.getUsername()) + 1);
    }

    /**allow the trader to agree the meeting
     * @param trader the trader who wants to agree the trade meeting
     * @param agree the boolean showing whether or not the trader agree this meeting
     */
    public void setAgree(String trader, Boolean agree) {
        isAgreed.replace(trader, agree);
    }

    /**allow the trader to confirm the meeting
     * @param trader the trader who wants to confirm this trade
     * @param confirm the boolean showing whether or not the trader confirm this trade
     */
    public void setConfirm(String trader, Boolean confirm) {
        isConfirmed.replace(trader, confirm);
    }

    /**
     *
     * @return Trade Id
     */
    public int getTradeId() {
        return tradeId;
    }
}
