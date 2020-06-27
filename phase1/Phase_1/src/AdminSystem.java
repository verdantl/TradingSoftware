import java.util.ArrayList;

public class AdminSystem extends UserSystem{
    private boolean running;
    private AdminActions adminActions;
    private Admin currentAdmin;
    private AdminPrompts adminPrompts;


    /**
     * Constructor for the AdminSystem
     * @param admin The admin user that has logged into the system
     * @param admins A list of all of the admins of the program
     */
    public AdminSystem(Admin admin, ArrayList<Admin> admins){
        currentAdmin = admin;
        adminActions = new AdminActions(admins);
        //May need to change constructor
        adminPrompts = new AdminPrompts();
        //may want to change the following


    }


    //Everything below here right now is part of the loop
    //This method helps set up some stuff
    private void init(){
        running = true;
    }


    /**
     * Loop for running the program
     */
    @Override
    public void run() {
        //this is the loop for the system
        while (running){
            adminPrompts.display();
            //all the stuff here
        }
    }

    //This might be better off in phase 2 as a listener, but here we're updating every iteration
    //and calling other methods that will update the controller
    protected void update(){
    }

    @Override
    protected void stop() {
        running = false;
    }
}
