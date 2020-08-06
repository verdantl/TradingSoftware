package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class UndoActivity extends AppCompatActivity {

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_undo);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ManageFrozenAccount.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void submit(View view){
        EditText undoTraderText = findViewById(R.id.undoUser);
        String chosenTrader = undoTraderText.getText().toString();
        TraderManager traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        assert traderManager != null;
        if(traderManager.containTrader(chosenTrader)){
            Intent intent = new Intent(this, UndoMenu.class);
            intent.putExtra("username", chosenTrader);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show();
        }

    }
}