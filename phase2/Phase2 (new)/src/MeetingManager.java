import com.sun.prism.shader.AlphaTexture_Color_AlphaTest_Loader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A MeetingManager class that manages meetings
 */
public class MeetingManager {

    //Each meeting is paired up with the corresponding trade's id
    private final HashMap<Integer, Meeting> meetings;

    /**
     * Constructs MeetingManager class
     * @param meetings A HashMap of Integer and Meeting
     */
    public MeetingManager(HashMap<Integer, Meeting> meetings){
        this.meetings = meetings;
    }

    /**Getter a meeting based on the trade's Id
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
     * Returns a list of tradeIDs that have on-going meetings
     * @param tradeIds The ids corresponding to the meetings
     * @return A list of Trade ids where their respective meeting status is other than "Completed"
     */
    public List<Integer> getOnGoingMeetings(List<Integer> tradeIds){
        List<Integer> temp = new ArrayList<>();
        for(Integer i:tradeIds){
            if(!getMeeting(i).getTradeStatus().equals("Completed")){
                temp.add(i);
            }

        }
        return temp;
    }

    /**Getter for description of a meeting
     * @param id the id of the trade
     * @return the string representation of a meeting
     */
    public String getMeetingDescription(int id){
        if(!containMeeting(id)){
            return null;
        }
        return meetings.get(id).toString();
    }

    /**create a trade
     * @param tradeId the id of the trade
     * @param tradeDate the date of the trade
     * @param returnDate the return date of the trade
     * @param location the location of the trade
     * @param returnLocation the return location of the trade
     * @param isAgreed a hashmap storing whether or not the traders agree with the meeting
     * @param isConfirmed a hashmap storing whether or not the traders confirm the meeting
     * @param numberOfEdits a hashmap storing traders' edit times
     * @return whether or not the meeting is created
     */
    public boolean createMeeting(int tradeId, LocalDate tradeDate, LocalDate returnDate,
                                 String location, String returnLocation, HashMap<String, Boolean> isAgreed,
                                 HashMap<String, Boolean> isConfirmed,
                                 HashMap<String, Integer> numberOfEdits,
                                 HashMap<String, Boolean> isReturned, boolean isPermanent){
        Meeting m = new Meeting(tradeId);
        m.setTradeDate(tradeDate);
        m.setReturnDate(returnDate);
        m.setLocation(location);
        m.setReturnLocation(returnLocation);
        m.setIsAgreed(isAgreed);
        m.setIsConfirmed(isConfirmed);
        m.setNumberOfEdits(numberOfEdits);
        m.setPermanent(isPermanent);
        m.setIsReturned(isReturned);
        if(meetings.containsKey(tradeId)){
            return false;
        }else{
            meetings.put(tradeId, m);
            return true;
        }
    }


    //Depending on when the meeting is created we can shorten the above method
    public boolean createMeeting(int tradeId, String initiator, String receiver, boolean isPermanent){
        if(meetings.containsKey(tradeId)){
            return false;}
        else{
        Meeting m = new Meeting(tradeId);

        m.setAgree(initiator, false);
        m.setAgree(receiver, false);


        m.setConfirm(initiator, false);
        m.setConfirm(receiver, false);

        m.setReturn(initiator, false);
        m.setReturn(receiver, false);

        HashMap<String, Integer> edits = new HashMap<>();
        edits.put(initiator, 0);
        edits.put(receiver, 0);
        m.setNumberOfEdits(edits);

        m.setPermanent(isPermanent);


        meetings.put(tradeId, m);
        return true;
        }
    }

    public void setMeetingInfo(int id, LocalDate tradeDate, LocalDate returnDate,
                               String location, String returnLocation){
        Meeting meeting = meetings.get(id);
        meeting.setTradeDate(tradeDate);
        meeting.setReturnDate(returnDate);
        meeting.setLocation(location);
        meeting.setReturnLocation(returnLocation);

    }


    /**
     * Removes the given meeting object from meetings
     * @param id The trade id
     * @return Return true iff the meeting object was removed
     */
    public boolean removeMeeting(int id){
        if(containMeeting(id)){
            meetings.remove(id);
            return true;
        }
        return false;
    }



