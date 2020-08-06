package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;

public class AddNewItemActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private String currentTrader;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, EditInventoryActivity.class);
        bundle.remove("ItemManager");
        bundle.remove("CurrentTrader");
        intent.putExtras(bundle);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
    }

    public void addItemButton(View view){
        EditText nameText = findViewById(R.id.enterActualName);
        EditText ratingText = findViewById(R.id.enterActualRating);
        EditText descriptionText = findViewById(R.id.enterActualDescription);
        EditText categoryText = findViewById(R.id.enterActualCategory);
        String name = nameText.getText().toString();
        int rating = Integer.parseInt(ratingText.getText().toString());
        String description = descriptionText.getText().toString();
        String category = categoryText.getText().toString();

        int itemID = itemManager.addItem(name, currentTrader);
        itemManager.addItemDetails(itemID, category, description, rating);
        itemManager.changeStatusToRequested(itemID);
        Toast.makeText(this, "Item requested. Check back later.",
                Toast.LENGTH_LONG).show();
        finish();
    }
}