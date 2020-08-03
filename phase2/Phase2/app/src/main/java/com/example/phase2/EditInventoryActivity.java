package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class EditInventoryActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private String currentTrader;
    private int chosenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        viewList();
    }

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
    public void displayRemoveItem(){
        Intent intent = new Intent(this, RemoveItemInventoryActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("ChosenItem", chosenItem);
        startActivity(intent);
    }
    public void addNewItem(View view){
        Intent intent = new Intent(this, AddNewItemActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

}