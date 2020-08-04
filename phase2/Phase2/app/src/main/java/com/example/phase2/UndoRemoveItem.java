package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class UndoRemoveItem extends AppCompatActivity {
    private ItemManager itemManager;
    private String username;
    private Integer chosenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        viewList();
        
    }

    private void viewList() {
        final List<Integer> removedItems = itemManager.getRemovedItemIds(username);
        ArrayList<String> removePresenter = new ArrayList<>();
        for(Integer i: removedItems){
            removePresenter.add(itemManager.getItemInString(i));
        }
        ArrayAdapter<String> removeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, removePresenter);
        setContentView(R.layout.activity_undo_remove_item);
        ListView listView = findViewById(R.id.removedItem);
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
        Bundle bundle = new Bundle();
        bundle.putString("undoType", "undoRemove");
        undoFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.removedItem, undoFragment).commit();
    }

    public void onUndoClick(){
        itemManager.undoRemoval(chosenItem);
        Toast.makeText(this,
                "Successfully undo remove", Toast.LENGTH_SHORT).show();
    }

    public void onCancel(){
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
    }
}