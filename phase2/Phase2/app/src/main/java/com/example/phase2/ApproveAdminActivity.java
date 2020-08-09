package com.example.phase2;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;

import java.util.ArrayList;


public class ApproveAdminActivity extends BundleActivity implements ClickableList, Dialogable {
    private AdminActions adminActions;
    private Boolean approved = null;
    private String approvedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_admin);
        adminActions = (AdminActions) getUseCase(ADMINKEY);
        viewList();
    }

    @Override
    public void onBackPressed(){
        replaceUseCase(adminActions);
        super.onBackPressed();
    }


    private void approveReject(){
        adminActions.approveAdmin(approvedUser, approved);
        String message = "Successfully ";
        if (approved){
            message += "approved!";
        }
        else{
            message += "denied!";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        viewList();
    }

    public void viewList(){
        final ArrayList<String> adminRequests = adminActions.getRequestedAdmins();
        setContentView(R.layout.activity_approve_admin);
        ListView listView= findViewById(R.id.requested_admins);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, adminRequests);
        listView.setAdapter(adminAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                approvedUser = adminRequests.get(i);
            }
        });
    }



    @Override
    public void clickPositive() {
        approved = false;
        approveReject();

    }

    @Override
    public void clickNegative() {
        approved = true;
        approveReject();

    }

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Approve")
                .show(getSupportFragmentManager(), "ApproveAdmin");
    }
}