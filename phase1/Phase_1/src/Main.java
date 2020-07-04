import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LoginSystem ls = new LoginSystem("E:/std y/Programming/" +
                "IntelliJ IDEA Community Edition 2020.1.1/CSC207/group_0011/phase1/phase_1/src/test_dummy.csv");
        ls.run();
    }
}
