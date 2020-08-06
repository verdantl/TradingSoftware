package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.TraderManager;

import java.util.List;

public class RequestedUnfrozenMenu extends AppCompatActivity implements ClickableList {
    private TraderManager traderManager;
    private String unfreezeRequest;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        dialog = new Dialog(this);
        assert bundle != null;
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");

        viewList();
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
                displayDialog();
                unfreezeRequest = allUnfreezeRequests.get(i);
            }
        });
    }


    public void onClickUnfreeze(View view) {
        if(traderManager.unfreezeAccount(unfreezeRequest)){
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,
                    "Fail: the account is already unfrozen", Toast.LENGTH_SHORT).show();
        }
        viewList();
    }

    public void onClickCancel(View view) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();
    }

    public void displayDialog() {
        dialog.setContentView(R.layout.fragment_unfreeze);
        dialog.show();
    }
}