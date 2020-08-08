package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class LocationChoiceActivity extends AppCompatActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private String currentTrader;
    private boolean useLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        viewStart();
    }

    public void viewStart(){
        setContentView(R.layout.activity_location_choice);
    }

    public void onClickUseLocation(View view){
        useLocation = true;
        continueToNextScreen(view);
    }

    public void onClickDontUseLocation(View view){
        useLocation = false;
        continueToNextScreen(view);
    }

    public void continueToNextScreen(View view){
        Intent intent = new Intent(this, BrowseItemsActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("LocationChoice", useLocation);
        startActivity(intent);
        finish();
    }
}