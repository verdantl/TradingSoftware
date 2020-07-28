package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.TraderManager;

import java.util.ArrayList;

public class ViewTradersActivity extends AppCompatActivity implements ClickableList{
    private TraderManager traderManager;
    private String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getIntent().getExtras().getSerializable("TraderManager");
        setContentView(R.layout.activity_view_traders);
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
                displayFragment();
            }
        });
    }

    public void displayFragment(){
        InfoFragment infoFragment = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("UserInfo", userInfo);
        infoFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.info_fragment_container, infoFragment).commit();
    }
}