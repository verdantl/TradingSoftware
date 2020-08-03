package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.MeetingManager;

import java.time.LocalDate;

public class EditMeetingActivity extends AppCompatActivity {
    //Date picker code taken from https://www.youtube.com/watch?v=hwe1abDO2Ag
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private MeetingManager meetingManager;
    private String date;
    private Integer trade;
    private String currentTrader;
    private LocalDate newDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meeting);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        trade = (Integer) bundle.getSerializable("Trade");
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
                if(!location.getText().toString().isEmpty() && !location.getText().toString().equals("Please Enter a Location")){
                    if(datePickerText.getText().toString().equals("Please Select a Date")){
                        Toast.makeText(EditMeetingActivity.this, R.string.invalid_date_edit, Toast.LENGTH_LONG).show();
                    }
                    else {
                        meetingManager.editDate(trade, newDate);
                        meetingManager.editLocation(trade, location.getText().toString());
                        meetingManager.increaseNumEdit(currentTrader, trade);
                        meetingManager.setBothDisagree(trade);
                        //TODO SAVE THE DATA SOMEHOW
                        EditMeetingActivity.this.finish();
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
        });
    }

    public void onSubmitClick(View view){


    }


}