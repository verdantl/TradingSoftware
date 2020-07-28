package com.example.phase2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.TraderManager;

public class SignupActivity extends AppCompatActivity {

    private AdminActions adminActions;
    private TraderManager traderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        setContentView(R.layout.activity_signup);
    }

    public void onCreateNewAccount(View view){
        RadioGroup radioGroup = findViewById(R.id.account_selection);
        EditText userEditText = findViewById(R.id.newUsername);
        EditText passEditText = findViewById(R.id.newPassword);
        String username = userEditText.getText().toString();
        if (checkUsername(username)) {
            String password = passEditText.getText().toString();
            int radioID = radioGroup.getCheckedRadioButtonId();
            if (radioID == R.id.newTrader) {
                traderManager.newTrader(username, password);
            }
            else{
                adminActions.newAdmin(username, password);
            }
            Toast.makeText(this, "Account created successfully!",
                    Toast.LENGTH_SHORT).show();
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
        return adminActions.checkUsername(username) && traderManager.isUsernameAvailable(username);
    }
}
