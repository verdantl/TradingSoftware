package com.example.phase2;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public abstract class ClickableListActivity extends DialogActivity {

    protected List<String> selections;
    protected ListView listView;

    public void setSelections(List<String> selections) {
        this.selections = selections;
    }

    protected void viewList(Integer listViewID){
        listView = findViewById(listViewID);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, selections);
        listView.setAdapter(adminAdapter);
    }
}
