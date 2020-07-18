//package items;

//import users.Trader;

import java.util.*;

public class ItemManager {
    //private ArrayList<Item> requestedItems, approvedItems;
    HashMap<String, Inventory> traderInventories;

    public ItemManager(){
        this.traderInventories = new HashMap<>();
//        requestedItems = new ArrayList<>();
//        approvedItems = new ArrayList<>();
    }

    public ItemManager(HashMap<String, Inventory> traderInventories){
//        this.approvedItems = approvedItems;
//        this.requestedItems = requestedItems;
        this.traderInventories = traderInventories;
    }


    /**
     * Return an ArrayList of all items that have been approved by admins that are not owned by the user with the username
     * @return the ArrayList of all approved items other than those that belong to the username passed in.
     */
    public ArrayList<Item> getApprovedItems(String username) {
        ArrayList<Item> approvedItems = new ArrayList<>();
        for(Map.Entry<String, Inventory> inventories: traderInventories.entrySet()){
            if (!inventories.getKey().equals(username)){
                approvedItems.addAll(inventories.getValue().getApprovedItems());
            }
        }
        return approvedItems;
    }
    /**
     * Getter for user's wantToBorrow
     * @return an arraylist of the user's wishlist
     */
    public List<Item> getWantToBorrow(String username) {
        return traderInventories.get(username).getWishlist();
    }
    /**
     * Adds the given item to username's  wantToBorrow
     * @param username the username of the user.
     * @param id the id of the item.
     */
    public void addToWantToBorrow(String username, int id){
        for(Map.Entry<String, Inventory> inventories: traderInventories.entrySet()){
            for(Item item: inventories.getValue().getApprovedItems()){
                if(item.getId()==id){
                    traderInventories.get(username).addToWishlist(item);
                }
            }
        }
    }
    /**
     * Removes the given item from wantToBorrow
     * @param username The username who wishes to remove an item from their id.
     * @param id the item's id.
     */
    public void removeFromWantToBorrow(String username, int id){
        for(Item i: traderInventories.get(username).getWishlist()){
            if(i.getId()== id) {
                traderInventories.get(username).removeFromWishlist(i);
            }
        }
    }




    /**
     * Getter for user's proposedItems
     * @return an arraylist of the items that the user wishes to lend that need to be approved
     */
    public List<Item> getProposedItems(String username) {
        return traderInventories.get(username).getProposedItems();
    }
    /**
     * Adds an item with the given specifications to username's proposed items
     * @param username The user who wants an item added to their list
     * @param name The name of the item
     * @param category The category of the item
     * @param description The description for the item
     * @param qualityRating A quality rating for the item
     */
    public void addToProposedItems(String username, String name, String category, String description, int qualityRating){
        traderInventories.get(username).addProposedItems(new Item(name, category,description,qualityRating));
    }
    /**
     * Removes the given item from username's proposed items
     * @param username The user's username
     * @param id The id of the item
     */
    public void removeFromProposedItems(String username, int id){
        for(Item i: traderInventories.get(username).getProposedItems()){
            if(i.getId()== id) {
                traderInventories.get(username).removeFromProposedItems(i);
            }
        }
    }

    /**
     * Getter for user's wantToLend
     * @return an arraylist of the user's lending list
     */
    public List<Item> getWantToLend(String username) {
        return traderInventories.get(username).getApprovedItems();
    }
    /**
     * Adds the given item to wantToLend from username's wantToLend list from username's proposedItems
     * @param username the user's username
     * @param id an item that will be added to the lending list
     */
    public void addToWantToLend(String username, int id){
        for(Item item:traderInventories.get(username).getProposedItems()){
            if(item.getId() == id){
                traderInventories.get(username).addToApprovedItems(item);
                traderInventories.get(username).removeFromProposedItems(item);
            }
        }
    }
    /**
     * Removes the given item from wantToLend
     * @param username The username of the user
     * @param id the id of the item
     */
    public void removeFromWantToLend(String username, int id){
        for(Item i: traderInventories.get(username).getApprovedItems()){
            if(i.getId()== id) {
                traderInventories.get(username).removeFromApprovedItems(i);
                return;
            }
        }
    }

    /**
     * Getter for user's borrowedItems
     * @return an arraylist of the items the user has borrowed
     */
    public List<Item> getBorrowedItems(String username) {
        return traderInventories.get(username).getBorrowedItems();
    }

    /**
     * Adds the given item to the user's borrowedItems from it's owners approvedItems
     * @param id id of the item
     * @param username The username of the item that is going to be added.
     */
    public void addToBorrowedItems(String username, int id){
        for(Map.Entry<String, Inventory> inventories: traderInventories.entrySet()){
            for(Item i: inventories.getValue().getApprovedItems()){
                if (i.getId()==id){
                    traderInventories.get(username).addToBorrowedItems(i);
                    inventories.getValue().removeFromApprovedItems(i);
                    return;
                }
            }
        }
    }

    /**
     * Removes the item from usernameBorrower's list of borrowedItems and adds it to usernameLender's list of approvedItems
     * @param usernameBorrower The username of the user that had borrowed the item
     * @param usernameLender The username of the user that had lent the item
     * @param id The id of the item
     */
    public void removeFromBorrowedItems(String usernameBorrower, String usernameLender, int id){
        Item temp;
        for(Item item: traderInventories.get(usernameBorrower).getBorrowedItems()){
            if(item.getId() == id){
                temp = item;
                traderInventories.get(usernameBorrower).removeFromBorrowedItems(item);
                traderInventories.get(usernameLender).addToApprovedItems(temp);
                return;
            }
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
     * Returns a list of the usernames of all traders that have items that need approving.
     * @return
     */
    public List<String> getTradersNeedingItemApproval(){
        List<String> temp = new ArrayList<>();
        for(Map.Entry<String, Inventory> inven: this.traderInventories.entrySet()){
            if(!inven.getValue().getProposedItems().isEmpty()){
                temp.add(inven.getKey());
            }
        }
        return temp;
    }

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
}
