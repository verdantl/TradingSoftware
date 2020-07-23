import java.io.IOException;
import java.io.ObjectOutput;

public class ConfigTrades extends Config{
    TradeManager tradeManager;

    @Override
    public TradeManager readInfo(String path) throws IOException, ClassNotFoundException {
        tradeManager = (TradeManager) super.readInfo(path);
        return tradeManager;
    }

    @Override
    public void saveInfo(String path) throws IOException {
        ObjectOutput output = getObjectOutput(path);
        output.writeObject(tradeManager);
        output.close();
    }
}
