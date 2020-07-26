package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.phase2.phase2.*;

public class MainActivity extends AppCompatActivity {
    private final AdminActions adminActions;
    private final TraderManager traderManager;

    private int nextSystem;
    private String nextUser;

    public MainActivity(TraderManager traderManager, AdminActions adminActions){
        this.traderManager = traderManager;
        this.adminActions = adminActions;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();
        if (traderManager.login(username, password)){
            nextUser = username;
            nextSystem = 2;
        }
        else if (adminActions.checkCredentials(username, password)){
            nextUser = username;
            nextSystem = 3;
        }
        else{

        }
    }
    public void onSignupClicked(View view){

    }
}