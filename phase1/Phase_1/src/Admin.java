public class Admin extends User{
    public Admin(String username, String password) {
        super(username, password);
    }

    //Alternative constructor used for reading from files, off of the User reading file
    public Admin(String username, String password, String date){
        super(username, password, date);
    }
}
