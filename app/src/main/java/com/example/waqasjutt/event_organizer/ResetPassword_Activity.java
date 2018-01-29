package com.example.waqasjutt.event_organizer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResetPassword_Activity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.et_NewPassword)
    EditText et_NewPassword;
    @Bind(R.id.et_ConfirmNewPassword)
    EditText et_ConfirmNewPassword;
    //    private EditText et_NewPassword, et_ConfirmNewPassword;
    private Button btnSave;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    private View view;
    private TextView mTitleTextView;
    private ActionBar mActionBar;
    private Context context;
    private static FragmentManager fragmentManager;

    private LinearLayout ResetPassword_layout;
    private TextInputLayout NewPassword_layout, ConfirmNewPassword_layout;
    private View Line_NewPassword, Line_ConfirmNewPassword;
    protected static Animation shakeAnimation;
    private String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("temp_username", Context.MODE_PRIVATE);
        strEmail = sharedPreferences.getString("email", null);

        fragmentManager = getSupportFragmentManager();

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(ResetPassword_Activity.this,
                R.anim.shake);

        //For go back to previous fragment
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // On close icon click finish activity
        findViewById(R.id.close_activityReset).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        finish();
                    }
                });

        initComponents();
        SetListner();
    }

    private void initComponents() {
        getSupportActionBar().setTitle("Change Password");
        et_NewPassword = (EditText) findViewById(R.id.et_NewPassword);
        et_ConfirmNewPassword = (EditText) findViewById(R.id.et_ConfirmNewPassword);
        btnSave = (Button) findViewById(R.id.btnNewPassword);

        NewPassword_layout = (TextInputLayout) findViewById(R.id.NewPassword_layout);
        Line_NewPassword = (View) findViewById(R.id.Line_NewPassword);
        ConfirmNewPassword_layout = (TextInputLayout) findViewById(R.id.ConfirNewPassword_layout);
        Line_ConfirmNewPassword = (View) findViewById(R.id.Line_ConfirmNewPassword);
        ResetPassword_layout = (LinearLayout) findViewById(R.id.ResetPassword_layout);
    }

    private void SetListner() {
        btnSave.setOnClickListener(this);
    }

    private void CheckValidation() {

        final String getPassword = et_NewPassword.getText().toString();
        final String getConfirmPassword = et_ConfirmNewPassword.getText().toString();

        if (getPassword.isEmpty() && getConfirmPassword.isEmpty()) {
            ResetPassword_layout.startAnimation(shakeAnimation);
            TastyToast.makeText(ResetPassword_Activity.this
                    , "Required fields are missing.", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
        } else if (getPassword.isEmpty()) {
            et_NewPassword.startAnimation(shakeAnimation);
            Line_NewPassword.startAnimation(shakeAnimation);
            NewPassword_layout.startAnimation(shakeAnimation);
            et_NewPassword.setError("Enter your new password");
        } else if (getPassword.length() <= 4) {
            et_NewPassword.startAnimation(shakeAnimation);
            Line_NewPassword.startAnimation(shakeAnimation);
            NewPassword_layout.startAnimation(shakeAnimation);
            et_NewPassword.setError("Length should be greater than 4");
        } else if (getPassword.contains(" ")) {
            et_NewPassword.startAnimation(shakeAnimation);
            Line_NewPassword.startAnimation(shakeAnimation);
            NewPassword_layout.startAnimation(shakeAnimation);
            et_NewPassword.setError("Space are not allowed");
        } else if (getConfirmPassword.isEmpty()) {
            et_ConfirmNewPassword.startAnimation(shakeAnimation);
            ConfirmNewPassword_layout.startAnimation(shakeAnimation);
            Line_ConfirmNewPassword.startAnimation(shakeAnimation);
            et_ConfirmNewPassword.setError("Enter your confirm password");
        } else if (!getConfirmPassword.equals(getPassword)) {
            et_ConfirmNewPassword.startAnimation(shakeAnimation);
            ConfirmNewPassword_layout.startAnimation(shakeAnimation);
            Line_ConfirmNewPassword.startAnimation(shakeAnimation);
            et_ConfirmNewPassword.setError("Both password does not match");
        } else {
            et_NewPassword.setError(null);
            et_ConfirmNewPassword.setError(null);
            progressDialog.setMessage("Updating Password...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Paths.URL_UPDATE_PASSWORD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("error") == "false") {
                                    TastyToast.makeText(ResetPassword_Activity.this, jsonObject.getString("message")
                                            , Toast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                    startActivity(new Intent(ResetPassword_Activity.this
                                            , MainActivity.class));
                                    finishAffinity();

                                } else if (jsonObject.getString("error") == "true") {
                                    TastyToast.makeText(ResetPassword_Activity.this, jsonObject.getString("message")
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
                            TastyToast.makeText(ResetPassword_Activity.this, "Server is not responding..."
                                    , Toast.LENGTH_LONG, TastyToast.ERROR).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", strEmail);
                    params.put("password", getConfirmPassword);
                    return params;
                }
            };
            RequestHandler.getInstance(ResetPassword_Activity.this).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewPassword:
                CheckValidation();
                break;
        }
    }
}