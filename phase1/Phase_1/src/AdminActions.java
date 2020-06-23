import java.util.ArrayList;
import java.util.Arrays;

public class AdminActions {
    // stores list of Trader objects
   // temporarily removing private Trader[] traderList;
    //potential idea: private ArrayList<String> admins;
    private ArrayList<Admin> admins;

    //I was thinking "is it necessary to store list of admin objects?"
    //because admin object only have id and password
    //and I think an admin does not need to view other admins id and password
    //so I was thinking instead of storing admin object, we just store list of usernames that are admin



    public AdminActions(Admin[] listOfAdmins){
        admins = new ArrayList<>();
        addAdmins(listOfAdmins);
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

    /**
     * Adds admins to the arraylist of admins
     * @param listOfAdmins an array of admins that will be added
     * @return true iff the admins were successfully added
     */
    // Here I don't know how to use the boolean, since successfully adding an admin doesn't have
    // any contingencies

    //the reason why I made the method to return boolean was to display message
    //like "this admin <admin> is already in the list"
    //and the boolean will tell the User whether admins were successfully added or not
    //it will be used by controller/presenter classes so that those classes can display relevant message

    //basically to send a confirmation message
    public boolean addAdmins(Admin[] listOfAdmins){
        boolean addedAll = true;
        for (Admin admin: listOfAdmins){
            if (!admins.contains(admin)){
                admins.add(admin);
            }else{
                addedAll = false;
            }
        }
        return addedAll;
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

    //parameter is trader's list of items that needs to be approved
    //admin can remove item in "items" (because of aliasing, the item will also
    // be removed in Trader's list of items)

    //I changed it to two methods, approve a single item and approve all the proposed items
    // I changed the parameter to the trader, got the items from the "want to be approved" list, and
    // approved them one by one.
    //In the trader class, we have an arraylist of "proposed items" and "actually lending items",

    //Not sure about including boolean as a parameter, can discuss
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

    //We don't have a place to store the limit currently, so we can store it here in the AdminActions or
    //in some other class.
    /**
     * Changes the limit for the number of...
     * @param newLimit The new limit for the number of ...
     */
    public void changeLimit(int newLimit){

    }


}