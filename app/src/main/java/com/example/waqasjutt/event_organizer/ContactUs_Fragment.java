package com.example.waqasjutt.event_organizer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactUs_Fragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.ed_Name)
    EditText ed_Name;
    //    @Bind(R.id.ed_Email)
//    EditText ed_Email;
    @Bind(R.id.ed_Subject)
    EditText ed_Subject;
    @Bind(R.id.ed_Message)
    EditText ed_Message;

    private static Animation shakeAnimation;

    private LinearLayout ContactUs_linear_layout;
    private Button btnSend;
    private TextInputLayout TextInputLayout_Name, TextInputLayout_Email, TextInputLayout_Message, TextInputLayout_Subject;
    private View view, Line_Name, Line_Email, Line_Subject, Line_Message;

    public ContactUs_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.contact_us_fragment, container, false);

        ButterKnife.bind(getActivity());
        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Contact Us");

        initComponents();
        setListner();
        return view;
    }

    private void initComponents() {
        ed_Name = (EditText) view.findViewById(R.id.ed_Name);
//        ed_Email = (EditText) view.findViewById(R.id.ed_Email);
        ed_Subject = (EditText) view.findViewById(R.id.ed_Subject);
        ed_Message = (EditText) view.findViewById(R.id.ed_Message);
        btnSend = (Button) view.findViewById(R.id.btnSend);

        ContactUs_linear_layout = (LinearLayout) view.findViewById(R.id.ContactUs_linear_layout);
        TextInputLayout_Name = (TextInputLayout) view.findViewById(R.id.TextInputLayout_Name);
//        TextInputLayout_Email = (TextInputLayout) view.findViewById(R.id.TextInputLayout_Email);
        TextInputLayout_Message = (TextInputLayout) view.findViewById(R.id.TextInputLayout_Subject);
        TextInputLayout_Subject = (TextInputLayout) view.findViewById(R.id.TextInputLayout_Message);

        Line_Name = (View) view.findViewById(R.id.Line_Name);
//        Line_Email = (View) view.findViewById(R.id.Line_Email);
        Line_Subject = (View) view.findViewById(R.id.Line_Subject);
        Line_Message = (View) view.findViewById(R.id.Line_Message);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
    }

    private void setListner() {
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                CheckValidation();
                break;
        }
    }

    private void CheckValidation() {
        String getName = ed_Name.getText().toString();
        String getEmail = "hr@EventOrganizer.com";
        String getSubject = ed_Subject.getText().toString();
        String getMessage = ed_Message.getText().toString();

        // Check for both field is empty or not
        if ((getName.isEmpty() || getName.equals("") || getName.length() == 0)
                && (getSubject.isEmpty() || getSubject.equals("") || getSubject.length() == 0)
                && (getMessage.isEmpty() || getMessage.equals("") || getMessage.length() == 0)) {
            ContactUs_linear_layout.startAnimation(shakeAnimation);

            new CustomToast().Show_Toast(getActivity(), view, "Required fields are missing.");
            TastyToast.makeText(getActivity(), "Try again.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }

        // Check Name
        if (getName.isEmpty()) {
            TextInputLayout_Name.startAnimation(shakeAnimation);
            ed_Name.startAnimation(shakeAnimation);
            Line_Name.startAnimation(shakeAnimation);
            ed_Name.setError("Enter your name.");
        } else if (getName.length() >= 20) {
            TextInputLayout_Name.startAnimation(shakeAnimation);
            ed_Name.startAnimation(shakeAnimation);
            Line_Name.startAnimation(shakeAnimation);
            ed_Name.setError("Only 20 characters allowed.");
        } else {
            ed_Name.setError(null);
        }

        // Check Subject
        if (getSubject.isEmpty()) {
            TextInputLayout_Subject.startAnimation(shakeAnimation);
            ed_Subject.startAnimation(shakeAnimation);
            Line_Subject.startAnimation(shakeAnimation);
            ed_Subject.setError("Enter Subject.");
        } else if (getSubject.length() >= 40) {
            TextInputLayout_Subject.startAnimation(shakeAnimation);
            ed_Subject.startAnimation(shakeAnimation);
            Line_Subject.startAnimation(shakeAnimation);
            ed_Subject.setError("Only 40 characters allowed.");
        } else {
            ed_Subject.setError(null);
        }

        // Check Message
        if (getMessage.isEmpty()) {
            TextInputLayout_Message.startAnimation(shakeAnimation);
            ed_Message.startAnimation(shakeAnimation);
            Line_Message.startAnimation(shakeAnimation);
            ed_Message.setError("Write your message.");
        } else {
            ed_Message.setError(null);
        }

        //Send message
        if ((!getName.isEmpty() && getName.length() <= 20)
                && (!getSubject.isEmpty() && getSubject.length() <= 40)
                && (!getMessage.isEmpty())) {
//            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");

            email.putExtra(Intent.EXTRA_EMAIL, new String[]{getEmail});
            email.putExtra(Intent.EXTRA_SUBJECT, getSubject);
            email.putExtra(Intent.EXTRA_TEXT, getName + ":\n               " + getMessage);

            //This will show prompts of email intent
            startActivity(Intent.createChooser(email, "Select Action"));
        }
    }
}