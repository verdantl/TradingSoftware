package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.phase2.phase2.TradeManager;

public class undoAgreeTrade extends AppCompatActivity {
    private String username;
    private TradeManager tradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo_agree_trade);
        Bundle bundle = getIntent().getExtras();
    }
}