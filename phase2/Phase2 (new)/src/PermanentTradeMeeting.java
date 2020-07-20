public class PermanentTradeMeeting extends Meeting{

    /**
     * Constructor for the meeting between two individuals that are conducting a trade
     *
     * @param tradeId The id of the trade
     */
    public PermanentTradeMeeting(int tradeId) {
        super(tradeId);
    }

    /**
     * String representation of this meeting
     * @return a string representation of the information in this meeting
     */
    @Override
    public String toString(){
        StringBuilder meetingString = new StringBuilder("-TradeID:" + super.getTradeId() + "\n" +
                "-MeetingDate:" + super.getTradeDate() + "\n" +
                "-MeetingLocation:" + super.getLocation() + "\n" +
                "-TradingStatus:" + super.getTradeStatus() + "\n");
        for(String user: super.getNumberOfEdits().keySet()){
            meetingString.append("-");
            meetingString.append(user).append("EditTimes:");
            meetingString.append(super.getNumberOfEdits().get(user));
            meetingString.append("\n");
        }

        return meetingString.toString();
    }
}
