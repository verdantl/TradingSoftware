import java.time.LocalDate;
import java.util.HashMap;

public class MeetingManager {
    private HashMap<Integer, Meeting> meetings;

    public MeetingManager(HashMap<Integer, Meeting> meetings){
        this.meetings = meetings;
    }

    public Meeting getMeeting(int id){
        if (!containMeeting(id)){
            return null;
        }
        return meetings.get(id);
    }

    public boolean editLocation(int id, String location){
        if (!containMeeting(id)){
            return false;
        }

        getMeeting(id).setLocation(location);
        return true;
    }

    public boolean editTradeDate(int id, LocalDate date){
        if (!containMeeting(id)){
            return false;
        }

        getMeeting(id).setTradeDate(date);
        return true;
    }

    public boolean confirmTrade(int id, String username){
        if (!containMeeting(id)){
            return false;
        }

        getMeeting(id).setConfirm(username, true);
        return true;
    }

    public boolean agreeOnTrade(int id, String username){
        if (!containMeeting(id)){
            return false;
        }

        getMeeting(id).setAgree(username, true);
        return true;
    }


    public boolean containMeeting(int id){
        return meetings.containsKey(id);
    }

    public boolean increaseNumEdits(int id, String username){
        if (!containMeeting(id)){
            return false;
        }

        getMeeting(id).increaseNumberOfEdits(username);
        return true;
    }



}