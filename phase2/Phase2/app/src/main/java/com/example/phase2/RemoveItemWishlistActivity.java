package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

public class RemoveItemWishlistActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private int chosenItem;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = (String) bundle.getSerializable("CurrentTrader");
        chosenItem = (int) bundle.getSerializable("ChosenItem");
        setContentView(R.layout.activity_remove_item_wishlist);
        setValues();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, EditWishlistActivity.class);
        bundle.remove("ItemManager");
        bundle.remove("TradeManager");
        bundle.remove("CurrentTrader");
        bundle.remove("ChosenItem");
        intent.putExtras(bundle);
        intent.putExtra("ItemManager", itemManager);
        intent.putExtra("TraderManager", traderManager);
        intent.putExtra("CurrentTrader", currentTrader);
        startActivity(intent);
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

    public void removeItemWishlist(View view){
        itemManager.removeItem(chosenItem);
        itemManager.changeStatusToRemoved(chosenItem);
        traderManager.removeFromWishlist(currentTrader, chosenItem);
        Toast.makeText(this, "Successfully removed the item",
                Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}