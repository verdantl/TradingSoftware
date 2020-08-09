package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class RequestedUnfrozenMenu extends AppCompatActivity implements ClickableList, Dialogable {
    private TraderManager traderManager;
    private Bundle bundle;
    private String unfreezeRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");

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

    public void viewList(){
        final List<String> allUnfreezeRequests = traderManager.getAllRequestsToUnfreeze();
        setContentView(R.layout.activity_requested_unfrozen_menu);
        ListView listView = findViewById(R.id.unfreeze);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allUnfreezeRequests);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                unfreezeRequest = allUnfreezeRequests.get(i);
            }
        });
    }

    @Override
    public void clickPositive() {
        if(traderManager.unfreezeAccount(unfreezeRequest)){
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,
                    "Fail: the account is already unfrozen", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Unfreeze")
                .show(getSupportFragmentManager(), "Unfreeze");

    }
}