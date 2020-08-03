package com.example.phase2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TradeManager;

public class TwoItemActivity extends AppCompatActivity {
    TradeManager tradeManager;
    Integer trade;
    ItemManager itemManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        //meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        //traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        //currentTrader = (String) bundle.getSerializable("CurrentTrader");
        trade = (Integer) bundle.getSerializable("Trade");
        setContentView(R.layout.activity_two_item);

        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=findViewById(R.id.itemName2);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = findViewById(R.id.descriptionText2);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = findViewById(R.id.itemRating2);
        itemRating.setText(tempItemRating);


        String tempItemName2 = itemManager.getItemName(tradeManager.getItems(trade).get(1));
        TextView itemName2=findViewById(R.id.secondItem);
        itemName2.setText(tempItemName2);

        String tempItemDescription2 = itemManager.getItemDescription(tradeManager.getItems(trade).get(1));
        TextView itemDescription2 = findViewById(R.id.descriptionText3);
        itemDescription2.setText(tempItemDescription2);

        String tempItemRating2 = itemManager.getItemQuality(tradeManager.getItems(trade).get(1));
        TextView itemRating2 = findViewById(R.id.itemRating3);
        itemRating2.setText(tempItemRating2);
    }

}