package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class DisplayChangeLimitActivity extends AppCompatActivity {

    private TraderManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_change_limit);
        TextView tv = (TextView) findViewById(R.id.textView7);
        int currLimit = 0;
        LimitType limitToChange = getLimitToChange();

        tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
        assert tm != null;

        switch (limitToChange){
            case WEEKLY_LIMIT:
                currLimit = tm.getWeeklyLimit();
                break;
            case MORE_LEND:
                currLimit = tm.getMoreLend();
                break;
            default:
                currLimit = tm.getMaxInComplete();
        }

        String displayCurrLimit = getApplicationContext().getResources().getString(R.string.current_limit) + " "+currLimit;
        tv.setText(displayCurrLimit);

    }

    public void enterNewLimit(View view){
        EditText editText = (EditText) findViewById(R.id.editNewLimit);
        int newLimit = Integer.parseInt(editText.getText().toString());
        LimitType limitToChange = getLimitToChange();

        //TraderManager tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
        assert tm!= null;
        switch (limitToChange){
            case WEEKLY_LIMIT:
                tm.setWeeklyLimit(newLimit);
                break;
            case MORE_LEND:
                    tm.setMoreLend(newLimit);
                    break;
            default:
                tm.setMaxInComplete(newLimit);

        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("TraderManager", tm);
        Toast.makeText(this, R.string.successfully_changed_limit, Toast.LENGTH_SHORT).show();
        setResult(RESULT_FIRST_USER, resultIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, new Intent());
    }

    private LimitType getLimitToChange(){
        return (LimitType) getIntent().getSerializableExtra(LimitType.class.getName());
    }
}