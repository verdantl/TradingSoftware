package com.example.phase2;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.content.Intent;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;


public class ApproveAdminActivity extends ClickableListActivity{
    private AdminActions adminActions;
    private Boolean approved = null;
    private String approvedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        setContentView(R.layout.activity_approve_admin);
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        viewList(R.id.requested_admins);
    }

    @Override
    public void onBackPressed(){
        bundle.remove("AdminActions");
        bundle.putSerializable("AdminActions", adminActions);
        super.onBackPressed();
    }

    public void onApproveClicked(View view){
        dialog.hide();
        approved = true;
        approveReject();
    }

    public void onRejectClicked(View view){
        dialog.hide();
        approved = false;
        approveReject();
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
        viewList(R.id.requested_admins);
    }

    protected void viewList(Integer listViewID){
        selections = adminActions.getRequestedAdmins();

        super.viewList(listViewID);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayDialog(R.layout.fragment_approval);
                approvedUser = selections.get(i);
            }
        });
    }


}