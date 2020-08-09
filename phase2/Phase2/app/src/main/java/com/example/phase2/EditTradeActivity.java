package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.Objects;

public class EditTradeActivity extends BundleActivity{
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    private ItemManager itemManager;

    private void updateUseCases(){
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trade);

        trade = (Integer) Objects.requireNonNull(getIntent().getExtras()).getSerializable("Trade");
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
    }

    private void updateScreen(){
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
        //Sets the  status for the traders
        updateCurTraderMeetingStatus();
        updateOtherTraderMeetingStatus();
        //How many times you can edit
        String tempEditNumber = "You can edit " + meetingManager.getEditsLeft(trade, currentTrader) + " times.";
        TextView editInfo = findViewById(R.id.editInformation);
        editInfo.setText(tempEditNumber);
        //Checks to see if both users agreed to the meeting
        Button button = findViewById(R.id.confirmButton);
        if(meetingManager.bothAgreed(trade)){
            //If both users have agreed, removes the edit meeting/confirm meeting buttons.
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            //Checks to see if the user has confirmed to the meeting, if they have confirmed, removes the confirm button
            if(meetingManager.hasConfirmed(trade, currentTrader)){
                button.setVisibility(View.GONE);
            }
            else{
                button.setVisibility(View.VISIBLE);
            }
        }
        else{
            button.setVisibility(View.GONE);
        }
        Button declineButton = (Button) findViewById(R.id.declineButton);
        if(meetingManager.bothAgreed(trade)){
            declineButton.setVisibility(View.GONE);
        }
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeclineTrade();
            }
        });
        if(cancelTradeCheck()){
            onBackPressed();
        }
    }

    public void onDeclineTrade(){
        removeTrade();
        Toast.makeText(EditTradeActivity.this, R.string.tradeDeclined, Toast.LENGTH_LONG).show();
        onBackPressed();
    }


    private String updateCurTraderMeetingStatus(){
//        if(!meetingManager.bothAgreed(trade)) {
//            if (meetingManager.hasAgreed(trade, currentTrader)) {
//                tempAgreeStatus = "You have agreed to the appointment.";
//            } else {
//                tempAgreeStatus = "You have not agreed to the appointment.";
//            }
//            if(meetingManager.hasAgreed(trade, tradeManager.getOtherTrader(trade,currentTrader))){
//                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + " has agreed to the appointment.";
//            }
//            else{
//                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + " has not agreed to the appointment.";
//            }
//        }
//        else{
//            if(meetingManager.hasConfirmed(trade,currentTrader)){
//                tempAgreeStatus = "You have confirmed the appointment.";
//            }else{
//                tempAgreeStatus = "You have not confirmed the appointment.";
//            }
//            if(meetingManager.hasConfirmed(trade, tradeManager.getOtherTrader(trade,currentTrader))){
//                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + " has confirmed the item transfer.";
//            }
//            else{
//                tempAgreeStatus2 = tradeManager.getOtherTrader(trade,currentTrader) + " has not confirmed the item transfer.";
//            }
//        }
        String tempAgreeStatus;
        if(!meetingManager.bothAgreed(trade)) {
            if (meetingManager.hasAgreed(trade, currentTrader)) {
                tempAgreeStatus = "You have agreed to the appointment.";
            } else {
                tempAgreeStatus = "You have not agreed to the appointment.";
            }
        }
        else{
            if(meetingManager.hasConfirmed(trade,currentTrader)){
                tempAgreeStatus = "You have confirmed the appointment.";
            }else{
                tempAgreeStatus = "You have not confirmed the appointment.";
            }

        }
        TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
        selfTraderAgree.setText(tempAgreeStatus);
        return tempAgreeStatus;
    }

    private String updateOtherTraderMeetingStatus(){
        String tempAgreeStatus2;
        if(!meetingManager.bothAgreed(trade)) {
            if (meetingManager.hasAgreed(trade, tradeManager.getOtherTrader(trade, currentTrader))) {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has agreed to the appointment.";
            } else {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has not agreed to the appointment.";
            }
        }else {

            if (meetingManager.hasConfirmed(trade, tradeManager.getOtherTrader(trade, currentTrader))) {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has confirmed the appointment.";
            } else {
                tempAgreeStatus2 = tradeManager.getOtherTrader(trade, currentTrader) + " has not confirmed the appointment.";
            }
        }
        TextView selfTraderAgree2 = findViewById(R.id.opposingTradeAgree);
        selfTraderAgree2.setText(tempAgreeStatus2);
        return tempAgreeStatus2;
    }
    public void onEditMeetingClicked(View view){
        //Do something
        if(meetingManager.isValid(currentTrader, trade)){
            Intent intent = new Intent(this, EditMeetingActivity.class);
            if(tradeManager.getTradeType(trade).contains("ONLINE")){
                intent.putExtra("Online", true);
            }
            else{
                intent.putExtra("Online", false);
            }
            replaceUseCases();
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }
        else{
            Toast.makeText(EditTradeActivity.this, R.string.cannotEdit, Toast.LENGTH_LONG).show();
        }

    }

    private void replaceUseCases(){
        replaceUseCase(tradeManager);
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        replaceUseCase(itemManager);
    }
    @Override
    public void onBackPressed() {
        replaceUseCases();
        super.onBackPressed();
//        Intent intent = new Intent(this, BrowseTradesActivity.class);
//        putBundle(intent);
//        startActivity(intent);
        //super.onBackPressed();
        //returnToTrades();
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
            String tempAgreeStatus = "You have agreed to the appointment.";
            TextView selfTraderAgree = findViewById(R.id.selfTraderAgree);
            selfTraderAgree.setText(tempAgreeStatus);
        }
        //If both have agreed, removes edit/agree buttons, and makes confirm button visible.
        if(meetingManager.bothAgreed(trade)){
            LinearLayout linearLayout = findViewById(R.id.agreeOrEditButtons);
            linearLayout.setVisibility(View.GONE);
            Button button = findViewById(R.id.confirmButton);
            button.setVisibility(View.VISIBLE);
            updateCurTraderMeetingStatus();
            updateOtherTraderMeetingStatus();
        }

    }
    public void onConfirmMeetingClicked(View view){
        //TODO update so only able to confirm if date is appropriate
        meetingManager.confirmMeeting(trade, currentTrader);
        Button button = findViewById(R.id.confirmButton);
        button.setVisibility(View.GONE);
        updateCurTraderMeetingStatus();
        updateOtherTraderMeetingStatus();
        if(meetingManager.bothConfirmed(trade)){
            if(!tradeManager.isTradePermanent(trade)){
                if(meetingManager.getReturnLocation(trade).equals("N/A")){
                    meetingManager.setReturnInfo(trade);
                    String tempDate = "Date: " + meetingManager.getReturnDate(trade);
                    TextView meetingDate = findViewById(R.id.meetingDate);
                    meetingDate.setText(tempDate);
                    updateWishlistAndBorrowed();
                    Toast.makeText(this, R.string.return_meeting_popup, Toast.LENGTH_LONG).show();
                    button.setVisibility(View.VISIBLE);
                    updateCurTraderMeetingStatus();
                    updateOtherTraderMeetingStatus();
                }
                else{
                    for(Integer i: tradeManager.getItems(trade)){
                        if(traderManager.getIsFrozen(itemManager.getOwner(i))){
                            itemManager.changeStatusToFrozen(i);
                        }
                        else{
                            itemManager.changeStatusToAvailable(i);
                        }
                    }
                    completeTrade();
                }
            }
            else{
                updateItemOwnerAndStatus();
                completeTrade();
            }

        }
    }
    private void completeTrade(){
        meetingManager.setMeetingCompleted(trade);
        tradeManager.setTradeCompleted(trade);
        Toast.makeText(this, R.string.trade_completed, Toast.LENGTH_LONG).show();
        //returnToTrades();
        onBackPressed();
    }
    private void updateItemOwnerAndStatus(){
        for(Integer i: tradeManager.getItems(trade)){
            if(itemManager.getOwner(i).equals(currentTrader)){
                itemManager.setItemOwner(i, tradeManager.getOtherTrader(trade,currentTrader));
                if(traderManager.getIsFrozen(tradeManager.getOtherTrader(trade,currentTrader))){
                    itemManager.changeStatusToFrozen(i);
                }
                else{
                    itemManager.changeStatusToAvailable(i);
                }
            }
            else{
                itemManager.setItemOwner(i, currentTrader);
                if(traderManager.getIsFrozen(currentTrader)){
                    itemManager.changeStatusToFrozen(i);
                }else{
                    itemManager.changeStatusToAvailable(i);
                }
            }
        }
    }
    private void updateWishlistAndBorrowed(){
        for(Integer i: tradeManager.getItems(trade)){
            itemManager.changeStatusToUnavailable(i);
            if(itemManager.getOwner(i).equals(currentTrader)){
                traderManager.addToBorrowedItems(tradeManager.getOtherTrader(trade,currentTrader), i);
                traderManager.removeFromWishlist(tradeManager.getOtherTrader(trade,currentTrader), i);
            }
            else{
                traderManager.addToBorrowedItems(currentTrader, i);
                traderManager.removeFromWishlist(currentTrader, i);
            }
        }
    }

    public void returnToTrades(){
        Intent intent = new Intent(this, BrowseTradesActivity.class);
        replaceUseCase(tradeManager);
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        replaceUseCase(itemManager);

        startActivity(intent);

    }
    public void onViewItemInformationClicked(View view){
        if(tradeManager.getTradeType(trade).contains("ONEWAY")){
            displayFragmentOneWay();
        }
        else{
            displayFragmentTwoWay();
        }
    }

    public void displayFragmentOneWay() {
        Intent intent = new Intent(this, OneItemActivity.class);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("Trade", trade);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);

    }

    public void displayFragmentTwoWay(){
        Intent intent = new Intent(this, TwoItemActivity.class);
        intent.putExtra("CurrentTrader", currentTrader);
        intent.putExtra("TradeManager",tradeManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("Trade", trade);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }

    private boolean cancelTradeCheck(){
            if (meetingManager.isMaxEditsReached(trade)) {
                removeTrade();
                Toast.makeText(EditTradeActivity.this, R.string.maxEditsReached, Toast.LENGTH_LONG).show();
                return true;
            }
            return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUseCases();
    }

    private void removeTrade(){
        //Have to update item status's
        //Note only time this happens if both haven't agreed, thus item status is only unavailable
        for(Integer i: tradeManager.getItems(trade)){
            if(!traderManager.getIsFrozen(itemManager.getItemOwner(trade))){
                itemManager.changeStatusToAvailable(i);
            }
            else{
                itemManager.changeStatusToFrozen(i);
            }
        }
        //Remove from both traders' lists
        traderManager.removeTradeFromTraders(trade, currentTrader, tradeManager.getOtherTrader(trade, currentTrader));
        tradeManager.removeFromInventory(trade);
        meetingManager.removeMeeting(trade);
    }

}