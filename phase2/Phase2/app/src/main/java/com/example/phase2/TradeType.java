package com.example.phase2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.List;
/* Using the radio button template from
https://medium.com/@suragch/adding-a-list-to-an-android-alertdialog-e13c1df6cf00*/

public class TradeType  extends AppCompatDialogFragment {
    private Bundle bundle;
    private Dialogable dialogable;
    final String[] ways = {"One way", "Two way"};
    final String[] status = {"Permanent", "Temporary"};
    private Boolean chosenWay;
    private Boolean chosenStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        chosenStatus = false;
        chosenWay = false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogable = (Dialogable) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Dialogable");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(ways, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chosenWay = i == 0;
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("way", chosenWay);
                        nestedDialog().show();
                    }
                });

        return builder.create();
    }

    public Dialog nestedDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(status, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chosenStatus = i == 1;
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("status", chosenStatus);
                        setArguments(bundle);
                        dialogable.clickPositive();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogable.clickNegative();
                    }
                });

        return builder.create();

    }


}
