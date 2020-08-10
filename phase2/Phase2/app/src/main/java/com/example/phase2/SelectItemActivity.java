package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SelectItemActivity extends BundleActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
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
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        currentTrader = getUsername();
        chosenItem = (Integer) bundle.getInt("ChosenItem");
        oneWay = (Boolean) bundle.getBoolean("OneWay");
        temporary = (Boolean) bundle.getBoolean("Temporary");
        viewList();
    }

    public void viewList(){
        final List<Integer> itemList = itemManager.getApprovedItemsIDs(currentTrader);
        List<String> itemNameList = new ArrayList<>();
        List<String> itemDescription = new ArrayList<>();
        for (Integer item : itemList) {
            itemNameList.add(itemManager.getItemName(item));
        }
        for (Integer item : itemList) {
            itemDescription.add(itemManager.getItemDescription(item));
        }
        setContentView(R.layout.activity_select_item);
        ListView listView = findViewById(R.id.selectItem);
        ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, itemNameList);
        listView.setAdapter(allItemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                continuing();
                myItem = itemList.get(i);
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
        finish();
    }
}