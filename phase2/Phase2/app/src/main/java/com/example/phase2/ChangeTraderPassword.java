package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

public class ChangeTraderPassword extends BundleActivity {
    private TraderManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_trader_password);
        tm = (TraderManager) getIntent().getSerializableExtra("TraderManager");
    }

    public void submitTraderPassword(View view){
        EditText editText = (EditText) findViewById(R.id.editTextTextPassword2);
        String newPassword = editText.getText().toString();

        if(newPassword.equals("")){
            Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT).show();
        }else {
            tm.changePassword(getIntent().getStringExtra("CurrentTrader"), newPassword);
            Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();
            Intent result = new Intent();
            result.putExtra(TRADERKEY, tm);
            setResult(RESULT_FIRST_USER, result);
            finish();
        }

    }
}