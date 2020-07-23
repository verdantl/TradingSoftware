import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderManager {
    HashMap<String, Trader> users;

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

    public HashMap<String, Trader> getAllUsers(){
        return users;
    }


    //Instead of the two methods below, since we're just reading in by use case, we can just have
    //the use case class add new users
    /**
     * Adds a new trader to the system
     * @param username The username of the trader
     * @param password The password of the trader
     */
    public void newTrader(String username, String password){
        users.put(username, new Trader(username, password));
    }



    /**
     * Adds a Trader object to users
     * @param t The trader object to add
     * @return Return true iff the trader object was added
     */
    public boolean addTrader(Trader t){
        if(containTrader(t.getUsername())){
            return false;
        }
        users.put(t.getUsername(), t);
        return true;
    }

    /**
     * Adds a list of Trader objects to users
     * @param traders The list of trader objects to add
     * @return Return true iff all of the trader objects were added
     */
    public boolean addAllTraders(List<Trader> traders){
        boolean addAll = true;
        for(Trader t: traders){
            if(!addTrader(t)){
                addAll = false;
            }
        }
        return addAll;
    }

    /**
     * Checks if the Trader with the given username exists in users
     * @param username Trader's username
     * @return True iff the Trader exists
     */
    public boolean containTrader(String username){
        return users.containsKey(username);
    }

    /**
     * Removes a trader object
     * @param t The Trader object
     * @return True iff the Trader object was removed
     */
    public boolean removeTrader(Trader t){
        return users.remove(t.getUsername(), t);
    }

    /**
     * Freezes a given account
     * @param username The account username that has to be frozen
     * @return Return true iff the account is successfully frozen. Return false
     * if the account is already frozen
     */
    public boolean freezeAccount(String username){
        Trader trader = users.get(username);
        if (trader.isFrozen()){
            return false;
        }
        trader.setFrozen(true);
        trader.setFlagged(false);
        return true;
    }

    /**
     * Unfreezes a given account
     * @param username The account username that has to be unfrozen
     * @return Return true iff the account is successfully unfrozen. Return false
     * if the account is already unfrozen
     */
    public boolean unfreezeAccount(String username){
        Trader trader = users.get(username);

        trader.setRequestToUnfreeze(false);
        if (!trader.isFrozen()){
            return false;
        }
        trader.setFrozen(false);
        trader.setFlagged(false);
        return true;
    }

    /**
     * Returns the tradeIds of the given user
     * @param username The username of the user
     * @return trade ids of username
     */
    public List<Integer> getTradeIds(String username){
        return users.get(username).getTradeIds();
    }

    /**
     * Returns the IDs of the items in the inputted Trader's wishlist.
     * @param username the username of the Trader in question
     * @return a list of the IDs of the items in the inputted user's wishlist.
     */
    public List<Integer> getWishlistIds(String username) {return users.get(username).getWishlist();}

    /**
     * Removes an item from a Traders's wishlist.
     * @param username the username of the Trader in question
     * @param id the ID of the item in question
     */
    public void removeFromWishlist(String username, Integer id){
        users.get(username).getWishlist().remove(id);
    }

    /**
     * Adds an item to a Trader's wishlist.
     * @param username the username of the Trader in question
     * @param id the ID of the item in question
     */
    public void addToWishlist(String username, Integer id){
        users.get(username).getWishlist().add(id);
    }

    /**
     * Returns true iff the Trader with the given username is frozen.
     * @param username the username of the Trader in question
     * @return true, iff the Trader with the given username's account is frozen.
     */
    public boolean getIsFrozen(String username){
        return users.get(username).isFrozen();
    }

    /**
     * Returns true iff the Trader with the given username has requested to have their account
     * unfrozen.
     * @param username the username of the Trader in question
     * @return a boolean representing if the trader has an unfreeze request
     */
    public boolean getRequestToUnfreeze(String username){
        return users.get(username).isRequestToUnfreeze();
    }

    /**
     * Sets the Trader's requestToUnfreeze variable to the inputted boolean.
     * @param username the username of the Trader in question
     * @param setting the new state of the Trader's requestToUnfreeze variable.
     */
    public void setRequestToUnfreeze(String username, boolean setting){
        users.get(username).setRequestToUnfreeze(setting);
    }

//    /**
//     * Returns a list of the usernames of all traders that have items that need approving.
//     * @return a list of strings representing the usernames of all traders with items needing approval
//     */
//    public List<String> getTradersNeedingItemApproval() {
//        List<String> temp = new ArrayList<>();
//        for (String username : users.keySet()) {
//            if (!users.get(username).getProposedItems().isEmpty()) {
//                temp.add(username);
//            }
//        }
//        return temp;
//    }

    /**
     * Deletes the item from all traders' lists.
     * @param id the id of the item
     */
    public void deleteItem(Integer id){
        for (Trader trader: users.values()){
            trader.deleteItem(id);
        }
    }

    /**
     * Getter for the number of items lent by the trader with the given username
     * @param username the username of the trader
     * @return the number of items lent by the trader with the username
     */
    public int getNumLent(String username){
        return users.get(username).getNumLent();
    }

    /**
     * Getter for the number of items borrowed by the trader with the given username
     * @param username the username of the trader
     * @return the number of items borrowed
     */
    public int getNumBorrowed(String username){
        return users.get(username).getNumBorrowed();
    }

//    /**
//     * Approves or rejects an item in a user's list.
//     * @param username The account usernamecontaining the item to be approved
//     * @param itemID The id of the item to be approved
//     * @param approved A boolean representing if the item has been approved or not
//     */
//    public void approveItem(String username, int itemID, boolean approved){
//        Trader trader = users.get(username);
//        trader.removeFromProposedItems(itemID);
//        if (approved) {
//            trader.addToWishlist(itemID);
//        }
//    }

//    /**
//     * Approves or rejects all of the items in an account's proposed items list.
//     * @param username The account containing the items to be approved
//     * @param approved A boolean representing if the items are all approved or not
//     */
//    public void approveAllItems(String username, boolean approved){
//        users.get(username).approveAllItems(approved);
//    }

}
