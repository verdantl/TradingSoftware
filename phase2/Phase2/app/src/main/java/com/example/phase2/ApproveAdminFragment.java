package com.example.phase2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phase2.phase2.AdminActions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproveAdminFragment} factory method to
 * create an instance of this fragment.
 */
public class ApproveAdminFragment extends Fragment {

    private ArrayList<String> adminRequests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            adminRequests = getArguments().getStringArrayList("Admin Requests");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_approve_admin, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}