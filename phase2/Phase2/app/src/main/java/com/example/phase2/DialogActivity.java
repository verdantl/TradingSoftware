package com.example.phase2;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class DialogActivity extends BundleActivity {
    protected Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
    }

    public void displayDialog(Integer viewID){
        dialog.setContentView(viewID);
        dialog.show();
    }
}
