import java.util.ArrayList;
import java.util.Arrays;

public class AdminActions {
    // stores list of Trader objects
   // temporarily removing private Trader[] traderList;
    private ArrayList<Admin> admins;


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
    public boolean addAdmins(Admin[] listOfAdmins){
        for (Admin admin: listOfAdmins){
            if (!admins.contains(admin)){
                admins.add(admin);
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

    /**
     * Changes the limit for the number of...
     * @param newLimit The new limit for the number of ...
     */
    public void changeLimit(int newLimit){

    }


}
