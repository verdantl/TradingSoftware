//import users.Trader;

public class Item {
    private int qualityRating;
    private String name, category, description;
    private final int id;
    private static int idCounter = 0;
    private ItemStatus status;

    /**
     * The default constructor of the item class
     * @param name the string the item is called
     * @param category the string the item is called
     * @param description the string description of the item
     * @param qualityRating the int the represents the quality/condition of the item
     */
    public Item(String name, String category, String description, int qualityRating, String status){
        this.name = name;
        this.category = category;
        this.description = description;
        this.qualityRating = qualityRating;
        this.status = convertToItemStatus(status);
        id = idCounter;
        idCounter ++;
    }

    /**
     * Constructor of the item class for when items are read in.
     * @param name the name of the item
     * @param category the category of the item
     * @param description the description of the item
     * @param owner the owner of the item
     * @param qualityRating the quality rating of the item
     * @param id the item id
     */
    public Item(String name, String category, String description, Trader owner, int qualityRating, int id,
                String status){
        this.name = name;
        this.category = category;
        this.description = description;
        this.qualityRating = qualityRating;
        this.id = id;
        this.status = convertToItemStatus(status);
        idCounter++;
    }


    /**
     * Gets the item's name.
     * @return the string the item is called
     */
    public String getName(){
           return name;
        }

    /**
     * Sets the item's name.
      * @param name the string the item is called
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the item's category.
     * @return the string the item is categorized as
     */
    public String getCategory(){
        return category;
    }

    /**
     * Sets the item's category.
     * @param category the string the item is categorized as
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Gets the item's description.
     * @return the string the item is categorized as
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the item's description.
     * @param description the string the item is categorized as
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Gets the item's quality rating.
     * @return the int value that represents the item's quality rating
     */
    public int getQualityRating(){
        return qualityRating;
    }

    /**
     * Getter for status
     * @return Return the status
     */
    public ItemStatus getStatus(){
        return this.status;
    }

    /**
     * Setter for status
     * @param status The status of the item
     */
    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    //    /**
//     * Gets the item's owner.
//     * @return the instance of Trader that is this item's owner
//     */
//    public Trader getOwner() {
//        return owner;
//    }

//    /**
//     * Sets the item's owner.
//     * @param owner the Trader that is to become this item's owner
//     */
//    public void setOwner(Trader owner) {
//        this.owner = owner;
//    }

    /**
     * Gets the item's id.
     * @return an integer that represents this item's id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the item's qualityRating. Throws IllegalArgumentException if !(1 <= qualityRating <= 10).
     * @param qualityRating the item's quality rating that you want to set to.
     */
    public void setQualityRating(int qualityRating){
        if (qualityRating < 1 || qualityRating > 10){
            throw new IllegalArgumentException("The quality rating of this item must be an integer between 1-10");
        }
        this.qualityRating = qualityRating;
    }

    /**
     * Return a string representation of this Item.
     * @return a String containing the item's qualityRating, name, category, owner and ID.
     */
    @Override
    public String toString() {
        return "| " + name + " |" +
                "\nqualityRating: " + qualityRating +
                ",\ncategory: '" + category +
                "',\ndescription: '" + description +
//                "',\nowner: " + owner.getUsername() +
                ",\nID: " + id;
    }

    /**
     * Converts the status of the item in String to Enum
     * @param status The status of the item
     * @return Return the enum equivalent of status
     */
    public ItemStatus convertToItemStatus(String status){

        ItemStatus itemStatus = null;
        switch (status){
            case "available":
                itemStatus = ItemStatus.AVAILABLE;
                break;
            case "unavailable":
                itemStatus = ItemStatus.UNAVAILABLE;
                break;
            case "requested":
                itemStatus = ItemStatus.REQUESTED;
                break;
        }

        return itemStatus;
    }

    /**
     * Converts the status of the item in Enum to String
     * @param status The status of the item
     * @return Return the string equivalent of status
     */
    public String convertToString(ItemStatus status){

        switch (status){
            case AVAILABLE:
                return "available";
            case UNAVAILABLE:
                return "unavailable";
            case REQUESTED:
                return "requested";
        }
        return "";
    }
}
