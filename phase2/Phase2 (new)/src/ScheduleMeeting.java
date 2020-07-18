import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;


public class ScheduleMeeting {
    private final HashMap<String, List<Meeting>> meetingInventory;
    private int limitOfTradesPerWeek;

    public ScheduleMeeting(int limitOfTradesPerWeek){
        meetingInventory = new HashMap<>();
        this.limitOfTradesPerWeek = limitOfTradesPerWeek;
    }


    /**
     *Getter for a specific meeting in the meetingInventory
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

    /**Add a new meeting to the inventory
     * @param user the user who wants to add a new meeting
     * @param meeting the new meeting
     */
    public void addMeeting(String user, Meeting meeting){
        if(meetingInventory.containsKey(user)){
            meetingInventory.get(user).add(meeting);
        }else{
            List<Meeting> meetingList = new ArrayList<>();
            meetingList.add(meeting);
            meetingInventory.put(user, meetingList);
        }
    }




    public String agreeMeeting(String user, Meeting meeting){
        if(LocalDate.now().isAfter(meeting.getTradeDate())){
            return "Request refused: You have past the trade date";
        }else{
            meeting.setAgree(user, true);
            if(checkBothAgreed(meeting)){
                meeting.setTradeStatus("Agreed, Waiting to be confirmed");
                return "Both users have agreed to the trade meeting. Waiting to be confirmed.";
            }else{
                return "You have agreed to the trade meeting. Waiting for other user to agree.";
            }
        }
    }

    private boolean checkBothAgreed(Meeting meeting){
        boolean bothAgreed = true;
        for(boolean agree: meeting.getIsAgreed().values()){
            if(!agree) {
                bothAgreed = false;
                break;
            }
        }
        return bothAgreed;
    }



    public String editTradeMeeting(String user, Meeting meeting, LocalDate newDate, String newLocation){
        int totalEdits = 0;
        for(int value: meeting.getNumberOfEdits().values()){
            totalEdits += value;
        }

        if(exceedWeeklyLimit(newDate, user)){
            return "You have reached the trade limit in that week, please choose another week";
        }else if(meeting.getNumberOfEdits().get(user) == 3){
            if(totalEdits == 6 && !checkBothAgreed(meeting)){
                cancelMeeting(user, meeting);
                return "The trade is cancelled due to both users reaching a limit of editing meeting 3 times.";
            }else{return "You have run out of edit times and you cannot edit";}
        }else{
            meeting.setLocation(newLocation);
            meeting.setTradeDate(newDate);
            for(String key: meeting.getIsAgreed().keySet()){
                meeting.getIsAgreed().replace(key, false);
            }
            meeting.setTradeStatus("In Process");
            meeting.increaseNumberOfEdits(user);
            return "New date and location has been set. Trade in Progress";
        }
    }

    public void editReturnMeeting(String user, Meeting meeting, LocalDate newDate, String newLocation)
    {}

    private boolean exceedWeeklyLimit(LocalDate tradeDate, String user){
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = tradeDate.get(weekFields.weekOfWeekBasedYear());
        List<Meeting> meetings = meetingInventory.get(user);
        int n = 0;
        for(Meeting meeting: meetings){
            if(meeting.getTradeDate().getYear() == tradeDate.getYear() &&
            meeting.getTradeDate().get(weekFields.weekOfWeekBasedYear()) == weekNumber){n++;}
        }

        return n > limitOfTradesPerWeek;
    }


    public void setLimitOfTradesPerWeek(int limitOfTradesPerWeek) {
        this.limitOfTradesPerWeek = limitOfTradesPerWeek;
    }

    public void cancelMeeting(String user, Meeting meeting){
        meetingInventory.get(user).remove(meeting);
    }

    public String getListOfMeetings(String user){
        StringBuilder s = new StringBuilder();
        int n = 1;
        for(Meeting meeting: meetingInventory.get(user)){
            s.append(n);
            s.append(meeting.toString());
            s.append("\n");
        }
        return s.toString();
    }

}
