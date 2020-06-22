public class AdminActions {
    // stores list of Trader objects
    private Trader[] traderList;

    /**
     *
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

    public boolean addAdmins(Admin[] listOfAdmins){
        //this line will be removed
        return true;
    }

    /**
     *
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

    //parameter is trader's list of items that needs to be approved and the item that need to removed
    //admin can remove item in "items" (because of aliasing, the item will also
    // be removed in Trader's list of items)

    // or instead of passing item that needs to be removed, we could pass in the index
    // where the item is located?
    public boolean removeItems(Item[] items, Item item){

        //this line will be removed
        return true;

    }

    public boolean approveItems(Item[] items, Item item){
        //this line will be removed
        return true;

    }
    //other idea
    /*
    public boolean removeItems(ArrayList<Item> items, int[] listOfIndex){
        for (int index: listOfIndex){
            items.remove(index);
        }
    }
    same for approveItems method
     */


    public void changeLimit(int newLimit){

    }


}
