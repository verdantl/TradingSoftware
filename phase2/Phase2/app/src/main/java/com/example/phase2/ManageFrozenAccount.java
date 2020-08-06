package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class ManageFrozenAccount extends AppCompatActivity {
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_manage_frozen_account);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void viewFlaggedAccounts(View view) {
        Intent intent = new Intent(this, FlaggedAccountsMenu.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void viewUnfreezeRequests(View view){
        Intent intent = new Intent(this, RequestedUnfrozenMenu.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void viewAllTraders(View view){
        Intent intent = new Intent (this, AllTradersMenu.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}