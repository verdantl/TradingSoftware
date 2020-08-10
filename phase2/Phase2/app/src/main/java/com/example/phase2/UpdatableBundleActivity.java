package com.example.phase2;

import android.content.Intent;

import androidx.annotation.Nullable;

public abstract class UpdatableBundleActivity extends BundleActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUseCases();
    }

    protected abstract void updateUseCases();
}
