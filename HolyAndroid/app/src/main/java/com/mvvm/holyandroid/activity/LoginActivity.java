package com.mvvm.holyandroid.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mvvm.core.activity.BaseActivity;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.databinding.ActivityLoginBinding;
import com.mvvm.holyandroid.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity {

    public static final int REQ_ONE_TAP = 0001;
    private static final int REQUEST_CODE_GIS_SAVE_PASSWORD = 0002;
    private ActivityLoginBinding binding;
    private LoginViewModel model;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(LoginViewModel.class);
        model.getData().observe(this, data -> {

        });
        binding.loginBtn.setOnClickListener(this);

        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        Log.e("SUCCESS", "SUCCESS");
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("SUCCESS", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ERROR", e.getLocalizedMessage());
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            model.save("", "", this,result-> {
                try {
                    startIntentSenderForResult(
                            result.getPendingIntent().getIntentSender(),
                            REQUEST_CODE_GIS_SAVE_PASSWORD,
                            /* fillInIntent= */ null,
                            /* flagsMask= */ 0,
                            /* flagsValue= */ 0,
                            /* extraFlags= */ 0,
                            /* options= */ null);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (v.getId() == R.id.sign_up_btn) {

        } else if (v.getId() == R.id.more_help_btn) {

        } else if (v.getId() == R.id.forget_pwd_btn) {

        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    if (idToken != null) {
                        Log.e("ID", "Got ID token.");
                    } else if (password != null) {
                        Log.e("PWD", "Got password.");
                    }
                } catch (ApiException e) {
                    Log.e("ERROR", e.getLocalizedMessage());
                }
                break;
            case REQUEST_CODE_GIS_SAVE_PASSWORD:
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("SUCCESS", "password info was saved");
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("ERROR", "password saving was cancelled");
                }
                break;
        }
    }
}