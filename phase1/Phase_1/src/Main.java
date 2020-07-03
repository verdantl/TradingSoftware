import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LoginSystem ls = new LoginSystem("src/Configuration.csv");
        ls.run();
    }
}
