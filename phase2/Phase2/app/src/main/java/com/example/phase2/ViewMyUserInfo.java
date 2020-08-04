package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class ViewMyUserInfo extends AppCompatActivity {

    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private ItemManager itemManager;
    private Integer trade;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = bundle.getString("CurrentTrader");

        Intent intent = getIntent();
        String message = "Username: "+currentTrader;
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView7);
        textView.setText(message);

        message = "Homecity: "+traderManager.getHomeCity(currentTrader);
        textView = findViewById(R.id.textView8);
        textView.setText(message);

        if(traderManager.getIsFrozen(currentTrader)){
        message = "Status: Frozen";}
        else{
            message = "Status: Trading Available ";
        }

        textView = findViewById(R.id.textView6);
        textView.setText(message);

        //copy and pasted from TraderSystem finds top traders
        TreeMap<String, Integer> tradingPartners = new TreeMap<>();
        List<Integer> trades = traderManager.getTrades(currentTrader);
        // iterating over the user's trades
        for(Integer i: trades){
            String traderToAdd;
            // getting the right trader from the pair of traders involved in the trade.
            if (tradeManager.getTradeInitiator(i).equals(currentTrader)) {
                traderToAdd = tradeManager.getTradeReceiver(i);
            }
            else {
                traderToAdd = tradeManager.getTradeInitiator(i);
            }
            // putting the other trader into the hashmap
            if (tradingPartners.containsKey(traderToAdd)) {
                tradingPartners.put(traderToAdd, tradingPartners.get(traderToAdd) + 1);
            }
            else {
                tradingPartners.put(traderToAdd, 1);
            }
        }
        TreeSet<String> traders = new TreeSet<>(tradingPartners.keySet());
       if(trades.size() >= 1) {
           message = traders.toArray()[trades.size() - 1].toString();
           textView = findViewById(R.id.textView13);
           textView.setText(message);
               if(trades.size() >= 2) {
                   message = traders.toArray()[trades.size() - 2].toString();
                   textView = findViewById(R.id.textView11);
                   textView.setText(message);
                       if(trades.size() >= 3) {
                       message = traders.toArray()[trades.size() - 3].toString();
                       textView = findViewById(R.id.textView12);
                       textView.setText(message);
                       setContentView(R.layout.activity_view_my_user_info);
                   }
               }
       }

    }

    public void viewList(){
        //System.out.println(traderManager.getTrades(currentTrader));
        //System.out.println(tradeManager.getIncompleteTrades(traderManager.getTrades(currentTrader)));
        //System.out.println(meetingManager.getOnGoingMeetings((traderManager.getTrades(currentTrader))));

        List<Integer> trades = traderManager.getTrades(currentTrader);
        List<Integer> items = new ArrayList<>();

        //this was copy and pasted from trader system
        int n = trades.size();
        for (int j = 1; j < n; j++) {
            Integer bruh = trades.get(j);
            int i = j-1;
            while (i > -1 && tradeManager.getDateCreated(trades.get(i))
                    .isAfter(tradeManager.getDateCreated(bruh))) {
                trades.set(trades.get(i+1), trades.get(i));
                i--;
            }
            trades.set(i+1, bruh);
        }
        // This is an ugly, ugly piece of code but I'm too tired to do better.
        // iterating over the list of this user's trades from front to back
        for (int j = trades.size() - 1; j >= 0; j--) {
            // if the trade is completed, then we'll consider it
            if (meetingManager.getMeetingStatus(trades.get(j)).equals("COMPLETED")) {
                // iterating over the 1-2 items involved with the trade
                for (Integer i: tradeManager.getItems(trades.get(j))) {
                    // if the item is now owned by the appropriate trader
                    if (itemManager.getOwner(i).equals(currentTrader)){
                        items.add(i);
                    }
                }
            }
        }

        final List<Integer> recentTrades = items;
        setContentView(R.layout.activity_view_my_user_info);
        ListView listView = findViewById(R.id.recent_trades);
        //unsure about this line
        ArrayAdapter<Integer> allTradesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, recentTrades);
        listView.setAdapter(allTradesAdapter);
        //if the code below doesn't work just delete and add the "}"'s
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trade = recentTrades.get(i);
                displayEditTrade();
            }
        });
    }

    public void displayEditTrade(){
        Intent intent = new Intent(this, EditTradeActivity.class);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("Trade", trade);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }
}
