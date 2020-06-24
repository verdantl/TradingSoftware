public class Item {
    private int qualityRating;
    private String name, category, description;
    private Trader owner;
    private int id;
    private static int idCounter = 0;

    /**
     * The default constructor of the item class
     * @param name the string the item is called
     * @param category the string the item is called
     * @param description the string description of the item
     * @param owner the trader that the item belongs to
     * @param qualityRating the int the represents the quality/condition of the item
     */
    public Item(String name, String category, String description, Trader owner, int qualityRating){
        this.name = name;
        this.category = category;
        this.description = description;
        this.owner = owner;
        this.qualityRating = qualityRating;
        id = idCounter;
        idCounter ++;
    }

    //new constructor with a given id (when starting up the program)

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
     * Gets the item's owner.
     * @return the instance of Trader that is this item's owner
     */
    public Trader getOwner() {
        return owner;
    }

    /**
     * Sets the item's owner.
     * @param owner the Trader that is to become this item's owner
     */
    public void setOwner(Trader owner) {
        this.owner = owner;
    }

    /**
     * Gets the item's id.
     * @return an integer that represents this item's id
     */
    public int getId() {
        return id;
    }

    //this should be remove
    /**
     * Sets the item's id.
     * @param id the item's new id
     */
    public void setId(int id) {
        this.id = id;
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
        return "qualityRating=" + qualityRating +
                ",\nname='" + name + '\'' +
                ",\ncategory='" + category + '\'' +
                "'\ndescription='" + description + '\'' +
                ",\nowner=" + owner +
                ",\nID=" + id;
    }
}
