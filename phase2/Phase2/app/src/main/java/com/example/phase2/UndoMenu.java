package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;

public class UndoMenu extends BundleActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UndoActivity.class);
        startActivity(intent);
    }

    public void undoEditMeeting(View view){
        Intent intent = new Intent(this, UndoEditMeeting.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void undoAgreeTrade(View view){
        Intent intent = new Intent(this, UndoAgreeTrade.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void undoConfirmTrade(View view){
        Intent intent = new Intent(this, UndoConfirmTrade.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void undoProposeTrade(View view){
        Intent intent = new Intent(this, UndoProposeTrade.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void undoRemoveItem(View view){
        Intent intent = new Intent(this, UndoRemoveItem.class);
        putBundle(intent);
        startActivity(intent);
    }
}