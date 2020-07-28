package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private AdminActions adminActions;

    private ItemManager itemManager;
    private TradeManager tradeManager;
    private TraderManager traderManager;
    private MeetingManager meetingManager;
    private String currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActions = (AdminActions) getIntent().getSerializableExtra("AdminActions");
        setContentView(R.layout.activity_admin);
        getIntent();
    }

    public void addRemoveAdmin(View view){
        Intent intent = new Intent(this, ApproveAdminActivity.class);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }

    public void manageAccounts(View view){

    }

    public void addRemoveItems(View view){

    }

    public void viewStatus(View view){

    }

    public void changeLimits(View view){

    }

    public void changePassword(View view){

    }
}
