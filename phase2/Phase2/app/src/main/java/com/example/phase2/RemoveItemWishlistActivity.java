package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

/**
 * An activity class responsible for removing items in a wishlist in the Trading System.
 */
public class RemoveItemWishlistActivity extends BundleActivity {
    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private int chosenItem;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenItem = (int) getUseCase("ChosenItem");
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        setContentView(R.layout.activity_remove_item_wishlist);
        setValues();
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        replaceUseCase(itemManager);
        replaceUseCase(traderManager);
        Intent intent = new Intent(this, EditWishlistActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Sets the values to be displayed for an item's information
     */
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

    /**
     * This method is called when the user clicks on the Remove Item button. It removes the item
     * from their wishlist and tells the user it is removed.
     * @param view A view
     */
    public void removeItemWishlist(View view){
        traderManager.removeFromWishlist(currentTrader, chosenItem);
        Toast.makeText(this, "Successfully removed the item from wishlist.",
                Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}