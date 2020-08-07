package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BundleActivity extends AppCompatActivity {
    protected Bundle bundle;
    protected final String adminKey = "AdminActions";
    protected final String itemKey = "ItemManager";
    protected final String traderKey = "TraderManager";
    protected final String tradeKey = "TradeManager";
    protected final String meetingKey = "MeetingManager";
    protected final String username = "Username";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
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
}
