package com.example.waqasjutt.event_organizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactUs_Fragment extends Fragment {

    private View view;

    public ContactUs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.contact_us_fragment, container, false);

        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Contact Us");

        return view;
    }
}