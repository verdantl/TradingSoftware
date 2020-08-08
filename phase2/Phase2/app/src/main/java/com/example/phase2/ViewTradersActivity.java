package com.example.phase2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;
import java.util.Objects;

public class ViewTradersActivity extends BundleActivity implements ClickableList, Dialogable{
    private Dialog dialog;
    private TraderManager traderManager;
    private String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        setContentView(R.layout.activity_view_traders);
        dialog = new Dialog(this);
        viewList();
    }

    public void viewList(){
        final ArrayList<String> traders = traderManager.getTraders();
        ListView listView = findViewById(R.id.traders);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userInfo = traderManager.getTraderInfo(traders.get(i));
                displayDialog();
            }
        });
    }

    public void displayDialog() {
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(userInfo);
        dialog.show();
    }
}