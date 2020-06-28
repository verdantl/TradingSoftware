import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginSystem {


    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));
        LoginPrompts prompts = new LoginPrompts();
        try {
            String input = br.readLine();
            while (!input.equals("exit")) { // != compares memory addresses.

            }
        } catch (IOException e) {
            java.lang.System.out.println("Something went wrong");
        }
    }


    public static void main(String[] args) {
        LoginSystem ls = new LoginSystem();
        ls.run();
    }




}
