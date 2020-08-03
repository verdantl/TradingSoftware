package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class DisplayTradeOptions1Activity extends AppCompatActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private String currentTrader;
    private Integer chosenItem;
    private boolean oneWay;

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
        viewStart();
    }

    public void viewStart() { setContentView(R.layout.activity_item_options); }

    public void oneWayChoice(View view) {
        oneWay = true;
        continuing(view);
    }

    public void twoWayChoice(View view) {
        if (itemManager.getApprovedItemsIDs(currentTrader).size() < 1){
            Toast.makeText(this, "You must have items in your inventory to be able to " +
                    "initiate a two-way trade.", Toast.LENGTH_SHORT).show();
        }
        else {
            oneWay = false;
            continuing(view);
        }
    }

    public void continuing(View view) {
        Intent intent = new Intent(this, DisplayTradeOptions2Activity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("OneWay", oneWay);
        startActivity(intent);
        finish();
    }
}