package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.phase2.phase2.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AdminActions adminActions;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;

    private final String ADMINPATH = "admins.ser";
    private final String ITEMPATH = "items.ser";
    private final String MEETINGPATH = "meetings.ser";
    private final String TRADERPATH = "traders.ser";
    private final String TRADEPATH = "trade.ser";

    private final ConfigGateway configGateway = new ConfigGateway();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadClasses();
        startLogin();
    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }

    private void loadClasses(){
        final File contextFilesDir = getApplicationContext().getFilesDir();
        try {
            adminActions = (AdminActions) configGateway.readInfo(contextFilesDir + "admins.ser");
            meetingManager = (MeetingManager) configGateway.readInfo(contextFilesDir + "meetings.ser");
            itemManager = (ItemManager) configGateway.readInfo(contextFilesDir + "items.ser");
            tradeManager = (TradeManager) configGateway.readInfo(contextFilesDir + "trades.ser");
            traderManager = (TraderManager) configGateway.readInfo(contextFilesDir + "traders.ser");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            downloadInitial();
        }
    }

    private void downloadInitial(){
        final File contextFilesDir = getApplicationContext().getFilesDir();
        HashMap<String, Admin> admins = new HashMap<>();
        admins.put("Admin", new Admin("Admin", "Wordpass", "2020-07-27", true));
        adminActions = new AdminActions(admins);
        Item item1 = new Item(1, "Bruh", "Arjun");
        item1.setStatus(ItemStatus.AVAILABLE);
        item1.setCategory("What do you think?");
        item1.setDescription("bruhbruhbruh");
        HashMap<Integer, Item> tempMap = new HashMap<>();
        tempMap.put(1, item1);
        itemManager = new ItemManager(tempMap);
        meetingManager = new MeetingManager(new HashMap<Integer, Meeting>());
        tradeManager = new TradeManager(new HashMap<Integer, Trade>());
        traderManager = new TraderManager(new HashMap<String, Trader>(), 3, 1, 0);
        traderManager.addTrader(new Trader("Trader1", "Password"));
        traderManager.addTrader(new Trader("Trader2", "Password2"));
        Trader traderFlagged = new Trader("Arjun", "Password3");
        traderFlagged.setHomeCity("Toronto");
        traderFlagged.setFlagged(true);
        traderManager.addTrader(traderFlagged);

        //Creates the items for the trade and adds them to itemManager
        List<Integer> tempTradeItems = new ArrayList<>();
        Integer temp = itemManager.addItem("Bike", "Arjun");
        tempTradeItems.add(temp);
        itemManager.addItemDetails(temp, "Transportation", "Its a bike", 10);
        itemManager.changeStatusToUnavailable(temp);
        //CREATES THE TRADE AND ADDS IT TO TRADE INVENTORY
        Integer tradeId = tradeManager.createTrade("Arjun", "Trader1", "ONEWAY", true, tempTradeItems);
        LocalDate tempDate = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId,tempDate);
        traderManager.addNewTrade("Trader1", tradeId, tempDate);
        //CREATES THE MEETING
        meetingManager.createMeeting(tradeId, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");


        //This is to set up a two way trade that is permanent
        List<Integer> tempTradeItems2 = new ArrayList<>();
        Integer temp2 = itemManager.addItem("Jacket", "Trader1");
        Integer temp3 = itemManager.addItem("Watch", "Arjun");
        tempTradeItems2.add(temp2);
        tempTradeItems2.add(temp3);
        itemManager.addItemDetails(temp2, "Clothing", "Its a jacket", 7);
        itemManager.addItemDetails(temp3, "Accessories", "Its a watch", 5);
        itemManager.changeStatusToUnavailable(temp2);
        itemManager.changeStatusToUnavailable(temp3);

        Integer tradeId2 = tradeManager.createTrade("Arjun", "Trader1", "TWOWAY", true, tempTradeItems2);

        LocalDate tempDate2 = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId2,tempDate2);
        traderManager.addNewTrade("Trader1", tradeId2, tempDate2);

        meetingManager.createMeeting(tradeId2, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId2, LocalDate.now(), LocalDate.now(),
                "Toronto", "N/A");

        //Set up a one-way trade that is temporary

        //Set up a two-way trade that is temporary






        adminActions.newAdmin("Admin2", "Wordpass");
        adminActions.newAdmin("Sup", "nothing");
        Trader traderUnfreeze = new Trader("Jeffrey", "Password4");
        traderUnfreeze.setFrozen(true);
        traderUnfreeze.setRequestToUnfreeze(true);
        traderManager.addTrader(traderUnfreeze);

        try {
            configGateway.saveInfo(contextFilesDir + ADMINPATH, adminActions);
            configGateway.saveInfo(contextFilesDir + MEETINGPATH, meetingManager);
            configGateway.saveInfo(contextFilesDir + TRADERPATH, traderManager);
            configGateway.saveInfo(contextFilesDir + TRADEPATH, tradeManager);
            configGateway.saveInfo(contextFilesDir + ITEMPATH, itemManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}