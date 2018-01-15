package com.example.waqasjutt.event_organizer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutUs_Fragment extends Fragment {

    private View view;
    private TextView H1, H2, H3, H4, SH1, SH2, SH3, SH4, tv_CopyRights;

    public AboutUs_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.about_us_fragment, container, false);

        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("About Us");

        initComponents();

        return view;
    }

    private void initComponents() {
        H1 = (TextView) view.findViewById(R.id.tv_H1);
        H2 = (TextView) view.findViewById(R.id.tv_H2);
        H3 = (TextView) view.findViewById(R.id.tv_H3);
        H4 = (TextView) view.findViewById(R.id.tv_H4);
        SH1 = (TextView) view.findViewById(R.id.tv_SH1);
        SH2 = (TextView) view.findViewById(R.id.tv_SH2);
        SH3 = (TextView) view.findViewById(R.id.tv_SH3);
        SH4 = (TextView) view.findViewById(R.id.tv_SH4);

        tv_CopyRights = (TextView) view.findViewById(R.id.tv_copyright);

        H1.setText("Event Organizer");
        SH1.setText("Event organizer is a full service events planning, coordination, consultation and management company. Its services range from event conceptualization, consultation through the actual orchestration of the affair, sourcing and canvassing of suppliers, guest management and all other details.");

        H2.setText("Mission");
        SH2.setText("Events Organizer endeavours to define an efficient plan of action that would achieve the goals and objectives set by its client in developing and producing successful events. They are committed to providing the best quality service in the most cost effective manner without compromising the company’s ideals and integrity.");

        H3.setText("Vision");
        SH3.setText("To be the top-of-mind events organizer in the country by providing excellent quality service and generating the most creative and innovative ideas, with commitment and unwavering dedication in an effort to provide its clients the finest personalized events they have aspired for.");

        H4.setText("Our expertise covers all the aspects of Events");
        SH4.setText("Planning, Management & Coordination and Execution. We deliver cost effective events without compromising on the final output."
                + "\n• To provide complete services."
                + "\n• To provide standard & quality."
                + "\n• To provide fantastic events."
                + "\n• To provide beautiful events."
                + "\n• Satisfaction of our clients."
                + "\n• We have a very flexible approach."
                + "\n• We save your time & money."
                + "\n• Depth and breadth of expertise."
                + "\n• We Provide Unrivalled customer service."
                + "\n• Working with specialists.");

        tv_CopyRights.setText("Copyright © 2018 • Event Organizer Event • All rights reserved");
    }
}