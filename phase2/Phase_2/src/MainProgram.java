import adminsys.AdminSystem;
import loginsys.LoginSystem;
import signupsys.SignupSystem;
import tradersys.TraderSystem;

import java.io.IOException;

public class MainProgram implements Runnable{
    private UserSystem currentSystem;
    private int nextSystem; //Might change this to string depending on how we implement this.
    private boolean running;
    private final String PATH = "src/gateway/test_dummy.csv";
    private void init() throws IOException{
//        currentSystem = new LoginSystem("src/gateway/test_dummy.csv");
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
        }
    }

//    private void setCurrentSystem(){
//        switch (nextSystem){
//            case 0: currentSystem = new LoginSystem(PATH);
//            case 1: currentSystem = new SignupSystem();
//            case 2: currentSystem = new TraderSystem();
//            case 3: currentSystem = new AdminSystem();
//            case 4: stop();
//        }
//    }
//
    private void stop(){
        running = false;
    }
}
