package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class DisplayTradeOptions2Activity extends AppCompatActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private String currentTrader;
    private Integer chosenItem;
    private boolean oneWay;
    private boolean temporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = (String) bundle.getString("CurrentTrader");
        chosenItem = (Integer) bundle.getInt("ChosenItem");
        oneWay = (Boolean) bundle.getBoolean("OneWay");
        viewStart();
    }

    public void viewStart() { setContentView(R.layout.activity_item_options); }

    public void tempChoice(View view) {
        temporary = true;
        continuing(view);
    }

    public void permChoice(View view) {
        temporary = false;
        continuing(view);
    }

    public void continuing(View view) {
        Intent intent;
        if (oneWay) {
            intent = new Intent(this, EnterInfoProposeTradeActivity.class);
            intent.putExtra("MyItem", -1);
        }
        else {
            intent = new Intent(this, SelectItemActivity.class);
        }
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("Temporary", temporary);
        intent.putExtra("OneWay", oneWay);
        startActivity(intent);
        finish();
    }
}