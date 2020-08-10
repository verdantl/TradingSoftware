package com.example.phase2;

import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class SelectItemActivity extends BundleActivity implements RecommendedItemDialog.RecommendedItemListener, ClickableList{

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;
    private Integer myItem;
    private boolean oneWay;
    private boolean temporary;
    private boolean online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();
        chosenItem = bundle.getInt("ChosenItem");
        oneWay = bundle.getBoolean("OneWay");
        temporary = bundle.getBoolean("Temporary");
        online = bundle.getBoolean("Online");
        displayRecommendedItemFragment();
    }

    private void displayRecommendedItemFragment() {
        RecommendedItemDialog recItemFrag = new RecommendedItemDialog();
        recItemFrag.show(getSupportFragmentManager(), "recItemFrag");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        myItem = bestItem();
        Toast.makeText(this, "We chose the item " + itemManager.getItemName(myItem) +
                " with ID " + myItem.toString(), Toast.LENGTH_SHORT).show();
        continuing();
    }

    /**
     * Helper method for onDialogPositiveClick that returns the best item for the trade.
     * @return the ID of the best item for the trade.
     */
    private int bestItem() {
        String receiver = itemManager.getItemOwner(chosenItem);
        for(Integer receiverIDs : traderManager.getWishlistIds(receiver)){
            if(itemManager.getApprovedItemsIDs(currentTrader).contains(receiverIDs)){
                return receiverIDs;
            }
        }
        //the max should be 10
        int ratingDifference = 11;
        int helper;
        int currentItem = -1;
        //the suggested item is the one that is closest in quality
        for(Integer i : itemManager.getApprovedItemsIDs(currentTrader)){
            helper = Math.abs(Integer.parseInt(itemManager.getItemQuality(i)) -
                    Integer.parseInt(itemManager.getItemQuality(chosenItem)));
            if (helper == 0) {
                currentItem = i;
                return currentItem;
            }
            else if (helper < ratingDifference) {
                currentItem = i;
                ratingDifference = helper;
            }
        }
        return currentItem;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        viewList();
    }

    public void viewList(){
        final List<Integer> itemIDs = itemManager.getApprovedItemsIDs(currentTrader);
        List<String> itemNames = new ArrayList<>();
        for (Integer id: itemIDs){
            itemNames.add(itemManager.getItemName(id));
        }
        setContentView(R.layout.activity_select_item);
        ListView listView = findViewById(R.id.selectItem);
        ArrayAdapter<String> inventoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNames);
        listView.setAdapter(inventoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myItem = itemIDs.get(i);
                Toast.makeText(SelectItemActivity.this, currentTrader, Toast.LENGTH_SHORT).show();
                continuing();
            }
        });
    }

    public void continuing() {
        Intent intent = new Intent(this, EnterInfoProposeTradeActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("MyItem", myItem);
        intent.putExtra("Temporary", temporary);
        intent.putExtra("OneWay", oneWay);
        intent.putExtra("Online", online);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}