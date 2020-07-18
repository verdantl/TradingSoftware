
import java.time.LocalDate;
import java.util.HashMap;

public class Meeting implements Comparable<Meeting>{
    private int tradeId;
    private LocalDate tradeDate;
    private LocalDate returnDate;
    private String location;
    private String tradeStatus;
    private HashMap<String, Integer> numberOfEdits;
    private HashMap<String, Boolean> isAgreed;
    private HashMap<String, Boolean> isConfirmed;

    public Meeting(String initiator, String receiver, LocalDate tradeDate, String location){
        this.location = location;
        this.tradeDate = tradeDate;
        isConfirmed = new HashMap<>();
        isConfirmed.put(initiator, false);
        isConfirmed.put(receiver, false);
        numberOfEdits = new HashMap<>();
        numberOfEdits.put(initiator, 0);
        numberOfEdits.put(receiver, 0);
        isAgreed = new HashMap<>();
        isAgreed.put(initiator, false);
        isAgreed.put(receiver, false);
        this.tradeStatus = "In Progress";
    }

    @Override
    public int compareTo(Meeting meeting) {
        if(this.tradeDate.isBefore(meeting.getTradeDate())){
            return -1;
        }
        else if (this.tradeDate.isBefore(meeting.getTradeDate())){
            return 1;
        }
        else{
            return 0;
        }
    }

    /**Getter for the tradeDate
     * @return the date that the trade
     */
    public LocalDate getTradeDate(){return this.tradeDate;}

    /**Setter for the tradeDate
     * @param tradeDate the date that the trade will take place
     */
    public void setTradeDate(LocalDate tradeDate){this.tradeDate = tradeDate;}


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
     *Getter for trade id
     * @return Trade Id
     */
    public int getTradeId() {
        return tradeId;
    }

    /**Setter for trade id
     * @param id the id of the trade
     */
    public void setTradeId(int id) {this.tradeId = id;}

}
