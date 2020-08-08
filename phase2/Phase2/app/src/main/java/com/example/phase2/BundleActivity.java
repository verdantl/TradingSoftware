package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ConfigGateway;
import com.example.phase2.phase2.TraderManager;

import java.io.Serializable;


public abstract class BundleActivity extends AppCompatActivity {
    protected Bundle bundle;
    protected final String ADMINKEY = "AdminActions";
    protected final String ITEMKEY = "ItemManager";
    protected final String TRADERKEY = "TraderManager";
    protected final String TRADEKEY = "TradeManager";
    protected final String MEETINGKEY = "MeetingManager";
    protected final String USERNAMEKEY = "Username";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        if (bundle == null){
            ConfigGateway configGateway = new ConfigGateway(getApplicationContext().getFilesDir());
            bundle = configGateway.getBundle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        bundle = data.getExtras();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    protected void replaceTraderManager(TraderManager traderManager){
        bundle.putSerializable(TRADERKEY, traderManager);
    }

    protected void replaceAdminActions(AdminActions adminActions){

    }
}
