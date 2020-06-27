import java.util.ArrayList;
import java.util.Arrays;

public class AdminPrompts {
    private ArrayList<String> options;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";

    /**
     * Constructor for the AdminPrompts class
     */
    public AdminPrompts(){
        options = new ArrayList<>();
    }

    /**
     * Setter for the  list of options displayed to the user
     * @param options an arraylist of new options
     */
    //Here we have overloading, we could replace this with a single method and using Collections
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    /**
     * Alternate setter for the list of options displayed to the user
     * @param options an array of new options
     */
    public void setOptions(String[] options){
        this.options.clear();
        this.options.addAll(Arrays.asList(options));
    }



    /**
     * Prints to the screen....???
     */
    public void display(){

    }

    /**
     * Prints a list of messages to the screen
     * @param messages an array of strings representing the messages displayed to the
     *                 administrator
     */
    public void displayMessage(String[] messages){
        for (String message: messages){
            System.out.println(message);
        }
    }

    /**
     * Prints the options available to the administrator
     */
    public void displayOptions(){
        StringBuilder selections = new StringBuilder();
        for (int i = 0; i < options.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(options.get(i));
            selections.append(' ');
        }
        System.out.println(instruction);
        System.out.println(selections);
    }
}
