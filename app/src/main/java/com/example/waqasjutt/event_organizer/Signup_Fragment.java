package com.example.waqasjutt.event_organizer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Signup_Fragment extends Fragment implements View.OnClickListener {

    //For DOB
    private DatePickerFragment datePickerFragment;
    private static Calendar dateTime = Calendar.getInstance();
    protected static int mYear;
    protected static int mMonth;
    protected static int mDay;
    private static final int allowedDOB = 18;
    protected static Button btnDOB;
    protected static TextView tvDOB;
////////////////////////////////////////////////////////////

    private View view;

    @Bind(R.id.et_Fname)
    EditText et_Fname;
    @Bind(R.id.et_Lname)
    EditText et_Lname;
    @Bind(R.id.et_Email)
    EditText et_Email;
    @Bind(R.id.et_mobileNumber)
    EditText et_mobileNumber;
    @Bind(R.id.et_CNIC)
    EditText et_CNIC;
    //    @Bind(R.id.et_dob)
//    EditText et_dob;
//    @Bind(R.id.et_dd)
//    EditText et_dd;
//    @Bind(R.id.et_mm)
//    EditText et_mm;
//    @Bind(R.id.et_yyyy)
    EditText et_yyyy;
    @Bind(R.id.et_Telephone)
    EditText et_Telephone;
    private RadioGroup radioGroup;
    private RadioButton male, female;
    private CheckBox checkBox_cricket, checkBox_collecting, checkBox_reading;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_ConfirmPassword)
    EditText et_ConfirmPassword;
    @Bind(R.id.et_Address)
    EditText et_Address;
    @Bind(R.id.et_About)
    EditText et_About;

    MainActivity mainActivity = new MainActivity();

    protected static LinearLayout DOB_LinearLayout;
    private TextInputLayout DOB_layout, DD_layout, MM_layout, YYYY_layout, Email_layout, FirstName_layout, LastName_layout, CNIC_layout, Mobile_layout, Telephone_layout, Password_layout, ConfirmPasswoed_layout, Address_layout, About_layout;
    private View Line_Email, Line_FirstName, Line_LastName, Line_Mobile, Line_CNIC, Line_Telephone, Line_Password, Line_ConfirmPassword, Line_Address, Line_About, Line_Gender;
    protected static View Line_DOB;

    private Set<String> CheckBoxValues;
    private ProgressDialog progressDialog;
    private TextView already_user;
    private Button btnSignup;
    private CheckBox terms_conditions;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LinearLayout Signup_Layout;
    protected static Animation shakeAnimation;
    private String strGender;
    private int countMobileNumber = 1, countTelephoneNumber = 1, countCNIC = 1;

    public Signup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        ButterKnife.bind(getActivity());

        //For Go Back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle(" Signup");

        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        Signup_Layout = (LinearLayout) view.findViewById(R.id.Signup_layout);
        progressDialog = new ProgressDialog(getActivity());
        et_Fname = (EditText) view.findViewById(R.id.et_Fname);
        et_Lname = (EditText) view.findViewById(R.id.et_Lname);
        et_Email = (EditText) view.findViewById(R.id.et_Email);
        et_CNIC = (EditText) view.findViewById(R.id.et_CNIC);