    /**edit the meeting based on the new location
     * @param id id of the trade
     * @param location the new meeting location
     */
    public void editLocation(int id, String location){
        getMeeting(id).setLocation(location);
    }

    /**edit the meeting based on the new date
     * @param id id of the trade
     * @param date the new meeting date
     */
    public void editDate(int id, LocalDate date){
        getMeeting(id).setTradeDate(date);
    }

    /**edit the return location for the meeting
     * @param id the id of the trade
     * @param location the new return location
     */
    public void editReturnLocation(int id, String location){
        getMeeting(id).setReturnLocation(location);
    }

    /**edit the return date for the meeting
     * @param id the id of the trade
     * @param date the return date of the meeting
     */
    public void editReturnDate(int id, LocalDate date){
        getMeeting(id).setReturnDate(date);
    }

    /**increase the number of edit times
     * @param user the user who wants to edit the meeting
     * @param id the id of the trade
     */
    public void increaseNumEdit(String user, int id){
        getMeeting(id).increaseNumberOfEdits(user);
    }

    /**return true iff the user hasn't edited the trade more than 3 times
     * @param user the user who wants to edit the meeting
     * @param id the tradeID
     * @return whether or not the user is valid to edit the trade
     */
    public boolean isValid(String user, int id){
        return meetings.get(id).getNumberOfEdits().get(user) <= 3;
    }

    //if confirmTrade returns true, the program can switch trader's items in the controller class
    //this method is enough to check whether trade is complete
    //Based on how we define the non-meeting trade(see the comment in Trade)
    // ,even non-meeting trade can use this method
    /**Return true iff both traders confirm the meeting
     * Confirms that the meeting happened
     * @param id Id of the trade
     * @param username Username of the Trader confirming that the meeting happened
     * @return whether or not both trader confirm the meeting
     */
    public boolean confirmMeeting(int id, String username){
        getMeeting(id).setConfirm(username, true);
        if(checkAllConfirmed(id)){
            if(getMeeting(id).isPermanent()){
                getMeeting(id).setTradeStatus("Completed");
            }else{
                getMeeting(id).setTradeStatus("Waiting to be returned");
            }
            return true;
        }else{
            getMeeting(id).setTradeStatus("Confirmed: waiting the other to confirm");
            return false;
        }
    }



    private boolean checkAllConfirmed(int id){
        Meeting meeting = getMeeting(id);
        for(String user: meeting.getIsConfirmed().keySet()){
            if(!meeting.getIsConfirmed().get(user)){return false;}
        }
        return true;
    }

    /**Return true iff both traders agree with the meeting
     * Agrees on the meeting
     * @param id Id of the trade
     * @param username Username of the Trader agreeing on the meeting
     * @return whether or not both trader agree with the meeting
     */
    public boolean agreeOnTrade(int id, String username){
        getMeeting(id).setAgree(username, true);
        if(checkAllAgreed(id)){
            getMeeting(id).setTradeStatus("Both Agreed: waiting to be confirmed");
            return true;

        }else{
            getMeeting(id).setTradeStatus("Agreed: waiting the other to agree");
            return false;
        }
    }

    private boolean checkAllAgreed(int id){
        Meeting meeting = getMeeting(id);
        for(String user: meeting.getIsAgreed().keySet()){
            if(!meeting.getIsAgreed().get(user)){
                return false;
            }
        }
        return true;
    }

    /**Return True iff both traders confirm that the items are returned
     * confirm the items are returned
     * @param id the id of the trade
     * @param username Username of the Trader who confirms the items are returned
     */
    public boolean confirmReturn(int id, String username){
        if(checkAllReturned(id)){
            getMeeting(id).setTradeStatus("Completed");
            return true;
        }else{
            getMeeting(id).setTradeStatus("Waiting the other to confirm");
            return false;
        }

    }

    private boolean checkAllReturned(int id){
        Meeting meeting = getMeeting(id);
        for(String user: meeting.getIsReturned().keySet()){
            if(!meeting.getIsReturned().get(user)){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the Meeting Manager has a meeting with given int id
     * @param id Id of the trade
     * @return Return true iff meeting with the given id exists. Otherwise return false
     */
    public boolean containMeeting(int id){
        return meetings.containsKey(id);
    }



}