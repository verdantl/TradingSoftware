package com.example.phase2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;


import java.util.ArrayList;
import java.util.List;

public class UndoRemoveItem extends BundleActivity implements Dialogable {
    private ItemManager itemManager;
    private String chosenTrader;
    private Integer chosenItem;

    /**create this activity
     * @param savedInstanceState the bundle from the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenTrader = getIntent().getStringExtra("chosenTrader");
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        viewList();
        
    }

    /**
     * listener for the back button, return to the last menu
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(itemManager);
        Intent intent = new Intent();
        putBundle(intent);
        setResult(RESULT_FIRST_USER, intent);
        finish();
    }

    private void viewList() {
        final List<Integer> removedItems = itemManager.getRemovedItemIds(chosenTrader);
        ArrayList<String> removePresenter = new ArrayList<>();
        setContentView(R.layout.activity_undo_remove_item);
        ListView listView = findViewById(R.id.removedItem);
        for(Integer i: removedItems){
            removePresenter.add(itemManager.getItemInString(i));
        }
        ArrayAdapter<String> removeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, removePresenter);
        listView.setAdapter(removeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                chosenItem = removedItems.get(i);
            }
        });
    }



    /**
     * Listener for the positive button, undoRemoveItems
     */
    @Override
    public void clickPositive() {
        itemManager.undoRemoval(chosenItem);
        Toast.makeText(this,
                "Successfully undo remove", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * Listener for the negative button, cancel the action
     */
    @Override
    public void clickNegative() {
        Toast.makeText(this,
                "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    /**
     * open the dialog
     */
    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Undo")
                .show(getSupportFragmentManager(), "UndoRemove");


    }
}