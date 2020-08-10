package com.example.phase2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.phase2.AdminActions;
import com.example.phase2.phase2.ItemManager;
import com.example.phase2.phase2.MeetingManager;
import com.example.phase2.phase2.TradeManager;
import com.example.phase2.phase2.TraderManager;
import com.example.phase2.phase2.TraderPrompts;

import java.util.ArrayList;
import java.util.List;

public class TraderActivity extends BundleActivity {
    private String currentTrader;

    private final int REQ_ADMIN_REQ = 7;
    private final int CHANGE_PASSWORD_REQ = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentTrader = getUsername();
        setContentView(R.layout.activity_trader);
        TextView textView = findViewById(R.id.textView15);
        textView.setText(currentTrader);
    }

    public void browseAvailableItems(View view){
        Intent intent = new Intent(this, BrowseItemsActivity.class);
        putBundle(intent);
        startActivity(intent);
    }

    public void browseOnGoingTrades(View view){
        Intent intent = new Intent(this,BrowseTradesActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Edit Inventory button. It starts
     * the EditInventoryActivity
     * @param view A view
     */
    public void editInventory(View view){
        Intent intent = new Intent(this, EditInventoryActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * This method is called when the user clicks on the Edit Wishlist button. It starts
     * the EditWishlistActivity
     * @param view A view
     */
    public void editWishlist(View view){
        Intent intent = new Intent(this, EditWishlistActivity.class);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

//    /**
//     * sends a request to unfreeze the user and gives a responds based on the current state of the user
//     * @param view a standard  viable for andoird methods
//     */
//    public void requestToUnfreeze(View view){
//        if(traderManager.getIsFrozen(currentTrader)) {
//            if(traderManager.getRequestToUnfreeze(currentTrader)){
//                Toast.makeText(this, R.string.Trader_request_to_unfreeze_already_sent, Toast.LENGTH_LONG).show();
//            }
//            else{
//                Toast.makeText(this, R.string.Trader_request_to_unfreeze_sent, Toast.LENGTH_LONG).show();
//                traderManager.setRequestToUnfreeze(currentTrader, true);
//            }
//        }
//        else{
//            Toast.makeText(this, R.string.Trader_request_to_unfreeze_not_frozen, Toast.LENGTH_LONG).show();
//        }
//    }

    /**
     * This method is called when the user presses the View User Info button. Starts the Request
     * ViewMyUserInfoActivity
     * @param view A view
     */
    public void viewUserInfo(View view){
        Intent intent = new Intent(this, ViewMyUserInfoActivity.class);
        putBundle(intent);
        //????
        startActivity(intent);
    }

    /**
     * This method is called when the user presses the Request Admin button. Starts the Request
     * AdminActivity
     * @param view A view
     */
    public void requestAdmin(View view){
        Intent intent =  new Intent(this, RequestAdminActivity.class);
        putBundle(intent);
        startActivityForResult(intent, REQ_ADMIN_REQ);
    }

    /**
     * This method is called when the user presses the Change Password button. Starts the Change
     * TraderPassword Activity
     * @param view
     */
    public void changeTraderPassword(View view){
        Intent i =  new Intent(this, ChangeTraderPassword.class);
        putBundle(i);
        startActivityForResult(i, CHANGE_PASSWORD_REQ);
    }

    /**
     * This method is called when the user presses the Change Homecity button. Starts the
     * ChangeHomecity Activity
     * @param view A view
     */
    public void changeHomecity(View view){
        Intent i =  new Intent(this, ChangeHomecity.class);
        putBundle(i);
        startActivityForResult(i, CHANGE_PASSWORD_REQ);
    }

    /**
     * This method is called when the user clicks the loggout button. It lets the user to loggout
     * from the system. It also updates the use case classes.
     * @param view
     */
    public void onLogoutClicked(View view) {
        super.onBackPressed();
        finish();
    }



    /**
     * This is called when the user presses the back button. It forbids user from going back to
     * the previous activity and displays a toast that notifies the user that you cannot go back.
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(this,
                "You have reached the main menu!", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        assert data != null;
//        if(requestCode == CHANGE_PASSWORD_REQ){
//            traderManager = (TraderManager) data.getSerializableExtra(TRADERKEY);
//            bundle.remove(TRADERKEY);
//            bundle.putSerializable(TRADERKEY, traderManager);
//        }else if(requestCode == REQ_ADMIN_REQ){
//            traderManager = (TraderManager) data.getSerializableExtra(TRADERKEY);
//            itemManager = (ItemManager) data.getSerializableExtra(ITEMKEY);
//            bundle.remove(TRADERKEY);
//            bundle.remove(ITEMKEY);
//            bundle.putSerializable(TRADERKEY, traderManager);
//            bundle.putSerializable(ITEMKEY, itemManager);
//        }
//
//    }
}