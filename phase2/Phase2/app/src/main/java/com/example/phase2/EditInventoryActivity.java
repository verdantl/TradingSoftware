package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.ItemManager;

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
        super.onBackPressed();
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
     * This method is called when the user clicks on an item. It starts
     * the RemoveItemInventoryActivity.
     */
    public void displayRemoveItem(){
        Intent intent = new Intent(this, RemoveItemInventoryActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
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