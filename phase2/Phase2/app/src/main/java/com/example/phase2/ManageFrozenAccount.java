package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class ManageFrozenAccount extends BundleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_frozen_account);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminActivity.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void viewFlaggedAccounts(View view) {
        Intent intent = new Intent(this, FlaggedAccountsMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    public void viewUnfreezeRequests(View view){
        Intent intent = new Intent(this, RequestedUnfrozenMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    public void viewAllTraders(View view){
        Intent intent = new Intent (this, AllTradersMenu.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);

    }
}