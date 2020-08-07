package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;

import java.util.ArrayList;
import java.util.List;

public class TutorialBrowseItemsActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private String itemInfo;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        dialog = new Dialog(this);
        setContentView(R.layout.activity_tutorial_browse_items);
        viewList();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }

    public void viewList(){
        final List<Integer> itemIDs = itemManager.getAllApprovedItemsIDs("");
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        ListView listView = findViewById(R.id.itemsList);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemInfo = itemManager.getItemInString(itemIDs.get(i));
                displayDialog();
            }
        });

    }

    public void displayDialog(){
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(itemInfo);
        dialog.show();
    }
}