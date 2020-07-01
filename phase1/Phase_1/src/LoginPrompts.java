import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LoginPrompts {

    private ArrayList<String> prompts;
    private int current = 0;

    public LoginPrompts(){
        prompts = new ArrayList<>();

        //add a read file later?
        prompts.add("Please type 1 to login or 2 to register a new account \n" +
                "Alternatively you can type \"exit\" at anytime to leave the program");
        prompts.add("Please enter your username:");
        prompts.add("Please enter you password:");
    }

    public String openingMessage(){
        return "Welcome to the Phase 1 Book trading software";
    }

    public String invalidInput(){
        return "This is a invalid input " +
                "\nPlease try again";
    }

    public String wrongUser(){
        return "That user does not exist within the system \nPlease try again";
    }

    public String wrongPassword(){
        return "That is the incorrect password \nPlease try again";
    }

    public String next(){
        try {
            return prompts.get(current++);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }

    public void resetPrompts(){
        current = 0;
    }

}
