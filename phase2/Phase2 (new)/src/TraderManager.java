import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderManager {
    HashMap<String, Trader> users;

    /**
     * Constructor for TraderManager with no users
     */
    public TraderManager(){
        users = new HashMap<>();
    }

    /**
     * Constructor for TraderManager given a HashMap
     * @param users The hashmap of Traders to users.
     */
    public TraderManager(HashMap<String, Trader> users){
        this.users = users;
    }


    /**
     * The login method that returns True if the given credentials match a user in the system, and false otherwise.
     * @param username The username of the user
     * @param password The password of the user
     * @return true if the credentials match, false otherwise.
     */
    public boolean login(String username, String password){
        if(users.containsKey(username)){
            return users.get(username).getPassword().equals(password);
        }
        return false;
    }

    /**
     * Returns a list of the usernames of all users that have been flagged.
     * @return list of the usernames of all users that have been flagged.
     */
    public List<String> getListOfFlagged(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> flaggedUser: this.users.entrySet()){
            if(flaggedUser.getValue().isFlagged()){
                temp.add(flaggedUser.getKey());
            }
        }
        return temp;
    }

    /**
     * @return a list of the usernames of all users that have requested to unfreeze their accounts.
     */
    public List<String> getAllRequestsToUnfreeze(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> unfreezeRequest: this.users.entrySet()){
            if(unfreezeRequest.getValue().isRequestToUnfreeze()){
                temp.add(unfreezeRequest.getKey());
            }
        }
        return temp;
    }

    /**
     * Returns true if the username is not taken, returns false if the username is taken.
     * @param username The username to be checked
     * @return true if username is taken, false otherwise
     */
    public boolean isUsernameAvailable(String username){
        return !users.containsKey(username);
    }

    /**
     * @return A list of strings of all usernames in the system.
     */
    public List<String> getTraders(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Trader> usernames: this.users.entrySet()){
            temp.add(usernames.getKey());
        }
        return temp;
    }

}
