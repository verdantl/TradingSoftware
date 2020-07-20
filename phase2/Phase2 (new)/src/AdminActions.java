import java.util.ArrayList;
import java.util.HashMap;

public class AdminActions {
    private final HashMap<String, Admin> admins;
    private final HashMap<String, Admin> adminRequests;

    /**
     * Constructor for the AdminActions class
     * @param admins a HashMap of an admin's username to the Admin
     * @param adminRequests a HashMap of the admin requester's username to their account
     */
    public AdminActions(HashMap<String, Admin> admins, HashMap<String, Admin> adminRequests){
        this.admins = admins;
        this.adminRequests = adminRequests;
    }

    /**
     * A getter for a string version of all the admin requests for approval.
     * @return a string version of adminRequests
     */
    public StringBuilder getAdminRequests(){
        StringBuilder requests = new StringBuilder();
        for (String admin: adminRequests.keySet()){
            int LINELIMIT = 80;
            if (requests.length() > LINELIMIT){
                requests.append("\n");
            }
            requests.append(admin);
        }
        return requests;
    }

    /**
     * Getter for the hashmap of admins
     * @return A hashmap of an admins username to their account
     */
    public HashMap<String, Admin> getAdmins(){
        return admins;
    }

    /**
     * Getter for the list of admin requests
     * @return An arraylist of admin requests
     */
    public HashMap<String, Admin> getListOfRequestedAdmins(){
        return adminRequests;
    }

    /**
     * Adds an admin request to the list
     * @param admin an admin request
     */
    public void addAdminRequest(Admin admin){
        adminRequests.put(admin.getUsername(), admin);
    }

    /**
     * Finds an admin in adminRequests by
     * @param username the username of the admin being approved/rejected
     * @return the admin in adminRequests whose username matches the parameter
     */
    private Admin findRequestByUserName(String username){
        return adminRequests.getOrDefault(username, null);
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
            admins.put(username, admin);
        }
        adminRequests.remove(username);
        return approved;
    }

    /**
     * Adds admins to the list of admins
     * @param approved boolean representing whether or not the admins are approved
     * @return true if all admins were approved, and false if all admins were rejected
     */
    public boolean approveAllAdmins(boolean approved){
        if (approved) {
            admins.putAll(adminRequests);
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
        return admins.containsKey(username);
    }

    /**
     * Verifies the login information for the admin
     * @param username the username of the login
     * @param password the password for the login
     * @return a boolean representing if the login was successful or not
     */
    public boolean checkCredentials(String username, String password) {
        if (admins.containsKey(username)) {
            return admins.get(username).getPassword().equals(password);
        } else {
            return false;
        }
    }

    /**
     * Changes the username for this admin user's account
     * @param username the username for the admin
     * @param newUserName the new username for the admin
     */
    public void changeUsername(String username, String newUserName){
        if (admins.containsKey(username)) {
            Admin admin = admins.get(username);
            admins.remove(username);
            admins.put(newUserName, admin);
        }
    }

    /**
     * Changes the password for this admin user's account
     * @param username the username for the admin
     * @param password the new password for the admin
     */
    public void changePassword(String username, String password){
        if (admins.containsKey(username)){
            admins.get(username).setPassword(password);
        }
    }
}
