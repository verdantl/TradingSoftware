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
        //will be removed
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

    //parameter is trader's list of items that needs to be approved
    //admin can remove item in "items" (because of aliasing, the item will also
    // be removed in Trader's list of items)
    public boolean approveItems(Item[] items){
        //will be removed
        return true;

    }

    public void changeLimit(int newLimit){

    }


}
