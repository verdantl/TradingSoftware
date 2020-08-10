package com.example.phase2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity class responsible for viewing items in an inventory in the Trading System.
 */
public class EditInventoryActivity extends BundleActivity implements ClickableList {
    private ItemManager itemManager;
    private String currentTrader;
    private int chosenItem;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        currentTrader = getUsername();
        viewList();
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        replaceUseCase(itemManager);
        Intent intent = new Intent(this, TraderActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called to set up the list, display item names and call the displayRemoveItem
     * method when an item is selected.
     */
    public void viewList(){
        final List<Integer> itemIDs = itemManager.getApprovedItemsIDs(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_edit_inventory);
        ListView listView = findViewById(R.id.inventoryList);
        ArrayAdapter<String> inventoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(inventoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenItem = itemIDs.get(i);
                displayRemoveItem();
            }
        });

    }

    /**
     * This method is call to remove item from system and displays it is successfully.
     */
    public void removeItem(){
        itemManager.removeItem(chosenItem);
        Toast.makeText(this, "Successfully removed the item",
                Toast.LENGTH_LONG).show();
        viewList();
    }

    /**
     * This method is called when the user clicks on an item. It creates a new dialog showing the
     * item information and options to remove the item or cancel.
     */
    public void displayRemoveItem(){
        String itemInfo = itemManager.getItemInString(chosenItem);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to remove the item?\n"+itemInfo)
                .setPositiveButton("Remove Item", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeItem();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        viewList();
                    }
                });
        builder.show();
    }

    /**
     * This method is called when the user clicks on the Add New Item button. It starts
     * the AddNewItemActivity
     * @param view A view
     */
    public void addNewItem(View view){
        Intent intent = new Intent(this, AddNewItemActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

}