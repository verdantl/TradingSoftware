package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.example.phase2.phase2.*;

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

    //private final String ADMINPATH = "admins.ser";
    //private final String ITEMPATH = "/phase2/configfiles/items.ser";
    //private final String MEETINGPATH = "\\phase2\\configfiles\\meetings.ser";
    //private final String TRADERPATH = "src/main/java/com/example/phase2/phase2/configfiles/traders.ser";
    //private final String TRADEPATH = "src/main/java/com/example/phase2/phase2/configfiles/trade.ser";

    private String nextUser;
    private int nextSystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigGateway configGateway = new ConfigGateway();
//        try {
//            adminActions = (AdminActions) configGateway.readInfo(ADMINPATH);
//            itemManager = (ItemManager) configGateway.readInfo(ITEMPATH);
//            meetingManager = (MeetingManager) configGateway.readInfo(MEETINGPATH);
//            tradeManager = (TradeManager) configGateway.readInfo(TRADEPATH);
//            traderManager = (TraderManager) configGateway.readInfo(TRADERPATH);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        Context context = getApplicationContext();
        //try {
            //adminActions = (AdminActions) configGateway.readInfo(context.getFilesDir() + "admins.ser");
            //meetingManager = (MeetingManager) configGateway.readInfo(context.getFilesDir() + "meetings.ser");
            //itemManager = (ItemManager) configGateway.readInfo(context.getFilesDir() + "items.ser");
            //tradeManager = (TradeManager) configGateway.readInfo(context.getFilesDir() + "trades.ser");
            //traderManager = (TraderManager) configGateway.readInfo(context.getFilesDir() + "traders.ser");

        //} catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
        //}
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
        traderFlagged.setFlagged(true);
        traderManager.addTrader(traderFlagged);
        List<Integer> tempTradeItems = new ArrayList<>();
        tempTradeItems.add(itemManager.addItem("Bike", "Arjun"));
        itemManager.addItemDetails(tempTradeItems.get(0), "Transportation", "Its a bike", 10);
        //CREATES THE TRADE AND ADDS IT TO TRADE INVENTORY
        Integer tradeId = tradeManager.createTrade("Arjun", "Trader1", "ONEWAY", true, tempTradeItems);
        LocalDate tempDate = LocalDate.now();
        traderManager.addNewTrade("Arjun", tradeId,tempDate);
        traderManager.addNewTrade("Trader1", tradeId, tempDate);

        //CREATES THE MEETING
        meetingManager.createMeeting(tradeId, "Arjun", "Trader1", true);
        meetingManager.setMeetingInfo(tradeId, LocalDate.now(), LocalDate.now(),
                "Toronto", "Toronto");

        adminActions.newAdmin("Admin2", "Wordpass");
        adminActions.newAdmin("Sup", "nothing");
        Trader traderUnfreeze = new Trader("Jeffrey", "Password4");
        traderUnfreeze.setFrozen(true);
        traderUnfreeze.setRequestToUnfreeze(true);
        traderManager.addTrader(traderUnfreeze);
        try {
            configGateway.saveInfo(context.getFilesDir() + "admins.ser", adminActions);
            configGateway.saveInfo(context.getFilesDir() + "meetings.ser", meetingManager);
            configGateway.saveInfo(context.getFilesDir() + "traders.ser", traderManager);
            configGateway.saveInfo(context.getFilesDir() + "trades.ser", tradeManager);
            configGateway.saveInfo(context.getFilesDir() + "items.ser", itemManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nextUser = bundle.getString("NextUser");
            nextSystem = bundle.getInt("NextSystem");
        }
        else{
            nextSystem = 0;
        }

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }
}