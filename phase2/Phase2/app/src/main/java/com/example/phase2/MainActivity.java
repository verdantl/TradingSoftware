package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.phase2.phase2.*;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ConfigGateway configGateway;
    private AdminActions adminActions;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private TradeManager tradeManager;

    private final String ADMINPATH = "src/main/java/com/example/phase2/phase2/configfiles/admins.ser";
    private final String ITEMPATH = "src/main/java/com/example/phase2/phase2/configfiles/items.ser";
    private final String MEETINGPATH = "src/main/java/com/example/phase2/phase2/configfiles/meetings.ser";
    private final String TRADERPATH = "src/main/java/com/example/phase2/phase2/configfiles/traders.ser";
    private final String TRADEPATH = "src/main/java/com/example/phase2/phase2/configfiles/trade.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        configGateway = new ConfigGateway();
//        try {
//            adminActions = (AdminActions) configGateway.readInfo(ADMINPATH);
//            itemManager = (ItemManager) configGateway.readInfo(ITEMPATH);
//            meetingManager = (MeetingManager) configGateway.readInfo(MEETINGPATH);
//            tradeManager = (TradeManager) configGateway.readInfo(TRADEPATH);
//            traderManager = (TraderManager) configGateway.readInfo(TRADERPATH);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        HashMap<String, Admin> admins = new HashMap<>();
        adminActions = new AdminActions(admins);
        itemManager = new ItemManager(new HashMap<Integer, Item>());
        meetingManager = new MeetingManager(new HashMap<Integer, Meeting>());
        tradeManager = new TradeManager(new HashMap<Integer, Trade>());
        traderManager = new TraderManager(new HashMap<String, Trader>(), 3, 1, 1);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);

    }


}