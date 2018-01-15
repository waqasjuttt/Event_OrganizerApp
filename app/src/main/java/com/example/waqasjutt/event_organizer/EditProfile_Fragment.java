package com.example.waqasjutt.event_organizer;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

public class EditProfile_Fragment extends Fragment implements View.OnClickListener {

    //For DOB
    private DatePickerFragment datePickerFragment;
    private static Calendar dateTime = Calendar.getInstance();
    protected static int mYear;
    protected static int mMonth;
    protected static int mDay;
    private static final int allowedDOB = 18;
    protected static Button btnDOB;
    protected static TextView tvDOB;
    protected static String strDt;
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
    @Bind(R.id.et_Telephone)
    EditText et_Telephone;
    private RadioGroup radioGroup;
    private RadioButton male, female;
    //    private EditText et_Gender, female;
    private CheckBox checkBox_cricket, checkBox_collecting, checkBox_reading;
    @Bind(R.id.et_Address)
    EditText et_Address;
    @Bind(R.id.et_About)
    EditText et_About;

    MainActivity mainActivity = new MainActivity();

    protected static LinearLayout DOB_LinearLayout, EditProfile_layout;
    private TextInputLayout Gender_layout, Email_layout, FirstName_layout, LastName_layout, CNIC_layout, Mobile_layout, Telephone_layout, Address_layout, About_layout;
    private View Line_Email, Line_FirstName, Line_LastName, Line_Mobile, Line_CNIC, Line_Telephone, Line_Address, Line_About, Line_Gender;
    protected static View Line_DOB;

    private Set<String> CheckBoxValues;
    private ProgressDialog progressDialog;
    private Button btnEditProfile;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LinearLayout EditProfile_Layout;
    protected static Animation shakeAnimation;
    private String strGender;
    private int countMobileNumber = 1, countTelephoneNumber = 1, countCNIC = 1;
    private String strID;

    public EditProfile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        ButterKnife.bind(getActivity());

        //For Go Back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle("Edit Profile");

        initViews();
        UserData();
        setListeners();
        return view;
    }

    private void UserData() {
        strID = SharedPrefManager.getInstance(getActivity()).getUserID();
        et_Fname.setText(SharedPrefManager.getInstance(getActivity()).getUserFirstName());
        et_Lname.setText(SharedPrefManager.getInstance(getActivity()).getUserLastName());
        et_Email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());
        et_CNIC.setText(SharedPrefManager.getInstance(getActivity()).getUserCNIC());
        et_mobileNumber.setText(SharedPrefManager.getInstance(getActivity()).getUserMobile());
        tvDOB.setText(SharedPrefManager.getInstance(getActivity()).getUserDOB());
        et_Telephone.setText(SharedPrefManager.getInstance(getActivity()).getUserTelephone());
//        et_Gender.setText(SharedPrefManager.getInstance(getActivity()).getUserGender());
        et_Address.setText(SharedPrefManager.getInstance(getActivity()).getUserAddress());
        et_About.setText(SharedPrefManager.getInstance(getActivity()).getUserAbout());

        if (!SharedPrefManager.getInstance(getActivity()).getUserDOB().contains("Pick your date of birth")) {
            strDt = SharedPrefManager.getInstance(getActivity()).getUserDOB();
        }

        if (SharedPrefManager.getInstance(getActivity())
                .getUserGender().contains("male")) {
            checkBox_cricket.setChecked(true);
        }
        if (SharedPrefManager.getInstance(getActivity())
                .getUserGender().contains("female")) {
            checkBox_collecting.setChecked(true);
        }

        if (SharedPrefManager.getInstance(getActivity())
                .getUserInterest().contains("Cricket")) {
            checkBox_cricket.setChecked(true);
        }
        if (SharedPrefManager.getInstance(getActivity())
                .getUserInterest().contains("Collecting")) {
            checkBox_collecting.setChecked(true);
        }
        if (SharedPrefManager.getInstance(getActivity())
                .getUserInterest().contains("Reading")) {
            checkBox_reading.setChecked(true);
        }
    }

    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        EditProfile_Layout = (LinearLayout) view.findViewById(R.id.EditProfile_layout);
        progressDialog = new ProgressDialog(getActivity());
        et_Fname = (EditText) view.findViewById(R.id.et_Fname);
        et_Lname = (EditText) view.findViewById(R.id.et_Lname);
        et_Email = (EditText) view.findViewById(R.id.et_Email);
        et_CNIC = (EditText) view.findViewById(R.id.et_CNIC);
