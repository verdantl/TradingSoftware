public class PermanentTradeMeeting extends Meeting{
    public PermanentTradeMeeting(int tradeId) {
        super(tradeId);
    }

    @Override
    public String toString(){
        StringBuilder meetingString = new StringBuilder("-TradeID:" + super.getTradeId() + "\n" +
                "-RequestDate:" + super.getRequestMeetingDate() + "\n" +
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
