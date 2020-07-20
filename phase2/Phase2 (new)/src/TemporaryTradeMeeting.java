import java.time.LocalDate;
import java.util.HashMap;

public class TemporaryTradeMeeting extends Meeting{
    private LocalDate returnDate;
    private String returnLocation;
    private boolean isReturned;
    private HashMap<String, Boolean> confirmReturned;

    /**
     * Constructor for the temporary meeting
     * @param tradeId the ID of the trade
     */
    public TemporaryTradeMeeting(int tradeId) {
        super(tradeId);
        setReturned(false);
    }

    /**
     * String representation of the Temporary Meeting
     * @return a string representation of the temporary meeting information
     */
    @Override
    public String toString(){
        StringBuilder meetingString = new StringBuilder("-TradeID:" + super.getTradeId() + "\n" +
                "-MeetingDate:" + super.getTradeDate() + "\n" +
                "-MeetingLocation:" + super.getLocation() + "\n" +
                "-TradingStatus:" + super.getTradeStatus() + "\n" +
                "-ReturnDate:" + returnDate + "\n" +
                "-ReturnLocation:" + returnLocation + "\n"+
                "-IsReturned:" + isReturned);
        for(String user: super.getNumberOfEdits().keySet()){
            meetingString.append("-");
            meetingString.append(user).append("EditTimes:");
            meetingString.append(super.getNumberOfEdits().get(user));
            meetingString.append("\n");
        }

        return meetingString.toString();
    }


    /**Setter for the returnDate
     * @param returnDate the date that the second meeting should take place
     */
    public void setReturnDate(LocalDate returnDate){this.returnDate = returnDate;}

    /**
     * Getter for confirmReturned
     * @return a hashmap of username to boolean representing if the users have confirmed the meeting
     */
    public HashMap<String, Boolean> getConfirmReturned() {
        return confirmReturned;
    }

    /**
     * Setter for confirmReturned
     * @param confirmReturned a hashmap of username to boolean representing if the users have confirmed the meeting
     */
    public void setConfirmReturned(HashMap<String, Boolean> confirmReturned) {
        this.confirmReturned = confirmReturned;
    }

    /**
     * Getter for if the items have been returned
     * @return a boolean representing if the objects have been returned
     */
    public boolean isReturned() {
        return isReturned;
    }

    /**
     * Setter for the items being returned
     * @param returned a boolean representing if the objects have been returned
     */
    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    /**
     * Setter for the location of the return
     * @param returnLocation A string representing the location of the return
     */
    public void setReturnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
    }
}
