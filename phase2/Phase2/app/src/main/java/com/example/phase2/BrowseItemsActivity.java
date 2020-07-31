package com.example.phase2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class BrowseItemsActivity extends AppCompatActivity {

    private ItemManager itemManager;
    private String currentTrader;
    private int chosenItem;
    private boolean useLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        displayLocationChoiceFragment();
        viewList();
    }

    public void viewList(){
        final List<Integer> itemList = itemManager.getAllApprovedItemsIDs(currentTrader);

        setContentView(R.layout.activity_browse_items);
        ListView listView = findViewById(R.id.selectItem);
        ArrayAdapter<Integer> allItemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(allItemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFragment();
                chosenItem = itemList.get(i);
            }
        });
    }

    public void onClickUseLocation(View view) {
        useLocation = true;
    }

    public void onClickDontUseLocation(View view) {
        useLocation = false;
    }

    public void displayFragment(){

    }

    public void displayLocationChoiceFragment(){
        LocationChoiceFragment lcFragment = new LocationChoiceFragment();
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragManager
                .beginTransaction();
        fragmentTransaction.add(R.id.location_choice_fragment, lcFragment).commit();
    }
}
