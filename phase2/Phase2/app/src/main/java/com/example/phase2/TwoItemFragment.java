package com.example.phase2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TradeManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwoItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoItemFragment extends Fragment {
    TradeManager tradeManager;
    Integer trade;
    ItemManager itemManager;
    public TwoItemFragment() {
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
        View originalView = inflater.inflate(R.layout.fragment_two_item, container, false);

        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=originalView.findViewById(R.id.itemName2);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = originalView.findViewById(R.id.descriptionText2);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = originalView.findViewById(R.id.itemRating2);
        itemRating.setText(tempItemRating);


        String tempItemName2 = itemManager.getItemName(tradeManager.getItems(trade).get(1));
        TextView itemName2=originalView.findViewById(R.id.secondItem);
        itemName2.setText(tempItemName2);

        String tempItemDescription2 = itemManager.getItemDescription(tradeManager.getItems(trade).get(1));
        TextView itemDescription2 = originalView.findViewById(R.id.descriptionText3);
        itemDescription2.setText(tempItemDescription2);

        String tempItemRating2 = itemManager.getItemQuality(tradeManager.getItems(trade).get(1));
        TextView itemRating2 = originalView.findViewById(R.id.itemRating3);
        itemRating2.setText(tempItemRating2);

        return inflater.inflate(R.layout.fragment_one_item, container, false);
    }
}