package com.rohan.android.assignments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class AssignFragment extends Fragment {

    private RecyclerView AssignrecyclerView;
    private DataAdapter adapter;
    private AdView mAdView;
    private ProgressBar progressBar;
    private TextView mEmptyView;
    private DatabaseReference dbReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.assign_fragment, container, false);
        //Ad_Setup
        MobileAds.initialize(getActivity(), "ca-app-pub-4441201959431024~1191281378");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        progressBar = view.findViewById(R.id.loading_spinner);
        mEmptyView = view.findViewById(R.id.empty_state);
        progressBar.setVisibility(View.VISIBLE);


        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Connection successfull
            progressBar.setVisibility(View.GONE);

        } else {
            //Connection Failed !
            //Hide progress bar in order to display error message
            progressBar.setVisibility(View.GONE);

            //Update UI to show error message
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText(R.string.no_internet);
        }
        //Firebase_Setup
        dbReference = FirebaseDatabase.getInstance().getReference();
        AssignrecyclerView = view.findViewById(R.id.assignRV);
        AssignrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DataAdapter(getContext(), AssignrecyclerView, new ArrayList<String>(), new ArrayList<String>());
        //Load Data
        loadEntries();
        AssignrecyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadEntries() {

        dbReference.child("Assign").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String fileName = dataSnapshot.getKey();
                int onePos = fileName.indexOf("1");
                int startIndex = 0;
                int endIndex = onePos;
                fileName = fileName.substring(startIndex, endIndex);
                String url = dataSnapshot.getValue(String.class);
                ((DataAdapter) AssignrecyclerView.getAdapter()).Customupdate(fileName, url);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
