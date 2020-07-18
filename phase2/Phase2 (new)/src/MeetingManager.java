import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * A MeetingManager class that manages meetings
 */
public class MeetingManager {

    //Each meeting is paired up with the corresponding trade's id
    private HashMap<Integer, Meeting> meetings;

    /**
     * Constructs MeetingManager class
     * @param meetings A HashMap of Integer and Meeting
     */
    public MeetingManager(HashMap<Integer, Meeting> meetings){
        this.meetings = meetings;
    }

    /**
     *
     * @param id Id of the trade
     * @return Return Meeting object iff it exists. Otherwise return null
     */
    public Meeting getMeeting(int id){
        if (!containMeeting(id)){
            return null;
        }
        return meetings.get(id);
    }

    /**
     * Changes the location of the meeting with given id with the given location
     * @param id Id of the trade
     * @param location New location
     */
    public void editLocation(int id, String location){
        getMeeting(id).setLocation(location);
    }

    /**
     * Changes the meeting date of the meeting with given id with the given date
     * @param id Id of the trade
     * @param date New trade date
     */
    public void editTradeDate(int id, LocalDate date){
        getMeeting(id).setTradeDate(date);
    }

    /**
     * Confirms that the meeting happened
     * @param id Id of the trade
     * @param username Username of the Trader confirming that the meeting happened
     */
    public void confirmTrade(int id, String username){
        getMeeting(id).setConfirm(username, true);
    }

    /**
     * Agrees on the meeting
     * @param id Id of the trade
     * @param username Username of the Trader agreeing on the meeting
     */
    public void agreeOnTrade(int id, String username){
        getMeeting(id).setAgree(username, true);
    }

    /**
     * Checks if the Meeting Manager has a meeting with given int id
     * @param id Id of the trade
     * @return Return true iff meeting with the given id exists. Otherwise return false
     */
    public boolean containMeeting(int id){
        return meetings.containsKey(id);
    }

    /**
     * Increases number of edits
     * @param id Id of the trade
     * @param username Username of the Trader that edited the meeting
     */
    public void increaseNumEdits(int id, String username){
        getMeeting(id).increaseNumberOfEdits(username);
    }

    /**
     * Add new meeting object to meetings
     * @param meeting Meeting object to add
     * @return Return true iff meeting was successfully added
     */
    public boolean addMeeting(Meeting meeting){
        if (containMeeting(meeting.getTradeId())){
            return false;
        }

        meetings.put(meeting.getTradeId(), meeting);
        return true;
    }

    /**
     * Add list of meeting objects to meetings
     * @param meetingList List of meeting objects to add
     * @return Return true iff all of the meeting objects were added
     */
    public boolean addAllMeetings(List<Meeting> meetingList){
        boolean addAll = true;
        for(Meeting m: meetingList){
            if(!addMeeting(m)){
                addAll = false;
            }
        }
        return addAll;
    }



}