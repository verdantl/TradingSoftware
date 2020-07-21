package items;

import java.util.ArrayList;
import java.util.List;

/**
 * An items.Inventory class
 */

public class Inventory {
    private List<Item> wishlist;
    private List<Item> borrowedItems;
    private List<Item> proposedItems;
    private List<Item> approvedItems;

    /**
     * Construct items.Inventory
     * @param wishlist Trader's wishlist
     * @param borrowedItems Trader's borrowed items
     * @param proposedItems Trader's items
     * @param approvedItems Trader's items waiting to be approved by the Admin
     */
    public Inventory (List<Item> wishlist, List<Item> borrowedItems,
                      List<Item> proposedItems, List<Item> approvedItems){
        this.wishlist = wishlist;
        this.borrowedItems = borrowedItems;
        this.approvedItems = approvedItems;
        this.proposedItems = proposedItems;
    }

    /**
     *
     * @return Trader's wishlist
     */
    public List<Item> getWishlist() {
        return wishlist;
    }

    /**
     *
     * @return Trader's borrowed items
     */

    public List<Item> getBorrowedItems() {
        return borrowedItems;
    }

    /**
     *
     * @return Trader's items
     */

    public List<Item> getApprovedItems() {
        return approvedItems;
    }

    /**
     *
     * @return Trader's items waiting to be approved
     */

    public List<Item> getProposedItems() {
        return proposedItems;
    }

    /**
     *
     * @param approvedItems Trader's items
     */

    public void setApprovedItems(List<Item> approvedItems) {
        this.approvedItems = approvedItems;
    }

    /**
     *
     * @param borrowedItems Items that the Trader borrowed
     */

    public void setBorrowedItems(List<Item> borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    /**
     *
     * @param proposedItems Items that the Trader prposed
     */

    public void setProposedItems(List<Item> proposedItems) {
        this.proposedItems = proposedItems;
    }

    /**
     *
     * @param wishlist Trader's wishlist
     */

    public void setWishlist(List<Item> wishlist) {
        this.wishlist = wishlist;
    }

    /**
     *
     * @return String representation of items.Inventory
     */
    public String toString(){
        String s = "items.Inventory\n";

        s += "Wishlist:\n";
        s += formatItems(wishlist)+"\n";
        s += "Approved Items:\n";
        s += formatItems(approvedItems)+"\n";
        s += "Borrowed Items:\n";
        s += formatItems(borrowedItems)+"\n";
        s += "Proposed Items:\n";
        s += formatItems(proposedItems);

        return s;

    }

    private String formatItems(List<Item> items){
        String s = "";
        for (Item i: items){
            s = s.concat(i.toString()+"\n");
        }
        return s;

    }

    /**
     * Adds the item to borrowedItems
     * @param item the item to be added
     */
    public void addToBorrowedItems(Item item){
        this.borrowedItems.add(item);
    }
    /**
     * Adds the item to approvedItems
     * @param item the item to be added
     */
    public void addToApprovedItems(Item item){
        this.approvedItems.add(item);
    }
    /**
     * Adds the item to proposedItems
     * @param item the item to be added
     */
    public void addProposedItems(Item item){
        this.proposedItems.add(item);
    }
    /**
     * Adds the item to wishlist
     * @param item the item to be added
     */
    public void addToWishlist(Item item){
        this.wishlist.add(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromProposedItems(Item item){
        this.proposedItems.remove(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromWishlist(Item item){
        this.wishlist.remove(item);
    }
    /**
     * Removes the item from approvedItems
     * @param item the item to be removed
     */
    public void removeFromApprovedItems(Item item){
        this.approvedItems.remove(item);
    }
    /**
     * Removes the item from borrowedItems
     * @param item the item to be removed
     */
    public void removeFromBorrowedItems(Item item){
        this.borrowedItems.remove(item);
    }

    /**
     * Approves or rejects all of the items in proposed items
     * @param approved Whether or not the items should be approved or not
     */
    public void approveAllItems(boolean approved){
        if (approved) {
            approvedItems.addAll(proposedItems);
        }
        proposedItems.clear();
    }
}
