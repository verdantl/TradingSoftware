package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        //Trade type text
        String tempTradeType = "Trade Type: " + tradeManager.getTradeType(trade);
        TextView tradeType = findViewById(R.id.tradeType);
        tradeType.setText(tempTradeType);

        //Trade duration text
        String tempTradeDuration;
        if(tradeManager.isTradePermanent(trade)){
            tempTradeDuration = "Trade Duration: "+ "Permanent";
        }
        else{
            tempTradeDuration = "Trade Duration: "+ "1 Month";
        }
        TextView tradeDuration = findViewById(R.id.tradeDuration);
        tradeDuration.setText(tempTradeDuration);

        //Trade with text
        String temptradeWith = "Trade With: "+ tradeManager.getOtherTrader(trade,currentTrader);
        TextView tradeWith = findViewById(R.id.tradeWith);
        tradeWith.setText(temptradeWith);

        //Meeting date
        String tempDate = "Date: " + meetingManager.getMeetingDate(trade);
        TextView meetingDate = findViewById(R.id.meetingDate);
        meetingDate.setText(tempDate);
        //MeetingLocation
        String tempLocation = "Location: " + meetingManager.getMeetingLocation(trade);
        TextView meetingLocation = findViewById(R.id.meetingInformation);
        meetingLocation.setText(tempLocation);

        //Sets the agree status for the traders
        String tempAgreeStatus;
        String tempAgreeStatus2;
        if(!meetingManager.bothAgreed(trade)) {
            if (meetingManager.hasAgreed(trade, currentTrader)) {
                tempAgreeStatus = "You have agreed to the meeting.";
            } else {
                tempAgreeStatus = "You have not agreed to the meeting.";
            }
            if(meetingManager.hasAgreed(trade, tradeManager.getOtherTrader(trade,currentTrader))){
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + "has agreed to the meeting.";
            }
            else{
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + "has not agreed to the meeting.";
            }
        }
        else{
            if(meetingManager.hasConfirmed(trade,currentTrader)){
                tempAgreeStatus = "You have confirmed the meeting.";
            }else{
                tempAgreeStatus = "You have not confirmed the meeting.";
            }
            if(meetingManager.hasConfirmed(trade, tradeManager.getOtherTrader(trade,currentTrader))){
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + "has not confirmed the meeting.";
            }
            else{
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + "has confirmed the meeting.";
            }
        }
        TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
        selfTraderAgree.setText(tempAgreeStatus);
        TextView selfTraderAgree2 = findViewById(R.id.opposingTradeAgree);
        selfTraderAgree2.setText(tempAgreeStatus2);

        //How many times you can edit
        String tempEditNumber = "You can edit " + meetingManager.getEditsLeft(trade, currentTrader) + " times.";
        TextView editInfo = findViewById(R.id.editInformation);
        editInfo.setText(tempEditNumber);


        //Checks to see if both users agreed to the meeting
        if(meetingManager.bothAgreed(trade)){
            //If both users have agreed, removes the edit meeting/confirm meeting buttons.
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            //Checks to see if the user has confirmed to the meeting, if they have confirmed, removes the confirm button
            Button button = findViewById(R.id.confirmButton);
            if(meetingManager.hasConfirmed(trade, currentTrader)){
                button.setVisibility(View.GONE);
            }
            else{
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onEditMeetingClicked(View view){
        //Do something









    }

    public void onAgreeMeetingClicked(View view){
        //TODO If wanted update so only able to agree if date is appropriate
        //Checks to see if user has already agreed. If yes, does pop up msg. If no, agrees.
        if(meetingManager.hasAgreed(trade, currentTrader)){
            Toast.makeText(this, R.string.already_agreed, Toast.LENGTH_LONG).show();
        }
        else{
            //Agrees on the trade and sets the agree status to the right one
            meetingManager.agreeOnTrade(trade, currentTrader);
            String tempAgreeStatus = "You have agreed to the meeting.";
            TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
            selfTraderAgree.setText(tempAgreeStatus);
        }
        //If both have agreed, removes edit/agree buttons, and makes confirm button visible.
        if(meetingManager.bothAgreed(trade)){
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            Button button = findViewById(R.id.confirmButton);
            button.setVisibility(View.VISIBLE);
            String tempAgreeStatus = "You have not confirmed the meeting.";
            TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
            selfTraderAgree.setText(tempAgreeStatus);
            String tempAgreeStatus2= tradeManager.getOtherTrader(trade,currentTrader) + "has not confirmed the meeting.";
            TextView selfTraderAgree2 = findViewById(R.id.opposingTradeAgree);
            selfTraderAgree2.setText(tempAgreeStatus2);
        }


    }
    public void onConfirmMeetingClicked(View view){
        //TODO update so only able to confirm if date is appropriate
        meetingManager.confirmMeeting(trade, currentTrader);
        Button button = findViewById(R.id.confirmButton);
        button.setVisibility(View.GONE);
        String tempConfirmStatus = "You have confirmed the meeting.";
        TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
        selfTraderAgree.setText(tempConfirmStatus);
        if(meetingManager.bothConfirmed(trade)){
            if(!tradeManager.isTradePermanent(trade)){
                if(meetingManager.getReturnLocation(trade).equals("N/A")){
                    meetingManager.setReturnInfo(trade);
                    String tempDate = "Date: " + meetingManager.getReturnDate(trade);
                    TextView meetingDate = findViewById(R.id.meetingDate);
                    meetingDate.setText(tempDate);
                    Toast.makeText(this, R.string.return_meeting_popup, Toast.LENGTH_LONG).show();
                }
                else{
                    meetingManager.setMeetingCompleted(trade);
                    tradeManager.setTradeCompleted(trade);
                    this.finish();
                }
            }
            else{
                meetingManager.setMeetingCompleted(trade);
                tradeManager.setTradeCompleted(trade);
                this.finish();
            }

        }
    }

    public void onViewItemInformationClicked(View view){
        if(tradeManager.getTradeType(trade).equals("ONEWAY")){
            displayFragmentOneWay();
        }
        else{
            displayFragmentTwoWay();
        }
    }

    public void displayFragmentOneWay() {
        OneItemFragment oneItemFragment = new OneItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.itemFragmentContainer, oneItemFragment).commit();
    }

    public void displayFragmentTwoWay(){
        TwoItemFragment twoItemFragment = new TwoItemFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.itemFragmentContainer, twoItemFragment).commit();
    }
}