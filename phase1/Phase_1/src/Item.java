public class Item {
    int qualityRating;
    String name, category;
    User owner;

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

}
