package com.example.phase2.phase1;

import com.example.phase2.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FreeSystem extends UserSystem{

    private final TraderPrompts traderPrompts;
    private final ItemManager itemManager;

    private final Scanner sc;

    /**
     * Constructor for TraderSystem.
     * @param itemManager The ItemManager that this TraderSystem will use.
     */
    public FreeSystem(ItemManager itemManager) {
        this.itemManager = itemManager;
        this.traderPrompts = new TraderPrompts();
        sc = new Scanner(System.in);
        running = false;
    }

    public void run() {
        init();
        int option;
        while (running){
            int numOptions = 1;
            ArrayList<Integer> validOptions = new ArrayList<>();
            for (int i = 0; i < numOptions + 1; i++) {
                validOptions.add(i);
            }

            // Present the options to the user here.
            // traderPrompts.displayMainMenu();

            option = Integer.parseInt(sc.nextLine());

            // Ensuring that the user chooses a valid option.
            while (!validOptions.contains(option)) {
                traderPrompts.incorrectSelection();
                option = Integer.parseInt(sc.nextLine());
            }

            switch(option) {
                case 0:
                    // Exit the program
                    stop();
                    break;
                case 1:
                    // Browse their inventory
                    browseInventoryOfItems();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    /**
     * This is the method where the user browses the inventory, and decides if they want to add items to their wantToBorrow list or propose trades.
     */
    private void browseInventoryOfItems(){
        List<Integer> availableOptions = new ArrayList<>();
        List<Integer> itemList = itemManager.getAllApprovedItemsIDs(null);

        availableOptions.add(0);
        availableOptions.addAll(itemList);

        int o = -1;
        while(o != 0);{
            System.out.println("Here are all of the items currently available in the system:");
            for (String str: itemManager.getListOfItemsInString(itemList)){
                System.out.println(str);
            }
            System.out.println("Press [0] to return to the main menu, or type the ID of the item " +
                    "you would like to view individually:");
            o = Integer.parseInt(sc.nextLine());
            while (!availableOptions.contains(o)){
                traderPrompts.incorrectSelection();
                o = Integer.parseInt(sc.nextLine());
            }
            if (o != 0){
                int o2 = o;
                while (o2 != 0){
                    System.out.println(itemManager.getItemInString(o2));
                    System.out.println("Enter [0] to view all items or the ID of the next item you" +
                            " would like to view:");
                    o2 = Integer.parseInt(sc.nextLine());
                    while (!availableOptions.contains(o2)){
                        traderPrompts.incorrectSelection();
                        o2 = Integer.parseInt(sc.nextLine());
                    }
                }
            }
        }
    }

}