//        et_Gender = (EditText) view.findViewById(R.id.et_Gender);
        male = (RadioButton) view.findViewById(R.id.radioM);
        female = (RadioButton) view.findViewById(R.id.radioF);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGrp);
        checkBox_cricket = (CheckBox) view.findViewById(R.id.cb_cricketF);
        checkBox_collecting = (CheckBox) view.findViewById(R.id.cb_CollectingF);
        checkBox_reading = (CheckBox) view.findViewById(R.id.cb_ReadingF);
        et_mobileNumber = (EditText) view.findViewById(R.id.et_mobileNumber);
        et_Telephone = (EditText) view.findViewById(R.id.et_Telephone);
        et_Address = (EditText) view.findViewById(R.id.et_Address);
        et_About = (EditText) view.findViewById(R.id.et_About);
        btnDOB = (Button) view.findViewById(R.id.btn_select_dob);
        tvDOB = (TextView) view.findViewById(R.id.tv_Show_Date);
        btnEditProfile = (Button) view.findViewById(R.id.btnEditProfile);

        //Layout ids call
        FirstName_layout = (TextInputLayout) view.findViewById(R.id.FirstName_layout);
        LastName_layout = (TextInputLayout) view.findViewById(R.id.LastName_layout);
        Email_layout = (TextInputLayout) view.findViewById(R.id.Email_layout);
        Mobile_layout = (TextInputLayout) view.findViewById(R.id.Mobile_layout);
        CNIC_layout = (TextInputLayout) view.findViewById(R.id.CNIC_layout);
        DOB_LinearLayout = (LinearLayout) view.findViewById(R.id.DOB_Linearlylayout);
        Telephone_layout = (TextInputLayout) view.findViewById(R.id.Telephone_layout);
