package com.example.phase2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.TraderManager;

public class SignupActivity extends BundleActivity {
    private AdminActions adminActions;
    private TraderManager traderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActions = (AdminActions) getUseCase(ADMINKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        setContentView(R.layout.activity_signup);
    }

    public void onCreateNewAccount(View view){
        RadioGroup radioGroup = findViewById(R.id.account_selection);
        EditText userEditText = findViewById(R.id.newUsername);
        EditText passEditText = findViewById(R.id.newPassword);
        String username = userEditText.getText().toString();
        if(username.equals("")){
            Toast.makeText(this, "That is a invalid username",
                    Toast.LENGTH_SHORT).show();
        }
        else if (checkUsername(username)) {
            String password = passEditText.getText().toString();
            int radioID = radioGroup.getCheckedRadioButtonId();
            if (radioID == R.id.newTrader) {
                traderManager.newTrader(username, password);
                Toast.makeText(this, "Account created successfully!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                adminActions.newAdmin(username, password);
                Toast.makeText(this, "Account request successfully sent!",
                        Toast.LENGTH_SHORT).show();
            }
            backToLogin();
        }
        else{
            Toast.makeText(this, "That username is taken. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUsername(String username){
        if (username.isEmpty()){
            return false;
        }
        return adminActions.checkUsername(username) && traderManager.checkUsername(username);
    }

    private void backToLogin(){
        replaceUseCase(traderManager);
        replaceUseCase(adminActions);
        super.onBackPressed();
    }
}
