package com.example.waqasjutt.event_organizer;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetPassword_Fragment extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private View view;
    @Bind(R.id.et_Email)
    EditText et_email;
    //    private EditText et_email;
    private Button btnnext;
    private ProgressDialog progressDialog;
    private String strMobile_Number;
    private TextInputLayout Email_layout;
    private View Line_Email;
    private Animation shakeAnimation;

    public ForgetPassword_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.forget_password_fragment, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        ButterKnife.bind(getActivity());

        //For Go Back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.GONE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).setActionBarTitle(" Forget Password");

        et_email = (EditText) view.findViewById(R.id.editforgotuser);
        btnnext = (Button) view.findViewById(R.id.btnfornext);
        Email_layout = (TextInputLayout) view.findViewById(R.id.Email_layout);
        Line_Email = (View) view.findViewById(R.id.Line_Email);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");

        final String getEmailId = et_email.getText().toString();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if email id is valid or not
                if (et_email.getText().toString().trim().equals("")) {
                    Email_layout.startAnimation(shakeAnimation);
                    et_email.startAnimation(shakeAnimation);
                    Line_Email.startAnimation(shakeAnimation);
                    et_email.setError("Enter your email address.");
                } else if (getEmailId.contains(" ")) {
                    Email_layout.startAnimation(shakeAnimation);
                    et_email.startAnimation(shakeAnimation);
                    Line_Email.startAnimation(shakeAnimation);
                    et_email.setError("Space are not allowed.");
                } else if (!et_email.getText().toString().trim().equals("")) {
                    RegisteredUsernameAsync registeredUsernameAsync = new RegisteredUsernameAsync(getActivity(),
                            et_email.getText().toString());
                    registeredUsernameAsync.execute();
                } else {
                    et_email.setError(null);
                }

//                if (et_email.getText().toString().trim().equals("")) {
//                    Toast.makeText(getActivity(), "Email should not empty", Toast.LENGTH_SHORT).show();
//                } else if (!et_email.getText().toString().isEmpty()) {
//                    RegisteredUsernameAsync registeredUsernameAsync = new RegisteredUsernameAsync(getActivity(),
//                            et_email.getText().toString());
//                    registeredUsernameAsync.execute();
//                }
            }
        });

        return view;
    }

    public class RegisteredUsernameAsync extends AsyncTask<Void, Void, String> {

        private Activity activity;
        private String jsonresult;
        private String email;

        public RegisteredUsernameAsync(Activity activity, String username) {
            this.activity = activity;
            this.email = username;
        }

        @Override
        protected String doInBackground(Void... voids) {

            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("email", email)
                    .build();

            Request request = new Request.Builder()
                    .url(Paths.URL_FORGET_PASSWORD)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                jsonresult = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonresult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(s);
                if (jsonObject.getString("error") == "true") {

                    strMobile_Number = jsonObject.getString("mobile_number");

                    SharedPreferences sharedPreferences = activity.getSharedPreferences("temp_username", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email).commit();
                    editor.putString("mobile_number", strMobile_Number).commit();
                    Toast.makeText(activity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    fragmentTransaction =
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.left_out, R.anim.right_out)
                                    .replace(R.id.frameContainer,
                                            new GetMobileCode_Fragment());
                    fragmentTransaction
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(activity, "Email does not Exist", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}