import java.util.ArrayList;

public class AdminSystem {
    private AdminActions adminActions;
    private Admin currentAdmin;
    private AdminPrompts adminPrompts;

    public AdminSystem(Admin admin, ArrayList<Admin> admins){
        currentAdmin = admin;
        adminActions = new AdminActions(admins);
        //May need to change constructor
        adminPrompts = new AdminPrompts();

    }
}
