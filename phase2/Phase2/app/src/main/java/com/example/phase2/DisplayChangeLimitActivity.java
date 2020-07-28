package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class DisplayChangeLimitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_change_limit);
    }

    public void enterNewLimit(View view){
        EditText editText = (EditText) findViewById(R.id.editNewLimit);
        int newLimit = Integer.parseInt(editText.getText().toString());
        int limitToChange = getIntent().getIntExtra("limit", 0);
        TraderManager tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
        assert tm!= null;
        switch (limitToChange){
            case ChangeLimitActivity.WEEKLY_LIMIT:
                tm.setWeeklyLimit(newLimit);
                break;
                case ChangeLimitActivity.AT_LEAST:
                    tm.setMoreLend(newLimit);
                    break;
            default:
                tm.setMaxInComplete(newLimit);

        }
        Toast.makeText(this, R.string.successfully_changed_limit, Toast.LENGTH_SHORT).show();
        finish();

    }
}