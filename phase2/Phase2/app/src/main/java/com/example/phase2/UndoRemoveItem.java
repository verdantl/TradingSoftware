package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;


import java.util.ArrayList;
import java.util.List;

public class UndoRemoveItem extends AppCompatActivity implements UndoFragment.UndoClick {
    private Bundle bundle;
    private ItemManager itemManager;
    private String username;
    private Integer chosenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        viewList();
        
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UndoActivity.class);
        bundle.remove("ItemManager");
        bundle.putSerializable("ItemManager", itemManager);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void viewList() {
        final List<Integer> removedItems = itemManager.getRemovedItemIds(username);
        ArrayList<String> removePresenter = new ArrayList<>();
        setContentView(R.layout.activity_undo_remove_item);
        ListView listView = findViewById(R.id.removedItem);
        for(Integer i: removedItems){
            removePresenter.add(itemManager.getItemInString(i));
        }
        ArrayAdapter<String> removeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, removePresenter);
        listView.setAdapter(removeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFragment();
                chosenItem = removedItems.get(i);
            }
        });
    }

    private void displayFragment() {
        UndoFragment undoFragment = new UndoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.undoRemoveItem_container, undoFragment).commit();
    }


    @Override
    public void onUndoClick(View view) {
        itemManager.undoRemoval(chosenItem);
        Toast.makeText(this,
                "Successfully undo remove", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void onCancelClick(View view) {
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }
}