package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class LoginActivity extends AppCompatActivity {
    private AdminActions adminActions;
    private TraderManager traderManager;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        traderManager = (TraderManager) bundle.get("TraderManager");
        adminActions = (AdminActions) bundle.get("AdminActions");
        tradeManager = (TradeManager) bundle.get("TradeManager");
        meetingManager = (MeetingManager) bundle.get("MeetingManager");
        itemManager = (ItemManager) bundle.get("ItemManager");

    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();

        if (traderManager.login(username, password)){
            Intent intent = new Intent(this, TraderActivity.class);
            putAllUseCases(intent, username);
            startActivity(intent);
        }
        else if (adminActions.checkCredentials(username, password)){
            Intent intent = new Intent(this, AdminActivity.class);
            putAllUseCases(intent, username);
            intent.putExtra("AdminActions", adminActions);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
        }
    }

    public void onSignupClicked(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra("AdminActions", adminActions);
        intent.putExtra("TraderManager", traderManager);
        startActivity(intent);
    }

    private void putAllUseCases(Intent intent, String username){
        intent.putExtra("Username", username);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TradeManager", tradeManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("MeetingManager", meetingManager);
        intent.putExtra("AdminActions", adminActions);
    }
}
