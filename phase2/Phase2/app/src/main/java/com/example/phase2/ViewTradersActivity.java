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
import android.widget.TextView;

import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class ViewTradersActivity extends AppCompatActivity implements ClickableList{
    private TraderManager traderManager;
    private String userInfo;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getIntent().getExtras().getSerializable("TraderManager");
        setContentView(R.layout.activity_view_traders);
        dialog = new Dialog(this);
        viewList();
    }

    public void viewList(){
        final ArrayList<String> traders = traderManager.getTraders();
        setContentView(R.layout.activity_view_traders);
        ListView listView= findViewById(R.id.traders);
        ArrayAdapter<String> tradersAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, traders);
        listView.setAdapter(tradersAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userInfo = traderManager.getTraderInfo(traders.get(i));
                displayDialog();
            }
        });
    }

    public void displayDialog(){
        dialog.setContentView(R.layout.fragment_info);

        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(userInfo);
        dialog.show();
    }
}