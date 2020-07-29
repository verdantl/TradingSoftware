package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class AllTradersMenu extends AppCompatActivity {
    private TraderManager traderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        viewList();

    }

    public void viewList(){
        final ArrayList<String> allTraders = traderManager.getTraders();
        setContentView(R.layout.activity_all_traders_menu);
        ListView listView = findViewById(R.id.freezeTrader);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allTraders);
        listView.setAdapter(allTraderAdapter);
    }
}