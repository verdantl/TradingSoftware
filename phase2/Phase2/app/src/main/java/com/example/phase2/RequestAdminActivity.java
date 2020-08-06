package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class RequestAdminActivity extends AppCompatActivity{
    private TraderManager traderManager;
    private String currentTrader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = bundle.getString("CurrentTrader");
        setContentView(R.layout.activity_request_admin);
    }

    public void requestToDeactivateAccount(View view){
        if(traderManager.isInactive(currentTrader)){
            Toast.makeText(this, R.string.Account_already_deactivated, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, R.string.Deactivation_request_sent, Toast.LENGTH_LONG).show();
            traderManager.setTraderInactive(currentTrader, true);
        }
    }

    public void requestToActivateAccount(View view){
        if(traderManager.isInactive(currentTrader)){
            Toast.makeText(this, R.string.Account_already_activated, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, R.string.Activation_request_sent, Toast.LENGTH_LONG).show();
            traderManager.setTraderInactive(currentTrader, false);
        }
    }

    public void requestToUnfreeze(View view){
        if(traderManager.getIsFrozen(currentTrader)) {
            if(traderManager.getRequestToUnfreeze(currentTrader)){
                Toast.makeText(this, R.string.Trader_request_to_unfreeze_already_sent, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, R.string.Trader_request_to_unfreeze_sent, Toast.LENGTH_LONG).show();
                traderManager.setRequestToUnfreeze(currentTrader, true);
            }
        }
        else{
            Toast.makeText(this, R.string.Trader_request_to_unfreeze_not_frozen, Toast.LENGTH_LONG).show();
        }
    }

}