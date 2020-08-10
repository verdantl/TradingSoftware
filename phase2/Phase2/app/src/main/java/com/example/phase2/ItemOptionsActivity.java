package com.example.phase2;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

import java.time.LocalDate;
import java.util.Objects;

public class ItemOptionsActivity extends BundleActivity {

    private ItemManager itemManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer chosenItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chosenItem = Objects.requireNonNull(getIntent().getExtras()).getInt("ChosenItem");
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = getUsername();
        viewStart();
    }

    public void viewStart() {
        setContentView(R.layout.activity_item_options);

        String tempItemName = itemManager.getItemName(chosenItem);
        TextView itemName=findViewById(R.id.itemName);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(chosenItem);
        TextView itemDescription = findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Item Quality Rating: " + itemManager.getItemQuality(chosenItem);
        TextView itemRating = findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(chosenItem);
        TextView itemOwner = findViewById(R.id.ownedBy);
        itemOwner.setText(tempOwner);
    }

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
        if (canProposeTrade()) {
            displayTradeOptions(view);
        } else if (traderManager.isInactive(currentTrader)) {
            Toast.makeText(this, "Your account is inactive," +
                    " you cannot trade.", Toast.LENGTH_LONG).show();
        } else if (traderManager.exceedWeeklyLimit(currentTrader, LocalDate.now())) {
            Toast.makeText(this, "You have exceeded your " +
                    "weekly trade limit", Toast.LENGTH_SHORT).show();
        } else if (traderManager.exceedMaxIncomplete(currentTrader)) {
            Toast.makeText(this, "You have exceeded your " +
                    "maximum number of incomplete trades. The max is " +
                    traderManager.getWeeklyLimit(), Toast.LENGTH_SHORT).show();
        } else if (traderManager.needMoreLend(currentTrader)) {
            Toast.makeText(this, "You need to lend more " +
                    "items before you may initiate a new trade.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Your account is frozen," +
                    " you cannot trade.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper method for proposeTrade which checks if the currentTrader is allowed to intiate a new
     * trade.
     * @return true iff currentTrader can propose a new trade.
     */
    private boolean canProposeTrade() {
        return !traderManager.getIsFrozen(currentTrader) &&
                !traderManager.isInactive(currentTrader) &&
                !traderManager.exceedWeeklyLimit(currentTrader, LocalDate.now()) &&
                !traderManager.exceedMaxIncomplete(currentTrader) &&
                !traderManager.needMoreLend(currentTrader);
    }

    public void displayTradeOptions(View view) {
        Intent intent = new Intent(this, DisplayTradeOptionsActivity.class);
        intent.putExtra("ChosenItem", chosenItem);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }
}