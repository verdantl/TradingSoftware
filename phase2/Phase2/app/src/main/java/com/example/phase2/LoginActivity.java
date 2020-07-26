package com.example.phase2;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.TraderManager;

public class LoginActivity extends AppCompatActivity {
    private final AdminActions adminActions;
    private final TraderManager traderManager;

    private int nextSystem;
    private String nextUser;

    public LoginActivity(TraderManager traderManager, AdminActions adminActions){
        this.traderManager = traderManager;
        this.adminActions = adminActions;
    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();
        Intent intent;
        if (traderManager.login(username, password)){
            nextUser = username;
            nextSystem = 2;
        }
        else if (adminActions.checkCredentials(username, password)){
            nextUser = username;
            nextSystem = 3;
        }
        else{
            TextView textView = findViewById(R.id.loginerror);
            textView.setText(R.string.login_error);
            textView.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void onSignupClicked(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
