package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;

public class EditTradeActivity extends AppCompatActivity {
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    //private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trade);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        //traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        trade = (Integer) bundle.getSerializable("Trade");

        String tempTradeType = "Trade Type: " + tradeManager.getTradeType(trade);
        TextView tradeType = findViewById(R.id.tradeType);
        tradeType.setText(tempTradeType);


        String tempTradeDuration;
        if(tradeManager.isTradePermanent(trade)){
            tempTradeDuration = "Trade Duration: "+ "Permanent";
        }
        else{
            tempTradeDuration = "Trade Duration: "+ "1 Month";
        }
        TextView tradeDuration = findViewById(R.id.tradeDuration);
        tradeDuration.setText(tempTradeDuration);


        String temptradeWith = "Trade With: "+ tradeManager.getOtherTrader(trade,currentTrader);
        TextView tradeWith = findViewById(R.id.tradeWith);
        tradeWith.setText(temptradeWith);






        String tempDate = "Date: " + meetingManager.getMeetingDate(trade);
        TextView meetingDate = findViewById(R.id.meetingDate);
        meetingDate.setText(tempDate);

        String tempEditNumber = "You can edit " + meetingManager.getEditsLeft(trade, currentTrader) + " times.";
        TextView editInfo = findViewById(R.id.editInformation);
        editInfo.setText(tempEditNumber);

        //Need to edit TradeWith:
        //Opposing Trader Status:
        //Your trader Status.

    }

    public void onEditMeetingClicked(View view){
        //Do something
    }

    public void onAgreeMeetingClicked(View view){
        meetingManager.agreeOnTrade(trade, currentTrader);
    }

    public void onViewItemInformationClicked(View view){
        if(tradeManager.getTradeType(trade).equals("ONEWAY")){

        }
        else{

        }
    }

    public void displayFragment() {
        OneItemFragment oneItemFragment = new OneItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.itemFragmentContainer, oneItemFragment).commit();

    }
}