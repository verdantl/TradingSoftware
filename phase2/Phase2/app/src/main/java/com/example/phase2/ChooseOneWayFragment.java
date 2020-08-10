package com.example.phase2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
Code extracted from https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
 */

public class ChooseOneWayFragment extends DialogFragment {

    public interface ChooseOneWayListener {
        void onDialogOneWayClick(DialogFragment dialog);
        void onDialogTwoWayClick(DialogFragment dialog);
    }

    ChooseOneWayListener listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ChooseOneWayListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("This activity must implement ChooseOneWayListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.tradeOption)
                .setPositiveButton(R.string.oneWay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogOneWayClick(ChooseOneWayFragment.this);
                    }
                })
                .setNegativeButton(R.string.twoWay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogTwoWayClick(ChooseOneWayFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}