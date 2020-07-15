public class Admin extends User{
    /**
     * Constructor for the admin class when a new account is made.
     * @param username The user's username
     * @param password  The user's password
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Alternative constructor for the Admin class used for reading in from a file.
     * @param username this User's username.
     * @param password this User's password.
     * @param date the date this User was created, in LocalDate format ("year-month-date")
     */
    //Alternative constructor used for reading from files, off of the User reading file
    public Admin(String username, String password, String date){
        super(username, password, date);
    }

    /**
     * Converts the admin to a string representation.
     * @return a string of the admin's username, password, and date created
     */
    @Override
    public String toString() {
        return "Admin{" +
                "username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", dateCreated=" + super.getDateCreated().toString() +
                '}';
    }
}
