package com.example.waqasjutt.event_organizer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Login_Fragment extends Fragment implements View.OnClickListener {

    private Paths URL = new Paths();
    private View line_password;
    private View line_email;
    private TextInputLayout textInputLayout_email;
    private TextInputLayout textInputLayout_password;
    private static View view;
    private static TextView tvCreatNewAccount;
    private static TextView forgot_password;
    @Bind(R.id.login_emailid)
    EditText emailid;
    @Bind(R.id.login_password)
    EditText password;
    private ProgressDialog progressDialog;
    private static Button btnLogin;
    private static CheckBox show_hide_password;
    private static LinearLayout linearLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    //    private SharedPreferences sharedPreferences;
    private FragmentTransaction fragmentTransaction;
    private SharedPreferences sharedPreferences;
    Intent browserIntent = new Intent(Intent.ACTION_VIEW);

    public Login_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(getActivity());

//        //For Disable ActionBar Back Button
//        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
//            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
//        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle(" Event Organizer");

        initViews();
        setListeners();

        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            new MainActivity().finish();
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                    .replace(R.id.frameContainer, new HomePage_Fragment(),
                            Utils.HomePage_Fragment).commit();
        }

        line_email = (View) view.findViewById(R.id.line_email);
        line_password = (View) view.findViewById(R.id.line_password);
        textInputLayout_email = (TextInputLayout) view.findViewById(R.id.layout_email);
        textInputLayout_password = (TextInputLayout) view.findViewById(R.id.layout_password);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        btnLogin = (Button) view.findViewById(R.id.loginBtn);
        tvCreatNewAccount = (TextView) view.findViewById(R.id.createAccount);
        forgot_password = (TextView) view.findViewById(R.id.forgot_password);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        linearLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.color.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            show_hide_password.setTextColor(csl);
            tvCreatNewAccount.setTextColor(csl);
            forgot_password.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        btnLogin.setOnClickListener(this);
        tvCreatNewAccount.setOnClickListener(this);
        forgot_password.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText("Hide password");
                            // change checkbox text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText("Show password");
                            // change checkbox text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.createAccount:

//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//                Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
//                startActivity(browserChooserIntent );

