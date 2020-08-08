package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ConfigGateway;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class LoginActivity extends BundleActivity {
    private AdminActions adminActions;
    private TraderManager traderManager;
    private ItemManager itemManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();

        if (traderManager.login(username, password)){
            Intent intent = new Intent(this, TraderActivity.class);
            putAllUseCases(intent, username);
            intent.putExtra("Username", username);
            startActivity(intent);
        }

        else if (adminActions.login(username, password)){
            Intent intent = new Intent(this, AdminActivity.class);
            replaceUsername(username);
            putBundle(intent);
            startActivityForResult(intent, RESULT_OK);
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

    public void onTutorialClicked(View view){
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("ItemManager", itemManager);
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
