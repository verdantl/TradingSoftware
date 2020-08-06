package com.example.phase2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ConfigGateway;
import com.example.phase2.phase2.TraderManager;

public class LoginActivity extends AppCompatActivity {
    private Bundle bundle;
    private AdminActions adminActions;
    private TraderManager traderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConfigGateway configGateway = new ConfigGateway(getApplicationContext().getFilesDir());
        bundle = getIntent().getExtras();
        if (bundle == null){
            bundle = configGateway.getBundle();
        }
        else{
            //configGateway.saveBundle(bundle);
            bundle.remove("Username");
        }
        traderManager = (TraderManager) bundle.get("TraderManager");
        adminActions = (AdminActions) bundle.get("AdminActions");
    }

    public void onLoginClicked(View view){
        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        EditText passEditText = findViewById(R.id.editTextTextPassword);
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();

        if (traderManager.login(username, password)){
            Intent intent = new Intent(this, TraderActivity.class);
            startIntent(intent, username);
        }

        else if (adminActions.checkCredentials(username, password)){
            Intent intent = new Intent(this, AdminActivity.class);
            startIntent(intent, username);
        }
        else{
            Toast.makeText(this, R.string.login_error,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onSignupClicked(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startIntent(intent, "");
    }

    private void startIntent(Intent intent, String username){
        intent.putExtras(bundle);
        intent.putExtra("Username", username);
        startActivity(intent);
    }
}
