package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.TraderManager;

public class ChangeLimitActivity extends AppCompatActivity {

    //used for onActivityResult

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
        i.putExtra(LimitType.class.getName(), LimitType.WEEKLY_LIMIT);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);
    }

    public void changeMaxIncomplete(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("TraderManager", tm);
        i.putExtra(LimitType.class.getName(), LimitType.MAX_INCOMPLETE);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);

    }

    public void changeAtLeast(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra("TraderManager", tm);
        i.putExtra(LimitType.class.getName(), LimitType.MORE_LEND);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if(resultCode == RESULT_FIRST_USER) {
            tm = (TraderManager) data.getSerializableExtra("TraderManager");
        }
        Intent i = new Intent();
        i.putExtra("TraderManager", tm);
        setResult(RESULT_FIRST_USER, i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }
}