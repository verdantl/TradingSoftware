package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UndoEditMeeting extends AppCompatActivity {
    private String username;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;
    private TraderManager traderManager;
    private List<Integer> tempMeetings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        viewList();
        setContentView(R.layout.activity_undo_edit_meeting);
    }


    private void viewList() {
        tempMeetings = new ArrayList<>();
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
                    if (Objects.equals(tempHash.get(username), tempHash.get(tradeManager.getTradeReceiver(i)))) {
                        tempMeetings.remove(i);
                    }
                }
            }
        }
        ArrayList<String> editMeeting = new ArrayList<>();
        for(Integer i: tempMeetings){
            editMeeting.add(meetingManager.getMeeting(i).toString());
        }
    }
}