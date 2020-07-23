import java.io.IOException;
import java.io.ObjectOutput;

public class ConfigTraders extends Config {
    TraderManager traderManager;
    public TraderManager readInfo(String path) throws IOException, ClassNotFoundException {
        traderManager = (TraderManager) super.readInfo(path);
        return traderManager;
    }

    @Override
    public void saveInfo(String path) throws IOException {
        ObjectOutput output = getObjectOutput(path);
        output.writeObject(traderManager);
        output.close();
    }
}
