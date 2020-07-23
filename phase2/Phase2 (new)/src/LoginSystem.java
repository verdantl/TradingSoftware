import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginSystem extends UserSystem{
    private final LoginPrompts prompts;

    private final AdminActions adminActions;
    private final TraderManager traderManager;

    private int nextSystem;
    private String nextUser;
    private final BufferedReader br;

    /**
     * Constructor for this login system
     * @param traderManager the Trader Manager use case class
     * @param adminActions The Admin Actions use case class
     * */
    public LoginSystem(TraderManager traderManager, AdminActions adminActions) {

        this.traderManager = traderManager;
        this.adminActions = adminActions;
        br = new BufferedReader(new InputStreamReader(System.in));

        prompts = new LoginPrompts();
    }

    /**
     * runs the the program from the login screen allowing the user to login, sign up, or exit.
     */
    public void run() {
        init();
        try {
            while (running) {
                prompts.resetPrompts();
                System.out.println(prompts.next());
                String input = br.readLine();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e){
                    choice = 0;
                }
                switch (choice){
                    case 1:
                        login();
                        break;
                    case 2:
                        signup();
                        break;
                    default:
                        if (!input.equals("exit")) {
                            System.out.println(prompts.invalidInput());
                            break;
                        }
                        else{
                            nextSystem = 4;
                            stop();
                            break;
                        }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void login() throws IOException {
        System.out.println(prompts.next());
        String username = br.readLine();

        if (traderManager.containTrader(username)) {
            System.out.println(prompts.next());
            String password = br.readLine();

            if (traderManager.login(username, password)){
                nextUser = username;
                nextSystem = 2;
                stop();
            }
            else{
                System.out.println(prompts.wrongPassword());
            }
        }

        else if (adminActions.checkUsername(username)) {
            System.out.println(prompts.next());
            String password = br.readLine();

            if (adminActions.checkCredentials(username, password)){
                nextUser = username;
                nextSystem = 3;
                stop();
            }
            else{
                System.out.println(prompts.wrongPassword());
            }
        } else {
                System.out.println(prompts.wrongUser());
            }
    }

    private void signup(){
        nextSystem = 1;
        stop();
    }

    /**
     * Initiates the run loop for the program and prints the opening message
     */
    @Override
    protected void init() {
        super.init();
        System.out.println(prompts.openingMessage());
    }

    /**
     * Getter for the username of the user whose account is being logged into
     * @return A string representing the username of the trade or admin
     */
    public String getNextUser(){
        return nextUser;
    }

    /**
     * Getter for the next system that the program should run
     * @return an integer representing the next system that should be run
     */
    protected int getNextSystem(){
        return nextSystem;
    }
}
