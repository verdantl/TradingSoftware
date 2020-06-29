import java.util.ArrayList;

public class TraderPrompts {
    ArrayList<String> mainMenuPrompts, proposeItemPrompts;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";
    public TraderPrompts(){
        setUpMainMenuPrompts();
        setUpProposeItemPrompts();

    }
    public void setUpMainMenuPrompts(){
        mainMenuPrompts = new ArrayList<>();
        mainMenuPrompts.add(" - Exit the program.");
        mainMenuPrompts.add(" - Propose an item you want to lend.");
    }

    public void setUpProposeItemPrompts(){
        proposeItemPrompts = new ArrayList<>();
        proposeItemPrompts.add("Enter \"0\" to go back to the main menu at any time."); //0
        proposeItemPrompts.add("Otherwise, enter the item name:"); //1
        proposeItemPrompts.add("Enter the item's Category:");//2
        proposeItemPrompts.add("Enter a description for the item:");//3
        proposeItemPrompts.add("Enter the item's quality rating from 1-10:");//4
        proposeItemPrompts.add("Your item is waiting to be reviewed by an Administrator, please check back later.");//5
    }
    public ArrayList<String> getMainMenuPrompts(){
        return this.mainMenuPrompts;
    }

    public ArrayList<String> getProposeItemPrompts(){
        return this.proposeItemPrompts;
    }

    public void displayMainMenu(){
        StringBuilder selections = new StringBuilder();
        for (int i = 0; i < mainMenuPrompts.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(mainMenuPrompts.get(i));
            selections.append(' ');
        }
        System.out.println(instruction);
        System.out.println(selections);
    }
}
