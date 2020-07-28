package com.example.phase2;

import android.os.Bundle;

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
}
