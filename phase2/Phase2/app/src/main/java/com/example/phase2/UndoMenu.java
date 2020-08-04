package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;

public class UndoMenu extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getIntent().getStringExtra("username");
        setContentView(R.layout.activity_undo_menu);
    }

    public void undoEditMeeting(View view){
        Intent intent = new Intent(this, UndoEditMeeting.class);
        intent.putExtra("username", username);
        intent.putExtra("TradeManager",
                getIntent().getSerializableExtra("TradeManager"));
        intent.putExtra("MeetingManager",
                getIntent().getSerializableExtra("MeetingManager"));
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        startActivity(intent);
    }

    public void undoAgreeTrade(View view){
        Intent intent = new Intent(this, UndoAgreeTrade.class);
        intent.putExtra("username", username);
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        intent.putExtra("MeetingManager",
                getIntent().getSerializableExtra("MeetingManager"));
        startActivity(intent);
    }

    public void undoConfirmTrade(View view){
        Intent intent = new Intent(this, UndoConfirmTrade.class);
        intent.putExtra("username", username);
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        intent.putExtra("MeetingManager",
                getIntent().getSerializableExtra("MeetingManager"));
        startActivity(intent);
    }

    public void undoProposeTrade(View view){
        Intent intent = new Intent(this, UndoProposeTrade.class);
        intent.putExtra("username", username);
        intent.putExtra("TradeManager",
                getIntent().getSerializableExtra("TradeManager"));
        intent.putExtra("MeetingManager",
                getIntent().getSerializableExtra("MeetingManager"));
        intent.putExtra("TraderManager",
                getIntent().getSerializableExtra("TraderManager"));
        startActivity(intent);
    }

    public void undoRemoveItem(View view){
        Intent intent = new Intent(this, UndoRemoveItem.class);
        intent.putExtra("username", username);
        intent.putExtra("ItemManager",
                getIntent().getSerializableExtra("ItemManager"));
        startActivity(intent);
    }
}