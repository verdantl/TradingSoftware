import java.io.IOException;
import loginsys.LoginSystem;
public class Main {

    public static void main(String[] args) throws IOException {
        LoginSystem ls = new LoginSystem("src/gateway/test_dummy.csv");
        ls.run();
    }
}
