package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.TraderManager;

public class TraderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader);
    }
/*
    Methods here will be called when the corresponding button is clicked

    You can pass around use case classes by calling Intent.putExtra(String key, UseCase class)
    (Just like the dictionary)


    You can get Use Case classes by calling getIntent().getSerializableExtra(String key)
    (Don't forget to cast it)

    You can pass around Strings, Integers, and other primitives through putExtra and get them through
    getStringExtra()... etc.

    If you reached the end of the activity (so no more activity to call), then insert finish();

 */
    public void browseAvailableItems(View view){
        //TODO: Implement this method
    }

    public void browseOnGoingTrades(View view){
        //TODO: Implement this method
    }

    public void editInventory(View view){
        //TODO: Implement this method
    }

    public void editWishlist(View view){
        //TODO: Implement this method
    }

    public void automaticTradeSuggestion(View view){
        //TODO: Implement this method
    }

    public void viewUserInfo(View view){
        //TODO: Implement this method
    }

    public void requestToUnfreeze(View view){
        //TODO: Implement this method
    }

    public void changeTraderPassword(View view){
        Intent i =  new Intent(this, ChangePasswordActivity.class);
        i.putExtra("TraderManager", traderManager);
        i.putExtra("CurrentTrader", currentTrader);
        startActivity(i);
    }
}