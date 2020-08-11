package com.example.phase2.users;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminActions extends Manager implements Serializable, Loginable {
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
     * Adds a new admin request to the system
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public void newAdmin(String username, String password){
        admins.put(username, new Admin(username, password));
    }

    /**
     * Approves/rejects admins to the arraylist of admins
     * @param username the username of the admin that is being approved/rejected
     * @param approved a boolean representing whether or not the admin is approved
     */
    public void approveAdmin(String username, boolean approved){
        admins.get(username).setApproved(approved);
        if (!admins.get(username).isApproved()){
            admins.remove(username);
        }
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
    @Override
    public boolean checkUsername(String username){
        return !admins.containsKey(username);
    }

    /**
     * Verifies the login information for the admin
     * @param username the username of the login
     * @param password the password for the login
     * @return a boolean representing if the login was successful or not
     */
    public boolean login(String username, String password) {
        if (admins.containsKey(username)) {
            System.out.println(admins.get(username).getPassword());
            return admins.get(username).isApproved() && admins.get(username).getPassword().equals(password);
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
    public ArrayList<String> getRequestedAdmins(){
        ArrayList<String> requestedAdmins = new ArrayList<>();
        for(String username: admins.keySet()){
            if(!admins.get(username).isApproved()){
                requestedAdmins.add(username);
            }
        }
        return requestedAdmins;
    }

    @Override
    public String getIdentifier() {
        return "AdminActions";
    }
}