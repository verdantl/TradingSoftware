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

import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class AllTradersMenu extends AppCompatActivity implements ClickableList, Dialogable {
    private Bundle bundle;
    private TraderManager traderManager;
    private String frozenTrader;


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