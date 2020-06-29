import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginSystem {

    private ArrayList<String> userInfo = new ArrayList<>();


    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));
        LoginPrompts prompts = new LoginPrompts();
        try {
            System.out.println(prompts.openingMessage());
            String input;
            do {
                System.out.println(prompts.next());
                input = br.readLine();
                if (input.equals("1")) {
                    System.out.println(prompts.next());
                    input = br.readLine();
                    userInfo.add(input);

                    System.out.println(prompts.next());
                    input = br.readLine();
                    userInfo.add(input);
                    if(false){}
                    else {
                        System.out.println(prompts.next());
                        prompts.resetPrompts();
                    }
                }
                else if (input.equals("2")){
                    SignupSystem signup = new SignupSystem();
                    //signup.run();
                    break;
                }
                else {
                    if (!input.equals("exit")){
                    System.out.println(prompts.invalidInput());
                    prompts.resetPrompts();}
                }
            } while (!input.equals("exit"));
        } catch (IOException e) {
            java.lang.System.out.println("Something went wrong");
        }
    }


    public static void main(String[] args) {
        LoginSystem ls = new LoginSystem();
        ls.run();
    }




}
