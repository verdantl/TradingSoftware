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

public class EditWishlistActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private int chosenItem;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        viewList();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, TraderActivity.class);
        bundle.remove("ItemManager");
        bundle.remove("TraderManager");
        bundle.remove("CurrentTrader");
        intent.putExtras(bundle);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("Username", currentTrader);
        startActivity(intent);
    }

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
    public void displayRemoveItem(){
        Intent intent = new Intent(this, RemoveItemWishlistActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }
}