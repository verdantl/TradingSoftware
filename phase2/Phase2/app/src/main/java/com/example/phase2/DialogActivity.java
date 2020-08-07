package com.example.phase2;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class DialogActivity extends BundleActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
    }
}
