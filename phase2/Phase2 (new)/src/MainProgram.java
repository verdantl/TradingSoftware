
import java.io.IOException;


public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private String username; //this will be changed in the future
    private int nextSystem; //Might change this to string depending on how we implement this.
    private boolean running;
    private final String PATH = "src/gateway/test_dummy.csv";

    private Configuration configuration;

    public MainProgram() throws IOException {
        configuration = new Configuration(PATH);
    }

    private void init() throws IOException{
        currentSystem = new LoginSystem(configuration.getTraderActions(), configuration.getAdminActions());
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (running) {
            currentSystem.run();
            username = currentSystem.getNextUser();
            nextSystem = currentSystem.getNextSystem();
            try {
                setCurrentSystem();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCurrentSystem() throws IOException {
        switch (nextSystem){
            case 0: currentSystem = new LoginSystem(configuration.getTraderActions(), configuration.getAdminActions());
            case 1: currentSystem = new SignupSystem(configuration.getTraderActions(), configuration.getAdminActions());
            case 2: currentSystem = new TraderSystem(username, configuration.getTraderActions(),
                    configuration.getItemManager(), configuration.getTradeManager(), configuration.getAdminActions());
            case 3: currentSystem = new AdminSystem(username, configuration.getTraderActions(),
                    configuration.getAdminActions(), configuration.getTradeManager());

            case 4: stop();
        }
    }

    private void stop(){
        running = false;
    }
}
