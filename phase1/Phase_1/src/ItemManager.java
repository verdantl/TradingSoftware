import java.util.ArrayList;

public class ItemManager {
    private ArrayList<Item> requestedItems, approvedItems;

    public ItemManager(){
        requestedItems = new ArrayList<>();
        approvedItems = new ArrayList<>();
    }

    public ItemManager(ArrayList<Item> requestedItems, ArrayList<Item> approvedItems){
        this.approvedItems = approvedItems;
        this.requestedItems = requestedItems;
    }

    public ArrayList<Item> getApprovedItems() {
        return approvedItems;
    }

    public void setApprovedItems(ArrayList<Item> approvedItems) {
        this.approvedItems = approvedItems;
    }

    /**
     * adds a item to the ApprovedItems list
     * if the item is already in the list it returns false
     * @param approvedItem the item that is to be added to ApprovedItems
     * @return where the item was added
     */
    public boolean addApprovedItem(Item approvedItem){
        if(approvedItems.contains(approvedItem)){
            return false;
        }
        else{
            approvedItems.add(approvedItem);
            return true;
        }
    }

    public ArrayList<Item> getRequestedItems() {
        return requestedItems;
    }

    /**
     * adds a item to the requestedItems list
     * if the item is already in the list it returns false
     * @param requestedItem the item that is to be added to requestedItems
     * @return where the item was added
     */
    public boolean addRequestedItem(Item requestedItem){
        if(requestedItems.contains(requestedItem)){
            return false;
        }
        else{
            requestedItems.add(requestedItem);
            return true;
        }
    }

    public void setRequestedItems(ArrayList<Item> requestedItems) {
        this.requestedItems = requestedItems;
    }
}
