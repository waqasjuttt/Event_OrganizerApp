package com.example.waqasjutt.event_organizer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class GetMobileCode_Fragment extends Fragment {

    private EditText editphone, editcode;
    private Button btncode, btnnext;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private String mVerificationId;
    private String strEmail, strMobile_Number, phonenumber;
    private View view;
    private SharedPreferences sharedPreferences;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthlistener);
    }

    public GetMobileCode_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.get_mobile_code_fragment, container, false);
        editphone = (EditText) view.findViewById(R.id.editphonenumber);
        editcode = (EditText) view.findViewById(R.id.editcodenumber);
        btnnext = (Button) view.findViewById(R.id.btnnext);
        btncode = (Button) view.findViewById(R.id.btncode);

        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getActivity().getSharedPreferences("temp_username", Context.MODE_PRIVATE);
        strEmail = sharedPreferences.getString("email", null);
        strMobile_Number = sharedPreferences.getString("mobile_number", null);

        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };

        btncode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                phonenumber = editphone.getText().toString();
                if (phonenumber.trim().equals("") || phonenumber.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (phonenumber.equals(strMobile_Number)) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phonenumber, 60, TimeUnit.SECONDS, getActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    Toast.makeText(getActivity(), "OnVerification Failed" +
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    // super.onCodeSent(s, forceResendingToken);
                                    mVerificationId = s;
                                }

                                @Override
                                public void onCodeAutoRetrievalTimeOut(String s) {
                                    // super.onCodeAutoRetrievalTimeOut(s);
                                    Toast.makeText(getActivity(), "On Code AutoRetrival Time out "
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }else if (phonenumber.length() > 11 || phonenumber.length() < 11) {
                        Toast.makeText(getActivity(), "Length should be equal to 11 digits"
                                , Toast.LENGTH_SHORT).show();
                } else if (phonenumber != strMobile_Number || !phonenumber.contains(strMobile_Number)) {
                    Toast.makeText(getActivity(), "Please Enter Valid Mobile Number "
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editcode.getText().toString();
                if (code.trim().equals("")) {
                    Toast.makeText(getActivity(), " Please enter verified code "
                            , Toast.LENGTH_LONG).show();
                } else {
                    signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));
                }
            }
        });

        return view;
    }

    public void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Password Reset Fragment ", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getActivity(), ResetPassword_Activity.class));
                        } else {
                            Toast.makeText(getActivity(), "Failed To Password Reset Fragment: " + task.getException().getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthlistener);
    }
}