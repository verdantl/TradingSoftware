import java.util.ArrayList;

public class Configuration {
    ArrayList<Trader> traders;
    ArrayList<Admin> admins;


    public Configuration(ArrayList<Trader> traders, ArrayList<Admin> admins){
        this.traders = traders;
        this.admins = admins;
    }

    public ArrayList<Trader> getTraders() {
        return traders;
    }

    public void setTraders(ArrayList<Trader> traders) {
        this.traders = traders;
    }

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }
}
