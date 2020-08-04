package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class UndoAgreeTrade extends AppCompatActivity {
    private String username;
    private TraderManager traderManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        username = bundle.getString("username");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        viewList();
    }

    public void viewList(){
        List<Integer> incompleteTrades = meetingManager.getOnGoingMeetings(traderManager.getTrades(username));
        final List<Integer> undoableMeetingAgreements = new ArrayList<>();
        for (Integer i : incompleteTrades) {
            if (meetingManager.canUndoAgree(i, username)) {
                undoableMeetingAgreements.add(i);
            }
        }
        ArrayList<String> undoPresentation = new ArrayList<>();
        for (Integer i : undoableMeetingAgreements) {
            undoPresentation.add(meetingManager.getMeetingDescription(i));
        }
        setContentView(R.layout.activity_undo_agree_trade);
        ListView listView = findViewById(R.id.agreedTrade);
        ArrayAdapter<String> agreeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, undoPresentation);
        listView.setAdapter(agreeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFragment();
                chosenTrade = undoableMeetingAgreements.get(i);
            }
        });
    }

    public void displayFragment(){
        UndoFragment undoFragment = new UndoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.agreedTrade, undoFragment).commit();

    }
}