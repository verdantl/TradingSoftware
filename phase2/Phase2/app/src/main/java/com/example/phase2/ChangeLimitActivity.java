package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.TraderManager;

public class ChangeLimitActivity extends AppCompatActivity {

    public static final int WEEKLY_LIMIT = 1;
    public static final int MAX_INCOMPLETE = 2;
    public static final int AT_LEAST = 3;

    private TraderManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_limit);
        tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
    }

    public void changeWeeklyLimit(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("TraderManager", tm);
        i.putExtra("limit", WEEKLY_LIMIT);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    public void changeMaxIncomplete(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("TraderManager", tm);
        i.putExtra("limit", MAX_INCOMPLETE);
        startActivityForResult(i, RESULT_FIRST_USER);

    }

    public void changeAtLeast(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("TraderManager", tm);
        i.putExtra("limit", AT_LEAST);
        startActivityForResult(i, RESULT_FIRST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("This was called");
        assert data != null;
        tm = (TraderManager) data.getSerializableExtra("TraderManager");
    }

}