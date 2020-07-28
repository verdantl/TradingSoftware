package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.phase2.phase2.TraderManager;

public class FlaggedAccountsMenu extends AppCompatActivity {
    private TraderManager traderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagged_accounts_menu);
        Bundle bundle = getIntent().getExtras();
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
    }
}