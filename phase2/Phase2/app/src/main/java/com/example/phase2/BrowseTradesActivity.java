package com.example.phase2;

import androidx.annotation.Nullable;
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

public class BrowseTradesActivity extends UpdatableBundleActivity implements ClickableList {

    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_trades);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        updateUseCases();
    }

    protected void updateUseCases(){
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
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
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}


