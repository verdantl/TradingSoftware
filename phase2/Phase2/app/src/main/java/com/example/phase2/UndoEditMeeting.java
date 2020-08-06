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

public class UndoEditMeeting extends AppCompatActivity implements UndoFragment.UndoClick {
    private String username;
    private Bundle bundle;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;
    private TraderManager traderManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        viewList();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UndoMenu.class);
        bundle.remove("TradeManager");
        bundle.remove("MeetingManager");
        bundle.remove("TraderManager");
        bundle.putSerializable("TradeManager", tradeManager);
        bundle.putSerializable("MeetingManager", meetingManager);
        bundle.putSerializable("TraderManager", traderManager);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void viewList() {
        final List<Integer> tempMeetings = new ArrayList<>();
        for (Integer i : tradeManager.getIncompleteTrades(traderManager.getTrades(username))){
            if (!meetingManager.meetingCanBeUndone(i)){
                tempMeetings.add(i);
            }
        }


        for (Integer i : tempMeetings) {
            HashMap<String, Integer> tempHash = meetingManager.getEdits(i);
            if (tradeManager.getTradeInitiator(i).equals(username)) {
                if (tempHash.get(username) <
                        tempHash.get(tradeManager.getTradeReceiver(i))) {
                    tempMeetings.remove(i);
                } else {
                    if (Objects.equals(tempHash.get(username),
                            tempHash.get(tradeManager.getTradeReceiver(i)))) {
                        tempMeetings.remove(i);
                    }
                }
            }
        }
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
                displayFragment();
                chosenTrade = tempMeetings.get(i);
            }
        });
    }

    private void displayFragment() {
        UndoFragment undoFragment = new UndoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.undoEditedTrade_container, undoFragment).commit();
    }




    @Override
    public void onUndoClick(View view) {
        meetingManager.undoEdit(chosenTrade, username);
        Toast.makeText(this, "Successfully undo edit", Toast.LENGTH_SHORT).show();
        viewList();
    }


    @Override
    public void onCancelClick(View view) {
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }
}