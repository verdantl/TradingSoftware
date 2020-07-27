package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.TraderManager;

public class LoginActivity extends AppCompatActivity {
    private AdminActions adminActions;
    private TraderManager traderManager;

    private int nextSystem;
    private String nextUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Bundle bundle = getIntent().getExtras();
//        assert bundle != null;
//        traderManager = (TraderManager) bundle.get("TraderManager");
//        adminActions = (AdminActions) bundle.get("AdminActions");
    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Next User", username);

        if (traderManager.login(username, password)){
            intent.putExtra("NextSystem", 2);
            startActivity(intent);
        }
        else if (adminActions.checkCredentials(username, password)){
            intent.putExtra("NextSystem", 3);
            startActivity(intent);
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
