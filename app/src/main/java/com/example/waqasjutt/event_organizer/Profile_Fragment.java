package com.example.waqasjutt.event_organizer;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Profile_Fragment extends Fragment {

    private View view;
    private TextView profileName;
    private TextView name;
    private TextView email;
    private TextView cnic;
    private TextView mobile;
    private TextView dob;
    private TextView gender;
    private TextView interest;
    private TextView about;
    private TextView domain;
    private TextView telephone;
    private TextView address;
    //    String leftBraket = "[";
    String strBrakets;

    private FragmentManager fragmentManager;

    public Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        profileName = (TextView) view.findViewById(R.id.tvProfileName);
        name = (TextView) view.findViewById(R.id.tvFirstName);
        email = (TextView) view.findViewById(R.id.tvEmail);
        mobile = (TextView) view.findViewById(R.id.tvMobileNo);
        cnic = (TextView) view.findViewById(R.id.tvCNIC);
        dob = (TextView) view.findViewById(R.id.tvDOB);
        domain = (TextView) view.findViewById(R.id.tvDomain);
        gender = (TextView) view.findViewById(R.id.tvGender);
        interest = (TextView) view.findViewById(R.id.tvInterest);
        about = (TextView) view.findViewById(R.id.tvAbout);
        telephone = (TextView) view.findViewById(R.id.tvTelephone);
        address = (TextView) view.findViewById(R.id.tvAddress);

        profileName.setText(SharedPrefManager.getInstance(getActivity()).getUserFirstName() +
                " " + SharedPrefManager.getInstance(getActivity()).getUserLastName());
        name.setText(SharedPrefManager.getInstance(getActivity()).getUserFirstName() +
                " " + SharedPrefManager.getInstance(getActivity()).getUserLastName());
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        mobile.setText("+92" + SharedPrefManager.getInstance(getActivity()).getUserMobile());
        gender.setText(SharedPrefManager.getInstance(getActivity()).getUserGender());
//        interest.setText(SharedPrefManager.getInstance(getActivity()).getUserInterest());

        if (SharedPrefManager.getInstance(getActivity()).getUserInterest().contains("[")) {
            strBrakets = SharedPrefManager.getInstance(getActivity()).getUserInterest().replace("[", "");
            if (strBrakets.contains("]")) {
                interest.setText(strBrakets.replace("]", ""));
            }
        }
        address.setText(SharedPrefManager.getInstance(getActivity()).getUserAddress());
        dob.setText(SharedPrefManager.getInstance(getActivity()).getUserDOB());
        domain.setText(SharedPrefManager.getInstance(getActivity()).getUserDomain());
        if (SharedPrefManager.getInstance(getActivity()).getUserAbout().isEmpty()) {
            about.setText("Tell about your self");
            about.setTextColor(getResources().getColor(R.color.gray));
        }
        if (!SharedPrefManager.getInstance(getActivity()).getUserAbout().isEmpty()) {
            about.setText(SharedPrefManager.getInstance(getActivity()).getUserAbout());
        }
        telephone.setText(SharedPrefManager.getInstance(getActivity()).getUserTelephone());
        cnic.setText(SharedPrefManager.getInstance(getActivity()).getUserCNIC());

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle(" Profile");

        return view;
    }
}