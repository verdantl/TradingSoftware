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
