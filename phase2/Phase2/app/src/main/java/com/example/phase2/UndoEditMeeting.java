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
import android.widget.Toast;

import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UndoEditMeeting extends BundleActivity implements Dialogable{
    private String chosenTrader;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;
    private TraderManager traderManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenTrader = getIntent().getStringExtra("chosenTrader");
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        viewList();
    }

    @Override
    public void onBackPressed() {
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        replaceUseCase(tradeManager);
        Intent intent = new Intent();
        putBundle(intent);
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    private void viewList() {
        final List<Integer> tempMeetings = new ArrayList<>();
        for (Integer i : tradeManager.getIncompleteTrades(traderManager.getTrades(chosenTrader))){
            if (meetingManager.meetingCanBeUndone(i)){
                tempMeetings.add(i);
            }
        }

        List<Integer> tempMeetings2 = new ArrayList<>();


        for (Integer i : tempMeetings2) {
            HashMap<String, Integer> tempHash = meetingManager.getEdits(i);
            if (tradeManager.getTradeInitiator(i).equals(chosenTrader)) {
                if (tempHash.get(chosenTrader) <
                        tempHash.get(tradeManager.getTradeReceiver(i))) {
                    tempMeetings.remove(i);
                } else {
                    if (Objects.equals(tempHash.get(chosenTrader),
                            tempHash.get(tradeManager.getTradeReceiver(i)))) {
                        tempMeetings.remove(i);
                    }
                }
            }
        }
        //System.out.println(tempMeetings.size());
        ArrayList<String> editMeeting = new ArrayList<>();
        for(Integer i: tempMeetings){
            editMeeting.add(meetingManager.getMeeting(i).toString());
        }
        setContentView(R.layout.activity_undo_edit_meeting);
        ListView listView = findViewById(R.id.editedTrade);
        ArrayAdapter<String>  editAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, editMeeting);
        listView.setAdapter(editAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                chosenTrade = tempMeetings.get(i);
            }
        });
    }




    @Override
    public void clickPositive() {
        meetingManager.undoEdit(chosenTrade, chosenTrader);
        Toast.makeText(this, "Successfully undo edit", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void clickNegative() {
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoEditMeeting");

    }
}