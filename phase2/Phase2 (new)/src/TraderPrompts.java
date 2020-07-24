import java.util.ArrayList;

public class TraderPrompts {
    ArrayList<String> mainMenuPrompts, proposeItemPrompts;
    private final String instruction = "Please enter the number of the option " +
            "you would like to select.";

    /**
     * Constructor for the presenter of tradersystem
     */
    public TraderPrompts(){
        setUpMainMenuPrompts();
        setUpProposeItemPrompts();
    }

    /**
     * Method used in the constructor to set up the main menu prompts.
     */
    private void setUpMainMenuPrompts(){
        mainMenuPrompts = new ArrayList<>();
        mainMenuPrompts.add(" - Exit the program.");
        mainMenuPrompts.add(" - Propose an item you want to lend.");
        mainMenuPrompts.add(" - View/remove items from your lending list.");
        mainMenuPrompts.add(" - View/remove items from your wishlist");
        mainMenuPrompts.add(" - Browse all available items.");
        mainMenuPrompts.add(" - Browse your on-going trades.");
        mainMenuPrompts.add(" - Browse your three most recently traded items.");
        mainMenuPrompts.add(" - Browse your three most traded with trading partners.");
        mainMenuPrompts.add(" - Request to unfreeze your account.");
    }

    /**
     * Method used in the constructor to set up the ProposeItemPrompts.
     */
    private void setUpProposeItemPrompts(){
        proposeItemPrompts = new ArrayList<>();
        proposeItemPrompts.add("Enter \"0\" to go back to the main menu at any time."); //0
        proposeItemPrompts.add("Otherwise, enter the item name:"); //1
        proposeItemPrompts.add("Enter the item's Category:");//2
        proposeItemPrompts.add("Enter a description for the item:");//3
        proposeItemPrompts.add("Enter the item's quality rating from 1-10:");//4
        proposeItemPrompts.add("Your item is waiting to be reviewed by an Administrator, please check back later.");//5
    }

    /**
     * This method is used to display the main menu prompts with the correct number options
     */
    public void displayMainMenu(){
        StringBuilder selections = new StringBuilder();
        for (int i = 0; i < mainMenuPrompts.size(); i++){
            selections.append(i);
            selections.append(". ");
            selections.append(mainMenuPrompts.get(i));
            selections.append('\n');
        }
        System.out.println(instruction);
        System.out.println(selections);
    }

    public void requestUnfreeze(){
        System.out.println("You have requested to unfreeze your account. Please wait until an adminsitrator reviews your request.");
        System.out.println("Type \"0\" to return to the main menu");
    }

    public void incorrectSelection(){
        System.out.println("That is not a valid option, please try again.");
    }

    /**
     * Displays the options for a selected trade
     */
    public void displayTradeOptions(){
        System.out.println("Here are options for the selected trade.");
        System.out.println("Type 1 to edit the trade's meeting");
        System.out.println("Type 2 to agree to the trade's meeting");
        System.out.println("Type 3 to confirm the trade happened");
    }

    /**
     * Displays a single string to screen.
     * @param string The string that is to be displayed.
     */
    public void displayString(String string){
        System.out.println(string);
    }

    /**
     * Displays the string "Returning to Main Menu..."
     */
    public void returnToMain(){
        System.out.println("Returning to Main Menu...");
    }

    /**describe the process of requesting a trade
     * @param i the integer represents different states in the process of requesting a trade
     */
    public void displayTradeProcess(int i){
        switch (i){
            case 1:
                System.out.println("Request fails: You have achieved the max number of weekly trades");
                break;
            case 2:
                System.out.println("Request fails: Please lend or trade before borrowing");
                break;
            case 3:
                System.out.println("Request successes: Your trade is now in progress");
                break;
        }
    }

    /**
     * Displays the submenu for the proposal screen
     */
    public void displayProposalMenu(){
        System.out.println("Please select one of the following options:");
        System.out.println("0. Go back");
        System.out.println("1. Initiate a one-way trade");
        System.out.println("2. Initiate a two-way trade");

    }
}
