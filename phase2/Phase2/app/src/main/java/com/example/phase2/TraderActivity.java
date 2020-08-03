package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;
import com.example.phase2.phase2.TraderPrompts;

public class    TraderActivity extends AppCompatActivity {

    private TraderPrompts traderPrompts;
    private TraderManager traderManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private AdminActions adminActions;
    private String currentTrader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = (String) bundle.getString("Username");
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        setContentView(R.layout.activity_trader);
    }
/*
    Methods here will be called when the corresponding button is clicked

    You can pass around use case classes by calling Intent.putExtra(String key, UseCase class)
    (Just like the dictionary)


    You can get Use Case classes by calling getIntent().getSerializableExtra(String key)
    (Don't forget to cast it)

    You can pass around Strings, Integers, and other primitives through putExtra and get them through
    getStringExtra()... etc.

    If you reached the end of the activity (so no more activity to call), then insert finish();

 */
    public void browseAvailableItems(View view){
        Intent intent = new Intent(this, LocationChoiceActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

    public void browseOnGoingTrades(View view){
        Intent intent = new Intent(this,BrowseTradesActivity.class);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

    public void editInventory(View view){
        Intent intent = new Intent(this, EditInventoryActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

    public void editWishlist(View view){
        Intent intent = new Intent(this, EditWishlistActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

    public void automaticTradeSuggestion(View view){
        //TODO: Implement this method
    }

    public void viewUserInfo(View view){
        //TODO: Implement this method
    }

    public void requestToUnfreeze(View view){
        //TODO: Implement this method
    }

    public void changeTraderPassword(View view){
        Intent i =  new Intent(this, ChangePasswordActivity.class);
        i.putExtra("TraderManager", traderManager);
        i.putExtra("CurrentTrader", currentTrader);
        i.putExtra("AdminActions",adminActions);
        startActivity(i);
    }

}