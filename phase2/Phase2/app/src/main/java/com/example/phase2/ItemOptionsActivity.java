package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

public class ItemOptionsActivity extends AppCompatActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;
    private boolean useLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        currentTrader = (String) bundle.getString("CurrentTrader");
        useLocation = (Boolean) bundle.getBoolean("LocationChoice");
        chosenItem = (Integer) bundle.getInt("ChosenItem");
        viewStart();
    }

    public void viewStart() { setContentView(R.layout.activity_item_options); }

    public void addToWishList(View view) {
        if(!traderManager.getWishlistIds(currentTrader).contains(chosenItem)) {
            traderManager.addToWishlist(currentTrader, chosenItem);
            Toast.makeText(this, "Item added to your wishlist!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "This item is already in your wishlist.", Toast.LENGTH_LONG).show();
        }
    }

    public void proposeTrade(View view) {
        if (!traderManager.getIsFrozen(currentTrader)){
            // Continue to the next activity
        }
        else{
            Toast.makeText(this, "Your account is frozen," +
                    " you cannot trade.", Toast.LENGTH_LONG).show();
        }
    }
}