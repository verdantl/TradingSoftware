package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class AllTradersMenu extends BundleActivity implements ClickableList, Dialogable {
    private TraderManager traderManager;
    private ItemManager itemManager;
    private String frozenTrader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        viewList();

    }

    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }

    public void viewList(){
        final ArrayList<String> allTraders = traderManager.getTraders();
        setContentView(R.layout.activity_all_traders_menu);
        ListView listView = findViewById(R.id.freezeTrader);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allTraders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                frozenTrader = allTraders.get(i);
            }
        });
    }

    @Override
    public void clickPositive() {
        if(traderManager.freezeAccount(frozenTrader)){
            itemManager.setStatusForFrozenUser(frozenTrader);
            traderManager.setTraderInactive(frozenTrader, false);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,
                    "Fail: the account is already frozen", Toast.LENGTH_SHORT).show();
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
        dialogFactory.getDialog("Freeze")
                .show(getSupportFragmentManager(), "FreezeTrader");

    }
}