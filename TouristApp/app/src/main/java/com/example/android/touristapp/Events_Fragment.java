package com.example.android.touristapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Events_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Context context;
    RecyclerView recyclerView;
    LocationAdapter locationAdapter;
    int[] images = new int[]{
            R.drawable.event1,
            R.drawable.event2,
            R.drawable.event3,
            R.drawable.event4,
    };
    private OnFragmentInteractionListener mListener;

    public Events_Fragment() {
        // Required empty public constructor
    }


    public static Events_Fragment newInstance(String param1, String param2) {
        Events_Fragment fragment = new Events_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_, container, false);
        ArrayList<Location> dataList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.event_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Location a = new Location(images[0], getString(R.string.event1));
        dataList.add(a);
        a = new Location(images[1], getString(R.string.event2));
        dataList.add(a);
        a = new Location(images[2], getString(R.string.event3));
        dataList.add(a);
        a = new Location(images[3], getString(R.string.event4));
        dataList.add(a);
        recyclerView.setAdapter(new LocationAdapter(getContext(), dataList));

        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
