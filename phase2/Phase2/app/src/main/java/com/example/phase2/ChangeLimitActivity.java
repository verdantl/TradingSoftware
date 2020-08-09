package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.TraderManager;

/**
 * An activity class responsible for changing the limits in the Trading System.
 */
public class ChangeLimitActivity extends BundleActivity{

    //used for onActivityResult

    private TraderManager tm;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_limit);
        tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
    }

    /**
     * This method is called when the user clicks on the Weekly Limit button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeWeeklyLimit(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra(TRADERKEY, tm);
        i.putExtra(LimitType.class.getName(), LimitType.WEEKLY_LIMIT);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);
    }

    /**
     * This method is called when the user clicks on the Max Incomplete button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeMaxIncomplete(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra(TRADERKEY, tm);
        i.putExtra(LimitType.class.getName(), LimitType.MAX_INCOMPLETE);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);

    }

    /**
     * This method is called when the user clicks on the More Lend button. It starts
     * the DisplayChangeLimitActivity
     * @param view A view
     */
    public void changeAtLeast(View view){
        Intent i = new Intent(this, DisplayChangeLimitActivity.class);
        //i.putExtra("TraderManager", getIntent().getSerializableExtra("TraderManager"));
        i.putExtra(TRADERKEY, tm);
        i.putExtra(LimitType.class.getName(), LimitType.MORE_LEND);
        startActivityForResult(i, RESULT_FIRST_USER);
        //setResult(RESULT_FIRST_USER, i);
    }

    /**
     * Gets the result from the DisplayChangeLimit activity
     * @param requestCode An int that determines which method called startActivityForResult
     * @param resultCode Indicates the result of the activity
     * @param data The result intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if(resultCode == RESULT_FIRST_USER) {
            tm = (TraderManager) data.getSerializableExtra(TRADERKEY);
        }
        Intent i = new Intent();
        i.putExtra(TRADERKEY, tm);
        setResult(RESULT_FIRST_USER, i);
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
    }
}