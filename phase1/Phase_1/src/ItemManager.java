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

    public void setRequestedItems(ArrayList<Item> requestedItems) {
        this.requestedItems = requestedItems;
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

    /**
     * changes the name of a given item
     * @param i the given item
     * @param name the new name
     */
    public void changeName(Item i, String name){
        i.setName(name);
    }

    /**
     * changes the category of a given item
     * @param i the given item
     * @param category the new category
     */
    public void changeCategory(Item i, String category){
        i.setCategory(category);
    }

    /**
     * changes the description of a given item
     * @param i the given item
     * @param description the new description
     */
    public void changeDescription(Item i, String description){
        i.setDescription(description);
    }

    /**
     * changes the owner of a given item
     * @param i the given item
     * @param owner the new owner
     */
    public void changeOwner(Item i, Trader owner){
        i.setOwner(owner);
    }

    /**
     * changes the qualityRating of a given item
     * @param i the given item
     * @param qualityRating the new qualityRating
     */
    public void changeQualityRating(Item i, int qualityRating){
        i.setQualityRating(qualityRating);
    }
}
