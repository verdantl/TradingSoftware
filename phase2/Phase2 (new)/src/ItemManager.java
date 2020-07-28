//package items;

//import users.Trader;

import java.io.Serializable;
import java.util.*;

public class ItemManager implements Serializable {
    HashMap<Integer, Item> items;
    private int idCounter;

    /**
     * Contructor for a new ItemManager class
     * @param items a Hashmap that maps item ids to the item in the system
     */
    public ItemManager(HashMap<Integer, Item> items){
        this.items = items;
        if (items.keySet().size() != 0) {
            idCounter = Collections.max(items.keySet()) + 1;
        }
        else{
            idCounter = 0;
        }
    }

    /**
     * Creates a new item to be added to the system
     * @param name the name of the item
     * @param owner the username of the trader who owns the item
     * @return the id of the item
     */
    public int addItem(String name, String owner){
        Item item = new Item(idCounter, name, owner);
        items.put(idCounter, item);
        idCounter++;
        return idCounter - 1;
    }

    /**
     * Adds item details to an item
     * @param itemID the id of the item
     * @param category the category of the item
     * @param description the description of the item
     * @param quality the quality of the item
     */
    public void addItemDetails(Integer itemID, String category, String description, int quality){
        Item item = items.get(itemID);
        item.setCategory(category);
        item.setDescription(description);
        item.setQualityRating(quality);
    }

    /**
     * Getter for items needing approval
     * @return A list of integers representing the id's of the items requiring approval
     */
    public List<Integer> getItemsNeedingApproval(){
        ArrayList<Integer> approvalNeeded = new ArrayList<>();
        for (Item item: items.values()){
            if (item.getStatus() == ItemStatus.REQUESTED){
                approvalNeeded.add(item.getId());
            }
        }
        return approvalNeeded;
    }

    /**
     * Gets trader with the given username's approved items
     * @param username The trader's username
     * @return Return the trader's approved items
     */
    public List<Item> getApprovedItems(String username){
        List<Item> approvedItems = new ArrayList<>();
        Set<Integer> ids = items.keySet();

        for (Integer id: ids){
            if(items.containsKey(id)) {
                Item item = items.get(id);
                if (item.getOwner().equals(username) && item.getStatus() != ItemStatus.REQUESTED) {
                    approvedItems.add(item);
                }
            }
        }

        return approvedItems;
    }

    /**
     * Gets trader with the given username's approved items IDs.
     * Used for controllers, whom should not have access to an instance of an entity.
     * @param username The trader's username
     * @return Return the trader's approved items' IDs
     */
    public List<Integer> getApprovedItemsIDs(String username){
        List<Integer> approvedItems = new ArrayList<>();
        Set<Integer> ids = items.keySet();

        for (Integer id: ids){
            if(items.containsKey(id)) {
                Item item = items.get(id);
                if (item.getOwner().equals(username) && item.getStatus() != ItemStatus.REQUESTED) {
                    approvedItems.add(item.getId());
                }
            }
        }

        return approvedItems;
    }

    /**
     * Gets all items in the system not owned by the inputted user.
     * Used for controllers, whom should not have access to an instance of an entity.
     * @param username The trader's username
     * @return Return the trader's approved items' IDs
     */
    public List<Integer> getAllApprovedItemsIDs(String username){
        List<Integer> approvedItems = new ArrayList<>();
        Set<Integer> ids = items.keySet();

        for (Integer id: ids){
            if(items.containsKey(id)) {
                Item item = items.get(id);
                if (!item.getOwner().equals(username) && item.getStatus() != ItemStatus.REQUESTED) {
                    approvedItems.add(item.getId());
                }
            }
        }
        return approvedItems;
    }

    /**
     * Converts all the approved items into a String representation
     * @param username The Trader's username
     * @return Return the string representation of approved items of the Trader with the given username
     */
    public List<String> getApprovedItemsInString(String username){
        List<Item> approvedItems = getApprovedItems(username);

        return convertItemListToString(approvedItems);
    }

    /**
     * Converts all the proposed items into a String representation
     * @param username The Trader's username
     * @return Return the string representation of proposed items of the Trader with the given username
     */
    public List<String> getProposedItemsInString(String username){
        List<Item> proposedItems = getProposedItems(username);

        return convertItemListToString(proposedItems);
    }

