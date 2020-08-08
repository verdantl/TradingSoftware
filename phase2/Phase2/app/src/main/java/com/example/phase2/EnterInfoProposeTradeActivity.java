package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EnterInfoProposeTradeActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private String currentTrader;
    private Integer chosenItem;
    private Integer myItem;
    private boolean oneWay;
    private boolean temporary;
    private String location = null;
    private LocalDate date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = bundle.getString("CurrentTrader");
        chosenItem = bundle.getInt("ChosenItem");
        myItem = bundle.getInt("MyItem");
        oneWay = bundle.getBoolean("OneWay");
        viewStart();
    }

    public void viewStart() { setContentView(R.layout.activity_enter_info_propose_trade); }

    public void setLocation(View view) {
        EditText locationEditText = findViewById(R.id.editTextLocation);
        location =  locationEditText.getText().toString();
    }

    public void setDate(View view) {
        EditText dateEditText = findViewById(R.id.editTextDate);
        try {
            Toast.makeText(this, dateEditText.getText().toString(),
                    Toast.LENGTH_LONG).show();
            date = LocalDate.parse(dateEditText.getText().toString());
        }
        catch (DateTimeParseException e) {
            Toast.makeText(this, "Please enter your location in the format DD-MM-YYYY",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void continuing(View view) {
        if (location == null) {
            Toast.makeText(this, "Please enter a location.", Toast.LENGTH_SHORT).show();
        } else if (date == null) {
            Toast.makeText(this, "Please enter a date.", Toast.LENGTH_SHORT).show();
        } else {
            String receiver = itemManager.getOwner(chosenItem);
            int i;

            List<Integer> items = new ArrayList<>();
            items.add(chosenItem);
            if (oneWay) {
                i = tradeManager.createTrade(currentTrader, receiver, "ONEWAY", temporary, items);
            }
            else {
                items.add(myItem);
                i = tradeManager.createTrade(currentTrader, receiver, "TWOWAY", temporary, items);
            }

            meetingManager.createMeeting(i, currentTrader, receiver, temporary);
            if (temporary){
                // We determine the returnDate later, after the initial date is set.
                LocalDate returnDate = date.plusMonths(1);
                meetingManager.setMeetingInfo(i, date, returnDate, location, location);
            }
            else {
                meetingManager.setMeetingInfo(i, date, null, location, null);
            }
            Toast.makeText(this, "Trade Created!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void cancel(View view){
        finish();
    }
}
