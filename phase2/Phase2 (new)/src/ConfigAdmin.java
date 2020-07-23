import java.io.*;

public class ConfigAdmin extends Config{
    AdminActions adminActions;
    @Override
    public AdminActions readInfo(String path) throws IOException, ClassNotFoundException {
        adminActions = (AdminActions) super.readInfo(path);
        return adminActions;
    }


    public void saveInfo(String path) throws IOException {
        ObjectOutput output = getObjectOutput(path);
        output.writeObject(adminActions);
        output.close();
    }

}
