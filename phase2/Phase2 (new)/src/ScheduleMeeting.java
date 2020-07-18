import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScheduleMeeting {
    private final HashMap<String, List<Meeting>> meetingInventory;

    public ScheduleMeeting(HashMap<String, List<Meeting>> meetingInventory){
        this.meetingInventory = meetingInventory;
    }

    /**
     *
     * @param user Username of the Trader
     * @param tradeId Id of the Trade
     * @return Return Meeting iff the username and the tradeId is valid
     */
    public Meeting getMeeting(String user, int tradeId){
        if (!meetingInventory.containsKey(user)){
            return null;
        }

        return getMeetingWithTradeId(meetingInventory.get(user), tradeId);
    }

    private Meeting getMeetingWithTradeId(List<Meeting> meetings, int tradeId){
        for (Meeting m: meetings){
            if (m.getTradeId() == tradeId){
                return m;
            }
        }
        return null;
    }


}
