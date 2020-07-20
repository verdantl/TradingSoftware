//package items;

//import users.Trader;

import java.util.*;

public class ItemManager {
    //private ArrayList<Item> requestedItems, approvedItems;
//    HashMap<String, items.Inventory> traderInventories;
    HashMap<Integer, Item> inventory;

    public ItemManager(HashMap<Integer, Item> inventory){
//        this.approvedItems = approvedItems;
//        this.requestedItems = requestedItems;
        this.inventory = inventory;
//        this.traderInventories = traderInventories;
    }

    public Collection<Item> getAllItems(){
        return inventory.values();
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

    public boolean eraseItem(Integer id){
        boolean value = inventory.containsKey(id);
        inventory.remove(id);
        return value;
    }

    /**
     * changes the name of a given item
     * @param id the given item's id
     * @param name the new name
     */
    public void changeName(Integer id, String name){
        inventory.get(id).setName(name);
    }

    /**
     * changes the category of a given item
     * @param id the given item's ID
     * @param category the new category
     */
    public void changeCategory(Integer id, String category){
        inventory.get(id).setCategory(category);
    }

    /**
     * changes the description of a given item
     * @param id the given item's ID
     * @param description the new description
     */
    public void changeDescription(Integer id, String description){
        inventory.get(id).setDescription(description);
    }


    //TODO Move all of these to TraderManager if we're staying on track with the idea
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

    /**
     * From a given list of item gives back a list of the item's ids
     * @param items the ArrayList of Items
     * @return an ArrayList of Integers containing the items' ids
     */
    public ArrayList<Integer> getItemIDs(List<Item> items){
        ArrayList<Integer> out = new ArrayList<>();
        for (Item i : items){
            out.add(i.getId());
        }
        //this may be removed
        Collections.sort(out);
        return out;
    }

    /**
     * A getter for a list of items based on a list of their ids.
     * @param itemIDs A list of the item ids
     * @return A list of items
     */
    public ArrayList<Item> getItems(List<Integer> itemIDs){
        ArrayList<Item> items = new ArrayList<>();

        for (Integer id: itemIDs){
            items.add(inventory.get(id));
        }
        return items;
    }
}
