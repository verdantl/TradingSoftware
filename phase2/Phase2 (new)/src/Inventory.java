import java.util.List;

/**
 * An Inventory class
 */

public class Inventory {
    private List<Item> wishlist;
    private List<Item> borrowedItems;
    private List<Item> proposedItems;
    private List<Item> approvedItems;

    /**
     * Construct Inventory
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
     * @return String representation of Inventory
     */
    public String toString(){
        String s = "Inventory\n";

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
            s += i.toString()+"\n";
        }
        return s;

    }
}
