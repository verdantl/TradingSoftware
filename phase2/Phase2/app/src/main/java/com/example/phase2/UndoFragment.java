package com.example.phase2;

import android.app.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class UndoFragment extends Fragment{
    private UndoClick undoClick;

    public UndoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_undo, container, false);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            undoClick = (UndoClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UndoClick");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button undoButton = requireActivity().findViewById(R.id.undoButton);
        Button undoCancel = requireActivity().findViewById(R.id.undoCancel);
        undoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                undoClick.onUndoClick(view);
            }
        });

        undoCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                undoClick.onCancelClick(view);
            }
        });
    }

    public interface UndoClick{

        void onUndoClick(View view);

        void onCancelClick(View view);
    }

}