package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;

import java.util.ArrayList;

public class ApproveAdminActivity extends AppCompatActivity implements ClickableList{
    private AdminActions adminActions;
    Boolean approved = null;
    String approvedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        adminActions.newAdmin("Admin2", "Wordpass");
        adminActions.newAdmin("Sup", "nothing");
        viewList();
    }

    public void onApproveClicked(View view){
        approved = true;
        approveReject();
    }

    public void onRejectClicked(View view){
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
                displayFragment();
                approvedUser = adminRequests.get(i);
            }
        });
    }

    public void displayFragment() {
        ApprovalFragment approvalFragment = new ApprovalFragment();
        TextView textView = findViewById(R.id.question);
        textView.setText(R.string.approve_or_reject);
        Button approve  = findViewById(R.id.approve);
        Button reject = findViewById(R.id.reject);
        approve.setText(R.string.approve);
        reject.setText(R.string.reject);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, approvalFragment).commit();
    }

}