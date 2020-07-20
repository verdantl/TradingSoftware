import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeMeetingManager {

    //stores Trade Id as a key
    //a parallel hashmap I guess

    //I originally wanted to store both the trade and meeting into one
    //but there wasn't a way to do that (you could do <Integer, List<Object>>
    //but can't avoid casting)
    //so I made two hashmaps
    private HashMap<Integer, Trade> trades;
    private HashMap<Integer, Meeting> meetings;

    /**
     * Updates the status of the meeting as completed for Trader with the given username
     * @param id Id of the trade
     * @param username The trader's username
     */
    public void confirmMeeting(int id, String username){
        meetings.get(id).setConfirm(username, true);

        String receiver = trades.get(id).getReceiver();
        String initiator = trades.get(id).getInitiator();

        boolean receiverConfirmed = meetings.get(id).getIsConfirmed().get(receiver);
        boolean initiatorConfirmed = meetings.get(id).getIsConfirmed().get(initiator);

        if (receiverConfirmed && initiatorConfirmed){
            trades.get(id).setCompleted(true);
        }

    }

    /**
     * Cancels the meeting confirmation
     * @param id Id of the trade
     * @param username The trader's username
     */
    public void cancelConfirmation(int id, String username){
        meetings.get(id).setConfirm(username, false);
    }

    /**
     * Allows trader to disagree on meeting
     * @param id Id of the trade
     * @param username The trader's username
     */
    public void disagreeOnMeeting(int id, String username){
        meetings.get(id).setAgree(username, false);
    }

    /**
     * Changes the location of the meeting
     * @param id Id of the trade
     * @param location New location
     */
    public void editLocation(int id, String location){
        meetings.get(id).setLocation(location);
    }

    /**
     * Changes the meeting date
     * @param id Id of the trade
     * @param date New meeting date
     */
    public void editTradeDate(int id, LocalDate date){
        meetings.get(id).setTradeDate(date);
    }

    /**
     *
     * @param id Id of the trade
     * @return Returns the list of item ids
     */
    public List<Integer> getItems(int id){
        //I have to use casting here
        return trades.get(id).getItems();
    }

    /**
     * Allows the trader with the given username to agree on the meeting
     * @param id
     */
    public void agreeOnMeeting(int id, String username){
        meetings.get(id).setAgree(username, true);

    }

    /**
     * Calculates the number of incomplete trades based on given list of trade ids
     * @return Return number of incomplete trades
     */
    public int getNumIncompleteTrades(List<Integer> ids){
        List<Trade> listOfTrades = new ArrayList<>();
        int numIncomplete = 0;

        for (Integer i: ids){
            listOfTrades.add(trades.get(i));
        }

        for (Trade t: listOfTrades){
            if(!t.getCompleted()){
                numIncomplete++;
            }
        }

        return numIncomplete;

    }

    /**
     * Calculates the number of weekly trades based on given list of trade ids
     * @param ids List of trade ids
     * @return Return number of trades made within a week
     */
    public int getNumWeeklyTrades(List<Integer> ids){
        LocalDate startDate = calculateStartDate();
        LocalDate endDate = startDate.plusDays(7);

        int numWeekly = 0;

        for (Integer i: ids){
            LocalDate date = trades.get(i).getCreatedDate();
            if (startDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0){
                numWeekly++;
            }
        }
        return  numWeekly;
    }

    //calculates the start of the week based on today's date
    private LocalDate calculateStartDate(){
        LocalDate date = null;

        LocalDate today = LocalDate.now();
        switch (today.getDayOfWeek()){
            case MONDAY:
                date = today;
                break;
            case TUESDAY:
                date = today.minusDays(1);
                break;
            case WEDNESDAY:
                date = today.minusDays(2);
                break;
            case THURSDAY:
                date = today.minusDays(3);
                break;
            case FRIDAY:
                date = today.minusDays(4);
                break;
            case SATURDAY:
                date = today.minusDays(5);
                break;
            case SUNDAY:
                date = today.minusDays(6);
                break;
        }
        return date;
    }

    /**
     *
     * @param id Id of the trade
     * @return Return the username of the initiator and receiver
     */
    public List<String> getTradeInfo(int id){
        List<String> info = new ArrayList<>();
        Trade t = trades.get(id);
        info.add(t.getInitiator());
        info.add(t.getReceiver());
        return info;
    }

    /**
     * Removes trade and meeting object with the given id
     * @param id Id of the trade
     */
    public void removeTrade(int id){
        trades.remove(id);
        meetings.remove(id);
    }

    /**
     * Increases the number of edits
     * @param id Id of the trade that was edited
     * @param username Username of the trader who edited
     */
    public void increaseNumEdits(int id, String username){
        meetings.get(id).increaseNumberOfEdits(username);
    }

    /**
     * Checks whether the trader with the given username can edit or not
     * @param id Id of the trade
     * @param username Trader's username
     * @return Return true iff the trader can edit. Otherwise return false
     */
    public boolean canEdit(int id, String username){
        return meetings.get(id).getNumberOfEdits().get(username) < 3;
    }

}
