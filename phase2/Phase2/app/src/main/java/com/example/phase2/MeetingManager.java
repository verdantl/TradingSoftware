package com.example.phase2;

import com.example.phase2.Manager;
import com.example.phase2.Meeting;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A MeetingManager class that manages meetings
 */
public class MeetingManager extends Manager implements Serializable {
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
        return Objects.requireNonNull(meetings.get(id)).toString();
    }

    /**
     * Creates a meeting to be added to the system
      * @param tradeId The id of the trade that this meeting represents
     * @param initiator the initiator of the trade
     * @param receiver the receiver of the trade offer
     * @param isPermanent a boolean representing if the trade is permanent
     * @return a boolean representing if the meeting creation was successful
     */
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

    /**
     * A setter for the meeting information
     * @param id the id of the meeting
     * @param tradeDate the date of the trade
     * @param returnDate the return date of the trade
     * @param location the location of the meeting
     * @param returnLocation the return location of the meeting
     */
    public void setMeetingInfo(int id, LocalDate tradeDate, LocalDate returnDate,
                               String location, String returnLocation){
        Meeting meeting = meetings.get(id);
        meeting.setTradeDate(tradeDate);
        meeting.setReturnDate(returnDate);
        meeting.setLocation(location);
        meeting.setReturnLocation(returnLocation);
    }

    /**
     * Returns the meeting status of the inputted trade.
     * @param id the id of the trade in question.
     * @return the status of the meeting.
     */
    public String getMeetingStatus(Integer id){
        return meetings.get(id).getTradeStatus();
    }


    /**
     * Removes the given meeting object from meetings
     * @param id The trade id
     * @return Return true iff the meeting object was removed
     */
    //TODO: unused method
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
    //TODO: unused method
    public void editReturnLocation(int id, String location){
        getMeeting(id).setReturnLocation(location);
    }

    /**edit the return date for the meeting
     * @param id the id of the trade
     * @param date the return date of the meeting
     */
    //TODO: unused method
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
        return meetings.get(id).getNumberOfEdits().get(user) < 3;
    }

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
    //TODO: unused method
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

    /**
     * Undoes the last edit made to the meeting.
     * @param id The ID of the meeting that is to be rolled back.
     * @param user The username of the user whose actions are to be undone.
     */
    public void undoEdit(int id, String user) {
        Meeting temp = meetings.get(id);

        temp.setLocation(temp.getLastLocation());
        temp.setTradeDate(temp.getLastTradeDate());
        temp.setReturnLocation(temp.getLastReturnLocation());
        temp.setReturnDate(temp.getLastReturnDate());

        temp.decreaseNumberOfEdits(user);

        temp.resetLastInfo();
    }

    /**
     * Checks if the inputted meeting has an edit that can be undone.
     * @param id
     * @return
     */
    public boolean canUndoEdit(int id) {
        return meetings.get(id).getCanBeUndone();
    }

    /**
     * Undoes a user's decision to agree to the trade.
     * Precondition: The user is involved with the trade of trade ID 'id' and the only user that has
     * agreed to the trade is the inputted user.
     * @param id The ID of the meeting in question.
     * @param user The username of the user in question.
     */
    public void undoAgree(int id, String user) {
        Meeting temp = meetings.get(id);
        HashMap tempMap = temp.getIsAgreed();
        tempMap.replace(user, false);
        temp.setIsAgreed(tempMap);
    }

    /**
     * A method that checks if the meeting can be unagreed by the inputted user.
     * @param id ID of the trade the meeting is attatched to.
     * @param username The username of the user in question.
     * @return true, iff the user was the last one to agree to a meeting date and both traders
     * involved haven't mutually agreed.
     * */
    public boolean canUndoAgree(int id, String username){
        Meeting temp = meetings.get(id);
        assert temp != null;
        temp.getIsAgreed().values().size();
        int counter = 0;
        for(boolean b: temp.getIsAgreed().values()){
            if(b){
                counter+=1;
            }
        }
        if(counter == temp.getIsAgreed().values().size()){
            return false;
        }
        else{
            if(temp.getIsAgreed().get(username)){
                return true;
            }
            return false;
        }
    }

    /**
     * A method that checks if the meeting can be unconfirmed by the inputted user.
     * @param id ID of the trade the meeting is attatched to.
     * @param username The username of the user in question.
     * @return true, iff the user was the last one to confirm a meeting meeting happened and both
     * traders involved haven't mutually confirmed.
     * */
    public boolean canUndoConfirm(int id, String username){
        Meeting temp = meetings.get(id);
        temp.getIsConfirmed().values().size();
        int counter = 0;
        for(boolean b: temp.getIsConfirmed().values()){
            if(b){
                counter+=1;
            }
        }
        if(counter == temp.getIsConfirmed().values().size()){
            return false;
        }
        else{
            if(temp.getIsConfirmed().get(username)){
                return true;
            }
            return false;
        }
    }

    /**
     * Undoes a user's decision to confirm the trade.
     * Precondition: The user is involved with the trade of trade ID 'id' and the only user that has
     * agreed to the trade is the inputted user.
     * @param id The ID of the meeting in question.
     * @param user The username of the user in question.
     */
    public void undoConfirm(int id, String user) {
        Meeting temp = meetings.get(id);
        meetings.get(id).setConfirm(user,false);
//        HashMap tempMap = temp.getIsConfirmed();
//        tempMap.replace(user, false);
//        temp.setIsConfirmed(tempMap);
    }

    /**
     * Checks whether the meeting with the given id can be undone.
     * @param id
     * @return
     */
    public boolean meetingCanBeUndone(int id){
        return meetings.get(id).isEdited();
    }


    /**
     * Undoes the given meeting, effectively deleting it from the system.
     * @param id The id of the meeting
     */
    public void undoMeetingProposal(int id){
        meetings.remove(id);
    }

    /**
     * Returns the edits made to the given meeting.
     * @param id The id of the meeting
     * @return A hashmap representing the edits made to the meeting
     */
    public HashMap<String, Integer> getEdits(int id) { return meetings.get(id).getNumberOfEdits(); }

    /**
     * Returns the meeting's date in string format
     * @param id the id of the meeting
     * @return The date in yyyy-mm-dd format
     */
    public String getMeetingDate(int id){
        return meetings.get(id).getTradeDate().toString();
    }

    /**
     * Returns if the given date is after the date of the meeting with the given id
     * @param id The id of the meeting
     * @param date The date to compare to
     * @return true if date is after the meeting date, false otherwise
     */
    public boolean dateIsAfterMeeting(int id, LocalDate date){
        if(date.isBefore(meetings.get(id).getTradeDate())){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Returns the meeting's current location
     * @param id The id of the meeting
     * @return The location
     */
    public String getMeetingLocation(int id){
        return meetings.get(id).getLocation();
    }

    public Integer getEditsLeft(int id, String username){
        return 3-meetings.get(id).getNumberOfEdits().get(username);
    }

    /**
     * Returns true if the user has agreed to the meeting.
     * @param id The id of the meeting
     * @param username The username of the user
     * @return true iff the user has agreed to the meeting.
     */
    public boolean hasAgreed(int id, String username){
        if(meetings.get(id).getIsAgreed().containsKey(username)){
            return meetings.get(id).isAgreed(username);
        }
        else{
            return false;
        }
    }

    /**
     * Returns whether both users in the given meeting have agreed to the meeting location.
     * @param id The id of the meeting
     * @return true iff both have agreed.
     */
    public boolean bothAgreed(int id){
        if(meetings.get(id).getIsAgreed().values().size()==2){
            for(boolean b: (meetings.get(id).getIsAgreed().values())){
                if(!b){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns whether the user has agreed confirmed the meeting with the given id
     * @param id The id of the meeting
     * @param username The username of the user
     * @return true iff they have confirmed.
     */
    public boolean hasConfirmed(int id, String username){
        return meetings.get(id).hasConfirmed(username);
    }

    /**
     * Returns whether both users have confirmed the meeting with the given id.
     * @param id The id of the meeting.
     * @return true iff they have both confirmed.
     */
    public boolean bothConfirmed(int id){
        if(meetings.get(id).getIsConfirmed().values().size()==2){
            for(boolean b: (meetings.get(id).getIsConfirmed().values())){
                if(!b){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Used to set up the second meeting for a temporary trade.
     * @param id
     */
    public void setReturnInfo(int id){
        meetings.get(id).setReturnLocation(meetings.get(id).getLocation());
        meetings.get(id).setReturnDate(meetings.get(id).getTradeDate().plusMonths(1));
        meetings.get(id).setBothConfirm(false);
    }

    /**
     * Sets the given meeting status to completed.
     * @param id The id of the meeting.
     */
    public void setMeetingCompleted(int id){
        meetings.get(id).setTradeStatus("Completed");
    }

    /**
     * Returns the return location of the meeting.
     * @param id The id of the meeting
     * @return The location of the meeting.
     */
    public String getReturnLocation(int id){
        return meetings.get(id).getReturnLocation();
    }

    /**
     * Returns the return date of the trade with the given id.
     * @param id The id of the trade
     * @return The date of the return.
     */
    public String getReturnDate(int id){
        return meetings.get(id).getReturnDate().toString();
    }

    /**
     * Makes both users in a meeting disagree to the meeting.
     * @param id The id of the meeting.
     */
    public void setBothDisagree(int id){
        meetings.get(id).bothDisagree();
    }

    @Override
    public String getIdentifier() {
        return "MeetingManager";
    }

    /**
     * Returns if the max number of edits to the meeting have been reached.
     * @param id The id of the trade
     * @return Whether or not the max number of edits has been reached.
     */
    public boolean isMaxEditsReached(int id){
        for(Integer i: meetings.get(id).getNumberOfEdits().values()){
            if(i<3){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the given date is after the return date of meeting with given id
     * @param id The id of the meeting
     * @param date The date to check
     * @return True if date is after, false otherwise
     */
    public boolean dateIsAfterReturnMeeting(int id, LocalDate date){
        if(date.isBefore(meetings.get(id).getReturnDate().plusMonths(1))){
            return false;
        }
        else{
            return true;
        }

    }
}