    /**
     * Returns a single item in string form.
     * @param itemId the item in question.
     * @return the string representation of the item corresponding to itemId
     */
    public String getItemInString(Integer itemId){
        return items.get(itemId).toString();
    }

    /**
     * Converts the list of items with the given list of item ids into a String representation
     * @param itemIds List of item ids
     * @return Return the string representation of the list of items
     */
    public List<String> getListOfItemsInString(List<Integer> itemIds){
        return convertItemListToString(getListOfItems(itemIds));
    }


    /**
     * Gets trader with the given username's proposed items
     * @param username The trader's username
     * @return Return the trader's proposed items
     */
    public List<Item> getProposedItems(String username){
        List<Item>  proposedItems = new ArrayList<>();
        Set<Integer> ids = items.keySet();

        for (Integer id: ids){
            if(items.containsKey(id)) {
                Item item = items.get(id);
                if (item.getOwner().equals(username) && item.getStatus() == ItemStatus.REQUESTED) {
                    proposedItems.add(item);
                }
            }
        }

        return proposedItems;
    }

    /**
     * Returns list of items based on the given list of item ids
     * @param itemIds List of item ids
     * @return Return list of items
     */
    public List<Item> getListOfItems(List<Integer> itemIds){
        List<Item> itemList = new ArrayList<>();
        for(Integer id: itemIds){
            if(items.containsKey(id)) {
                itemList.add(items.get(id));
            }
        }

        return itemList;
    }

    /**
     * Returns the username of the owner whom owns the item registered to the inputted item ID.
     * @param itemId the item in question.
     * @return the username of the owner of the item.
     */
    public String getOwner(Integer itemId){
        return items.get(itemId).getOwner();
    }

    /**
     * Returns the item of the inputted item ID.
     * @param itemId the item in question.
     * @return the Item object with the inputted item ID.
     */
    public Item getItem(Integer itemId){ return items.get(itemId); }

    /**
     * Changes the status of the item with the given id to AVAILABLE
     * @param id Id of the item
     */
    public void changeStatusToAvailable(Integer id){
        items.get(id).setStatus(ItemStatus.AVAILABLE);
    }

    /**
     * Changes the status of the item with the given id to UNAVAILABLE
     * @param id Id of the item
     */
    public void changeStatusToUnavailable(Integer id){
        items.get(id).setStatus(ItemStatus.UNAVAILABLE);
    }

    /**
     * Changes the status of the item with the given id to UNAVAILABLE
     * @param id Id of the item
     */
    public void changeStatusToRequested(Integer id){
        items.get(id).setStatus(ItemStatus.REQUESTED);
    }

    /**
     * Removes them item with the given id
     * @param id Id of the item to remove
     */
    public void removeItem(Integer id){
        items.remove(id);
    }

    /**
     * Adds new item to the hash map
     * @param item Item to add
     */
    public void addItem(Item item){
        items.put(item.getId(), item);
    }

    /**
     * Edits the description of the item with the given item
     * @param id Id of the Item
     * @param desc New description
     */
    public void editDescription(Integer id, String desc){
        items.get(id).setDescription(desc);
    }

    /**
     * Edits the name of the item with the given item
     * @param id Id of the Item
     * @param name New name
     */
    public void editName(Integer id, String name){
        items.get(id).setName(name);
    }

    /**
     * Edits the category of the item with the given item
     * @param id Id of the Item
     * @param category New category
     */
    public void editCategory(Integer id, String category){
        items.get(id).setCategory(category);
    }

    /**
     * Edits the rating of the item with the given item
     * @param id Id of the Item
     * @param rating New rating
     */
    public void editQualityRating(Integer id, int rating){
        items.get(id).setQualityRating(rating);
    }

//    public List<String> getUsersItemInString(List<String> usernames){
//        List<Item> itemList = new ArrayList<>();
//        for(Item item: items.values()){
//            if(usernames.contains(item.getOwner())){
//                itemList.add(item);
//            }
//        }
//        return convertItemListToString(itemList);
//    }


    private String convertItemToString(Item item){
        //TODO: Implement this method
        return "";
    }

    private List<String> convertItemListToString(List<Item> items){
        //TODO: Implement this method

        List<String> stringList = new ArrayList<>();
        for(Item item: items){
            stringList.add(convertItemToString(item));
        }
        return stringList;
    }

    public void approveItem(Integer id, boolean approved){
        if (approved){
            items.get(id).setStatus(ItemStatus.AVAILABLE);
        }
    }
}
