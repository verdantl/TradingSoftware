package com.example.phase2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.phase2.AdminActions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproveAdminFragment} factory method to
 * create an instance of this fragment.
 */
public class ApproveAdminFragment extends Fragment {

    private ListView listView;
    private ArrayList<String> adminRequests;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            adminRequests = getArguments().getStringArrayList("Admin Requests");
        }
        listView = getView().findViewById(R.id.requested_admins);
        ArrayAdapter<String> adminAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, adminRequests);
        listView.setAdapter(adminAdapter);

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