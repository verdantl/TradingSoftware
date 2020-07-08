import java.util.Scanner;

public class SignupSystem {

    //Since signup system only needs to create a new user, it only needs the traderActions class (at least thats what i think)
    //Note that it doesn't extend UserSystem as the run method doesn't need a user- We can change this, idk if its a good idea atm
    TraderActions traderActions;
    AdminActions adminActions;
    private SignupPrompts signupPrompts;
    private Scanner scanner;
    private boolean admin;
    private boolean running;
    private String username;
    private String password;

    //Here we instantiate traderActions so we can use it.
    public SignupSystem(TraderActions traderActions, AdminActions adminActions){
        //this.traderActions = traderActions;
        this.signupPrompts = new SignupPrompts();
        scanner = new Scanner(System.in);
        this.traderActions = traderActions;
        this.adminActions = adminActions;
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

    public void createPassword(){
        signupPrompts.displayCreatePassword();
        password = scanner.nextLine();
        createAccount();
    }

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

    private void stop(){
        running = false;
    }
}