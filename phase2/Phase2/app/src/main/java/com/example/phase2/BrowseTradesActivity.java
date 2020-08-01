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

import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class BrowseTradesActivity extends AppCompatActivity {

    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_trades);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        viewList();
    }

    public void viewList(){
        List<Integer> incompleteTrades = tradeManager.getIncompleteTrades(traderManager.getTrades(currentTrader));
        final List<Integer> onGoingTrades = meetingManager.getOnGoingMeetings(incompleteTrades);
        setContentView(R.layout.activity_browse_trades);
        ListView listView = findViewById(R.id.tradesList);
        ArrayAdapter<Integer> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, onGoingTrades);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayEditTrade();
                trade = onGoingTrades.get(i);
            }
        });
    }


    public void displayFragment() {
        FreezeFragment freezeFragment = new FreezeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragment_freeze_container, freezeFragment).commit();
    }

    public void displayEditTrade(){
        Intent intent = new Intent(this, EditTradeActivity.class);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("Trade", trade);
        startActivity(intent);
    }
}


