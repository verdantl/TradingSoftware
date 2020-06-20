public class Item {
    private int qualityRating;
    private String name, category;
    private Trader owner;
    private int id;
    private static int idCounter = 0;

    public Item(String name, String category, Trader owner, int qualityRating){
        this.name = name;
        this.category = category;
        this.owner = owner;
        this.qualityRating = qualityRating;
        id = idCounter;
        idCounter ++;
    }

    public String getName(){
           return name;
        }

    public void setName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public int getQualityRating(){
        return qualityRating;
    }

    public Trader getOwner() {
        return owner;
    }

    public void setOwner(Trader owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

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
                ",\nowner=" + owner +
                ",\nID=" + id;
    }
}
