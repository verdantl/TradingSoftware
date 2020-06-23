import java.util.ArrayList;

public class TraderActions {
    private ArrayList<Trader> traders;

    /**
     * This is the constructor for reading in users.
     * @param traders A list of traders that were read in to the program
     */
    public TraderActions(ArrayList<Trader> traders){
        this.traders = traders;
    }

    /**
     * Adds item to list of wantToBorrow items of the Trader
     * @param trader The given trader whose item is to be added
     * @param item The item to be added
     */
    public void addToWantToBorrow(Trader trader, Item item){
        trader.addToWantToBorrow(item);
    }

    /**
     * Removes items from list of wantToBorrow items of the Trader
     * @param trader The trader who's item is to be removed
     * @param item The item to be removed
     */
    public void removeFromWantToBorrow(Trader trader, Item item){
        trader.removeFromWantToBorrow(item);
    }

    /*Okay so browsing items probably works with the presenter, so this method will return a list of available items that
    can be lent, and i will leave it to the presenter class to present it to the user.
    The current way im doing it isn't efficient because we have to compute a list of all the items everytime a user wants to
    browse them.*/

    /**
     * Returns a list of items that are available for lending
     * @return The list of items that are available for lending in the system
     */
    public ArrayList<Item> browseItems(){
        ArrayList<Item> availableItems = new ArrayList<>();
        for(Trader t: traders){
            availableItems.addAll(t.getWantToLend());
        }
        return availableItems;
    }

    /**
     * Adds the given trader to the list of traders.
     * @param trader The trader that is to be added to the system
     */
    public void addTrader(Trader trader){
        if(!traders.contains(trader)){
            traders.add(trader);
        }
    }
}
