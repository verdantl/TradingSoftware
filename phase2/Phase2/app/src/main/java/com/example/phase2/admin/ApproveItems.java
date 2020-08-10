package com.example.phase2.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.deliverable.ItemManager;
import com.example.phase2.R;
import com.example.phase2.dialogs.DialogFactory;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.Dialogable;

import java.util.List;

import static com.example.phase2.deliverable.ItemStatus.AVAILABLE;
import static com.example.phase2.deliverable.ItemStatus.REMOVED;

public class ApproveItems extends BundleActivity implements ClickableList, Dialogable {
    private ItemManager itemManager;
    private Integer processedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        viewList();
    }

    @Override
    public void onBackPressed() {
        replaceUseCase(itemManager);
        super.onBackPressed();
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
                openDialog();
                processedItem = itemIds.get(i);
            }
        });
    }

    @Override
    public void clickPositive() {
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

    @Override
    public void clickNegative() {
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

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Approve")
                .show(getSupportFragmentManager(), "ApproveItem");

    }
}