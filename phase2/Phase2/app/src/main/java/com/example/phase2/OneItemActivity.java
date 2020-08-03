package com.example.phase2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TradeManager;

public class OneItemActivity extends AppCompatActivity {
    TradeManager tradeManager;
    Integer trade;
    ItemManager itemManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getIntent().getExtras();
        assert bundle != null;
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        //meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        //traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        //currentTrader = (String) bundle.getSerializable("CurrentTrader");
        trade = (Integer) bundle.getSerializable("Trade");
        setContentView(R.layout.activity_one_item);
        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=findViewById(R.id.itemName);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Item Quality Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(0));
        TextView itemOwner = findViewById(R.id.ownedBy);
        itemOwner.setText(tempOwner);
    }

}