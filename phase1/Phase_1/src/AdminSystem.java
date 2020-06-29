import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminSystem extends UserSystem{
    private boolean running;
    private AdminActions adminActions;
    private Admin currentAdmin;
    private AdminPrompts adminPrompts;
    private boolean changeDisplay;
    private String fileName;


    /**
     * Constructor for the AdminSystem
     * @param admin The admin user that has logged into the system
     * @param fileName The name of the file
     */
    public AdminSystem(Admin admin, String fileName){
        //I think we should read in from files.
        this.fileName = fileName;
        currentAdmin = admin;

        ArrayList<Admin> admins = new ArrayList<>();
        //something like this
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null){
                String[] info = line.split(",");
                admins.add(new Admin(info[0], info[1]));
                line = in.readLine();
            }
            in.close();

        }catch (IOException iox){
            System.out.println("File Not Found");
        }
        adminActions = new AdminActions(admins);
        //May need to change constructor
        adminPrompts = new AdminPrompts();
        //may want to change the following


    }


    //Everything below here right now is part of the loop
    //This method helps set up some stuff
    private void init(){
        running = true;

        //this is a temporary holder
        adminPrompts.setOptions(new String[2]);
    }


    /**
     * Runs the program in a loop
     */
    @Override
    public void run() {
        //this is the loop for the system
        while (running){
            adminPrompts.displayOptions();
            Scanner input = new Scanner(System.in);
            int option = input.nextInt();
            switch (option){
                case 1:
                case 2:
                    adminPrompts.displayFreezeMenu();
                case 3:
                    adminPrompts.displayApproveItemsMenu();
                case 4:
                case 5:
                case 6:

            }

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
