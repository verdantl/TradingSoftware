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
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        setContentView(R.layout.activity_admin);
    }

    public void addRemoveAdmin(View view){
        Intent intent = new Intent(this, ApproveAdminActivity.class);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }

    public void manageAccounts(View view){
        Intent intent = new Intent(this, ManageFrozenAccount.class);
        intent.putExtra("TraderManager", traderManager);
        startActivity(intent);
    }

    public void addRemoveItems(View view){
        Intent intent = new Intent(this, ApproveItems.class);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }

    public void viewStatus(View view){
        Intent intent = new Intent(this, ViewTradersActivity.class);
        intent.putExtra("TraderManager", traderManager);
        startActivity(intent);
    }

    public void changeLimits(View view){
        Intent i = new Intent(this, ChangeLimitActivity.class);
        i.putExtra("TraderManager", traderManager);
        startActivity(i);

    }

    public void changePassword(View view){
        Intent i =  new Intent(this, ChangePasswordActivity.class);
        i.putExtra("AdminActions", adminActions);
        i.putExtra("CurrentAdmin", currentAdmin);
        startActivity(i);
    }

    public void undoMenu(View view){
        Intent i = new Intent(this, undo.class);
        startActivity(i);
    }
}
