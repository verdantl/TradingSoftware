package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class BrowseTradesActivity extends BundleActivity implements ClickableList {

    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    private ItemManager itemManager;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
//        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
//        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
//        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
//        currentTrader = (String) bundle.getSerializable("CurrentTrader");
//        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) getUseCase("TradeManager");
        meetingManager = (MeetingManager) getUseCase("MeetingManager");
        traderManager = (TraderManager) getUseCase("TraderManager");
        currentTrader = (String) getUseCase("Username");
        itemManager = (ItemManager) getUseCase("ItemManager");
        viewList();
    }

    public void viewList(){
        final List<Integer> onGoingTrades = meetingManager.getOnGoingMeetings(traderManager.getTrades(currentTrader));
        setContentView(R.layout.activity_browse_trades);
        ListView listView = findViewById(R.id.tradesList1);
        ArrayAdapter<Integer> allTradesAdapter = new ArrayAdapter<Integer>(this,
               android.R.layout.simple_list_item_1, onGoingTrades);
        listView.setAdapter(allTradesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               trade = onGoingTrades.get(i);
                displayEditTrade();
            }
       });
    }

    public void displayEditTrade(){
        Intent intent = new Intent(this, EditTradeActivity.class);
        intent.putExtra("Trade", trade);
        putBundle(intent);
        //intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
//        Intent intent = new Intent(this, TraderActivity.class);
//        bundle.remove("TradeManager");
//        bundle.remove("MeetingManager");
//        bundle.remove("TraderManager");
//        bundle.remove("ItemManager");
//        intent.putExtras(bundle);
//        intent.putExtra("TradeManager",tradeManager);
//        intent.putExtra("MeetingManager", meetingManager);
//        intent.putExtra("TraderManager", traderManager);
//        intent.putExtra("ItemManager", itemManager);
//        intent.putExtra("Username", currentTrader);
//        startActivity(intent);

        replaceUseCase(tradeManager);
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        replaceUseCase(itemManager);
        super.onBackPressed();
    }

}


