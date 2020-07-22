import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActions {
    private final HashMap<String, Admin> admins;

    /**
     * Constructor for the AdminActions class
     * @param admins a HashMap of an admin's username to the Admin
     */
    public AdminActions(HashMap<String, Admin> admins){
        this.admins = admins;
    }

    /**
     * A getter for a string version of all the admin requests for approval.
     * @return a string version of adminRequests
     */
    public StringBuilder getAdminRequests(){
        int LINE_LIMIT = 80;
        StringBuilder requests = new StringBuilder();
        List<String> adminRequests = getRequestedAdmins();
        for (String admin: adminRequests){
            if (requests.length() > LINE_LIMIT){
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
     * Adds an admin request to the list
     * @param admin an admin request
     */
    public void addAdmin(Admin admin){
        admins.put(admin.getUsername(), admin);
    }

    /**
     * Finds an admin in adminRequests by
     * @param username the username of the admin being approved/rejected
     * @return the admin in adminRequests whose username matches the parameter
     */
    private Admin findRequestByUserName(String username){
        return admins.getOrDefault(username, null);
    }


    /**
     * Approves/rejects admins to the arraylist of admins
     * @param username the username of the admin that is being approved/rejected
     * @param approved a boolean representing whether or not the admin is approved
     */
    public void approveAdmin(String username, boolean approved){
        admins.get(username).setApproved(approved);
    }

    /**
     * Adds admins to the list of admins
     * @param approved boolean representing whether or not the admins are approved
     * @param usernames List of usernames
     */
    public void approveAllAdmins(List<String> usernames, boolean approved){
        for(String username: usernames){
            admins.get(username).setApproved(approved);
        }
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

    /**
     * Gets list of requested admins
     * @return Return a list of requested admins
     */
    public List<String> getRequestedAdmins(){
        List<String> requestedAdmins = new ArrayList<>();
        for(String username: admins.keySet()){
            if(!admins.get(username).isApproved()){
                requestedAdmins.add(username);
            }
        }
        return requestedAdmins;
    }

    /**
     * Gets list of approved admins
     * @return Return a list of approved admins
     */
    public List<String> getApprovedAdmins(){
        List<String> approvedAdmins = new ArrayList<>();
        for(String username: admins.keySet()){
            if(admins.get(username).isApproved()){
                approvedAdmins.add(username);
            }
        }
        return approvedAdmins;
    }
}
