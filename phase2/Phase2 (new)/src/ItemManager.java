//package items;

//import users.Trader;

import java.util.*;

public class ItemManager {
    HashMap<Integer, Item> items;
    private int idCounter;

    /**
     * Contructor for a new ItemManager class
     * @param items a Hashmap that maps item ids to the item in the system
     */
    public ItemManager(HashMap<Integer, Item> items){
        this.items = items;
        idCounter = Collections.max(items.keySet()) + 1;
    }

//    /**
//     * We need this method for reading in
//     */
//    public void createNewItem(){
//        Item item = new Item(idCounter);
//        idCounter ++;
//    }
    //This is a temporary method to see how the reduction of constructors would work for Item\
    public void addItem(ArrayList<String> itemInfo, int quality, String status, String owner){
        Item item = new Item(idCounter);
        item.setName(itemInfo.get(0));
        item.setCategory(itemInfo.get(1));
        item.setDescription(itemInfo.get(2));
        item.setQualityRating(quality);
        item.setStatus(status);
        item.setOwner(owner);
        items.put(idCounter, item);
        idCounter++;
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

    /**
     *
     * @param item The Item
     * @return Return String representation of the item
     */
    public String convertItemToString(Item item){
        //TODO: Implement this method
        return "";
    }

    public List<String> convertItemListToString(List<Item> items){
        //TODO: Implement this method

        List<String> stringList = new ArrayList<>();
        for(Item item: items){
            stringList.add(convertItemToString(item));
        }
        return stringList;
    }



//    /**
//     * Return an ArrayList of all items that have been approved by admins that are not owned by the user with the username
//     * @return the ArrayList of all approved items other than those that belong to the username passed in.
//     */
//    public ArrayList<Item> getApprovedItems(List<Integer> itemIDs) {
//        ArrayList<Item> approvedItems = new ArrayList<>();
//        for (Integer i: itemIDs){
//
//        }
//        for(Map.Entry<String, items.Inventory> inventories: traderInventories.entrySet()){
//            if (!inventories.getKey().equals(username)){
//                approvedItems.addAll(inventories.getValue().getApprovedItems());
//            }
//        }
//        return approvedItems;
//    }
//    /**
//     * Getter for user's wantToBorrow
//     * @return an arraylist of the user's wishlist
//     */
//    public List<Item> getWantToBorrow(String username) {
//        return traderInventories.get(username).getWishlist();
//    }
//    /**
//     * Adds the given item to username's wantToBorrow
//     * @param username the username of the user.
//     * @param id the id of the item.
//     */
//    public void addToWantToBorrow(String username, int id){
//        for(Map.Entry<String, items.Inventory> inventories: traderInventories.entrySet()){
//            for(Item item: inventories.getValue().getApprovedItems()){
//                if(item.getId()==id){
//                    traderInventories.get(username).addToWishlist(item);
//                }
//            }
//        }
//    }
//    /**
//     * Removes the given item from wantToBorrow
//     * @param username The username who wishes to remove an item from their id.
//     * @param id the item's id.
//     */
//    public void removeFromWantToBorrow(String username, int id){
//        for(Item i: traderInventories.get(username).getWishlist()){
//            if(i.getId()== id) {
//                traderInventories.get(username).removeFromWishlist(i);
//            }
//        }
//    }
//
//    /**
//     * Getter for user's proposedItems
//     * @return an arraylist of the items that the user wishes to lend that need to be approved
//     */
//    public List<Item> getProposedItems(String username) {
//        return traderInventories.get(username).getProposedItems();
//    }
//    /**
//     * Adds an item with the given specifications to username's proposed items
//     * @param username The user who wants an item added to their list
//     * @param name The name of the item
//     * @param category The category of the item
//     * @param description The description for the item
//     * @param qualityRating A quality rating for the item
//     */
//    public void addToProposedItems(String username, String name, String category, String description, int qualityRating){
//        traderInventories.get(username).addProposedItems(new Item(name, category,description,qualityRating));
//    }
//    /**
//     * Removes the given item from username's proposed items
//     * @param username The user's username
//     * @param id The id of the item
//     */
//    public void removeFromProposedItems(String username, int id){
//        for(Item i: traderInventories.get(username).getProposedItems()){
//            if(i.getId()== id) {
//                traderInventories.get(username).removeFromProposedItems(i);
//            }
//        }
//    }
//
//    /**
//     * Getter for user's wantToLend
//     * @return an arraylist of the user's lending list
//     */
//    public List<Item> getWantToLend(String username) {
//        return traderInventories.get(username).getApprovedItems();
//    }
//    /**
//     * Adds the given item to wantToLend from username's wantToLend list from username's proposedItems
//     * @param username the user's username
//     * @param id an item that will be added to the lending list
//     */
//    public void addToWantToLend(String username, int id){
//        for(Item item:traderInventories.get(username).getProposedItems()){
//            if(item.getId() == id){
//                traderInventories.get(username).addToApprovedItems(item);
//                traderInventories.get(username).removeFromProposedItems(item);
//            }
//        }
//    }
//    /**
//     * Removes the given item from wantToLend
//     * @param username The username of the user
//     * @param id the id of the item
//     */
//    public void removeFromWantToLend(String username, int id){
//        for(Item i: traderInventories.get(username).getApprovedItems()){
//            if(i.getId()== id) {
//                traderInventories.get(username).removeFromApprovedItems(i);
//                return;
//            }
//        }
//    }

//    /**
//     * Getter for user's borrowedItems
//     * @return an arraylist of the items the user has borrowed
//     */
//    public List<Item> getBorrowedItems(String username) {
//        return traderInventories.get(username).getBorrowedItems();
//    }
//
//    /**
//     * Adds the given item to the user's borrowedItems from it's owners approvedItems
//     * @param id id of the item
//     * @param username The username of the item that is going to be added.
//     */
//    public void addToBorrowedItems(String username, int id){
//        for(Map.Entry<String, items.Inventory> inventories: traderInventories.entrySet()){
//            for(Item i: inventories.getValue().getApprovedItems()){
//                if (i.getId()==id){
//                    traderInventories.get(username).addToBorrowedItems(i);
//                    inventories.getValue().removeFromApprovedItems(i);
//                    return;
//                }
//            }
//        }
//    }
//
//    /**
//     * Removes the item from usernameBorrower's list of borrowedItems and adds it to usernameLender's list of approvedItems
//     * @param usernameBorrower The username of the user that had borrowed the item
//     * @param usernameLender The username of the user that had lent the item
//     * @param id The id of the item
//     */
//    public void removeFromBorrowedItems(String usernameBorrower, String usernameLender, int id){
//        Item temp;
//        for(Item item: traderInventories.get(usernameBorrower).getBorrowedItems()){
//            if(item.getId() == id){
//                temp = item;
//                traderInventories.get(usernameBorrower).removeFromBorrowedItems(item);
//                traderInventories.get(usernameLender).addToApprovedItems(temp);
//                return;
//            }
//        }
//    }

//    public boolean eraseItem(Integer id){
//        boolean value = inventory.containsKey(id);
//        inventory.remove(id);
//        return value;
//    }
//
//    /**
//     * changes the name of a given item
//     * @param id the given item's id
//     * @param name the new name
//     */
//    public void changeName(Integer id, String name){
//        inventory.get(id).setName(name);
//    }
//
//    /**
//     * changes the category of a given item
//     * @param id the given item's ID
//     * @param category the new category
//     */
//    public void changeCategory(Integer id, String category){
//        inventory.get(id).setCategory(category);
//    }
//
//    /**
//     * changes the description of a given item
//     * @param id the given item's ID
//     * @param description the new description
//     */
//    public void changeDescription(Integer id, String description){
//        inventory.get(id).setDescription(description);
//    }

//    /**
//     * Approves or rejects an item
//     * @param username The account usernamecontaining the item to be approved
//     * @param item The item to be approved
//     * @param approved A boolean representing if the item has been approved or not
//     */
//    public void approveItem(String username, int item, boolean approved){
//        removeFromProposedItems(username, item);
//        if (approved) {
//            addToWantToLend(username, item);
//        }
//    }

//    /**
//     * changes the owner of a given item
//     * @param i the given item
//     * @param owner the new owner
//     */
//    public void changeOwner(Item i, Trader owner){
//        i.setOwner(owner);
//    }

//    /**
//     * changes the qualityRating of a given item
//     * @param i the given item
//     * @param qualityRating the new qualityRating
//     */
//    public void changeQualityRating(Item i, int qualityRating){
//        i.setQualityRating(qualityRating);
//    }

//    /**
//     * From a given list of item gives back a list of the item's ids
//     * @param items the ArrayList of Items
//     * @return an ArrayList of Integers containing the items' ids
//     */
//    public ArrayList<Integer> getItemIDs(List<Item> items){
//        ArrayList<Integer> out = new ArrayList<>();
//        for (Item i : items){
//            out.add(i.getId());
//        }
//        //this may be removed
//        Collections.sort(out);
//        return out;
//    }

//    /**
//     * A getter for a list of items based on a list of their ids.
//     * @param itemIDs A list of the item ids
//     * @return A list of items
//     */
//    public ArrayList<Item> getItems(List<Integer> itemIDs){
//        ArrayList<Item> items = new ArrayList<>();
//
//        for (Integer id: itemIDs){
//            items.add(inventory.get(id));
//        }
//        return items;
//    }
}
