package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void submitPassword(View view){
        EditText et = (EditText) findViewById(R.id.enterPassword);
        AdminActions adminActions = (AdminActions) getIntent().getSerializableExtra("AdminActions");
        assert adminActions != null;
        adminActions.changePassword(getIntent().getStringExtra("CurrentAdmin"), et.getText().toString());
        Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();
        finish();
    }
}