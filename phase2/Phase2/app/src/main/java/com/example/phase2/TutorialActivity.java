package com.example.phase2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.phase2.phase2.ItemManager;

public class TutorialActivity extends AppCompatActivity {
    private ItemManager itemManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        itemManager = (ItemManager) bundle.getSerializable(getString(R.string.ITEMKEY));
        setContentView(R.layout.activity_tutorial);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onBrowseItems(View view){
        Intent intent = new Intent(this, TutorialBrowseItemsActivity.class);
        intent.putExtra("ItemManager",itemManager);
        startActivity(intent);
    }

    public void onBrowseTrades(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view or modify your trade  \n ")
                .append("- You could see all your trades' information  \n")
                .append("-You could only edit your trade 3 times before the conformation  \n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BrowseTradePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    public void onEditInventory(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to edit your inventory  \n ")
                .append("- You could view your items' information \n")
                .append("-You could remove items from your inventory \n")
                .append("-You could add items to your inventory \n")
                .append("-items need to be reviewed by an admin");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("InventoryPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    public void onEditWishList(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to edit your wishList   \n")
                .append("- You could view items in your wish list  \n")
                .append("-You could remove items from your wish list");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WishListPreview")
                .setMessage(stringBuilder);
        builder.show();

    }

    public void onAutoTrade(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view items " +
                "to trade for automatically ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AutoTradePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    public void onViewInfo(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view your information \n")
                .append("- You could check your recent trades  \n")
                .append("-You could check traders you frequently have trades with");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ViewInfoPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    public void onRequestUnfreeze(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to request to unfreeze your account \n")
                .append("- Only do it when you account gets flagged or frozen");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UnfreezePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    public void onChangePassword(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to change your password");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(stringBuilder);
        builder.show();
    }
}