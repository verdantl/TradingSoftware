package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class ManageFrozenAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_frozen_account);

    }


    public void viewFlaggedAccounts(View view) {
        Intent intent = new Intent(this, FlaggedAccountsMenu.class);
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        startActivity(intent);
    }

    public void viewUnfreezeRequests(View view){
        Intent intent = new Intent(this, RequestedUnfrozenMenu.class);
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        startActivity(intent);
    }

    public void viewAllTraders(View view){
        Intent intent = new Intent (this, AllTradersMenu.class);
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        startActivity(intent);

    }
}