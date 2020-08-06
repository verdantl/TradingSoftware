package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;

public class ChangePasswordActivity extends AppCompatActivity {

    private AdminActions adminActions;
    private String currentAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActions = (AdminActions) getIntent().getSerializableExtra(adminActions.getClass().getName());
        currentAdmin = getIntent().getStringExtra(currentAdmin.getClass().getName());
        setContentView(R.layout.activity_change_password);
    }

    public void submitPassword(View view){
        EditText et = (EditText) findViewById(R.id.enterPassword);
        assert adminActions != null;
        adminActions.changePassword(getIntent().getStringExtra("CurrentAdmin"), et.getText().toString());
        Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("AdminActions", adminActions);
        setResult(RESULT_FIRST_USER, resultIntent);
        finish();
    }

}