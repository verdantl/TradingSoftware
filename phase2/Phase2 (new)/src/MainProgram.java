import java.io.IOException;


public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private String username; //this will be changed in the future
    private int nextSystem; //Might change this to string depending on how we implement this.
    private boolean running;

    private final Configuration configuration;

    /**
     * Sets up the main program for the application.
     */
    public MainProgram() {
        configuration = new Configuration();
    }

    private void init() throws IOException, ClassNotFoundException {
        currentSystem = new LoginSystem(configuration.getTraderManager(), configuration.getAdminActions());
    }

    /**
     * Runs the program and updates the current system/user each loop.
     */
    @Override
    public void run() {
        try {
            init();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        while (running) {
            currentSystem.run();
            try {
                configuration.saveInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
            username = currentSystem.getNextUser();
            nextSystem = currentSystem.getNextSystem();

            try {
                setCurrentSystem();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCurrentSystem() throws IOException, ClassNotFoundException {
        switch (nextSystem){
            case 0: currentSystem = new LoginSystem(configuration.getTraderManager(), configuration.getAdminActions());
            case 1: currentSystem = new SignupSystem(configuration.getTraderManager(), configuration.getAdminActions(),
                    configuration.getTradeManager());
            case 2: currentSystem = new TraderSystem(username,
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
