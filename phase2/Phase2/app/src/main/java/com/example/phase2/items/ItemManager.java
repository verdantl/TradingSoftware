package com.example.phase2.items;//package items;

//import users.Trader;

import com.example.phase2.highabstract.Manager;

import java.io.Serializable;
import java.util.*;

public class ItemManager extends Manager implements Serializable {
    protected HashMap<Integer, Item> items;
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
     * Updates the status of frozen user's items with either FROZEN or FROZEN_REQ
     * @param username The username of the frozen trader
     */
    public void setStatusForFrozenUser(String username){
        Set<Integer> ids = items.keySet();
        for(Integer id: ids){
            Item item = items.get(id);
            assert item != null;
            String owner = item.getOwner();
            ItemStatus status = item.getStatus();
            if(owner.equals(username)){
                if(status == ItemStatus.AVAILABLE){
                    item.setStatus(ItemStatus.FROZEN);
                }else if(status == ItemStatus.REQUESTED){
                    item.setStatus(ItemStatus.FROZEN_REQ);
                }
            }
        }
    }

    /**
     * Updates the status of the items of the trader who has been frozen or been inactive
     * @param username The username of the trader
     */
    public void setStatusForRegularUser(String username){
        Set<Integer> ids = items.keySet();
        for(Integer id: ids){
            Item item = items.get(id);
            assert item != null;
            String owner = item.getOwner();
            ItemStatus status = item.getStatus();
            if(owner.equals(username)){
                if(status == ItemStatus.FROZEN || status == ItemStatus.INACTIVE_AVA){
                    item.setStatus(ItemStatus.AVAILABLE);
                }else if(status == ItemStatus.INACTIVE_REQ || status == ItemStatus.FROZEN_REQ){
                    item.setStatus(ItemStatus.REQUESTED);
                }
            }
        }
    }

