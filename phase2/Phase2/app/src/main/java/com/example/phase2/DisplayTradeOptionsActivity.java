package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class DisplayTradeOptionsActivity extends BundleActivity implements ChooseOneWayFragment.ChooseOneWayListener,
        ChooseTemporaryFragment.ChooseTemporaryListener {

    private Integer chosenItem;
    private boolean oneWay;
    private boolean temporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        chosenItem = bundle.getInt("ChosenItem");
        setContentView(R.layout.activity_display_trade_options);
        createDialogChooseOneWay();
    }

    private void createDialogChooseOneWay(){
        ChooseOneWayFragment oneWayFragment = new ChooseOneWayFragment();
        oneWayFragment.show(getSupportFragmentManager(), "oneWayFrag");
    }

    private void createDialogChooseTemporary(){
        ChooseTemporaryFragment tempFragment = new ChooseTemporaryFragment();
        tempFragment.show(getSupportFragmentManager(), "oneWayFrag");
    }

    @Override
    public void onDialogOneWayClick(DialogFragment dialog) {
        oneWay = true;
        createDialogChooseTemporary();
    }

    @Override
    public void onDialogTwoWayClick(DialogFragment dialog) {
        oneWay = false;
        createDialogChooseTemporary();
    }

    @Override
    public void onDialogTempClick(DialogFragment dialog) {
        temporary = true;
        continuing();
    }

    @Override
    public void onDialogPermClick(DialogFragment dialog) {
        temporary = false;
        continuing();
    }

    public void continuing() {
        Intent intent;
        if (oneWay) {
            intent = new Intent(this, EnterInfoProposeTradeActivity.class);
            intent.putExtra("MyItem", -1);
        }
        else {
            intent = new Intent(this, SelectItemActivity.class);
        }
        intent.putExtra("ChosenItem", chosenItem);
        intent.putExtra("Temporary", temporary);
        intent.putExtra("OneWay", oneWay);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}