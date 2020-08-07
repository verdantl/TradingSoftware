package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;

public class TutorialActivity extends AppCompatActivity {
    private ItemManager itemManager;
    private Bundle bundle;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable("ItemManager");
        dialog = new Dialog(this);
        setContentView(R.layout.activity_tutorial);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        bundle.remove("ItemManager");
        intent.putExtras(bundle);
        intent.putExtra("ItemManager", itemManager);
        startActivity(intent);
    }

    public void onBrowseItems(View view){
        Intent intent = new Intent(this, TutorialBrowseItemsActivity.class);
        intent.putExtra("ItemManager",itemManager);
        startActivity(intent);
    }

    public void onBrowseTrades(View view){
        String info = "Using an account, you could to view your on-going trades, " +
                "agree to a trade meeting and edit a trade meeting.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onEditInventory(View view){
        String info = "Using an account, you could view items in your inventory and its " +
                "information. You could remove items from your inventory. You could add items " +
                "to your inventory that would show for trading. This items need to be" +
                " requested and reviewed by an admin";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onEditWishList(View view){
        String info = "Using an account, you could view items in your wish list and its " +
                "information. You could remove items from your wish list.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onAutoTrade(View view){
        String info = "Using an account, you could view items to trade for automatically.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onViewInfo(View view){
        String info = "Using an account, you could view your account information such as" +
                " username, top traders, and recent trades.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onRequestUnfreeze(View view){
        String info = "Using an account, you could request to unfreeze your account if" +
                " your account gets flagged and frozen by an admin.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }

    public void onChangePassword(View view){
        String info = "Using an account, you could change your password.";
        dialog.setContentView(R.layout.fragment_info);
        TextView close = dialog.findViewById(R.id.trader_info);
        close.setText(info);
        dialog.show();
    }
}