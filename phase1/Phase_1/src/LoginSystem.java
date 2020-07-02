import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    So this class gets the config file from the gateway class, and instantiates all the other classes from that config file
    TraderSystem traderSystem;
    AdminSystem adminSystem;
    SignupSystem signupSystem;
    LoginPrompts loginPrompts;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;

    public LoginSystem(Configuration config){
        //Only login Prompts is instantiated here. The other prompts are instantiated within their respective controllers
        this.loginPrompts = new LoginPrompts();

        //This is where the other "Systems" are instantiated. Note that a user is not passed into the system when we're instantiating the system.
        //The constructors can change to include the other variables in this class if they're needed, i just put what i thought we need for
        //each class.
        this.traderSystem = new TraderSystem(config.getTraderActions(), config.getItemManager(), config.getTradeManager());
        this.adminSystem = new AdminSystem(config.getAdminActions(), config.getTraderActions);
        this.signupSystem = new SignupSystem(traderActions);

    }
    //Now that the class is instantiated, this run method is run from the main method in the gateway class (or where ever the main method is)
    public void run() {
        //Here we use login prompts, and ask user to login or signup or close the program
        // If they signup we use the run method of signup System.
        //
        // Now is a good time to check the SignUpSystem class
        //
        // Once the run method of signup system ends, it should return here and we again
        // ask the user to sign in (i.e thats in a loop)

        // Once they enter their credentials we use TraderActions/Admin Actions to get a current user. Then we run the respective System
        //based on if they're a trader or an admin.
        //I.e User currentUser = someUser
        //So for example if they're an admin, we do:
        //adminSystem.run(Here we pass in the user);
        Note that when we pass in we should be careful of the java rules for this stuff^
        If u guys want now is the time to look at AdminSystem and TraderSystem's comments. But before that take a look at user System.
        Now once the admin system is finished, its run method ends and we continue
        We return to the login screen and the loop goes on UNTIL they decide to close the program.
        If they choose to close the program, the RUN method of this program ends and we return to the gateway class where the Modified Configuration
        file is written to a file, before the program ends. (Here im not sure if aliasing would allow that but i think it will)
        Note that UserSystem would only be a parent for AdminSystem and TraderSystem since we use different parameters for the SignUpSystem and
        LoginSystem*/


public class LoginSystem {

    TraderSystem traderSystem;
    AdminSystem adminSystem;
    SignupSystem signupSystem;
    LoginPrompts prompts;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;

    private ArrayList<String> userInfo;
    private User user;


    /**
     * runs the the progame from the login screen allowing the user to login, sign up, or exit.
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));
        prompts = new LoginPrompts();
        ArrayList<Trader> t = new ArrayList<>();
        t.add(new Trader("Carl", "Wu"));
        traderActions = new TraderActions(t);
        adminActions = new AdminActions(new ArrayList<>(), new ArrayList<>());
        try {
            System.out.println(prompts.openingMessage());
            String input;
            do {
                userInfo = new ArrayList<>();
                prompts.resetPrompts();
                System.out.println(prompts.next());
                input = br.readLine();
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        System.out.println(prompts.next());

                        input = br.readLine();
                        userInfo.add(input);

                        if (!traderActions.checkUsername(userInfo.get(0))) {

                            System.out.println(prompts.next());
                            input = br.readLine();
                            userInfo.add(input);

                            user = traderActions.login(userInfo.get(0), userInfo.get(1));
                            if (user != null) {
                                traderSystem.run();
                            }
                        } else {
                            if (!adminActions.checkUsername(userInfo.get(0))) {
                                System.out.println(prompts.next());
                                input = br.readLine();
                                userInfo.add(input);

                                user = adminActions.checkCredentials(userInfo.get(0), userInfo.get(1));
                                if (user != null) {
                                    adminSystem.run();
                                }
                            } else {
                                System.out.println(prompts.wrongUser());
                                continue;
                            }
                        }
                        System.out.println(prompts.wrongPassword());
                    break;
                    case 2:
                        SignupSystem signup = new SignupSystem(traderActions, adminActions);
                        signup.run();
                        break;
                    default:
                        if (!input.equals("exit")) {
                            System.out.println(prompts.invalidInput());
                    }
                }

            } while (!input.equals("exit"));
        } catch (IOException e) {
            java.lang.System.out.println("Something went wrong.");
        }
    }

}
