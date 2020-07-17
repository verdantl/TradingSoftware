import users.Admin;
import users.AdminActions;
import users.Trader;
import users.TraderActions;

import java.util.Scanner;

public class SignupSystem extends UserSystem{

    TraderActions traderActions;
    AdminActions adminActions;
    private final SignupPrompts signupPrompts;
    private final Scanner scanner;
    private boolean admin;
    private boolean running;
    private String username;
    private String password;

    /**
     * Constructor for the signup system
     * @param traderActions The trader actions instance
     * @param adminActions The admin actions instance
     */
    public SignupSystem(TraderActions traderActions, AdminActions adminActions){
        this.signupPrompts = new SignupPrompts();
        scanner = new Scanner(System.in);
        this.traderActions = traderActions;
        this.adminActions = adminActions;
    }

    protected void init(){
        running = true;
    }

    /**
     * The run method for signup system.
     */
    public void run(){
        init();
        while (running){
            adminOrTrader();
        }
    }

    /**
     * This determines whether the new account is an admin or a trader.
     */
    public void adminOrTrader(){
        signupPrompts.adminOrTrader();
        String option = scanner.next();
        switch (option){
            case "0": admin = true;
                break;
            case "1": admin = false;
                break;
            case "2":
                stop();
                break;
            default:
                signupPrompts.commandNotRecognized();
                stop();
                break;
        }
        if (running){
            createUsername();
        }
    }

    /**
     * This method is used to create a username for the user
     */
    public void createUsername(){
        signupPrompts.displayCreateUserName();
        scanner.nextLine();
        String username = scanner.nextLine();
        checkUsername(username);
    }

    private void checkUsername(String username){
        boolean available;
        if (admin){
            available = adminActions.checkUsername(username);
        }
        else{
            available = traderActions.checkUsername(username);
        }
        signupPrompts.displayUserAvailable(available);
        if (available){
            this.username = username;
            createPassword();
        }
        else{
            createUsername();
        }
    }

    /**
     * This method is used to create a password for the user.
     */
    public void createPassword(){
        signupPrompts.displayCreatePassword();
        password = scanner.nextLine();
        createAccount();
    }

    /**
     * This method is used to create the account
     */
    public void createAccount(){
        signupPrompts.displayAccountSuccessful();
        if (admin){
            Admin admin = new Admin(username, password);
            adminActions.addAdminRequest(admin);
        }
        else{
            Trader trader = new Trader(username, password);
            traderActions.addTrader(trader);
        }
        //Here we want to write the information
        stop();
    }

    protected int stop(){
        running = false;
        return 0;
    }
}