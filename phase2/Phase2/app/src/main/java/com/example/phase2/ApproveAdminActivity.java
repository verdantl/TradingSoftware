package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;

import java.util.ArrayList;

public class ApproveAdminActivity extends AppCompatActivity implements ClickableList{
    private AdminActions adminActions;
    private Bundle bundle;
    private Dialog dialog;
    private Boolean approved = null;
    private String approvedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        dialog = new Dialog(this);
        viewList();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AdminActivity.class);
        bundle.remove("AdminActions");
        intent.putExtras(bundle);
        intent.putExtra("AdminActions", adminActions);
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState
                .putSerializable("AdminActions", adminActions);
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
                displayDialog();
                approvedUser = adminRequests.get(i);
            }
        });
    }

    public void displayDialog() {
        dialog.setContentView(R.layout.fragment_approval);
        dialog.show();
    }

}