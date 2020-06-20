public class Item {
    private int qualityRating;
    private String name, category;
    private Trader owner;
    private int id;
    static int idCounter = 0;

    /**
     * The default constructor of the item class
     * @param name the string the item is called
     * @param category the string the item is called
     * @param owner the trader that the item belongs to
     * @param qualityRating the int the represents the quality/condition of the item
     */
    public Item(String name, String category, Trader owner, int qualityRating){
        this.name = name;
        this.category = category;
        this.owner = owner;
        this.qualityRating = qualityRating;
        id = idCounter;
        idCounter ++;
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


    public int getQualityRating(){
        return qualityRating;
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
                ",\nowner=" + owner +
                ",\nID=" + id;
    }
}
