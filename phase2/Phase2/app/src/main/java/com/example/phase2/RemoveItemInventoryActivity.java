package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;


public class RemoveItemInventoryActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private int chosenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        chosenItem = (int) bundle.getSerializable("ChosenItem");
        setContentView(R.layout.activity_remove_item_inventory);
        setValues();
    }

    public void setValues(){
        String name = itemManager.getItemName(chosenItem);
        String rating = itemManager.getItemQuality(chosenItem);
        String description = itemManager.getItemDescription(chosenItem);

        TextView actualName = findViewById(R.id.actualName);
        TextView actualRating = findViewById(R.id.actualRating);
        TextView actualDescription = findViewById(R.id.actualDescription);

        actualName.setText(name);
        actualRating.setText(rating);
        actualDescription.setText(description);
    }

    public void removeItemInventory(View view){
        itemManager.removeItem(chosenItem);
        itemManager.changeStatusToRemoved(chosenItem);
        Toast.makeText(this, "Successfully removed the item",
                Toast.LENGTH_LONG).show();
        finish();
    }

}