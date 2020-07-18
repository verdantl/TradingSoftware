import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleMeeting {
    private  HashMap<String, ArrayList<Meeting>> meetingInventory;

    public ScheduleMeeting(HashMap<String, ArrayList<Meeting>> meetingInventory){
        this.meetingInventory = meetingInventory;
    }

    public Meeting getMeeting(String user, int index){
        return meetingInventory.get(user).get(index);
    }



}
