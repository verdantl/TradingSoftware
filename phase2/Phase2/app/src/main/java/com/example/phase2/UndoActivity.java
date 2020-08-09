package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class UndoActivity extends BundleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undo);
    }


    public void submit(View view){
        EditText undoTraderText = findViewById(R.id.undoUser);
        String chosenTrader = undoTraderText.getText().toString();
        TraderManager traderManager = (TraderManager) getUseCase(TRADERKEY);

        assert traderManager != null;
        if(traderManager.containTrader(chosenTrader)){
            Intent intent = new Intent(this, UndoMenu.class);
            intent.putExtra("Username", chosenTrader);
            putBundle(intent);
            startActivityForResult(intent, RESULT_FIRST_USER);
        }else{
            Toast.makeText(this, "User doesn't exist", Toast.LENGTH_SHORT).show();
        }

    }
}