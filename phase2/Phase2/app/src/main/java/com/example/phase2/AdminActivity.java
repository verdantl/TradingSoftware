package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    private final int CHANGE_LIMIT_REQ = 5;
    private final int CHANGE_PASSWORD_REQ = 6;

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

        currentAdmin = bundle.getString("Username");
        setContentView(R.layout.activity_admin);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(bundle.getString("UserName"));
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
        startActivityForResult(i, CHANGE_LIMIT_REQ);

    }

    public void changePassword(View view){
        Intent i =  new Intent(this, ChangePasswordActivity.class);
        i.putExtra("AdminActions", adminActions);
        i.putExtra("Username", currentAdmin);
        startActivityForResult(i, CHANGE_PASSWORD_REQ);
    }

    public void undoMenu(View view){
        Intent i = new Intent(this, Undo.class);
        i.putExtra("TraderManager", traderManager);
        i.putExtra("TradeManager", tradeManager);
        i.putExtra("ItemManager", itemManager);
        i.putExtra("MeetingManager", meetingManager);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        switch(requestCode){
            case CHANGE_LIMIT_REQ:
                traderManager = (TraderManager) data.getSerializableExtra("TraderManager");
                break;
            case CHANGE_PASSWORD_REQ:
                adminActions = (AdminActions) data.getSerializableExtra("AdminActions");
                break;
            default:

        }
    }
}
