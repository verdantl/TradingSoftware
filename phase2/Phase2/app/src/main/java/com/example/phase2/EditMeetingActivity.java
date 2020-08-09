package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.time.LocalDate;

public class EditMeetingActivity extends BundleActivity {
    //Date picker code taken from https://www.youtube.com/watch?v=hwe1abDO2Ag
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private MeetingManager meetingManager;
    private String date;
    private Integer trade;
    private String currentTrader;
    private LocalDate newDate;
    private boolean online;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);
        Bundle bundle = getIntent().getExtras();
//        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
//        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        assert bundle != null;
        trade = (Integer) bundle.getSerializable("Trade");
        online = (boolean) bundle.get("Online");
        meetingManager = (MeetingManager) getUseCase("MeetingManager");
        currentTrader = (String) getUseCase("Username");

        EditText location=findViewById(R.id.meetingLocationEdit);
        TextView newLocation = findViewById(R.id.newLocation);
        if(online){
            location.setVisibility(View.GONE);
            newLocation.setVisibility(View.GONE);
        }

        Button button = findViewById(R.id.submit_button);

        final TextView datePickerText = findViewById(R.id.editTextDate);
        datePickerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = LocalDate.now().getDayOfMonth();
                int month = LocalDate.now().getMonthValue()-1;
                int year = LocalDate.now().getYear();
                DatePickerDialog datePicker = new DatePickerDialog(EditMeetingActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,day);
                datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePicker.show();
            }
        }
        );
        onDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date = year + "-" + month+"-"+day;
                newDate = LocalDate.of(year,month,day);
                datePickerText.setText(date);
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText location=findViewById(R.id.meetingLocationEdit);
                if(online){
                    if(datePickerText.getText().toString().equals("+ Select a new date")){
                        Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_edit, Toast.LENGTH_LONG).show();
                    }
                    else{
                        meetingManager.editDate(trade, newDate);
                        meetingManager.increaseNumEdit(currentTrader, trade);
                        meetingManager.setBothDisagree(trade);
                        onSubmitClick();
                    }
                }
                else{
                    if(!location.getText().toString().isEmpty() && !location.getText().toString().equals("Please Enter a Location")){
                        if(datePickerText.getText().toString().equals("+ Select a new date")){
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_edit, Toast.LENGTH_LONG).show();
                        }
                        else {
                            meetingManager.editDate(trade, newDate);
                            meetingManager.editLocation(trade, location.getText().toString());
                            meetingManager.increaseNumEdit(currentTrader, trade);
                            meetingManager.setBothDisagree(trade);
                            onSubmitClick();
                        }
                    }
                    else{
                        if(datePickerText.getText().toString().equals("Please Select a Date")){
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_location, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(EditMeetingActivity.this, R.string.invalid_location_edit, Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
    }


    public void onSubmitClick(){
        replaceUseCase(meetingManager);
//        bundle.remove("MeetingManager");
//        bundle.remove("CurrentTrader");
//        bundle.remove("Trade");
//        bundle.remove("Online");
//        intent.putExtras(bundle);
//        intent.putExtra("MeetingManager", meetingManager);
//        intent.putExtra("Trade", trade);
//        intent.putExtra("Username", currentTrader);
        super.onBackPressed();
    }

}