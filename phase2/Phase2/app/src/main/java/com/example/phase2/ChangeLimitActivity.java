package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.Admin;

public class ChangeLimitActivity extends AppCompatActivity {

    public static final int WEEKLY_LIMIT = 1;
    public static final int MAX_INCOMPLETE = 2;
    public static final int AT_LEAST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_limit);
    }

    public void changeWeeklyLimit(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("limit", WEEKLY_LIMIT);
        startActivity(i);
    }

    public void changeMaxIncomplete(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("limit", MAX_INCOMPLETE);
        startActivity(i);

    }

    public void changeAtLeast(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("limit", AT_LEAST);
        startActivity(i);

    }

}