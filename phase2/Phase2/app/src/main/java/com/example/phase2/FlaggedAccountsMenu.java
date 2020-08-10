package com.example.phase2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.TraderManager;

import java.util.List;

public class FlaggedAccountsMenu extends BundleActivity implements ClickableList, Dialogable{
    private TraderManager traderManager;
    private ItemManager itemManager;
    private String frozenTrader;

    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flagged_accounts_menu);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        viewList();
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {
        replaceUseCase(traderManager);
        super.onBackPressed();
    }


    public void viewList() {
        final List<String> allFlaggedTraders = traderManager.getListOfFlagged();
        ListView listView = findViewById(R.id.flagged);
        ArrayAdapter<String> allTraderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allFlaggedTraders);
        listView.setAdapter(allTraderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDialog();
                frozenTrader = allFlaggedTraders.get(i);
            }
        });
    }


    @Override
    public void clickPositive() {
        if (traderManager.freezeAccount(frozenTrader)) {
            traderManager.setTraderInactive(frozenTrader, false);
            itemManager.setStatusForFrozenUser(frozenTrader);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Fail: the account is already frozen", Toast.LENGTH_SHORT).show();
        }
        viewList();

    }

    @Override
    public void clickNegative() {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        viewList();

    }

    @Override
    public void openDialog() {
        DialogFactory dialogFactory = new DialogFactory();
        dialogFactory.getDialog("Freeze")
                .show(getSupportFragmentManager(), "FreezeTrader");
    }
}

