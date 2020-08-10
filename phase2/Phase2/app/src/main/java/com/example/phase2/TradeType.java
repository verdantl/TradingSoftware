package com.example.phase2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private AlertDialog nestedDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = new Bundle();
        chosenStatus = false;
        chosenWay = false;
        nestedDialog = (AlertDialog) nestedDialog();
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
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(ways, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chosenWay = i == 0;
                        AlertDialog dialog = (AlertDialog) getDialog();
                        assert dialog != null;
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putBoolean("way", chosenWay);
                        dismiss();
                        nestedDialog.show();
                        nestedDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                });

            return builder.create();


    }

    public Dialog nestedDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("TradeType")
                .setSingleChoiceItems(status, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chosenStatus = i == 1;
                        nestedDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
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

    @Override
    public void onResume()
    {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        assert dialog != null;
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }


}
