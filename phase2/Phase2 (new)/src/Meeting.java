
import java.time.LocalDate;
import java.util.HashMap;

public abstract class Meeting{
    private final int tradeId;
    private LocalDate tradeDate;
    private String location;
    private String tradeStatus;
    private String returnLocation;
    private LocalDate returnDate;
    private  HashMap<String, Integer> numberOfEdits;
    private  HashMap<String, Boolean> isAgreed;
    private  HashMap<String, Boolean> isConfirmed;

    public Meeting(int tradeId){
        this.tradeId = tradeId;
        isConfirmed = new HashMap<>();
        numberOfEdits = new HashMap<>();
        isAgreed = new HashMap<>();
        this.tradeStatus = "In Progress";
        numberOfEdits = new HashMap<>();
        isAgreed = new HashMap<>();
        isConfirmed = new HashMap<>();
        returnDate = LocalDate.of(0,0,0);
        returnLocation = "N/A";
    }

    public String toString(){
        StringBuilder s = new StringBuilder("-TradeID: " + getTradeId() +
                "\n" +
                "-MeetingDate: " + getTradeDate() +
                "\n" +
                "-MeetingLocation: " + getLocation() +
                "\n" +
                "-ReturnDate: " + getReturnDate() +
                "\n" +
                "-ReturnLocation: " + getReturnLocation() +
                "\n" +
                "-TradeStatus: " + getTradeStatus() +
                "\n");
        for(String trader: numberOfEdits.keySet()){
            s.append("-").append(trader).append("EditTimes: ").append(getNumberOfEdits().get(trader)).append("\n");
        }

        return s.toString();
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



    /**Getter for numberOfEdits
     * @return a hashmap storing the number of times each trader edits the meeting information
     */
    public HashMap<String, Integer> getNumberOfEdits() {
        return numberOfEdits;
    }


    /**increase the number of edits when the user edits the meeting
     * @param user the user who edit this meeting
     */

    public void increaseNumberOfEdits(String user) {
        numberOfEdits.replace(user, numberOfEdits.get(user) + 1);
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


    /**Getter for returnLocation
     * @return the returnMeeting's location
     */
    public String getReturnLocation() {
        return returnLocation;
    }

    /**Setter for returnLocation
     * @param returnLocation the return meeting's location
     */
    public void setReturnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
    }

    /**Getter for returnDate
     * @return the return meeting's date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**Setter for the return meeting
     * @param returnDate the return meeting's date
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
