package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

import java.util.List;

public class FlaggedAccountsMenu extends AppCompatActivity implements ClickableList{
    private Bundle bundle;
    private TraderManager traderManager;
    private String frozenTrader;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagged_accounts_menu);
        bundle = getIntent().getExtras();
        assert bundle != null;
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        dialog = new Dialog(this);
        viewList();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ManageFrozenAccount.class);
        bundle.remove("TraderManager");
        bundle.putSerializable("TraderManager", traderManager);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void onClickFreeze(View view) {
        if (traderManager.freezeAccount(frozenTrader)) {
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this,
                    "Fail: the account is already frozen", Toast.LENGTH_SHORT).show();
        }
        dialog.hide();
        viewList();
    }

    public void onClickCancel(View view) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        dialog.hide();
        viewList();
    }

    public void viewList() {
        final List<String> allFlaggedTraders = traderManager.getListOfFlagged();
        ListView listView = findViewById(R.id.flagged);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allFlaggedTraders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayDialog();
                frozenTrader = allFlaggedTraders.get(i);
            }
        });
    }

    public void displayDialog() {
        dialog.setContentView(R.layout.fragment_freeze_flagged);
        dialog.show();
    }
}

