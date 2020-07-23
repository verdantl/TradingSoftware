import java.io.IOException;
import java.io.ObjectOutput;

public class ConfigItems extends Config{
    ItemManager itemManager;
    public ItemManager readInfo(String path) throws IOException, ClassNotFoundException {
        itemManager = (ItemManager) super.readInfo(path);
        return itemManager;
    }

    @Override
    public void saveInfo(String path) throws IOException {
        ObjectOutput output = getObjectOutput(path);
        output.writeObject(itemManager);
        output.close();
    }
}
