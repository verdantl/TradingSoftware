package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class ChangeTraderPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_trader_password);
    }

    public void submitTraderPassword(View view){
        TraderManager tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
        EditText editText = (EditText) findViewById(R.id.editTextTextPassword2);
        String newPassword = editText.getText().toString();

        tm.changePassword(getIntent().getStringExtra("CurrentTrader"), newPassword);
        Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();
        finish();
    }
}