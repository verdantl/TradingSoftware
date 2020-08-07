package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;

public class UndoMenu extends AppCompatActivity {
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        setContentView(R.layout.activity_undo_menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UndoActivity.class);
        bundle.remove("username");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void undoEditMeeting(View view){
        Intent intent = new Intent(this, UndoEditMeeting.class);
        //System.out.println(bundle.getSerializable("Username"));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void undoAgreeTrade(View view){
        Intent intent = new Intent(this, UndoAgreeTrade.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void undoConfirmTrade(View view){
        Intent intent = new Intent(this, UndoConfirmTrade.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void undoProposeTrade(View view){
        Intent intent = new Intent(this, UndoProposeTrade.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void undoRemoveItem(View view){
        Intent intent = new Intent(this, UndoRemoveItem.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}