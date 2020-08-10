package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class DisplayTradeOptionsActivity extends BundleActivity implements Dialogable {

    private Integer chosenItem;
    private boolean oneWay;
    private boolean temporary;
    private AppCompatDialogFragment dialogFragment;

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

    @Override
    public void clickPositive() {
        assert dialogFragment.getArguments() != null;
        oneWay = dialogFragment.getArguments().getBoolean("way");
        temporary = dialogFragment.getArguments().getBoolean("status");
        continuing();
    }

    @Override
    public void clickNegative() {
        super.onBackPressed();

    }

    @Override
    public void openDialog() {
        dialogFragment.show(getSupportFragmentManager(), "TradeType");
    }
}