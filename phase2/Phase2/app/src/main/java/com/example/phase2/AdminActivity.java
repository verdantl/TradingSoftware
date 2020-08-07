package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;

public class AdminActivity extends BundleActivity {
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
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");

        currentAdmin = bundle.getString("Username");
        setContentView(R.layout.activity_admin);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(bundle.getString("Username"));
    }

    public void onLogoutClicked(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        bundle.remove("Username");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addRemoveAdmin(View view){
        Intent intent = new Intent(this, ApproveAdminActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    public void manageAccounts(View view){
        Intent intent = new Intent(this, ManageFrozenAccount.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addRemoveItems(View view){
        Intent intent = new Intent(this, ApproveItems.class);
        intent.putExtras(bundle);
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
        Intent i = new Intent(this, UndoActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        assert data != null;
//        switch(requestCode){
//            case CHANGE_LIMIT_REQ:
//                if(resultCode == RESULT_FIRST_USER) {
//                    traderManager = (TraderManager) data.getSerializableExtra("TraderManager");
//                    bundle.remove("TraderManager");
//                    bundle.putSerializable("TraderManager", traderManager);
//                }
//                break;
//            case CHANGE_PASSWORD_REQ:
//                if(resultCode == RESULT_FIRST_USER) {
//                    adminActions = (AdminActions) data.getSerializableExtra("AdminActions");
//
//                    bundle.remove("AdminActions");
//                    bundle.putSerializable("AdminActions", adminActions);
//                }
//                break;
//            default:
//
//        }
//    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this,
                "You have reached the main menu!", Toast.LENGTH_SHORT).show();
    }
}
