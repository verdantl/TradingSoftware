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

import java.util.ArrayList;
import java.util.List;

public class SelectItemActivity extends BundleActivity implements RecommendedItemDialog.RecommendedItemListener, ClickableList{

    private ItemManager itemManager;
    private String currentTrader;
    private Integer chosenItem;
    private Integer myItem;
    private boolean oneWay;
    private boolean temporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        currentTrader = getUsername();
        chosenItem = bundle.getInt("ChosenItem");
        oneWay = bundle.getBoolean("OneWay");
        temporary = bundle.getBoolean("Temporary");
        displayRecommendedItemFragment();
    }

    private void displayRecommendedItemFragment() {
        RecommendedItemDialog recItemFrag = new RecommendedItemDialog();
        recItemFrag.show(getSupportFragmentManager(), "recItemFrag");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        continuing();
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
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}