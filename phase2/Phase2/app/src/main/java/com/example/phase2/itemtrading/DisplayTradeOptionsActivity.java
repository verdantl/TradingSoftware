package com.example.phase2.itemtrading;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.phase2.R;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.Dialogable;

public class DisplayTradeOptionsActivity extends BundleActivity implements Dialogable {

    private Integer chosenItem;
    private boolean oneWay;
    private boolean temporary;
    private boolean online;
    private AppCompatDialogFragment dialogFragment;

    /**create the activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        chosenItem = bundle.getInt("ChosenItem");
        setContentView(R.layout.activity_display_trade_options);
        DialogFactory dialogFactory = new DialogFactory();
        dialogFragment = dialogFactory.getDialog("TradeType");
        openDialog();

    }

    private void continuing() {
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
        intent.putExtra("Online", online);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Listener for positive button, prompt the trader to edit the proposedTrade
     */
    @Override
    public void clickPositive() {
        assert dialogFragment.getArguments() != null;
        oneWay = dialogFragment.getArguments().getBoolean("isOneWay");
        temporary = dialogFragment.getArguments().getBoolean("isTemporary");
        online = dialogFragment.getArguments().getBoolean("isOnline");
        continuing();
    }

    /**
     * Listener for negative button, go back to the last menu
     */
    @Override
    public void clickNegative() {
        super.onBackPressed();
    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        dialogFragment.show(getSupportFragmentManager(), "TradeType");
    }
}