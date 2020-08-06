package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.phase2.phase2.ItemStatus.AVAILABLE;
import static com.example.phase2.phase2.ItemStatus.REMOVED;

public class ApproveItems extends AppCompatActivity implements ClickableList{
    private Bundle bundle;
    private ItemManager itemManager;
    private Integer processedItem;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        dialog = new Dialog(this);
        viewList();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminActivity.class);
        bundle.remove("ItemManager");
        intent.putExtras(bundle);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
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
                displayDialog();
                processedItem = itemIds.get(i);
            }
        });
    }

    public void displayDialog(){
        dialog.setContentView(R.layout.fragment_approve_items);

        dialog.show();
    }

    public void onApproveClicked(View view) {
        dialog.hide();
        if(itemManager.getItemStatus(processedItem) == AVAILABLE){
            Toast.makeText(this,
                    "fail: the item has been approved", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.approveItem(processedItem, true);
            Toast.makeText(this,
                    "Successfully approved!", Toast.LENGTH_SHORT).show();
        }
        viewList();
    }

    public void onRejectClicked(View view) {
        if(itemManager.getItemStatus(processedItem) == REMOVED){
            Toast.makeText(this,
                    "fail: the item has been rejected", Toast.LENGTH_SHORT).show();
        }else{
            itemManager.removeItem(processedItem);
            Toast.makeText(this,
                    "Successfully rejected!", Toast.LENGTH_SHORT).show();
        }
        viewList();
    }
}