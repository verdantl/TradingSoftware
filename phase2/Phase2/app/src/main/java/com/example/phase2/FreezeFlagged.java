package com.example.phase2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreezeFlagged#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreezeFlagged extends Fragment {

    

    public FreezeFlagged() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_freeze_flagged, container, false);
    }
}