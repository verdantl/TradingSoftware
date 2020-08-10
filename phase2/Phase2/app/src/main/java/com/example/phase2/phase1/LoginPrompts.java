package com.example.phase2.phase1;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LoginPrompts {

    private final ArrayList<String> prompts;
    private int current = 0;
    private final String programName = "Phase 1 Book Trading Software";

    /**
     * the constructor for the LoginPrompts based on hard coded prompts
     */
    public LoginPrompts(){
        prompts = new ArrayList<>();

        prompts.add("Please enter [1] to login or [2] to register a new account. \n" +
                "Alternatively, you can type \"exit\" at anytime to leave the program.");
        prompts.add("Please enter your username:");
        prompts.add("Please enter your password:");
    }

    /**
     * the opening message prompt
     * @return the string used for the opening message
     */
    public String openingMessage(){
        return "Welcome to the " + programName + '.';
    }

    /**
     * the invalid input prompt
     * @return the string used for when a invalid input is inputted
     */
    public String invalidInput(){
        return "This is a invalid input. " +
                "\nPlease try again";
    }

    /**
     * the wrong username prompt
     * @return the string used for when a wrong username is inputted
     */
    public String wrongUser(){
        return "That user does not exist within the system. \nPlease try again";
    }

    /**
     * the wrong password prompt
     * @return the string used for when a wrong password is inputted
     */
    public String wrongPassword(){
        return "That is the incorrect password. \nPlease try again";
    }

    /**
     * gives the current prompt and move to the next prompt
     * @return the string used for current prompt
     */
    public String next(){
        try {
            return prompts.get(current++);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * resets the prompts to the beginning
     */
    public void resetPrompts(){
        current = 0;
    }

}
