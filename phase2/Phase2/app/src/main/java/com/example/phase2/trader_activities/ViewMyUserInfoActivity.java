package com.example.phase2.trader_activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phase2.R;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.users.TraderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class ViewMyUserInfoActivity extends BundleActivity implements ClickableList {

    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private ItemManager itemManager;


    /**
     * the start up method for the ViewMyUserInfo activity to run
     * gets all the needed variable from the bundle a =nd update the display
     * @param savedInstance the bundle inputted to initialize the activity
     */
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        currentTrader = getUsername();
        setContentView(R.layout.activity_view_my_user_info);

        changeText((TextView) findViewById(R.id.textView7), "Username: "+currentTrader);
        String temp = traderManager.getHomeCity(currentTrader);
        if(!(temp == null)){
        changeText((TextView) findViewById(R.id.textView8), "Homecity: "+traderManager.getHomeCity(currentTrader));}
        else {
            changeText((TextView) findViewById(R.id.textView8), "Homecity: None");
        }

        if(traderManager.getIsFrozen(currentTrader)){
        changeText((TextView) findViewById(R.id.textView6), "Status: Frozen");}
        else{
            changeText((TextView) findViewById(R.id.textView6), "Status: Trading Available");
        }

        List<Integer> trades = traderManager.getTrades(currentTrader);
        TreeSet<String> traders = findsTopTraders(trades);
        if(traders.size() >= 1) {
            changeText((TextView) findViewById(R.id.textView13), traders.toArray()[traders.size() - 1].toString());
                if(traders.size() >= 2) {
                    changeText((TextView) findViewById(R.id.textView11), traders.toArray()[traders.size() - 2].toString());
                        if(traders.size() >= 3) {
                            changeText((TextView) findViewById(R.id.textView12), traders.toArray()[traders.size() - 3].toString());
                        }
                }
        }
        viewList();        //setContentView(R.layout.activity_view_my_user_info);
    }

    /**
     * the method needed to run the ListView in ViewMyUserInfo activity
     */
    public void viewList(){
        final List<String> recentTrades = findRecentTrades();
        //setContentView(R.layout.activity_view_my_user_info);
        ListView listView = findViewById(R.id.recent_trades);
        //unsure about this line
        ArrayAdapter<String> allTradesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, recentTrades);
        listView.setAdapter(allTradesAdapter);
/*
        //if the code below doesn't work just delete and add the "}"'s
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trade = recentTrades.get(i);
                displayEditTrade();
            }
        });
 */
    }

    private void changeText(TextView textView, String message){
            textView.setText(message);
    }

    private TreeSet<String> findsTopTraders(List<Integer> trades){
        //copy and pasted from TraderSystem finds top traders
        TreeMap<String, Integer> tradingPartners = new TreeMap<>();
        String traderToAdd;
        // iterating over the user's trades
        for(Integer i: trades){
            if (!tradeManager.getIncompleteTrades(trades).contains(i)) {
                 traderToAdd = tradeManager.getOtherTrader(i, currentTrader);

                // putting the other trader into the hashmap
                if (tradingPartners.containsKey(traderToAdd)) {
                    tradingPartners.put(traderToAdd, tradingPartners.get(traderToAdd) + 1);
                } else {
                    tradingPartners.put(traderToAdd, 1);
                }
            }
        }
        return new TreeSet<>(tradingPartners.keySet());
    }

    private List<String> findRecentTrades(){
        List<Integer> trades = traderManager.getTrades(currentTrader);
        List<String> items = new ArrayList<>();

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
            if (meetingManager.getMeetingStatus(trades.get(j)).equals("Completed")) {
                // iterating over the 1-2 items involved with the trade
                for (Integer i: tradeManager.getItems(trades.get(j))) {
                    // if the item is now owned by the appropriate trader
                    //if (itemManager.getOwner(i).equals(currentTrader)){
                        items.add(itemManager.getItemName(i));
                    //}
                }
            }
        }
        return items;
    }
}
