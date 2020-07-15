import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginSystem {
    String path;
    ConfigReader configReader;
    ConfigWriter configWriter;

    TraderSystem traderSystem;
    AdminSystem adminSystem;
    SignupSystem signupSystem;
    LoginPrompts prompts;

    TraderActions traderActions;
    AdminActions adminActions;
    ItemManager itemManager;
    TradeManager tradeManager;

    private ArrayList<String> userInfo;
    private Trader trader;
    private Admin admin;

    /**
     * Constructor for the Login System
     * @param path the file path.
     * @throws IOException Throws an input/output exception
     */
    public LoginSystem(String path) throws IOException {
        this.path = path;
        configReader = new ConfigReader(this.path);
        configWriter = new ConfigWriter();

        traderActions = configReader.getTraderActions();
        adminActions = configReader.getAdminActions();
        itemManager = configReader.getItemManager();
        tradeManager = configReader.getTradeManager();

        prompts = new LoginPrompts();
        signupSystem = new SignupSystem(traderActions, adminActions);
    }

    /**
     * runs the the program from the login screen allowing the user to login, sign up, or exit.
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));
        try {
            System.out.println(prompts.openingMessage());
            String input;
            do {
                userInfo = new ArrayList<>();
                prompts.resetPrompts();
                System.out.println(prompts.next());
                input = br.readLine();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e){
                    choice = 0;
                }
                switch (choice) {
                    case 1:
                        System.out.println(prompts.next());
                        input = br.readLine();
                        userInfo.add(input);
                        if (!traderActions.checkUsername(userInfo.get(0))) {
                            System.out.println(prompts.next());
                            input = br.readLine();
                            userInfo.add(input);
                            trader = traderActions.login(userInfo.get(0), userInfo.get(1));
                            if (trader != null) {
                                traderSystem = new TraderSystem(trader, traderActions, itemManager, tradeManager, adminActions);
                                traderSystem.run();
                            }
                            else{
                                System.out.println(prompts.wrongPassword());
                            }
                        } else {
                            if (!adminActions.checkUsername(userInfo.get(0))) {
                                System.out.println(prompts.next());
                                input = br.readLine();
                                userInfo.add(input);
                                admin = adminActions.checkCredentials(userInfo.get(0), userInfo.get(1));
                                if (admin != null) {
                                    adminSystem = new AdminSystem(traderActions, adminActions, tradeManager);
                                    adminSystem.setCurrentAdmin(admin);
                                    adminSystem.run();
                                }
                                else{
                                    System.out.println(prompts.wrongPassword());
                                }
                            } else {
                                System.out.println(prompts.wrongUser());
                                continue;
                            }
                        }
                    break;
                    case 2:
                        signupSystem.run();
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
