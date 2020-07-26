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

        }
        else if (adminActions.checkCredentials(username, password)){

        }
        else{
            TextView textView = findViewById(R.id.loginerror);
            textView.setText(R.string.login_error);
        }
    }

    public void onSignupClicked(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