//                browserIntent.setData(Uri.parse(Paths.URL_SIGNUP));
//                startActivity(browserIntent);

                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .setCustomAnimations(R.anim.left_out, R.anim.right_out)
                                .replace(R.id.frameContainer,
                                        new Signup_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.forgot_password:
//                browserIntent.setData(Uri.parse(Paths.URL_FORGET_PASSWORD));
//                startActivity(browserIntent);
                fragmentTransaction =
                        fragmentManager
                                .beginTransaction()
                                .setCustomAnimations(R.anim.left_out, R.anim.right_out)
                                .replace(R.id.frameContainer,
                                        new ForgetPassword_Fragment());
                fragmentTransaction
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmail = emailid.getText().toString();
        String getPassword = password.getText().toString();

        // Check patter for email id
        Pattern p = Pattern.compile(Utils.Email_Format);
        Matcher m = p.matcher(getEmail);

        // Check for both field is empty or not
        if ((getEmail.isEmpty() || getEmail.equals("") || getEmail.length() == 0)
                && (getPassword.isEmpty() || getPassword.equals("") || getPassword.length() == 0)) {
            linearLayout.startAnimation(shakeAnimation);

            new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
            TastyToast.makeText(getActivity(), "Try again.",
                    TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        }

        // Check if email id is valid or not
        if (getEmail.isEmpty()) {
            textInputLayout_email.startAnimation(shakeAnimation);
            emailid.startAnimation(shakeAnimation);
            line_email.startAnimation(shakeAnimation);
            emailid.setError("Enter your email address.");
        } else if (!m.find()) {
            textInputLayout_email.startAnimation(shakeAnimation);
            emailid.startAnimation(shakeAnimation);
            line_email.startAnimation(shakeAnimation);
            emailid.setError("Enter a valid email address.");
        } else if (getEmail.contains(" ")) {
            textInputLayout_email.startAnimation(shakeAnimation);
            emailid.startAnimation(shakeAnimation);
            line_email.startAnimation(shakeAnimation);
            emailid.setError("Space are not allowed.");
        } else {
            emailid.setError(null);
        }

        // Check if password length
        if (getPassword.isEmpty()) {
            password.startAnimation(shakeAnimation);
            textInputLayout_password.startAnimation(shakeAnimation);
            line_password.startAnimation(shakeAnimation);
            password.setError("Enter your password");
        } else if (getPassword.length() <= 4) {
            password.startAnimation(shakeAnimation);
            textInputLayout_password.startAnimation(shakeAnimation);
            line_password.startAnimation(shakeAnimation);
            password.setError("Password length should be greater than 4.");
        } else {
            password.setError(null);
        }

        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );

        // heck if email id or password is valid
        if ((!getEmail.isEmpty() &&
                !getPassword.isEmpty()) &&
                getPassword.length() > 4 &&
                EMAIL_ADDRESS_PATTERN.matcher(getEmail).matches()) {
//            TastyToast.makeText(getActivity(), "Login Successful.", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
//            fragmentTransaction =
//                    fragmentManager
//                            .beginTransaction()
//                            .replace(R.id.frameContainer,
//                                    new HomePage_Fragment());
//            fragmentTransaction
//                    .addToBackStack(null)
//                    .commit();

            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
//                                if (!obj.getBoolean("error")) {
//                                    SharedPrefManager.getInstance(getActivity())
//                                            .userLoginTest(
//                                                    obj.getInt("id"),
//                                                    obj.getString("first_name"),
//                                                    obj.getString("last_name"),
//                                                    obj.getString("email"),
//                                                    obj.getString("mobile_number"),
//                                                    obj.getString("cnic"),
//                                                    obj.getString("date_of_birth"),
//                                                    obj.getString("geneder"),
//                                                    obj.getString("interests"),
//                                                    obj.getString("address"),
//                                                    obj.getString("telephone_number"),
//                                                    obj.getString("about"),
//                                                    obj.getString("domain")
//                                            );
                                if (obj.getString("error") == "false") {
                                    SharedPrefManager.getInstance(getActivity())
                                            .userLoginTest(
                                                    obj.getString("id"),
                                                    obj.getString("first_name"),
                                                    obj.getString("last_name"),
                                                    obj.getString("email"),
                                                    obj.getString("mobile_number"),
                                                    obj.getString("cnic"),
                                                    obj.getString("date_of_birth"),
                                                    obj.getString("geneder"),
                                                    obj.getString("interests"),
                                                    obj.getString("address"),
                                                    obj.getString("telephone_number"),
                                                    obj.getString("about"),
                                                    obj.getString("domain")
                                            );

                                    TastyToast.makeText(getActivity(), "Login Successful.",
                                            TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                                    fragmentManager
                                            .beginTransaction()
                                            .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                            .replace(R.id.frameContainer, new HomePage_Fragment(),
                                                    Utils.HomePage_Fragment).commit();
                                    new MainActivity().finishAffinity();

                                } else if (obj.getString("error") == "true") {
                                    textInputLayout_email.startAnimation(shakeAnimation);
                                    emailid.startAnimation(shakeAnimation);
                                    line_email.startAnimation(shakeAnimation);

                                    textInputLayout_password.startAnimation(shakeAnimation);
                                    password.startAnimation(shakeAnimation);
                                    line_password.startAnimation(shakeAnimation);

                                    TastyToast.makeText(getActivity(),
                                            obj.getString("message"),
                                            Toast.LENGTH_SHORT,
                                            TastyToast.ERROR).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),
                                    "Server is not responding...",
                                    Toast.LENGTH_SHORT).show();
//                            TastyToast.makeText(getActivity(),
//                                    error.getMessage() + " Not responding...",
//                                    Toast.LENGTH_SHORT,
//                                    TastyToast.ERROR).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("email", emailid.getText().toString());
                    param.put("password", password.getText().toString());
                    param.put("domain", "customer");
                    return param;
                }
            };
            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }
}