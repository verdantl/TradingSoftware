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

    public ArrayList<Item> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(ArrayList<Item> requestedItems) {
        this.requestedItems = requestedItems;
    }
}
