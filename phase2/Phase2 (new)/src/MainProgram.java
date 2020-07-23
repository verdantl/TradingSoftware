import java.io.IOException;


public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private String username; //this will be changed in the future
    private int nextSystem; //Might change this to string depending on how we implement this.
    private boolean running;
    private final String PATH = "src/gateway/test_dummy.csv";

    private final Configuration configuration;

    /**
     * Sets up the main program for the application.
     */
    public MainProgram() {
        configuration = new Configuration();
    }

    private void init(){
        currentSystem = new LoginSystem(configuration.getTraderManager(), configuration.getAdminActions());
    }

    /**
     * Runs the program and updates the current system/user each loop.
     */
    @Override
    public void run() {
        init();
        while (running) {
            currentSystem.run();
            configuration.saveInfo(currentSystem, PATH);
            username = currentSystem.getNextUser();
            nextSystem = currentSystem.getNextSystem();
            setCurrentSystem();
        }
    }

    private void setCurrentSystem() {
        switch (nextSystem){
            case 0: currentSystem = new LoginSystem(configuration.getTraderManager(), configuration.getAdminActions());
            case 1: currentSystem = new SignupSystem(configuration.getTraderManager(), configuration.getAdminActions());
            case 2: currentSystem = new TraderSystem(username, new TraderActions(),
                    configuration.getItemManager(), configuration.getTradeManager(),
                    configuration.getTraderManager(), configuration.getMeetingManager());
            case 3: currentSystem = new AdminSystem(username, configuration.getAdminActions(), configuration.getItemManager(),
                    configuration.getTradeManager(), configuration.getTraderManager(), configuration.getMeetingManager());

            case 4: stop();
        }
    }

    private void stop(){
        running = false;
    }
}
