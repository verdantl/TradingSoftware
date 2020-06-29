import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AdminActions {
    // stores list of Trader objects
   // temporarily removing private Trader[] traderList;
    //potential idea: private ArrayList<String> admins;
    private ArrayList<Admin> admins;
    private ArrayList<Trader> freezeAccount;

    //temporary private static variable
    private static int limit = 1;

    //I was thinking "is it necessary to store list of admin objects?"
    //because admin object only have id and password
    //and I think an admin does not need to view other admins id and password
    //so I was thinking instead of storing admin object, we just store list of usernames that are admin



    public AdminActions(ArrayList<Admin> admins){
        this.admins = admins;
    }
    //Temporary getter for the admins in case it's needed
    public ArrayList<Admin> getAdmins(){
        return admins;
    }

    /**
     * Freezes a given account
     * @param trader The account that has to be frozen
     * @return Return true iff the account is successfully frozen. Return false
     * if the account is already frozen
     */
    public boolean freezeAccount(Trader trader){
        if (trader.isFrozen()){
            return false;
        }
        trader.setFrozen(true);
        freezeAccount.add(trader);
        return true;
    }


    // Here I don't know how to use the boolean, since successfully adding an admin doesn't have
    // any contingencies

    //the reason why I made the method to return boolean was to display message
    //like "this admin <admin> is already in the list"
    //and the boolean will tell the User whether admins were successfully added or not
    //it will be used by controller/presenter classes so that those classes can display relevant message

    //basically to send a confirmation message
    /**
     * Adds admins to the arraylist of admins
     * @param username the username of the admin request
     * @param password the password of the admin request
     * @param approved a boolean representing whether or not the admin is approved
     * @return true iff the admins were successfully added
     */
    public boolean approveAdmin(String username, String password, boolean approved){
        if (approved){
            admins.add(new Admin(username, password));
        }
        return approved;
    }

    /**
     * Adds admins to the arraylist of admins
     * @param usernames the usernames from the admin requests
     * @param passwords the passwors from the admin requests
     * @param approved a boolean representing whether or not the admins are approved
     * @return true iff the admins were successfully added
     */
    //Not sure about the array here, but just used it for now.
    public boolean approveAllAdmins(String[] usernames, String[] passwords, boolean approved){
        if (approved) {
            for (int i = 0; i <  usernames.length; i++){
                approveAdmin(usernames[i], passwords[i], approved);
            }
        }
        return approved;
    }

    /**
     * Check whether the username is valid or not
     * @param username the username that needs to be checked
     * @return Return true iff the username is valid
     */
    public boolean checkUsername(String username){
        for (Admin admin: admins){
            if (admin.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    //potential method
    //if we make the parameter as a list, then in the controller class, we need to ask the user to
    // enter list of accounts
    // and in the controller class, we need to create a list and then pass it into the method
    // if we do that, we need to check the formatting and etc.
    // so I think it is better to allow user to type username one by one

//    public boolean addAdmins(String username){
//        if (admins.contains(username)){
//            return false;
//        }
//        admins.add(username);
//        return true
//    }

    /**
     * Unfreezes a given account
     * @param trader The account that has to be unfrozen
     * @return Return true iff the account is successfully unfrozen. Return false
     * if the account is already unfrozen
     */
    public boolean unfreezeAccount(Trader trader){
        if (!trader.isFrozen()){
            return false;
        }
        trader.setFrozen(false);
        freezeAccount.remove(trader);
        return true;
    }

    /**
     * Approves or rejects an item
     * @param trader The account containing the item to be approved
     * @param item The item to be approved
     * @param approved A boolean representing if the item has been approved or not
     * @return Return true iff the item has been successfully approved. Otherwise, return false.
     */
    public boolean approveItem(Trader trader, Item item, boolean approved){
        trader.removeFromProposedItems(item);
        if (approved) {
            trader.addToWantToLend(item);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Approves or rejects all of the items in an account's proposed items list.
     * @param trader The account containing the items to be approved
     * @param approved A boolean representing if the items are all approved or not
     * @return Return true iff the items are all successfully approved. Otherwise, return false.
     */
    public boolean approveAllItems(Trader trader, boolean approved){
        ArrayList<Item> proposedItems = trader.getProposedItems();
        for (Item item: proposedItems){
            approveItem(trader, item, approved);
        }
        return approved;
    }
    /**
     * @param traders List of all the traders in the system
     * @param atLeast The trader's number of borrowed items must be at least 'atLeast' less than
     *                their number of lent items
     * @param maxIncomplete The max number of incomplete transactions that a trader can have
     * @param maxWeekly The max number of transactions that a trader can have in a week
     * @return Return list of trader accounts that needs to be frozen
     */

    public List<Trader> getListOfFlaggedAccounts(List<Trader> traders, int atLeast, int maxIncomplete, int maxWeekly){
        List<Trader> accounts = new ArrayList<>();
        for (Trader trader: traders){
            if (trader.getNumBorrowed() + atLeast > trader.getNumLent() && atLeast != 0){
                accounts.add(trader);
            }else if(trader.getNumIncompleteTransactions() > maxIncomplete){
                accounts.add(trader);
            }else if(trader.getNumWeeklyTransactions() > maxWeekly){
                accounts.add(trader);
            }
        }
        return accounts;
    }

    /**
     * Changes the limit for the number of items required to be lent
     * @param newLimit The new limit for the number of lent items required
     */
    public void changeLimit(int newLimit){
        limit = newLimit;
    }


    /**
     * Checks the given credentials and returns the user.
     * @param username The username of the user
     * @param password  The password of the user
     * @return The user if the credentials match, and null otherwise.
     */
    public Admin checkCredentials(String username, String password){
        for(Admin u: admins){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    return u;
                }
                else{
                    return null;
                }
            }
        }
        return null;

    }

    /**Getter for the frozen accounts
     * @return a list of frozen accounts in this system
     */
    public ArrayList<Trader> getFreezeAccount() {return this.freezeAccount;}
}