//        et_dob = (EditText) view.findViewById(R.id.et_dob);
//        et_dd = (EditText) view.findViewById(R.id.et_dd);
//        et_mm = (EditText) view.findViewById(R.id.et_mm);
//        et_yyyy = (EditText) view.findViewById(R.id.et_yyyy);
        male = (RadioButton) view.findViewById(R.id.radioM);
        female = (RadioButton) view.findViewById(R.id.radioF);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGrp);
        checkBox_cricket = (CheckBox) view.findViewById(R.id.cb_cricket);
        checkBox_collecting = (CheckBox) view.findViewById(R.id.cb_Collecting);
        checkBox_reading = (CheckBox) view.findViewById(R.id.cb_Reading);
        et_mobileNumber = (EditText) view.findViewById(R.id.et_mobileNumber);
        et_Telephone = (EditText) view.findViewById(R.id.et_Telephone);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_ConfirmPassword = (EditText) view.findViewById(R.id.et_ConfirmPassword);
        et_Address = (EditText) view.findViewById(R.id.et_Address);
        et_About = (EditText) view.findViewById(R.id.et_About);
        btnDOB = (Button) view.findViewById(R.id.btn_select_dob);
        tvDOB = (TextView) view.findViewById(R.id.tv_Show_Date);
        btnSignup = (Button) view.findViewById(R.id.signUpBtn);
        already_user = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        //Layout ids call
        FirstName_layout = (TextInputLayout) view.findViewById(R.id.FirstName_layout);
        LastName_layout = (TextInputLayout) view.findViewById(R.id.LastName_layout);
        Email_layout = (TextInputLayout) view.findViewById(R.id.Email_layout);
        Mobile_layout = (TextInputLayout) view.findViewById(R.id.Mobile_layout);
        CNIC_layout = (TextInputLayout) view.findViewById(R.id.CNIC_layout);
//        DOB_layout = (TextInputLayout) view.findViewById(R.id.DOB_layout);
//        DOB_LinearLayout = (LinearLayout) view.findViewById(R.id.DOB_layout);
//        DD_layout = (TextInputLayout) getActivity().findViewById(R.id.DD_layout);
//        MM_layout = (TextInputLayout) getActivity().findViewById(R.id.MM_layout);
//        YYYY_layout = (TextInputLayout) getActivity().findViewById(R.id.YYYY_layout);
        DOB_LinearLayout = (LinearLayout) view.findViewById(R.id.DOB_Linearlylayout);
        Telephone_layout = (TextInputLayout) view.findViewById(R.id.Telephone_layout);
        Password_layout = (TextInputLayout) view.findViewById(R.id.Password_layout);
        ConfirmPasswoed_layout = (TextInputLayout) view.findViewById(R.id.ConfirPassword_layout);
        Address_layout = (TextInputLayout) view.findViewById(R.id.Address_layout);
        About_layout = (TextInputLayout) view.findViewById(R.id.About_layout);

        //View Lines call
        Line_FirstName = (View) view.findViewById(R.id.Line_Fname);
        Line_LastName = (View) view.findViewById(R.id.Line_Lname);
        Line_Email = (View) view.findViewById(R.id.Line_email);
        Line_Mobile = (View) view.findViewById(R.id.Line_mobile);
        Line_Telephone = (View) view.findViewById(R.id.Line_Telephone);
        Line_CNIC = (View) view.findViewById(R.id.Line_CNIC);
        Line_DOB = (View) view.findViewById(R.id.Line_DOB);
        Line_Gender = (View) view.findViewById(R.id.Line_Gender);
        Line_Password = (View) view.findViewById(R.id.Line_password);
        Line_ConfirmPassword = (View) view.findViewById(R.id.Line_ConfirmPassword);
        Line_Address = (View) view.findViewById(R.id.Line_Address);
        Line_About = (View) view.findViewById(R.id.Line_About);

        CheckBoxValues = new HashSet<>();

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.color.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            already_user.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        btnSignup.setOnClickListener(this);
        already_user.setOnClickListener(this);

        //For Date of Birth
        btnDOB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_DOB:
