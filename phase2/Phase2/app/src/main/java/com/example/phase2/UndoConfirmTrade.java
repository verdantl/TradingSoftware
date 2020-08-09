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
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.List;

public class UndoConfirmTrade extends BundleActivity implements Dialogable {
    private String chosenTrader;
    private TraderManager traderManager;
    private MeetingManager meetingManager;
    private Integer chosenTrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenTrader = getIntent().getStringExtra("chosenTrader");
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        viewList();

    }

    @Override
    public void onBackPressed() {
        replaceUseCase(meetingManager);
        replaceUseCase(traderManager);
        Intent intent = new Intent();
        putBundle(intent);
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    private void viewList() {
        List<Integer> incompleteTrades =
                meetingManager.getOnGoingMeetings(traderManager.getTrades(chosenTrader));
        final List<Integer> undoableMeetingConfirmations = new ArrayList<>();
        for (Integer i : incompleteTrades) {
            if (meetingManager.canUndoConfirm(i, chosenTrader)) {
                undoableMeetingConfirmations.add(i);
            }
        }
        ArrayList<String> confirmationPresenter = new ArrayList<>();
        for (Integer i : undoableMeetingConfirmations) {
            confirmationPresenter.add(meetingManager.getMeetingDescription(i));
        }
        setContentView(R.layout.activity_undo_confirm_trade);
        ListView listView = findViewById(R.id.confirmedTrade);
        ArrayAdapter<String> confirmAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, confirmationPresenter);
        listView.setAdapter(confirmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                chosenTrade = undoableMeetingConfirmations.get(i);
            }
        });

    }


    @Override
    public void clickPositive() {
        meetingManager.undoConfirm(chosenTrade, chosenTrader);
        Toast.makeText(this, "Successfully undo confirm", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();


    }

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoConfirm");

    }
}