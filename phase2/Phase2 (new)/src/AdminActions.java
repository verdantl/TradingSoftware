import java.util.ArrayList;

public class AdminActions {
    private final ArrayList<Admin> admins;
    private final ArrayList<Admin> adminRequests;
    private final int LINELIMIT = 80;
    public AdminActions(ArrayList<Admin> admins, ArrayList<Admin> adminRequests){
        this.admins = admins;
        this.adminRequests = adminRequests;
    }
    /**
     * A getter for a string version of all the admin requests for approval.
     * @return a string version of adminRequests
     */
    public StringBuilder getAdminRequests(){
        StringBuilder requests = new StringBuilder();
        for (int i = 0; i < adminRequests.size(); i++){
            if (requests.length() > LINELIMIT){
                requests.append("\n");
            }
            requests.append(adminRequests.get(i).getUsername());
            if (i != adminRequests.size() - 1) {
                requests.append(" | ");
            }
        }
        return requests;
    }

    public ArrayList<Admin> getAdmins(){
        return admins;
    }

    public ArrayList<Admin> getListOfRequestedAdmins(){
        return adminRequests;
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
        trader.setFlagged(false);
        return true;
    }

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

    /**
     * Unfreezes a given account
     * @param trader The account that has to be unfrozen
     * @return Return true iff the account is successfully unfrozen. Return false
     * if the account is already unfrozen
     */
    public boolean unfreezeAccount(Trader trader){
        trader.setRequestToUnfreeze(false);
        if (!trader.isFrozen()){
            return false;
        }
        trader.setFrozen(false);
        trader.setFlagged(false);
        return true;
    }

    /**
     * Approves or rejects an item
     * @param trader The account containing the item to be approved
     * @param item The item to be approved
     * @param approved A boolean representing if the item has been approved or not
     */
    public void approveItem(Trader trader, Item item, boolean approved){
        trader.removeFromProposedItems(item);
        if (approved) {
            trader.addToWantToLend(item);
        }
    }

    /**
     * Approves or rejects all of the items in an account's proposed items list.
     * @param trader The account containing the items to be approved
     * @param approved A boolean representing if the items are all approved or not
     */
    public void approveAllItems(Trader trader, boolean approved){
        ArrayList<Item> proposedItems = new ArrayList<>(trader.getProposedItems());
        for (Item item: proposedItems){
            approveItem(trader, item, approved);
        }
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
