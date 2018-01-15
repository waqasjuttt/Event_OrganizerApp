package com.example.waqasjutt.event_organizer;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
    private String strBrakets;
    private Button btnEditProfile;
    private ProgressDialog progressDialog;

    private FragmentTransaction fragmentTransaction;
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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Profile updating, Please wait...");

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
        btnEditProfile = (Button) view.findViewById(R.id.btn_EditProfile);

        profileName.setText(SharedPrefManager.getInstance(getActivity()).getUserFirstName() +
                " " + SharedPrefManager.getInstance(getActivity()).getUserLastName());
        name.setText(SharedPrefManager.getInstance(getActivity()).getUserFirstName() +
                " " + SharedPrefManager.getInstance(getActivity()).getUserLastName());
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        mobile.setText("+92" + SharedPrefManager.getInstance(getActivity()).getUserMobile());
        gender.setText(SharedPrefManager.getInstance(getActivity()).getUserGender());

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
        ((MainActivity) getActivity()).setActionBarTitle("Profile");

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameContainer,
                                        new EditProfile_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}