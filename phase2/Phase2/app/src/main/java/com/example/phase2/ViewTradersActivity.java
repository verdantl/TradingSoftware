package com.example.phase2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.phase2.phase2.TraderManager;

public class ViewTradersActivity extends ClickableListActivity{
    private TraderManager traderManager;
    private String userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        traderManager = (TraderManager) getIntent().getExtras().getSerializable(traderKey);
        setContentView(R.layout.activity_view_traders);
        viewList(R.id.traders);
    }

    public void viewList(Integer listViewID){
        setSelections(traderManager.getTraders());
        super.viewList(listViewID);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                userInfo = traderManager.getTraderInfo(selections.get(i));
                displayDialog(R.layout.fragment_info);
            }
        });
    }

    public void displayDialog(Integer viewID){
        super.displayDialog(viewID);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(userInfo);
    }
}