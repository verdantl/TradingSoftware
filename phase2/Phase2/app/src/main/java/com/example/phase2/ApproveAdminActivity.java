package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.AdminActions;

public class ApproveAdminActivity extends AppCompatActivity {
    private AdminActions adminActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminActions = (AdminActions) getIntent().getSerializableExtra("AdminActions");

        ListView listView = findViewById(R.id.requested_admins);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, adminActions.getRequestedAdmins());
        listView.setAdapter(adminAdapter);
        setContentView(R.layout.activity_approve_admin);
    }
}