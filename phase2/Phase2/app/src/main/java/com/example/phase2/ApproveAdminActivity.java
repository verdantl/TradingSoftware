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
        Bundle bundle = getIntent().getExtras();
        adminActions = (AdminActions) bundle.getSerializable("AdminActions");
        adminActions.newAdmin("Admin2", "Wordpass");
        setContentView(R.layout.activity_approve_admin);
        ListView listView = findViewById(R.id.requested_admins);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, adminActions.getRequestedAdmins());
        listView.setAdapter(adminAdapter);

    }
}