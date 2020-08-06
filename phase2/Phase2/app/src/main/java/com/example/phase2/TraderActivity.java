package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;
import com.example.phase2.phase2.TraderPrompts;

import java.util.ArrayList;
import java.util.List;

public class TraderActivity extends AppCompatActivity {

    private Bundle bundle;
    private TraderManager traderManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private AdminActions adminActions;
    private String currentTrader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = bundle.getString("Username");
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        setContentView(R.layout.activity_trader);
        TextView textView = findViewById(R.id.textView15);
        textView.setText(bundle.getString("Username"));
    }

    public void browseAvailableItems(View view){
        Intent intent = new Intent(this, LocationChoiceActivity.class);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("Username", currentTrader);
        intent.putExtra("adminActions", adminActions);
        startActivity(intent);
    }

    public void browseOnGoingTrades(View view){
        Intent intent = new Intent(this,BrowseTradesActivity.class);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }

    public void editInventory(View view){
        Intent intent = new Intent(this, EditInventoryActivity.class);
        intent.putExtras(bundle);
        //intent.putExtra("ItemManager", itemManager);
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
        Intent intent = new Intent(this, ViewMyUserInfo.class);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }

    //TODO: javadoc
    public void requestAdmin(View view){
        Intent intent =  new Intent(this, RequestAdminActivity.class);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("CurrentTrader", currentTrader);

        startActivity(intent);
    }

    public void changeTraderPassword(View view){
        Intent i =  new Intent(this, ChangeTraderPassword.class);
        i.putExtra("TraderManager", traderManager);
        i.putExtra("CurrentTrader", currentTrader);
        startActivity(i);
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras(bundle);
//        bundle.remove("TradeManager");
//        bundle.remove("MeetingManager");
//        bundle.remove("TraderManager");
//        bundle.remove("ItemManager");
//        bundle.remove("CurrentTrader");
//        bundle.remove("AdminActions");
//        intent.putExtras(bundle);
//        intent.putExtra("Username", currentTrader);
//        intent.putExtra("ItemManager", itemManager);
//        intent.putExtra("TradeManager", tradeManager);
//        intent.putExtra("TraderManager", traderManager);
//        intent.putExtra("MeetingManager", meetingManager);
//        intent.putExtra("AdminActions", adminActions);
//        System.out.println(adminActions==null);
        startActivity(intent);
    }
}