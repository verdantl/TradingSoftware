package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        chosenItem = (int) bundle.getSerializable("ChosenItem");
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

        setContentView(R.layout.activity_remove_item_wishlist);
    }

    public void removeItemWishlist(View view){
        itemManager.removeItem(chosenItem);
        itemManager.changeStatusToRemoved(chosenItem);
        traderManager.removeFromWishlist(currentTrader, chosenItem);
        Toast.makeText(this, "Successfully removed the item",
                Toast.LENGTH_LONG).show();
        finish();
    }
}