import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LoginSystem ls = new LoginSystem("src/test_dummy.csv");
        ls.run();
    }
}