//                showDatePicker();
//                break;
            case R.id.signUpBtn:
                // Call checkValidation method
                checkValidation();
                break;

            case R.id.btn_select_dob:
                datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "Select Your Birthday");
                break;

            case R.id.already_user:
                // Replace already_user fragment
                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                .replace(R.id.frameContainer,
                                        new Login_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    // Check Validation Method
    private void checkValidation() {
        // Get all edittext texts
        final String getFirstName = et_Fname.getText().toString();
        final String getLastName = et_Lname.getText().toString();
        final String getEmailId = et_Email.getText().toString();
        final String getCNIC = et_CNIC.getText().toString();
//        final String getDOB = et_dob.getText().toString();
//        final String getDD = et_dd.getText().toString();
//        final String getMM = et_mm.getText().toString();
//        final String getYYYY = et_yyyy.getText().toString();
        final String getDOB = tvDOB.getText().toString();
        final String getMobileNumber = et_mobileNumber.getText().toString();
        final String getPassword = et_password.getText().toString();
        final String getConfirmPassword = et_ConfirmPassword.getText().toString();
        final String getTelephone = et_Telephone.getText().toString();
        final String getAbout = et_About.getText().toString();
        final String getAddress = et_Address.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.Email_Format);
        Matcher m = p.matcher(getEmailId);

        // Pattern match for DOB
//        Pattern DOB = Pattern.compile(Utils.DOB_Format2);
//        Matcher DOB_matcher = DOB.matcher(getDOB);

        // Pattern match for Uper Case Letter
        Pattern UperCaseLetter = Pattern.compile(Utils.UperCaseLetter);
        Matcher UperCaseLetter_matcher = UperCaseLetter.matcher(getPassword);

        // Check if all strings are null or not
        if ((getFirstName.isEmpty() || getFirstName.equals("") || getFirstName.length() == 0)
                && (getLastName.isEmpty() || getLastName.equals("") || getLastName.length() == 0)
                && (getEmailId.isEmpty() || getEmailId.equals("") || getEmailId.length() == 0)
                && (getMobileNumber.isEmpty() || getMobileNumber.equals("") || getMobileNumber.length() == 0)
                && (tvDOB.getText().toString().contains("Pick your date of birth"))
                && (getAddress.isEmpty() || getAddress.equals("") || getAddress.length() == 0)
                && (getCNIC.isEmpty() || getCNIC.equals("") || getCNIC.length() == 0)
                && (getPassword.isEmpty() || getPassword.equals("") || getPassword.length() == 0)
                && (getConfirmPassword.isEmpty() || getConfirmPassword.equals("") || getConfirmPassword.length() == 0)
                && (!male.isChecked() && !female.isChecked())) {
            Signup_Layout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Required fields are missing.");
            TastyToast.makeText(getActivity(), "Try again",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
            CheckBoxValues.clear();
        }
        // Gender is selected
        else if (!male.isChecked() && !female.isChecked()) {
            radioGroup.startAnimation(shakeAnimation);
            male.startAnimation(shakeAnimation);
            female.startAnimation(shakeAnimation);
            Line_Gender.startAnimation(shakeAnimation);

            Toast.makeText(getActivity(), "Gender not selected.", Toast.LENGTH_SHORT).show();
        }

        // Check if first name is valid or not
        if (getFirstName.isEmpty()) {
            FirstName_layout.startAnimation(shakeAnimation);
            et_Fname.startAnimation(shakeAnimation);
            Line_FirstName.startAnimation(shakeAnimation);
            et_Fname.setError("Enter your First Name.");
        } else if (getFirstName.length() >= 15) {
            FirstName_layout.startAnimation(shakeAnimation);
            et_Fname.startAnimation(shakeAnimation);
            Line_FirstName.startAnimation(shakeAnimation);
            et_Fname.setError("Only 15 characters allow.");
        } else {
            et_Fname.setError(null);
        }

        // Check if last name is valid or not
        if (getLastName.isEmpty()) {
            LastName_layout.startAnimation(shakeAnimation);
            et_Lname.startAnimation(shakeAnimation);
            Line_LastName.startAnimation(shakeAnimation);
            et_Lname.setError("Enter your Last Name.");
        } else if (getLastName.length() >= 15) {
            LastName_layout.startAnimation(shakeAnimation);
            et_Lname.startAnimation(shakeAnimation);
            Line_LastName.startAnimation(shakeAnimation);
            et_Lname.setError("Only 15 characters allow.");
        } else {
            et_Lname.setError(null);
        }

        // Check date of birth is valid or not
//        if (getDOB.isEmpty()) {
//            DOB_layout.startAnimation(shakeAnimation);
//            et_dob.startAnimation(shakeAnimation);
//            Line_DOB.startAnimation(shakeAnimation);
//            et_dob.setError("Enter your date of birth.");
//        } else if (!DOB_matcher.find()) {
//            DOB_layout.startAnimation(shakeAnimation);
//            et_dob.startAnimation(shakeAnimation);
//            Line_DOB.startAnimation(shakeAnimation);
//            et_dob.setError("Enter valid date.");
//            Toast.makeText(getActivity(), "Month and day start with 0 if digit below to 10", Toast.LENGTH_LONG).show();
//        } else if (DOB_matcher.toString().contains("2017")
//                || DOB_matcher.toString().contains("2016")
//                || DOB_matcher.toString().contains("2015")
//                || DOB_matcher.toString().contains("2014")
//                || DOB_matcher.toString().contains("2013")
//                || DOB_matcher.toString().contains("2012")
//                || DOB_matcher.toString().contains("2011")
//                || DOB_matcher.toString().contains("2010")
//                || DOB_matcher.toString().contains("2009")
//                || DOB_matcher.toString().contains("2008")
//                || DOB_matcher.toString().contains("2007")
//                || DOB_matcher.toString().contains("2006")
//                || DOB_matcher.toString().contains("2005")
//                || DOB_matcher.toString().contains("2004")
//                || DOB_matcher.toString().contains("2003")
//                || DOB_matcher.toString().contains("2002")
//                || DOB_matcher.toString().contains("2001")
//                || DOB_matcher.toString().contains("2000")
//                || DOB_matcher.toString().contains("1999")) {
//            DOB_layout.startAnimation(shakeAnimation);
//            et_dob.startAnimation(shakeAnimation);
//            Line_DOB.startAnimation(shakeAnimation);
//            et_dob.setError("Your are not 18 years old.");
//        } else {
//            et_dob.setError(null);
//        }

        if (getDOB.toString().contains("Pick your date of birth"))
        {
            DOB_LinearLayout.startAnimation(shakeAnimation);
            btnDOB.startAnimation(shakeAnimation);
            Line_DOB.startAnimation(shakeAnimation);
            Toast.makeText(getActivity(),"Pick you DOB",Toast.LENGTH_SHORT).show();
        }

        // Check if email id is valid or not
        if (getEmailId.isEmpty()) {
            Email_layout.startAnimation(shakeAnimation);
            et_Email.startAnimation(shakeAnimation);
            Line_Email.startAnimation(shakeAnimation);
            et_Email.setError("Enter your email address.");
        } else if (!m.find()) {
            Email_layout.startAnimation(shakeAnimation);
            et_Email.startAnimation(shakeAnimation);
            Line_Email.startAnimation(shakeAnimation);
            et_Email.setError("Enter a valid email address.");
        } else if (getEmailId.contains(" ")) {
            Email_layout.startAnimation(shakeAnimation);
            et_Email.startAnimation(shakeAnimation);
            Line_Email.startAnimation(shakeAnimation);
            et_Email.setError("Space are not allowed.");
        } else {
            et_Email.setError(null);
        }


        // Check if Mobile Number is valid or not
        if (getMobileNumber.isEmpty()) {
            Mobile_layout.startAnimation(shakeAnimation);
            et_mobileNumber.startAnimation(shakeAnimation);
            Line_Mobile.startAnimation(shakeAnimation);
            et_mobileNumber.setError("Enter Mobile Number.");
        } else if (getMobileNumber.contains(" ")) {
            Mobile_layout.startAnimation(shakeAnimation);
            et_mobileNumber.startAnimation(shakeAnimation);
            Line_Mobile.startAnimation(shakeAnimation);
            et_mobileNumber.setError("Space are not allowed.");
        } else if (!getMobileNumber.startsWith("03")) {
            Mobile_layout.startAnimation(shakeAnimation);
            et_mobileNumber.startAnimation(shakeAnimation);
            Line_Mobile.startAnimation(shakeAnimation);
            et_mobileNumber.setError("Enter a valid mobile number start with 03.");
        } else if (getMobileNumber.length() <= 10 || getMobileNumber.length() >= 12) {
            if ((countMobileNumber > 2) &&
                    (getMobileNumber.length() <= 10) || getMobileNumber.length() >= 12) {
                Mobile_layout.startAnimation(shakeAnimation);
                et_mobileNumber.startAnimation(shakeAnimation);
                Line_Mobile.startAnimation(shakeAnimation);
                et_mobileNumber.setError("Enter 11 digits phone number.");
            } else {
                Mobile_layout.startAnimation(shakeAnimation);
                et_mobileNumber.startAnimation(shakeAnimation);
                Line_Mobile.startAnimation(shakeAnimation);
                et_mobileNumber.setError("Enter valid phone number.");
            }
            countMobileNumber++;
        } else {
            et_mobileNumber.setError(null);
        }

        // Check if CNIC is valid or not
        if (getCNIC.isEmpty()) {
            CNIC_layout.startAnimation(shakeAnimation);
            et_CNIC.startAnimation(shakeAnimation);
            Line_CNIC.startAnimation(shakeAnimation);
            et_CNIC.setError("Enter your CNIC number.");
        } else if (getCNIC.contains(" ")) {
            CNIC_layout.startAnimation(shakeAnimation);
            et_CNIC.startAnimation(shakeAnimation);
            Line_CNIC.startAnimation(shakeAnimation);
            et_CNIC.setError("Space are not allowed.");
        } else if (!getCNIC.startsWith("3")) {
            CNIC_layout.startAnimation(shakeAnimation);
            et_CNIC.startAnimation(shakeAnimation);
            Line_CNIC.startAnimation(shakeAnimation);
            et_CNIC.setError("First digit should be 3.");
        } else if (getCNIC.length() <= 12 || getCNIC.length() >= 14) {
            if ((countCNIC > 3) &&
                    (getCNIC.length() <= 12) || getCNIC.length() >= 14) {
                CNIC_layout.startAnimation(shakeAnimation);
                et_CNIC.startAnimation(shakeAnimation);
                Line_CNIC.startAnimation(shakeAnimation);
                et_CNIC.setError("Enter 13 digits CNIC number.");
            } else {
                CNIC_layout.startAnimation(shakeAnimation);
                et_CNIC.startAnimation(shakeAnimation);
                Line_CNIC.startAnimation(shakeAnimation);
                et_CNIC.setError("Enter valid CNIC number.");
            }
            countCNIC++;
        } else {
            et_CNIC.setError(null);
        }

        // Check if Telephone Number is valid or not
        if (getTelephone.isEmpty()) {
            countTelephoneNumber = 0;
        } else if (getTelephone.contains(" ")) {
            Telephone_layout.startAnimation(shakeAnimation);
            et_Telephone.startAnimation(shakeAnimation);
            Line_Telephone.startAnimation(shakeAnimation);
            et_Telephone.setError("Space are not allowed.");
        } else if (!getTelephone.startsWith("042")) {
            Telephone_layout.startAnimation(shakeAnimation);
            et_Telephone.startAnimation(shakeAnimation);
            Line_Telephone.startAnimation(shakeAnimation);
            et_Telephone.setError("Enter valid telephone number start with 042.");
        } else if ((getTelephone.length() <= 10)
                || (getTelephone.length() >= 12)) {
            if ((countTelephoneNumber > 2) &&
                    (getTelephone.length() <= 10 || getTelephone.length() >= 12)) {
                Telephone_layout.startAnimation(shakeAnimation);
                et_Telephone.startAnimation(shakeAnimation);
                Line_Telephone.startAnimation(shakeAnimation);
                et_Telephone.setError("Enter 11 digits telephone number.");
            } else {
                Telephone_layout.startAnimation(shakeAnimation);
                et_Telephone.startAnimation(shakeAnimation);
                Line_Telephone.startAnimation(shakeAnimation);
                et_Telephone.setError("Enter valid telephone number.");
            }
            countTelephoneNumber++;
        } else {
            et_Telephone.setError(null);
        }

        if (checkBox_cricket.isChecked()) {
            CheckBoxValues.add("Cricket");
        } else if (!checkBox_cricket.isChecked()) {
            CheckBoxValues.remove("Cricket");
        }
        if (checkBox_collecting.isChecked()) {
            CheckBoxValues.add("Collecting");
        } else if (!checkBox_collecting.isChecked()) {
            CheckBoxValues.remove("Collecting");
        }
        if (checkBox_reading.isChecked()) {
            CheckBoxValues.add("Reading");
        } else if (!checkBox_reading.isChecked()) {
            CheckBoxValues.remove("Reading");
        }


        // Check if Address is valid or not
        if (getAddress.isEmpty()) {
            Address_layout.startAnimation(shakeAnimation);
            et_Address.startAnimation(shakeAnimation);
            Line_Address.startAnimation(shakeAnimation);
            et_Address.setError("Enter your address.");
        } else if (getAddress.length() >= 50) {
            Address_layout.startAnimation(shakeAnimation);
            et_Address.startAnimation(shakeAnimation);
            Line_Address.startAnimation(shakeAnimation);
            et_Address.setError("Only 50 characters allow.");
        } else {
            et_Address.setError(null);
        }

        // Check About length
        if (getAbout.length() >= 100) {
            About_layout.startAnimation(shakeAnimation);
            et_About.startAnimation(shakeAnimation);
            Line_About.startAnimation(shakeAnimation);
            et_About.setError("Only 100 characters allow.");
        } else {
            et_About.setError(null);
        }

        if (male.isChecked()) {
            strGender = "male";
        } else if (female.isChecked()) {
            strGender = "Female";
        }

        // Check if password should be greater than 3
        if (getPassword.isEmpty()) {
            Password_layout.startAnimation(shakeAnimation);
            et_password.startAnimation(shakeAnimation);
            Line_Password.startAnimation(shakeAnimation);
            et_password.setError("Enter your password.");
        } else if (getPassword.length() <= 4) {
            Password_layout.startAnimation(shakeAnimation);
            et_password.startAnimation(shakeAnimation);
            Line_Password.startAnimation(shakeAnimation);
            et_password.setError("Password length should be greater than 4.");
        } else {
            et_password.setError(null);
        }

        // Check if both password should be equal
        if (!getConfirmPassword.equals(getPassword)) {
            ConfirmPasswoed_layout.startAnimation(shakeAnimation);
            et_ConfirmPassword.startAnimation(shakeAnimation);
            Line_ConfirmPassword.startAnimation(shakeAnimation);
            et_ConfirmPassword.setError("Both paassword does not match");
        } else {
            et_ConfirmPassword.setError(null);
        }

        // Make sure user should check Terms and Conditions checkbox
        if (!et_Fname.getText().toString().isEmpty() &&
                !et_Lname.getText().toString().isEmpty() &&
                !et_Email.getText().toString().isEmpty() &&
                !et_Address.getText().toString().isEmpty() &&
                !et_CNIC.getText().toString().isEmpty() &&
                !tvDOB.getText().toString().contains("Pick your date of birth") &&
                (male.isChecked() || female.isChecked()) &&
                !et_password.getText().toString().isEmpty() &&
                getConfirmPassword.equals(getPassword) &&
                !et_mobileNumber.getText().toString().isEmpty() &&
                !terms_conditions.isChecked()) {
            terms_conditions.startAnimation(shakeAnimation);
            TastyToast.makeText(getActivity(), "Terms and conditions not checked.",
                    TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
        }

        // Else do signup or do your stuff
        if (!et_Fname.getText().toString().isEmpty() &&
                !et_Lname.getText().toString().isEmpty() &&
                !et_Email.getText().toString().isEmpty() &&
                !et_CNIC.getText().toString().isEmpty() &&
                !tvDOB.getText().toString().contains("Pick your date of birth") &&
                (male.isChecked() || female.isChecked()) &&
                !et_Address.getText().toString().isEmpty() &&
                !et_password.getText().toString().isEmpty() &&
                !et_ConfirmPassword.getText().toString().isEmpty() &&
                et_ConfirmPassword.getText().toString().equals(getPassword) &&
                !et_mobileNumber.getText().toString().isEmpty() &&
                terms_conditions.isChecked()) {
            TastyToast.makeText(getActivity(), "Do SignUp.",
                    TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

//            progressDialog.setMessage("Registering user...");
//            progressDialog.show();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                    Paths.URL_SIGNUP,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            progressDialog.dismiss();
//                            try {
//                                JSONObject jsonObject = new JSONObject(response);
//                                if (jsonObject.getString("error") == "false") {
//                                    TastyToast.makeText(getActivity(), jsonObject.getString("message")
//                                            , Toast.LENGTH_LONG, TastyToast.SUCCESS).show();
//                                    fragmentManager
//                                            .beginTransaction()
//                                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//                                            .replace(R.id.frameContainer, new Login_Fragment(),
//                                                    Utils.Login_Fragment).commit();
//                                } else if (jsonObject.getString("error") == "true") {
//                                    TastyToast.makeText(getActivity(), jsonObject.getString("message")
//                                            , Toast.LENGTH_LONG, TastyToast.ERROR).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            progressDialog.hide();
//                            TastyToast.makeText(getActivity(), error.getMessage()
//                                    , Toast.LENGTH_SHORT, TastyToast.ERROR).show();
//                        }
//                    }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("first_name", getFirstName);
//                    params.put("last_name", getLastName);
//                    params.put("email", getEmailId);
//                    params.put("mobile_number", getMobileNumber);
//                    params.put("cnic", getCNIC);
////                    params.put("date_of_birth", getDOB);
//                    params.put("telephone_number", getTelephone);
//                    params.put("geneder", strGender);
//                    params.put("interests", CheckBoxValues.toString());
//                    params.put("password", getConfirmPassword);
//                    params.put("address", getAddress);
//                    params.put("about", getAbout);
//                    return params;
//                }
//            };
//            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }

    // For Date of Birth
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Using current date as start Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Get DatePicker Dialog
            return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            dateTime.set(mYear, monthOfYear, dayOfMonth);
            long selectDateInMilliSeconds = dateTime.getTimeInMillis();

            Calendar currentDate = Calendar.getInstance();
            long currentDateInMilliSeconds = currentDate.getTimeInMillis();

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String strDt = simpleDate.format(dateTime.getTime());

            if (selectDateInMilliSeconds > currentDateInMilliSeconds) {
                Toast.makeText(getActivity(), "Your birthday date must come before taday's date", Toast.LENGTH_LONG).show();
                DOB_LinearLayout.startAnimation(shakeAnimation);
                tvDOB.startAnimation(shakeAnimation);
                Line_DOB.startAnimation(shakeAnimation);
                return;
            }

            long diffDate = currentDateInMilliSeconds - selectDateInMilliSeconds;
            Calendar yourAge = Calendar.getInstance();
            yourAge.setTimeInMillis(diffDate);

            long returnedYear = yourAge.get(Calendar.YEAR) - 1970;

            if (returnedYear < allowedDOB) {
                Toast.makeText(getActivity(), "Sorry!!! You are not allowed because you are " + returnedYear + " years old", Toast.LENGTH_LONG).show();
                tvDOB.setText("Pick your date of birth");
                DOB_LinearLayout.startAnimation(shakeAnimation);
                tvDOB.startAnimation(shakeAnimation);
                Line_DOB.startAnimation(shakeAnimation);
                return;
            } else {
                // move to another activity page
                Toast.makeText(getActivity(), "You are allowed to use this app \nYour Age is: " + returnedYear, Toast.LENGTH_LONG).show();
                tvDOB.setText("Your DOB is: " + strDt);
            }
        }
    }
}