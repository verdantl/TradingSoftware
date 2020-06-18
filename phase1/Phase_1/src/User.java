import java.time.LocalDate;

public abstract class User {
    private String username;
    private String password;
    private LocalDate dateCreated;

    /**
     * Default constructor of User's subclasses.
     * @param username this User's username.
     * @param password this User's password.
     */
    public User (String username, String password){
        this.username = username;
        this.password = password;
        dateCreated = LocalDate.now();
    }

    /**
     * Alternative constructor of User's subclasses, mainly used when reading in from a file.
     * @param username this User's username.
     * @param password this User's password.
     * @param date the date this User was created, in LocalDate format ("year-month-date")
     */
    public User (String username, String password, String date){
        this.username = username;
        this.password = password;

        // Extrapolating the date from the input string, and creating a LocalDate object.
        String[] dateParsed = date.split("-");
        int year = Integer.parseInt(dateParsed[0]);
        int month = Integer.parseInt(dateParsed[1]);
        int day = Integer.parseInt(dateParsed[2]);
        this.dateCreated = LocalDate.of(year, month, day);
    }

    /**
     * Converts this User into String representation.
     * @return this User's String representation.
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated.toString() +
                '}';
    }

    /**
     * Getter for username
     * @return this User's username
     */
    public String getUsername (){
        return username;
    }

    /**
     * Getter for password
     * @return this User's password
     */
    public String getPassword (){
        return password;
    }

    /**
     * Getter for dateCreated
     * @return this user's dateCreated (which is a LocalDate object)
     */
    public LocalDate getDateCreated (){
        return dateCreated;
    }

    /**
     * Setter for username
     * @param username this User's new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for password
     * @param password this User's new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
