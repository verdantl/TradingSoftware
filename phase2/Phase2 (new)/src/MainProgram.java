
import java.io.IOException;
import java.util.ArrayList;

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
        currentSystem = new LoginSystem(configuration.getTraderActions(), configuration.getAdminActions(),
                configuration.getItemManager(), configuration.getTradeManager());
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
            nextSystem = currentSystem.stop();
        }
    }

    private void setCurrentSystem() throws IOException {
        switch (nextSystem){
            case 0: currentSystem = new LoginSystem(configuration.getTraderActions(), configuration.getAdminActions(),
                    configuration.getItemManager(), configuration.getTradeManager());
            case 1: currentSystem = new SignupSystem(configuration.getTraderActions(), configuration.getAdminActions());
            case 2: currentSystem = new TraderSystem();
            case 3: currentSystem = new AdminSystem();
            case 4: stop();
        }
    }

    private void stop(){
        running = false;
    }
}
