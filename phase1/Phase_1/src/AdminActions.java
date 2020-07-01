import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AdminActions {
    private ArrayList<Admin> admins;
    private static int limit = 1;
    private ArrayList<Admin> adminRequests;

    public AdminActions(ArrayList<Admin> admins, ArrayList<Admin> adminRequests){
        this.admins = admins;
        this.adminRequests = adminRequests;
    }
    //Temporary getter for the admins in case it's needed

    /**
     * A getter for a string version of all the admin requests for approval.
     * @return a string version of adminRequests
     */
    public StringBuilder getAdminRequests(){
        StringBuilder requests = new StringBuilder();
        for (int i = 0; i < adminRequests.size(); i++){
            if (requests.length() > 80){
                requests.append("\n");
            }
            requests.append(i + 1).append(". ");
            requests.append(adminRequests.get(i).getUsername());
        }
        return requests;
    }

    /**
     * Adds an admin request to the list
     * @param admin an admin request
     */
    public void addAdminRequest(Admin admin){
        adminRequests.add(admin);
    }
    /**
     * Finds an admin in adminRequests by
     * @param username the username of the admin being approved/rejected
     * @return the admin in adminRequests whose username matches the parameter
     */
    private Admin findRequestByUserName(String username){
        for (Admin admin: adminRequests){
            if (admin.getUsername().equals(username)){
                return admin;
            }
        }
        return null;
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
     * Approves/rejects admins to the arraylist of admins
     * @param username the username of the admin that is being approved/rejected
     * @param approved a boolean representing whether or not the admin is approved
     * @return true iff the admins were successfully added
     */
    public boolean approveAdmin(String username, boolean approved){
        Admin admin = findRequestByUserName(username);
        if (approved){
            admins.add(admin);
        }
        adminRequests.remove(admin);
        return approved;
    }

    /**
     * Adds admins to the list of admins
     * @param approved boolean representing whether or not the admins are approved
     * @return true if all admins were approved, and false if all admins were rejected
     */
    //Not sure about the array here, but just used it for now.
    public boolean approveAllAdmins(boolean approved){
        if (approved) {
            for (Admin adminRequest : adminRequests) {
                approveAdmin(adminRequest.getUsername(), approved);
            }
        }
        adminRequests.clear();
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
}