    /**
     * Updates the status of inactive user's items with either INACTIVE_AVA or INACTIVE_REQ
     * @param username The username of the inactive user
     */
    public void setStatusForInactiveUser(String username){
        Set<Integer> ids = items.keySet();
        for(Integer id: ids){
            Item item = items.get(id);
            assert item != null;
            String owner = item.getOwner();
            ItemStatus status = item.getStatus();
            if(owner.equals(username)){
                if(status == ItemStatus.AVAILABLE){
                    item.setStatus(ItemStatus.INACTIVE_AVA);
                }else if(status == ItemStatus.REQUESTED){
                    item.setStatus(ItemStatus.INACTIVE_REQ);
                }
            }
        }
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
                if (item.getOwner().equals(username) && item.getStatus() == ItemStatus.AVAILABLE) {
                    approvedItems.add(item.getId());
                }
            }
        }

        return approvedItems;
    }

    /**
     * Gets username's list of removedItems
     * @param username
     * @return
     */
    public List<Integer> getRemovedItemIds(String username){
        List<Integer> removedItems = new ArrayList<>();
        Set<Integer> ids = items.keySet();

        for (Integer id: ids){
            if(items.containsKey(id)) {
                Item item = items.get(id);
                if (item.getOwner().equals(username) && item.getStatus() == ItemStatus.REMOVED) {
                    removedItems.add(item.getId());
                }
            }
        }
        return removedItems;
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
                if (!item.getOwner().equals(username) && item.getStatus() == ItemStatus.AVAILABLE) {
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
    //TODO: unused method
    public List<String> getApprovedItemsInString(String username){
        List<Item> approvedItems = getApprovedItems(username);

        return convertItemListToString(approvedItems);
    }

    /**
     * Converts all the proposed items into a String representation
     * @param ids the ids of the proposed items
     * @return Return the string representation of proposed items of the Trader with the given username
     */
    //TODO: unused method
    public List<String> getProposedItemsInString(List<Integer> ids){
        List<Item> proposedItems = getListOfItems(ids);

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
     * Getter for item name.
     * @param itemId The ID of the item in question.
     * @return the item's name.
     */
    public String getItemName(Integer itemId) { return items.get(itemId).getName(); }

    /**
     * Getter for item description.
     * @param itemId The ID of the item in question.
     * @return the item's description.
     */
    public String getItemDescription(Integer itemId) { return items.get(itemId).getDescription(); }

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
    //TODO: unused method
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

    /**Return the status of the given item
     * @param id the id of the item
     * @return the status of the item
     */
    public ItemStatus getItemStatus(Integer id){
        return items.get(id).getStatus();
    }

    /**
     * Changes the status of the item with given id to FROZEN
     */
    public void changeStatusToFrozen(Integer id){
        items.get(id).setStatus(ItemStatus.FROZEN);
    }

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
     * Changes the status of the item with the given id to REMOVED
     * @param id Id of the item
     */
    public void changeStatusToRemoved(Integer id){
        items.get(id).setStatus(ItemStatus.REMOVED);
    }

    /**
     * Sets the status of the item with the given ID to 'REMOVED'
     * @param id Id of the item to remove
     */
    public void removeItem(Integer id){
        changeStatusToRemoved(id);
    }

    /**
     * Deletes the item with the given id from the system.
     * @param id Id of the item to delete
     */
    //TODO: unused method
    public void deleteItem(Integer id) { items.remove(id); }

    /**
     * Adds new item to the hash map
     * @param item Item to add
     */
    //TODO: unused method
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
    //TODO: unused method
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
        return "Item: " + item.getName() + "\n" +  "Owner: " + item.getOwner();
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

    /**
     * If inactive is true, sets the given list of item ids to inactive if they are available.
     * If inactive is false, sets the given list of item ids to available if they are inactive.
     * @param inactiveItems
     * @param inactive
     */
    //TODO: unused method
    public void setItemsInactive(List<Integer> inactiveItems, boolean inactive){
        if(inactive){
            for(Integer i:inactiveItems){
                if(items.get(i).getStatus()==ItemStatus.AVAILABLE){
                    items.get(i).setStatus(ItemStatus.INACTIVE);
                }
            }
        }
        else{
            for(Integer i:inactiveItems){
                if(items.get(i).getStatus()==ItemStatus.INACTIVE){
                    items.get(i).setStatus(ItemStatus.AVAILABLE);
                }
            }
        }

    }

    /**
     * Makes the removed item available once again.
     * @param id the id of the removed item.
     */
    public void undoRemoval(int id){
        items.get(id).setStatus(ItemStatus.AVAILABLE);
    }

    /**
     * Method for returning the item name with the given id
     * @param id the id of the item
     * @return the item name
     */
    //TODO: unused method
    public String getItemName(int id){
        return items.get(id).getName();
    }

    /**
     * Method for returning the item description with the given id
     * @param id the id of the item
     * @return the item description
     */
    //TODO: unused method
    public String getItemDescription(int id){
        return items.get(id).getDescription();
    }

    /**
     * Method for returning the item owner with the given id
     * @param id The item id
     * @return The item's owner's name
     */
    public String getItemOwner(int id){
        return items.get(id).getOwner();
    }

    /**
     * Method for returning the item's quality rating with the given id
     * @param id The item id
     * @return The item's quality rating.
     */
    public String getItemQuality(int id){
        return Integer.toString(items.get(id).getQualityRating());
    }

    /**
     * Sets the items owner to the given username
     * @param id The id of the item
     * @param owner The owner of the item
     */
    public void setItemOwner(int id, String owner){
        items.get(id).setOwner(owner);
    }

    @Override
    public String getIdentifier() {
        return "ItemManager";
    }

    /**
     * Changes the status of the item with the given id to inactive.
     * @param id The id of the item
     */
    public void changeStatusToInactiveAva(int id){
        items.get(id).setStatus(ItemStatus.INACTIVE_AVA);
    }
}
