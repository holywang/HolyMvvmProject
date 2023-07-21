package com.mvvm.holyandroid.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.mvvm.core.activity.BaseActivity;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.databinding.ActivityThirdPartyLoginBinding;
import com.mvvm.holyandroid.dialog.ThirdPartyDialog;
import com.mvvm.holyandroid.utils.LoginUtils;
import com.mvvm.holyandroid.viewmodel.ThirdPartyLoginViewModel;

public class ThirdPartyLoginActivity extends BaseActivity {

    public static final String TAG = "ThirdPartyLoginActivity";
    ActivityThirdPartyLoginBinding binding;
    ThirdPartyLoginViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartyLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ThirdPartyLoginViewModel.class);
        model.getData().observe(this, data -> {

        });
        binding.showThirdPartyDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_third_party_dialog){
            showThirdPartyDialog();
        }
    }

    private void showThirdPartyDialog() {
        ThirdPartyDialog loginDialog = new ThirdPartyDialog(this);
        loginDialog.setOwnerActivity(this);
        loginDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ThirdPartyDialog.GOOGLE_LOGIN:
                try {
                    Log.e(TAG, "onActivityResult: "+ data);
                    SignInCredential credential = LoginUtils.oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.e(TAG, "Got ID token. token is "+idToken);
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.e(TAG, "Got password.password is "+password);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
                break;
        }
    }
}