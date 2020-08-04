package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.List;

public class UndoProposeTrade extends AppCompatActivity {
    private String username;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;
    private TraderManager traderManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        viewList();
    }

    public void viewList(){
        final List<Integer> tempMeetings = new ArrayList<>();
        for (Integer i : tradeManager.getIncompleteTrades(traderManager.getTrades(username))) {
            if (tradeManager.getTradeInitiator(i).equals(username)) {
                if (meetingManager.meetingCanBeUndone(i)) {
                    tempMeetings.add(i);
                }
            }
        }
        ArrayList<String> meetingPresenter = new ArrayList<>();
        for (Integer i : tempMeetings) {
            meetingPresenter.add(meetingManager.getMeeting(i).toString());
        }
        setContentView(R.layout.activity_undo_propose_trade);
        ListView listView = findViewById(R.id.proposedTrade);
        ArrayAdapter<String> editAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, meetingPresenter);
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
        Bundle bundle = new Bundle();
        bundle.putString("undoType", "undoPropose");
        undoFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.proposedTrade, undoFragment).commit();
    }

    public void onUndoClick(){
        meetingManager.undoMeetingProposal(chosenTrade);
        tradeManager.undoTradeProposal(chosenTrade);
        traderManager.undoTradeProposal(chosenTrade);
        Toast.makeText(this, "Successfully undo propose", Toast.LENGTH_SHORT).show();
    }

    public void onCancel(){
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
    }
}