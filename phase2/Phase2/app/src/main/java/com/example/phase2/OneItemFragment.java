package com.example.phase2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;

import java.util.Objects;

public class OneItemFragment extends Fragment {
    TradeManager tradeManager;
    Integer trade;
    ItemManager itemManager;
    public OneItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= this.getArguments();
        assert bundle != null;
        tradeManager = (TradeManager) bundle.getSerializable("TradeManager");
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        //meetingManager = (MeetingManager) bundle.getSerializable("MeetingManager");
        //traderManager = (TraderManager) bundle.getSerializable("TraderManager");
        //currentTrader = (String) bundle.getSerializable("CurrentTrader");
        trade = (Integer) bundle.getSerializable("Trade");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View originalView = inflater.inflate(R.layout.fragment_one_item, container, false);

        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=originalView.findViewById(R.id.tradeType);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = originalView.findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = originalView.findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        return inflater.inflate(R.layout.fragment_one_item, container, false);

    }
}