import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class SignupSystem {

    //Since signup system only needs to create a new user, it only needs the traderActions class (at least thats what i think)
    //Note that it doesn't extend UserSystem as the run method doesn't need a user- We can change this, idk if its a good idea atm
    TraderActions traderActions;
    AdminActions adminActions;
    SignupPrompts signupPrompts;
    Scanner scanner;
    boolean admin;
    boolean running;
    String username;
    String password;

    //Here we instantiate traderActions so we can use it.
    public SignupSystem(/*TraderActions traderActions*/){
        //this.traderActions = traderActions;
        this.signupPrompts = new SignupPrompts();
        scanner = new Scanner(System.in);
        traderActions = new TraderActions(new ArrayList<>());
        adminActions = new AdminActions(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    private void init(){
        running = true;
    }
    //This is the run method
    //It makes use of signup prompts to ask the user to create an account. Theres no need to return a user because
    //The user gets added to traderActions in HERE and we ask them to log in again in LoginSystem
    public void run(){
        init();
        while (running){
            adminOrTrader();
        }
    }
    //Now its time to go back to loginSystem

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
    public void createUsername(){
        signupPrompts.displayCreateUserName();
        String username = scanner.next();
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

    public void createPassword(){
        signupPrompts.displayCreatePassword();
        password = scanner.next();
        createAccount();
    }

    public void createAccount(){
        signupPrompts.displayAccountSuccessful();
        if (admin){
            Admin admin = new Admin(username, password);
        }
        else{
            Trader trader = new Trader(username, password);
        }
        //Here we want to write the information
        stop();
    }

    private void stop(){
        running = false;
    }
}