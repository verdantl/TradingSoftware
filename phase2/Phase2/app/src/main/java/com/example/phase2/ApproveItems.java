package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.phase2.phase2.ItemStatus.AVAILABLE;
import static com.example.phase2.phase2.ItemStatus.REMOVED;

public class ApproveItems extends AppCompatActivity {
    private ItemManager itemManager;
    private Integer processedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        viewList();
    }


    public void viewList(){
        final List<Integer> itemIds = itemManager.getItemsNeedingApproval();
        List<String> items = itemManager.getListOfItemsInString(itemIds);
        setContentView(R.layout.activity_approve_items);
        ListView listView= findViewById(R.id.processedItems);
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayFragment();
                processedItem = itemIds.get(i);
            }
        });
    }

    public void displayFragment(){
        ApproveItemsFragment approveItemsFragment = new ApproveItemsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.items_container, approveItemsFragment).commit();
    }

    public void onApproveClicked(View view) {
        if(itemManager.getItemStatus(processedItem) == AVAILABLE){
            Toast.makeText(this,
                    "fail: the item has been approved", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.approveItem(processedItem, true);
        }
    }

    public void onRejectClicked(View view) {
        if(itemManager.getItemStatus(processedItem) == REMOVED){
            Toast.makeText(this,
                    "fail: the item has been rejected", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.removeItem(processedItem);
        }
    }
}