//        Gender_layout = (TextInputLayout) view.findViewById(R.id.Gender_layout);
        Address_layout = (TextInputLayout) view.findViewById(R.id.Address_layout);
        About_layout = (TextInputLayout) view.findViewById(R.id.About_layout);
        EditProfile_layout = (LinearLayout) view.findViewById(R.id.EditProfile_layout);

        //View Lines call
        Line_FirstName = (View) view.findViewById(R.id.Line_Fname);
        Line_LastName = (View) view.findViewById(R.id.Line_Lname);
        Line_Email = (View) view.findViewById(R.id.Line_email);
        Line_Mobile = (View) view.findViewById(R.id.Line_mobile);
        Line_Telephone = (View) view.findViewById(R.id.Line_Telephone);
        Line_CNIC = (View) view.findViewById(R.id.Line_CNIC);
        Line_DOB = (View) view.findViewById(R.id.Line_DOB);
        Line_Gender = (View) view.findViewById(R.id.Line_Gender);
        Line_Address = (View) view.findViewById(R.id.Line_Address);
        Line_About = (View) view.findViewById(R.id.Line_About);

        CheckBoxValues = new HashSet<>();

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);
    }

    // Set Listeners
    private void setListeners() {
        //For Profile Edited
        btnEditProfile.setOnClickListener(this);
//        //For Date of Birth
        btnDOB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_select_dob:
                datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getActivity().getSupportFragmentManager(), "Select Your Birthday");
                break;

            case R.id.btnEditProfile:
                // Call checkValidation method
                checkValidation();
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
        final String getMobileNumber = et_mobileNumber.getText().toString();
        final String getTelephone = et_Telephone.getText().toString();
        final String getAbout = et_About.getText().toString();
        final String getAddress = et_Address.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.Email_Format);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if ((getFirstName.isEmpty() || getFirstName.equals("") || getFirstName.length() == 0)
                && (getLastName.isEmpty() || getLastName.equals("") || getLastName.length() == 0)
                && (getEmailId.isEmpty() || getEmailId.equals("") || getEmailId.length() == 0)
                && (getMobileNumber.isEmpty() || getMobileNumber.equals("") || getMobileNumber.length() == 0)
                && (tvDOB.getText().toString().contains("Pick your date of birth"))
                && (getAddress.isEmpty() || getAddress.equals("") || getAddress.length() == 0)
                && (getCNIC.isEmpty() || getCNIC.equals("") || getCNIC.length() == 0)
                && (!male.isChecked() && !female.isChecked())) {
            EditProfile_layout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Required fields are missing.");
            TastyToast.makeText(getActivity(), "Try again",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
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
        if (tvDOB.getText().toString().contains("Pick your date of birth")) {
            DOB_LinearLayout.startAnimation(shakeAnimation);
            btnDOB.startAnimation(shakeAnimation);
            Line_DOB.startAnimation(shakeAnimation);
            Toast.makeText(getActivity(), "Pick you DOB", Toast.LENGTH_SHORT).show();
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
        } else if (getAddress.length() >= 70) {
            Address_layout.startAnimation(shakeAnimation);
            et_Address.startAnimation(shakeAnimation);
            Line_Address.startAnimation(shakeAnimation);
            et_Address.setError("Only 70 characters allow.");
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


        // Else do signup or do your stuff
        if ((!et_Fname.getText().toString().isEmpty()
                && !(getFirstName.length() >= 15)) &&
                (!(getLastName.length() >= 15)
                        && !et_Lname.getText().toString().isEmpty()) &&
                (!et_Email.getText().toString().isEmpty()
                        && !getEmailId.contains(" ")) &&
                (!et_CNIC.getText().toString().isEmpty()
                        && !et_CNIC.getText().toString().contains(" ")
                        && getCNIC.startsWith("3")
                        && getCNIC.length() == 13) &&
                !tvDOB.getText().toString().contains("Pick your date of birth") &&
                (male.isChecked() || female.isChecked()) &&
                (!et_Address.getText().toString().isEmpty()
                        && getAddress.length() <= 70) &&
                (!et_mobileNumber.getText().toString().isEmpty()
                        && !getMobileNumber.contains(" ")
                        && getMobileNumber.startsWith("03")
                        && getMobileNumber.length() == 11) &&
                (getTelephone.toString().isEmpty()
                        || (!getTelephone.toString().isEmpty()
                        && getTelephone.startsWith("042")
                        && !getTelephone.contains(" ")
                        && getTelephone.length() == 11))) {

//            TastyToast.makeText(getActivity(), "Updated Successfully.",
//                    TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

            progressDialog.setMessage("User updating...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Paths.URL_EDITPROFILE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("error") == "false") {
                                    TastyToast.makeText(getActivity(), jsonObject.getString("message")
                                            , Toast.LENGTH_LONG, TastyToast.SUCCESS).show();
                                    fragmentManager
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                            .replace(R.id.frameContainer, new Login_Fragment(),
                                                    Utils.HomePage_Fragment).commit();
                                    fragmentManager
                                            .popBackStack(null, FragmentManager
                                                    .POP_BACK_STACK_INCLUSIVE);
                                } else if (jsonObject.getString("error") == "true") {
                                    TastyToast.makeText(getActivity(), jsonObject.getString("message")
                                            , Toast.LENGTH_LONG, TastyToast.ERROR).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            TastyToast.makeText(getActivity(), error.getMessage()
                                    , Toast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", strID);
                    params.put("first_name", getFirstName);
                    params.put("last_name", getLastName);
                    params.put("email", getEmailId);
                    params.put("mobile_number", getMobileNumber);
                    params.put("cnic", getCNIC);
                    params.put("date_of_birth", strDt);
                    params.put("telephone_number", getTelephone);
                    params.put("geneder", strGender);
                    params.put("interests", CheckBoxValues.toString());
                    params.put("address", getAddress);
                    params.put("about", getAbout);
                    return params;
                }
            };
            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
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

            SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            strDt = simpleDate.format(dateTime.getTime());

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
//                Toast.makeText(getActivity(), "You are allowed to use this app \nYour Age is: " + returnedYear, Toast.LENGTH_LONG).show();
                tvDOB.setText("Your DOB is: " + strDt);
            }
        }
    }
}
