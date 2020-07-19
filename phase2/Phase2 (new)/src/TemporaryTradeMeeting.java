import java.time.LocalDate;
import java.util.HashMap;

public class TemporaryTradeMeeting extends Meeting{
    private LocalDate returnDate;
    private LocalDate returnLocation;
    private boolean isReturned;
    private HashMap<String, Boolean> confirmReturned;
    public TemporaryTradeMeeting(int tradeId) {
        super(tradeId);
        setReturned(false);
    }

    @Override
    public String toString(){
        StringBuilder meetingString = new StringBuilder("-TradeID:" + super.getTradeId() + "\n" +
                //"-RequestDate:" + super.getRequestMeetingDate() + "\n" +
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

    /**Getter the returnDate of the trade
     * @return the returnDate of the trade
     */
    public LocalDate getReturnDate(){return this.returnDate;}

    /**Setter for the returnDate
     * @param returnDate the date that the second meeting should take place
     */
    public void setReturnDate(LocalDate returnDate){this.returnDate = returnDate;}

    public LocalDate getReturnLocation() {
        return returnLocation;
    }

    public HashMap<String, Boolean> getConfirmReturned() {
        return confirmReturned;
    }

    public void setConfirmReturned(HashMap<String, Boolean> confirmReturned) {
        this.confirmReturned = confirmReturned;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public void setReturnLocation(LocalDate returnLocation) {
        this.returnLocation = returnLocation;
    }
}
