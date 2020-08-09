package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity class responsible for viewing items in a wishlist in the Trading System.
 */
public class EditWishlistActivity extends BundleActivity {
    private ItemManager itemManager;
    private TraderManager traderManager;
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
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);
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
    public void viewList() {
        final List<Integer> itemIDs = traderManager.getWishlistIds(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id : itemIDs) {
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_edit_wishlist);
        ListView listView = findViewById(R.id.wishList);
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
     * the RemoveItemWishlistActivity.
     */
    public void displayRemoveItem(){
        Intent intent = new Intent(this, RemoveItemWishlistActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
        startActivity(intent);
    }
}